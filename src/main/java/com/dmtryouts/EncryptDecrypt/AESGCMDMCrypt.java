package com.dmtryouts.EncryptDecrypt;

import static com.dmtryouts.EncryptDecrypt.AesEncryption.generateNewValueFromDataSource;
import static com.dmtryouts.EncryptDecrypt.AesEncryption.generateSecretKeySpec;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESGCMDMCrypt {

    public static final int AES_KEY_SIZE = 256;
    public static final int GCM_IV_LENGTH = 96;
    public static final int GCM_TAG_LENGTH = 128;
    byte[] aadTagData = "".getBytes() ;

    /**
     * Highly Confidential Logic :)
     * @param id
     * @return String
     */
    public static String generateNewValueFromDataSource(String id){
        Integer newId = null;
        int temp = Integer.valueOf(id);
        newId = temp * 74 * 29;
        return newId.toString();
    }

    /**
     * Takes the String value(updated dataSourceId) and return the SecretKeySpec
     * @param secretKey
     * @return SecretKeySpec
     */
    public static SecretKey generateSecretKeySpec(String secretKey){
        byte[] key;
        SecretKey sks = null;
        try {
            key = secretKey.getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            sks = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sks;
    }

    /**
     * Accumulator which takes DataSourceId and generates the SecretKey used for Encryption and Decryption
     * @param dataSourceId
     * @return SecretKeySpec
     */
    public SecretKey generateSecretKey(String dataSourceId){
        String tmp = generateNewValueFromDataSource(dataSourceId);
        return generateSecretKeySpec(tmp);
    }

    /**
     * Uses SecureRandom to return initialization vector of size 96
     * @return initialization vector
     */
    public static byte[] generateIV(){
        byte iv[] = new byte[16];
        SecureRandom secRandom = new SecureRandom() ;
        // SecureRandom initialized using self-seeding
        secRandom.nextBytes(iv);

        return iv;
    }

    /**
     * Generates the GCMParameterSpec object of length 128
     * @return GCMParameterSpec
     */
    public GCMParameterSpec generateGCMParamSpec(){
        byte[] IV = generateIV();
        return new GCMParameterSpec(GCM_TAG_LENGTH, IV) ;
    }

    /**
     * Encrypt the given string using AES/GCM/NoPadding Algorithm.
     * Since the GCM mode works on Streams, it doesn't require any Padding. So NoPadding is given.
     * @param toEncrypt
     * @param secretKey
     * @return byte[]
     */
    public byte[] encrypt(String toEncrypt, SecretKey secretKey, GCMParameterSpec gcmParameterSpec){
        byte[] encrypted = null;
        try{
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmParameterSpec, new SecureRandom());
            cipher.updateAAD(aadTagData);
            encrypted = cipher.doFinal(toEncrypt.getBytes());
        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return encrypted;
    }

    /**
     * Decrypt the given string using AES/GCM/NoPadding Algorithm.
     * Since the GCM mode works on Streams, it doesn't require any Padding. So NoPadding is given.
     * @param toDecrypt
     * @param secretKey
     * @return byte[]
     */
    public byte[] decrypt(byte[] toDecrypt, SecretKey secretKey, GCMParameterSpec gcmParameterSpec){
        byte[] decrypted = null;
        try{
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec, new SecureRandom());
            cipher.updateAAD(aadTagData);
            decrypted = cipher.doFinal(toDecrypt);
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return decrypted;
    }

    public static int IV_SIZE = 16;
    public String encryptAESGCM1(char[] toEncrypt, SecretKey secretKey){
        try{
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

            ByteBuffer byteBuffer = Charset.defaultCharset().encode(CharBuffer.wrap(toEncrypt));
            byte[] bytePassword = new byte[byteBuffer.remaining()];
            byteBuffer.get(bytePassword);

            byte IV[] = new byte[IV_SIZE];
            SecureRandom secRandom = new SecureRandom() ;
            secRandom.nextBytes(IV);                        // SecureRandom initialized using self-seeding

            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, IV);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmParameterSpec, secRandom);

            cipher.updateAAD(aadTagData);
            cipher.update(bytePassword);
            byte[] encryptedBytes = cipher.doFinal();


            byte[] ivCTAndTag = new byte[IV.length + encryptedBytes.length];
            System.arraycopy(IV, 0, ivCTAndTag, 0, IV.length);
            System.arraycopy(encryptedBytes, 0, ivCTAndTag, IV.length, encryptedBytes.length);

            return Base64.getEncoder().encodeToString(ivCTAndTag);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String decryptAESGCM1(String toDecrypt, SecretKey secretKey){
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            byte[] decodedToDecrypt = Base64.getDecoder().decode(toDecrypt);

            byte[] IV = new byte[IV_SIZE];
            System.arraycopy(decodedToDecrypt, 0, IV, 0, IV.length);

            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, IV);
            byte[] encryptedBytes = new byte[decodedToDecrypt.length - IV.length];
            System.arraycopy(decodedToDecrypt, IV.length, encryptedBytes, 0, encryptedBytes.length);

            cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec, new SecureRandom());
            cipher.updateAAD(aadTagData);
            cipher.update(encryptedBytes);

            byte[] decryptedBytes = cipher.doFinal();
            return new String(decryptedBytes, UTF_8);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String dataSourceId = "885756";
        String toEncrypt = "OPSWARE_ADMIN";
        AESGCMDMCrypt obj = new AESGCMDMCrypt();

        String encrypted = obj.encryptAESGCM1(toEncrypt.toCharArray(), obj.generateSecretKey(dataSourceId));
        System.out.println(encrypted);
        String decrypted = obj.decryptAESGCM1(encrypted, obj.generateSecretKey(dataSourceId));
        System.out.println(decrypted);

//        GCMParameterSpec gcmParameterSpec = obj.generateGCMParamSpec();
//
//        byte[] encrypted = obj.encrypt(toEncrypt, obj.generateSecretKey(dataSourceId), obj.generateGCMParamSpec());
//        String base64Encrypt = Base64.getEncoder().encodeToString(encrypted);
//        System.out.println(base64Encrypt);
//
//        byte[] base64Decrypt = Base64.getDecoder().decode(base64Encrypt);
//        byte[] decrypted = obj.decrypt(base64Decrypt, obj.generateSecretKey(dataSourceId), obj.generateGCMParamSpec());
//        System.out.println(new String(decrypted));
    }
}
