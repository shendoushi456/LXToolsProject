package com.baidu.maps.utils;

import android.text.TextUtils;
import android.util.Base64;

import com.lx.c_interface_library.CommonAPI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class AesUtil {

    private static final String KEY_ALGORITHM = "AES";
    //AES/CBC/PKCS5Padding默认对应PHP则为：AES-128-CBC
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    public static String sSecretKey = CommonAPI.RELEASE_SSK;

    public static String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(sSecretKey));
            byte[] encryptByte = cipher.doFinal(data.getBytes());
            return Base64.encodeToString(encryptByte, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String data) {
        try {
            byte[] encrypted = Base64.decode(data, Base64.DEFAULT);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(sSecretKey));
            byte[] result = cipher.doFinal(encrypted);
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String data,String key) {
        try {
            byte[] encrypted = Base64.decode(data, Base64.DEFAULT);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(key));
            byte[] result = cipher.doFinal(encrypted);
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static SecretKeySpec getSecretKey(String secretKey) {
        secretKey = secretKey.substring(0, 16);
        return new SecretKeySpec(secretKey.getBytes(), KEY_ALGORITHM);
    }

    public static String getsort(Map<String, String> params) {
        ArrayList<String> sortList = new ArrayList<String>();
        for (Map.Entry<String, String> valuePair : params.entrySet()) {
            String name = valuePair.getKey();
            String value = valuePair.getValue();
            if (TextUtils.isEmpty(value)) {
                sortList.add(String.format("%s=%s", name, ""));
            } else {
                sortList.add(String.format("%s=%s", name, value));
            }
        }
        Collections.sort(sortList);
        StringBuilder sb = new StringBuilder();
        for (String param : sortList) {
            sb.append(param);
        }
        return sb.toString();
    }


}
