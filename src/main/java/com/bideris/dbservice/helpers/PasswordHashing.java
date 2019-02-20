package com.bideris.dbservice.helpers;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//sudu gaiva
public class PasswordHashing {



    public static String hashPassword(String password)  {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            byte[] hash = md.digest(password.getBytes());
            String hashedPassword =  bytesToHex(hash);
            System.out.println("    pass = " + password);
            System.out.println("MD5 hash = " + hash);
            System.out.println("MD5 pass = " + hashedPassword);

            return hashedPassword;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
