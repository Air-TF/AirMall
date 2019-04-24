package com.example.airmall;

import com.example.airmall.utils.MD5Utils;

import org.junit.Test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test() {
        String mPassword = "123456";
        mPassword = MD5Utils.md5(mPassword);
        mPassword = MD5Utils.md5(mPassword + MD5Utils.addSalt(mPassword));

    }

    public String MD5(String password) {
        String md5code = "";
        try {
            byte[] secretBytes = MessageDigest.getInstance("md5").digest(password.getBytes());
            md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
            // 如果生成数字未满32位，需要前面补0
            for (int i = 0; i < 32 - md5code.length(); i++) {
                md5code = "0" + md5code;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5code;
    }

    public String convertMD5(String inStr) {

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;

    }
}