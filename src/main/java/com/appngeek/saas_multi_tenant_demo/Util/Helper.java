package com.appngeek.saas_multi_tenant_demo.Util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.mongodb.util.Util.toHex;


/**
 * Created by Win10 on 11/23/20.
 */
public class Helper
{

    public static final java.lang.String SHA_1 = "SHA-1";
    public static final java.lang.String SHA_256 = "SHA-256";
    public static String hashPassword(String val, String salt) throws UnsupportedEncodingException, NoSuchAlgorithmException {


        String hash= digestWithAlgName(SHA_256, digestWithAlgName(SHA_1, salt + val) + digestWithAlgName(SHA_1, val + salt) + salt + digestWithAlgName(SHA_256, val + digestWithAlgName(SHA_1, salt)));
        return hash;
    }
    public static String digestWithAlgName(String digestName, String val) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance(digestName);
        md.reset();
        md.update(val.getBytes("UTF-8"));
        byte bytes[] = md.digest();
        return toHex(bytes);
    }
    public static boolean compare(String password, String salt, String hashPassowrd) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        return hashPassowrd.matches(hashPassword(password, salt));

    }

}
