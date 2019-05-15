package com.aikudwo.ccy.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestUtils {

    public static String[] getSHA1TokenList(String[] strings, String secret) {
        String[] newStringList = new String[strings.length];
        for (int i = 0; i < strings.length; i++) {
            newStringList[i] = getSHA1Token(strings[i], secret);
        }
        return newStringList;
    }

    public static String getSHA1Token(String str, String secret) {
        try {
            return getSHA1(str + secret);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getSHA1(String str) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        messageDigest.update(str.getBytes());
        StringBuilder buf = new StringBuilder();
        byte[] bits = messageDigest.digest();
        for (int i = 0; i < bits.length; i++) {
            int a = bits[i];
            if (a < 0) a += 256;
            if (a < 16) buf.append("0");
            buf.append(Integer.toHexString(a));
        }
        return buf.toString();
    }
}
