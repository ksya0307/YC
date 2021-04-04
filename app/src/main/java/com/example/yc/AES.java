package com.example.yc;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    private static final byte[] keyValue =
            new byte[]{'c', 'o', 'd', 'i', 'n', 'g', 'a', 'f', 'f', 'a', 'i', 'r', 's', 'c', 'o', 'm'};

public static String encrypt(String cleartext)
        throws Exception {
        byte[] rawKey = getRawKey();
        byte[] result = encrypt(rawKey, cleartext.getBytes());
        return toHex(result);
        }
public static String decrypt(String encrypted)
        throws Exception {
        byte[] enc = toByte(encrypted);
        byte[] result = decrypt(enc);
        return new String(result);
        }
private static byte[] getRawKey() throws Exception {
        SecretKey key = new SecretKeySpec(keyValue, "AES");
        byte[] raw = key.getEncoded();
        return raw;
        }
private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKey skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
        }
private static byte[] decrypt(byte[] encrypted)
        throws Exception {
        SecretKey skeySpec = new SecretKeySpec(keyValue, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
        }
public static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
        result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        return result;
        }
public static String toHex(byte[] buf) {
        if (buf == null)
        return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
        appendHex(result, buf[i]);
        }
        return result.toString();
        }
private final static String HEX = "0123456789ABCDEF";
private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
        }
//private static final String key = "aesEncryptionKey";
//private static final String initVector = "encryptionIntVec";
//
//public static void main(String[] args) {
//                String originalString = "password";
//                System.out.println("Original String to encrypt - " + originalString);
//                String encryptedString = encrypt(originalString).toString();
//                System.out.println("Encrypted String - " + encryptedString);
//                String decryptedString = decrypt(encryptedString);
//                System.out.println("After decryption - " + decryptedString);
//        }
//
//
//
//        public static byte[] encrypt(String value) {
//                try {
//                        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
//                        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
//
//                        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
//                        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
//
//                        byte[] encrypted = cipher.doFinal(value.getBytes());
//                        return Base64.encode(encrypted,Base64.DEFAULT);
//                } catch (Exception ex) {
//                        ex.printStackTrace();
//                }
//                return null;
//        }
//
//
//        public static String decrypt(String encrypted) {
//                try {
//                        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
//                        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
//
//                        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
//                        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
//                        byte[] original = cipher.doFinal(Base64.decode(encrypted,Base64.DEFAULT));
//
//                        return new String(original);
//                } catch (Exception ex) {
//                        ex.printStackTrace();
//                }
//
//                return null;
//        }


}
