package com.dmtryouts.EncryptDecrypt;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.Properties;

import static java.nio.charset.StandardCharsets.UTF_8;

public class AesEncryption {

    private static String DATAMINER_CONF_FILE_NAME = "src/main/java/com/utilities/dm.conf";
    private static final int CBC_IV_SIZE_BYTES = 16;

    public static void changePassword(String newPass){
        File confFile = new File(DATAMINER_CONF_FILE_NAME);
        if(confFile.exists()) {
            try {
                FileInputStream fis = new FileInputStream(DATAMINER_CONF_FILE_NAME);
                Properties confProp = new Properties();
                confProp.load(fis);

                String dataSourceId = confProp.getProperty("DATASOURCEID");
                String jdbcConnPropStr = confProp.getProperty("JDBCCONNECTIONPROPERTIES");
                String jdbcEncryptPassStr = confProp.getProperty("JDBC_ENCRYPTED_PASSWORD");

                String secretKey = generateNewValueFromDataSource(dataSourceId);
                SecretKeySpec secretKeySpec = generateSecretKeySpec(secretKey);
                String decryptOldPassValue = decryptAES(jdbcEncryptPassStr, secretKeySpec);
                System.out.println("Changing "+ decryptOldPassValue);

                if(!decryptOldPassValue.equals(newPass)) {
                    String encryptNewPassValue = encryptAES(newPass, secretKeySpec);
                    confProp.setProperty("JDBC_ENCRYPTED_PASSWORD", encryptNewPassValue);

                    System.out.println(jdbcConnPropStr);
                    String[] jdbcConnArr = jdbcConnPropStr.split(",");
                    StringBuilder newJdbcConnStr = new StringBuilder("");
                    for (String ele : jdbcConnArr) {
                        if (ele.startsWith("password")) {
                            String eleKey = ele.split("=")[0];
                            String passStr = eleKey + "=" + encryptNewPassValue;

                            ele = passStr;
                        }
                        newJdbcConnStr.append(ele).append(",");
                    }
                    String temp = newJdbcConnStr.toString().replaceAll("=", "\\=");
                    System.out.println("NewJDBConnStr:" + temp);
                    confProp.setProperty("JDBCCONNECTIONPROPERTIES", temp.substring(0, temp.length() - 1));

                    FileOutputStream fos = new FileOutputStream(DATAMINER_CONF_FILE_NAME);
                    confProp.store(fos, "conf file updated");
                } else {
                    System.out.println("Old and New Password is same. Exiting");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Configuration files missing. Exiting!!");
        }
    }

    public static String generateNewValueFromDataSource(String id){
        Integer newId = null;
        int temp = Integer.valueOf(id);
        newId = temp * 74 * 29;
        return newId.toString();
    }

    public static SecretKeySpec generateSecretKeySpec(String secretKey){
        byte[] key;
        MessageDigest sha = null;
        SecretKeySpec sks = null;
        try {
            key = secretKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-256");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            sks = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) { e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sks;
    }

    public static String encryptAES(String toEncrypt, SecretKeySpec secretKeySpec){

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] iv = generateRandomIV();
            IvParameterSpec spec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, spec);

            byte[] bytePassword = toEncrypt.getBytes(UTF_8);
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
        } catch (ShortBufferException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }

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

    public static void main(String[] args) {
        AesEncryption obj = new AesEncryption();
        AesEncryption.changePassword("abcv4");
    }
}
