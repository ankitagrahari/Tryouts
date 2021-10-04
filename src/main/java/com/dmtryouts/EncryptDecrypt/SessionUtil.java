package com.dmtryouts.EncryptDecrypt;

import java.io.*;
import java.util.zip.GZIPInputStream;

public class SessionUtil {
    /**
     * Decompresses the given data and returns the serialized object
     *
     * @param data the compressed bytes
     *
     * @return the serialized object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object decompressResults(byte[] data) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = null;
        byte[] decompressed = decompressBytes(data);
        try {
            objectInputStream = new ObjectInputStream(new BufferedInputStream(new ByteArrayInputStream(decompressed)));
            return objectInputStream.readObject();
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /***
     * Decompresses the given bytes (moved from SessionResultHelper)
     * @param data the compressed bytes
     *
     * @return the uncompressed data
     * @throws IOException
     */
    public static byte[] decompressBytes(byte[] data) throws IOException {
        GZIPInputStream is = null;
        ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
        BufferedInputStream bis = null;
        try {
            is = new GZIPInputStream(new ByteArrayInputStream(data));
            bis = new BufferedInputStream(is);
            byte[] buf = new byte[1024];
            int len = -1;
            while ((len = bis.read(buf)) != -1) {
                os.write(buf, 0, len);
            }
            os.flush();
            os.close();
            return os.toByteArray();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                }
            }
        }
    }

}
