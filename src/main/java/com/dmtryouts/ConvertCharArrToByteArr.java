package com.dmtryouts;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ConvertCharArrToByteArr {

    private static final int CBC_IV_SIZE_BYTES = 16;

    public static String decryptAES(String toDecrypt, SecretKeySpec secretKeySpec){
        try{
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] ivAndCTWithTag = Base64.getDecoder().decode(toDecrypt);
            IvParameterSpec spec = new IvParameterSpec(ivAndCTWithTag, 0, CBC_IV_SIZE_BYTES);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, spec);
            byte[] plaintext = cipher.doFinal(ivAndCTWithTag, CBC_IV_SIZE_BYTES, ivAndCTWithTag.length - CBC_IV_SIZE_BYTES);

            return new String(plaintext, UTF_8);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] generateRandomIV() {
        byte[] iv = new byte[CBC_IV_SIZE_BYTES];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        return iv;
    }

    public static String encryptAES(char[] toEncrypt, SecretKeySpec secretKeySpec){
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] iv = generateRandomIV();
            IvParameterSpec spec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, spec);
            byte[] bytePassword = Charset.defaultCharset().encode(CharBuffer.wrap(toEncrypt)).array();
            byte[] ivCTAndTag = new byte[CBC_IV_SIZE_BYTES + cipher.getOutputSize(bytePassword.length)];
            System.arraycopy(iv, 0, ivCTAndTag, 0, CBC_IV_SIZE_BYTES);
            cipher.doFinal(bytePassword, 0, bytePassword.length, ivCTAndTag, CBC_IV_SIZE_BYTES);

            return Base64.getEncoder().encodeToString(ivCTAndTag);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (ShortBufferException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String generateNewValueFromDataSource(String id){
        Integer newId = null;
        int temp = Integer.valueOf(id);
        newId = temp * 74 * 29;
        return newId.toString();
    }

    private static SecretKeySpec generateSecretKeySpec(String secretKey){
        SecretKeySpec sks = null;
        byte[] key;
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

    public static void main(String[] args) {
        String pass = "ABCDEFGH";
        String secretKey = generateNewValueFromDataSource("988888");
        SecretKeySpec secretKeySpec = generateSecretKeySpec(secretKey);
        String encPass = ConvertCharArrToByteArr.encryptAES(pass.toCharArray(), secretKeySpec);

        System.out.println(encPass);

        System.out.println(ConvertCharArrToByteArr.decryptAES(encPass, secretKeySpec));
    }
}
