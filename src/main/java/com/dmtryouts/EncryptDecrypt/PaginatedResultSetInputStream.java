package com.dmtryouts.EncryptDecrypt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is a wrapper for the ResultSet that contains the text_data from the SESSION_RESULT_VALUES table and supports pagination.
 * It extends <code>InputStream</code> in order to be passed to the XMLRPC parser.
 * Before actually going to the database, it processes the XML and XMLRPC header.
 * After reading the given number of rows it fetches the next rows from the table.
 * After all the rows from the table have been processed, it processes the XMLRPC footer.
 *
 * @author marius.ani
 */
public class PaginatedResultSetInputStream extends InputStream {
    /**
     * The session_result_id for which we read the session_result_values
     */
    private long sessionResultId;

    private ResultSet rs;

    /**
     * The number of rows to be read at once from the database
     */
    private int rows;

    /**
     * <code>InputStream</code> containing the text_data from all the rows read in the current step
     */
    private ByteArrayInputStream bais;

    /**
     * Flag that indicates if there are more rows to be read from the database.
     */
    private boolean hasMoreRows = true;

    /**
     * <code>true</code> when there are no more rows to be read and the parser is returning the XMLRPC footer
     */
    private boolean footerInitialized = false;

    private PreparedStatement stmt;

    /**
     * Holds the text_order value from which we will query the next rows.
     */
    private int currentPos = 0;

    private boolean initialized = false;
    private byte firstByte = ' ';

    public PaginatedResultSetInputStream(PreparedStatement stmt, long sessionResultId, int chunks) {
        this.rows = chunks;
        this.stmt = stmt;
        this.sessionResultId = sessionResultId;
    }

    /**
     * get first byte set during initialization.
     * call initialization if necessary
     *
     * @throws IOException
     */
    public byte getFirstByte() throws IOException {
        if (!initialized) {
            initialize();
        }
        return firstByte;
    }

    @Override
    public int read() throws IOException {
        if (!initialized) {
            initialize();
        }
        int b = bais.read();
        if (b == -1) { //no more data in the current inputstream
            bais.close();
            if (hasMoreRows) {
                System.out.println( "Reading next rows");
                byte[] buf = null;
                try {
                    reinitResultSet();
                    buf = readNextRows();
                } catch (Exception e) {
                    throw new IOException(e);
                }
                bais = new ByteArrayInputStream(buf);
                b = bais.read();
            } else {
                System.out.println("No more data to read. Done.");
            }
        }
        return b;
    }

    /**
     * get first set of rows and create input stream
     *
     * @throws IOException
     */
    private byte[] initialize() throws IOException {
        byte[] buf = null;
        if (!initialized) {
            System.out.println("Initializing. Read first rows");
            initialized = true;
            try {
                reinitResultSet();
                buf = readNextRows();
                firstByte = buf[0];
            } catch (Exception e) {
                throw new IOException(e);
            }
            bais = new ByteArrayInputStream(buf);
        }

        return buf;
    }

    /**
     * Re-executes the query in order to fetch the next rows
     *
     * @throws SQLException
     */
    private void reinitResultSet() throws SQLException {
        System.out.println("PaginatedResultSetInputStream.reinitResultSet currentPos = " + currentPos + "; maxPos = " + (currentPos + rows));
        if (rs != null) {
            rs.close();
            rs = null;
        }
        stmt.setLong(1, sessionResultId);
        stmt.setLong(2, currentPos);
        stmt.setLong(3, currentPos + rows);
//        stmt.setQueryTimeout(SQLHelper.getInstance().getTimeout());
//        stmt.setFetchSize(Configurator.FETCH_SIZE);

        //PerfContext.enterSQL(query);
        currentPos += rows;
        rs = stmt.executeQuery();
    }

    /**
     * Reads the next rows from the table and concatenates the read text_data values.
     * @return a byte array with all the read text_data contents
     * @throws Exception
     */
    private byte[] readNextRows() throws Exception {
        byte[] buf = new byte[rows * 4000];
        ByteArrayOutputStream baos = new ByteArrayOutputStream(rows * 4000);
        int i = 1;
        int destPos = 0;
        while (i <= rows && rs.next()) {
            byte[] currentChunk = rs.getString("text_data").getBytes("UTF-8");
            baos.write(currentChunk, 0, currentChunk.length);
            destPos += currentChunk.length;
            i++;
        }
        if (i <= rows) {
            hasMoreRows = false;
        }
        baos.close();
        return baos.toByteArray();
    }

    @Override
    public void close() throws IOException {
        System.out.println("PaginatedResultSetInputStream.close()");
        if (bais != null) {
            try {
                bais.close();
                bais = null;
            } catch (IOException e) {
            }
        }
//        SQLHelper.getInstance().cleanup(rs);
    }
}
