//package com.dmtryouts.EncryptDecrypt;
//
//import java.io.*;
//import java.lang.ref.SoftReference;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.zip.InflaterInputStream;
//
//public class Session {
//
//    private static final String PAGINATION_SQL = "select /* SessionCommandResultHelper.01 */ text_data from session_command_result_values where session_cmd_result_id = ? and text_order >= ? and text_order < ? order by text_order asc";
//    private static final Map<Serializable, SoftReference<Object>> sessionCommandResultsCache = new ConcurrentHashMap<>(500);
//
//    /**
//     * Return the data cached for a given collection item (SessionCommandResults)
//     * @param collectionItem  Object (SessionCommandResults)
//     *
//     * @return Object cached, or null if nothing cached
//     */
//    protected Object getCachedData(Object collectionItem) {
//        SoftReference<Object> softReference = sessionCommandResultsCache.get(getCollectionItemId(collectionItem));
//        if (softReference != null) {
//            return softReference.get();
//        }
//        return null;
//    }
//
//    /**
//     * Set cached data for a given collection item (SessionCommandResults)
//     * @param collectionItem  Object (SessionCommandResults)
//     *
//     */
//    protected void setCachedData(Object collectionItem, Object data){
//        sessionCommandResultsCache.put(getCollectionItemId(collectionItem), new SoftReference<>(data));
//    }
//
//    /**
//     * Read all bytes from an input stream and return as a byte array
//     * @param instream  input stream from which to read
//     *
//     * @return the bytes read from the input stream
//     */
//    protected byte[] readInputStream(InputStream instream) {
//        byte[] bytesFromStream = null;
//        try{
//            ByteArrayOutputStream outstream = new ByteArrayOutputStream();
//            byte[] readBytes = new byte[1024];
//            int bytesRead = 0;
//            while (bytesRead != -1){
//                bytesRead = instream.read(readBytes);
//                if (bytesRead != -1)
//                    outstream.write(readBytes, 0, bytesRead);
//            };
//            bytesFromStream = outstream.toByteArray();
//        } catch(IOException e){
//            e.printStackTrace();
//        }
//        finally{
//            try{
//                instream.close();
//            } catch(IOException e){
//                System.out.println("Unable to close input stream during getValueForKey processing");
//            }
//        }
//
//        return bytesFromStream;
//    }
//
//    /**
//     91      * Get the id of a specific SessionParams
//     92      * @param collectionItem  specific SessionParams
//     93      *
//     94      * @return id as Long
//     95      */
//     protected Long getCollectionItemId(Object collectionItem){
//        return ((SessionParams)collectionItem).getSessionParamId();
//     }
//
//    /**
//     * get paginated result set using pagination sql
//     *
//     * @param collectionItem    (eg SessionResultsHelper)
//     *
//     * @return the parsed result input stream
//     */
//    PaginatedResultSetInputStream getPaginatedResultSetInputStream(Object collectionItem) throws ReadException{
//
//        //our result set is "paginated", get the appropriate sql which is derived-class dependent
//        String query = PAGINATION_SQL;
//
//        //200 was the original pagination value used for compressed results. Since ALL results > 300 characters in length
//        //are now compressed, all multi-row values will be compressed. So, using original "compressed"
//        //value for paginated rowsets
//        int rows = 200;
//        System.out.println("Fetch " + rows + " rows at a time");
//        PreparedStatement stmt = null;
//
//        try{
//            Connection con = SQLHelper.getInstance().getDbConnection();
//            stmt = con.prepareStatement(query);
//        } catch(SQLException e){
//            //IF this happens, something is really really wrong
//            throw new RuntimeException(e);
//        }
//
//        return new PaginatedResultSetInputStream(stmt, getCollectionItemId(collectionItem), rows);
//    }
//
//
//    /**
//     * Parses the results for the given key
//     *
//     * NOTE: A significant change to compression/decompression was made as a result of CR 193417
//     * Session params and results as well as SessionCommand params and results are written by
//     * way jobs via spin. Spin decides whether to compress a given value or not by looking at the
//     * length of the xml-ified object to be written. If the object is written "uncompressed", the written
//     * value should look like xml (ie a string starting with '<'). If it is compressed, it will never
//     * begin with that character. Therefore results are "decompressed" if and only if they do not begin
//     * with '<'
//     *
//     * @param keyName
//     *
//     * @return the parsed results if the key exists, otherwise <code>null</code>
//     *
//     * <value><long>10240</long></value>
//     */
//    public Object getValueForKey(String keyName) throws ReadException {
//        //get the collection we're processing (eg session results for SessionResultHelper)
//        Collection<Object> c = getCollection();
//
//        Object returnObject = null;
//        for (Iterator<Object> i = c.iterator(); i.hasNext();) {
//            Object collectionItem = i.next();
//            if (getCollectionItemName(collectionItem).equals(keyName)) {
//                // keep the referent strongly reachable to prevent GC - since cachedData is a SoftReference
//                final Object cachedData = getCachedData(collectionItem);
//                if (cachedData != null) {
//                    try {
//                        returnObject = SessionUtil.decompressResults((byte[]) cachedData);
//                        System.out.println("Returning cached data");
//                        return returnObject;
//                    } catch (Exception e) {
//                        log.log(Level.WARNING, "Unable to restore the compressed data from session result " + keyName, e);
//                        setCachedData(collectionItem, null);
//                    }
//                } else {
//                    if (log.isLoggable(Level.FINE)){
//                        log.fine("No cached data for session result " + keyName);
//                    }
//                }
//
//                PaginatedResultSetInputStream prsis = null;
//                byte firstByte = ' ';
//                try{
//                    try{
//                        //determine the first byte of input
//                        prsis = getPaginatedResultSetInputStream(collectionItem);
//
//                        //we want to get a look at the first byte, call special method on
//                        //paginated result input stream to get the first byte
//                        firstByte = prsis.getFirstByte();
//                    }catch(IOException e){
//                        log.log(Level.FINE, "failed attempt to examine first character of result data", e);
//                        throw new ReadException(e);
//                    }
//
//                    //if we have at least one byte from the database, examine the first byte to see
//                    //if it looks like its xml (and therefore NOT compressed), set up to decompress
//                    InputStream inStream = prsis;
//                    if (firstByte != '<'){
//                        //appears to be compressed (it doesn't look like xml in any case)
//                        //we will attempt to decompress it
//                        InputStream base64InputStream = new Base64.InputStream(prsis);
//                        PushbackInputStream pushbackInStream = new PushbackInputStream(new InflaterInputStream(base64InputStream));
//                        try{
//                            //use "pushback" input stream to examine the first byte of input and then "unread" it
//                            firstByte = (byte)pushbackInStream.read();
//                            pushbackInStream.unread(firstByte);
//                            inStream = pushbackInStream;
//
//                            //if we've decompressed and we didn't get something that looks like xml, just take what
//                            //we got
//                            if (firstByte != '<'){
//                                returnObject = new String(readInputStream(inStream));
//                            }
//                        }catch(IOException e){
//                            log.log(Level.FINE, "failed attempt to read compressed data", e);
//                            throw new ReadException(e);
//                        }
//                    }
//
//                    //Unless we've had to settle for de-compressed, non-xml, parse as xml
//                    if (returnObject == null){
//                        //try to parse what we have as xml (assuming it looks like xml)
//                        returnObject = parseXMLValue(inStream);
//                    }
//                }catch (ReadException e){
//                    //We are unable to get data as compressed or decompressed xml, resort to getting it as is
//                    try{
//                        prsis = getPaginatedResultSetInputStream(collectionItem);
//                        returnObject = new String(readInputStream(prsis));
//                    }catch(IOException ioe){
//                        log.log(Level.FINE, "failed attempt to read uncompressed, non-xml", ioe);
//                        throw new ReadException(ioe);
//                    }
//                }
//
//                //compress and cache what we've got
//                try {
//                    if (log.isLoggable(Level.FINEST)){
//                        log.finest("caching " + returnObject);
//                    }
//                    byte[] compressedResult = SessionUtils.compressResults(returnObject);
//                    setCachedData(collectionItem, compressedResult);
//                } catch (IOException e) {
//                    log.log(Level.WARNING, "Unable to compress the XMLRPC parser results", e);
//                }
//
//                //we're done, so break out of our loop
//                break;
//            }
//        }
//
//        return returnObject;
//    }
//
//    protected Object parseXMLValue(InputStream inStream) throws ReadException {
//        Object returnObject = null;
//
//        byte[] inStreamBytes = readInputStream(inStream);
//        String paramValue = null;
//        try {
//            paramValue = new String(inStreamBytes, StringXMLRPCParser.PARSER_ENCODING);
//        }
//        catch (UnsupportedEncodingException e) {
//            log.log(Level.WARNING, "Unsupported Encoding, falling back to the default", e);
//            paramValue = new String(inStreamBytes);
//        }
//
//        PerfContext.enter("XML Parsing");
//        try{
//            try{
//                returnObject = StringXMLRPCParser.parse(paramValue);
//            }
//            catch(XMLRPCParseException e){
//                throw new ReadException(e);
//            }
//        }
//        finally{
//            PerfContext.leave();
//        }
//
//        return returnObject;
//    }
//}
