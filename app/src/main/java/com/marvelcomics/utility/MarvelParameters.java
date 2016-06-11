package com.marvelcomics.utility;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by SHUCHI on 6/3/2016.
 */
public class MarvelParameters {
    public String apikey = "28df3c51dfbaee681f185b92dXXXXXXX";
    public String privateKey = "29ad73a488a7c0101133d391b47814897XXXXXXX";


    public String getMD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            Log.d("thehash",sb.toString());
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

}
