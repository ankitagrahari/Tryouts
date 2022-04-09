package com.dmtryouts.EncryptDecrypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESGCMEncryption {

    public static final int AES_KEY_SIZE = 256;
    public static final int GCM_IV_LENGTH = 96;
    public static final int GCM_TAG_LENGTH = 128;

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
    public static SecretKeySpec generateSecretKeySpec(String secretKey){
        byte[] key;
        SecretKeySpec sks = null;
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
    public static SecretKeySpec generateSecretKey(String dataSourceId){
        String tmp = generateNewValueFromDataSource(dataSourceId);
        return generateSecretKeySpec(tmp);
    }

    public static void main(String args[]) {
        String messageToEncrypt = "OPSWARE_ADMIN" ;

        // Additional Authenticated Data. This is not encrypted but used in Authentication Tag.
        // Some common examples could be domain name.
        byte[] aadData = "".getBytes() ;

        // Use different key+IV pair for encrypting/decrypting different parameters

//        // Generating Key
//        SecretKey aesKey = null ;
//        try {
//            // Specifying algorithm key will be used for
//            KeyGenerator keygen = KeyGenerator.getInstance("AES") ;
//            // Specifying Key size to be used,
//            // Note: This would need JCE Unlimited Strength to be installed explicitly
//            keygen.init(AES_KEY_SIZE);
//            aesKey = keygen.generateKey();
//        } catch(NoSuchAlgorithmException noSuchAlgoExc) {
//            System.out.println("Key being request is for AES algorithm, but this cryptographic algorithm is not available in the environment "
//                    + noSuchAlgoExc) ;
//            System.exit(1) ;
//        }
        SecretKey aesKey = generateSecretKey("885756");

        // Generating IV
        byte iv[] = new byte[GCM_IV_LENGTH];
        SecureRandom secRandom = new SecureRandom() ;
        // SecureRandom initialized using self-seeding
        secRandom.nextBytes(iv);


        // Initialize GCM Parameters
        GCMParameterSpec gcmParamSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv) ;


        byte[] encryptedText = aesEncrypt(messageToEncrypt, aesKey,  gcmParamSpec, aadData) ;
        System.out.println("Encrypted Text: " + Base64.getEncoder().encodeToString(encryptedText) ) ;

        // Same key, IV and GCM Specs for decryption as used for encryption.
        byte[] decryptedText = aesDecrypt(encryptedText, aesKey, gcmParamSpec, aadData) ;
        System.out.println("Decrypted text: " + new String(decryptedText)) ;

        // Make sure not to repeat Key + IV pair, for encrypting more than one plaintext.
        secRandom.nextBytes(iv);
    }


    public static byte[] aesEncrypt(String message, SecretKey aesKey, GCMParameterSpec gcmParamSpec, byte[] aadData) {
        Cipher c = null ;

        try {
            c = Cipher.getInstance("AES/GCM/NoPadding");
        } catch(NoSuchAlgorithmException noSuchAlgoExc) {
            System.out.println("Exception while encrypting. Algorithm being requested is not available in this environment "
                    + noSuchAlgoExc);
            System.exit(1);
        } catch(NoSuchPaddingException noSuchPaddingExc) {
            System.out.println("Exception while encrypting. Padding Scheme being requested is not available this environment "
                    + noSuchPaddingExc);
            System.exit(1);
        }


        try {
            c.init(Cipher.ENCRYPT_MODE, aesKey, gcmParamSpec, new SecureRandom()) ;
        } catch(InvalidKeyException invalidKeyExc) {
            System.out.println("Exception while encrypting. Key being used is not valid. "
                    + "It could be due to invalid encoding, wrong length or uninitialized "
                    + invalidKeyExc);
            System.exit(1);
        } catch(InvalidAlgorithmParameterException invalidAlgoParamExc) {
            System.out.println("Exception while encrypting. Algorithm parameters being specified are not valid "
                    + invalidAlgoParamExc);
            System.exit(1);
        }

        try {
            // add AAD tag data before encrypting
            c.updateAAD(aadData) ;
        } catch(IllegalArgumentException illegalArgumentExc) {
            System.out.println("Exception thrown while encrypting. Byte array might be null " +illegalArgumentExc );
            System.exit(1);
        } catch(IllegalStateException illegalStateExc) {
            System.out.println("Exception thrown while encrypting. CIpher is in an illegal state " +illegalStateExc);
            System.exit(1);
        } catch(UnsupportedOperationException unsupportedExc) {
            System.out.println("Exception thrown while encrypting. Provider might not be supporting this method " +unsupportedExc);
            System.exit(1);
        }

        byte[] cipherTextInByteArr = null ;
        try {
            cipherTextInByteArr = c.doFinal(message.getBytes()) ;
        } catch(IllegalBlockSizeException illegalBlockSizeExc) {
            System.out.println("Exception while encrypting, due to block size " + illegalBlockSizeExc) ;
            System.exit(1);
        } catch(BadPaddingException badPaddingExc) {
            System.out.println("Exception while encrypting, due to padding scheme " + badPaddingExc);
            System.exit(1);
        }

        return cipherTextInByteArr ;
    }


    public static byte[] aesDecrypt(byte[] encryptedMessage, SecretKey aesKey, GCMParameterSpec gcmParamSpec, byte[] aadData) {
        Cipher c = null ;

        try {
            // Transformation specifies algortihm, mode of operation and padding
            c = Cipher.getInstance("AES/GCM/NoPadding");
        } catch(NoSuchAlgorithmException noSuchAlgoExc) {
            System.out.println("Exception while decrypting. "
                    + "Algorithm being requested is not available in environment "
                    + noSuchAlgoExc);
            System.exit(1);
        } catch(NoSuchPaddingException noSuchAlgoExc) {
            System.out.println("Exception while decrypting. "
                    + "Padding scheme being requested is not available in environment "
                    + noSuchAlgoExc);
            System.exit(1);
        }

        try {
            c.init(Cipher.DECRYPT_MODE, aesKey, gcmParamSpec, new SecureRandom()) ;
        } catch(InvalidKeyException invalidKeyExc) {
            System.out.println("Exception while encrypting. "
                    + "Key being used is not valid. It could be due to invalid encoding, "
                    + "wrong length or uninitialized " + invalidKeyExc);
            System.exit(1);
        } catch(InvalidAlgorithmParameterException invalidParamSpecExc) {
            System.out.println("Exception while encrypting. Algorithm Param being used is not valid. "
                    + invalidParamSpecExc);
            System.exit(1);
        }

        try {
            // Add AAD details before decrypting
            c.updateAAD(aadData) ;
        } catch(IllegalArgumentException illegalArgumentExc) {
            System.out.println("Exception thrown while encrypting. Byte array might be null " + illegalArgumentExc );
            System.exit(1);
        } catch(IllegalStateException illegalStateExc) {
            System.out.println("Exception thrown while encrypting. Cipher is in an illegal state " + illegalStateExc);
            System.exit(1);
        }

        byte[] plainTextInByteArr = null ;
        try {
            plainTextInByteArr = c.doFinal(encryptedMessage) ;
        } catch(IllegalBlockSizeException illegalBlockSizeExc) {
            System.out.println("Exception while decryption, due to block size " + illegalBlockSizeExc);
            System.exit(1);
        } catch(BadPaddingException badPaddingExc) {
            System.out.println("Exception while decryption, due to padding scheme " + badPaddingExc);
            System.exit(1);
        }

        return plainTextInByteArr ;
    }

//    /**
//     * Encrypt Using AES/GCM
//     * @return
//     */
//    public byte[] encryptAESGCM(byte[] toEncrypt, SecretKeySpec keySpec, byte[] IV)
//            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
//            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
//        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
//
//        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, IV);
//        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);
//        cipher.updateAAD("".getBytes(StandardCharsets.UTF_8));
//        return cipher.doFinal(toEncrypt);
//    }
//
//    /**
//     * Encrypt Using AES/GCM
//     * @return
//     */
//    public byte[] decryptAESGCM(byte[] toDecrypt, SecretKeySpec keySpec, byte[] IV)
//            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
//            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
//        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
//
//        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, IV);
//        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);
//        cipher.updateAAD("".getBytes(StandardCharsets.UTF_8));
//        return cipher.doFinal(toDecrypt);
//    }
//
//    public static SecretKeySpec generateSecretKeySpec(String secretKey){
//        byte[] key;
//        MessageDigest sha = null;
//        SecretKeySpec sks = null;
//        try {
//            key = secretKey.getBytes("UTF-8");
//            sha = MessageDigest.getInstance("SHA-256");
//            key = sha.digest(key);
//            key = Arrays.copyOf(key, 128);
//            sks = new SecretKeySpec(key, "AES");
//        } catch (NoSuchAlgorithmException e) { e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return sks;
//    }
//
//    public static String generateNewValueFromDataSource(String id){
//        Integer newId = null;
//        int temp = Integer.valueOf(id);
//        newId = temp * 74 * 29;
//        return newId.toString();
//    }
//
//    public static void main(String[] args) {
//        AESGCMEncryption obj = new AESGCMEncryption();
//
//        String plainText = "ABCDEF";
//        try {
//            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//            keyGenerator.init(AES_KEY_SIZE);
//
////            SecretKey secretKey = keyGenerator.generateKey();
////            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
//
//            byte[] IV = new byte[GCM_IV_LENGTH];
//            SecureRandom random = new SecureRandom();
//            random.nextBytes(IV);
//
//            String dataSourceId = "1876534";
//            String secretKey = generateNewValueFromDataSource(dataSourceId);
//            SecretKeySpec secretKeySpec = generateSecretKeySpec(secretKey);
//
//            byte[] cipherText = obj.encryptAESGCM(plainText.getBytes(), secretKeySpec, IV);
//            System.out.println(cipherText);
//            String t = Base64.getEncoder().encodeToString(cipherText);
//            System.out.println("Encrypted Text : " + t);
//
//            byte[] t1 = Base64.getDecoder().decode(t);
//            System.out.println(t1);
//            byte[] decryptedText = obj.decryptAESGCM(t1, secretKeySpec, IV);
//            System.out.println("Decrypted Text : " + decryptedText);
//        } catch (InvalidAlgorithmParameterException e) {
//            e.printStackTrace();
//        } catch (NoSuchPaddingException e) {
//            e.printStackTrace();
//        } catch (IllegalBlockSizeException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (BadPaddingException e) {
//            e.printStackTrace();
//        } catch (InvalidKeyException e) {
//            e.printStackTrace();
//        }
//    }
}
