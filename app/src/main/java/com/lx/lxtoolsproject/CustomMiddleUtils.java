package com.lx.lxtoolsproject;


import android.util.Log;

import com.lx.c_interface_library.CommonAPI;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CustomMiddleUtils {
    private static final String KEY_ALGORITHM = "AES";
    public static String sSecretKey = CommonAPI.RELEASE_SSK;
    private static final Map<String, Method> METHOD_CACHE = new ConcurrentHashMap<>();

    public static Object invokeStatic2(String encClassName, String encMethodName, Object... args) {
        try {
            String className = encClassName;
            String methodName = encMethodName;
            Class<?> clazz = Class.forName(className);
            return invoke(clazz, null, methodName, args);
        } catch (Exception e) {
            throw new RuntimeException("Reflect static call failed: " + e.getMessage(), e);
        }
    }



    public static Object invokeStatic(String encClassName, String encMethodName, Object... args) {
        try {
            String className = decrypt(encClassName);
            String methodName = decrypt(encMethodName);
            Class<?> clazz = Class.forName(className);
            return invoke(clazz, null, methodName, args);
        } catch (Exception e) {
            throw new RuntimeException("Reflect static call failed: " + e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T invokeStaticCallback(String encClassName, String encMethodName, Object... args) {
        try {
            String className = decrypt(encClassName);
            String methodName = decrypt(encMethodName);
            Class<?> clazz = Class.forName(className);
            return invokeCallback(clazz, null, methodName, args);
        } catch (Exception e) {
            throw new RuntimeException("Reflect static call failed: " + e.getMessage(), e);
        }
    }




    private static  <T> T  invokeCallback(Class<?> clazz, Object target, String methodName, Object[] args) throws Exception {
        // 构建缓存 Key: 类名 + 方法名 + 参数类型列表
        String cacheKey = clazz.getName() + "#" + methodName + getParamTypesString(args);

        Method method = METHOD_CACHE.get(cacheKey);

        if (method == null) {
            // 获取参数类型列表
            Class<?>[] parameterTypes = null;
            if (args != null && args.length > 0) {
                parameterTypes = new Class<?>[args.length];
                for (int i = 0; i < args.length; i++) {
                    parameterTypes[i] = getPrimitiveType(args[i].getClass());
                }
            }

            // 查找方法（包括私有方法）
            method = clazz.getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true); // 突破私有权限限制
            METHOD_CACHE.put(cacheKey, method);
        }

        return (T) method.invoke(target, args);
    }




    public static Object invokeInstance(Object instance, String encMethodName, Object... args) {
        if (instance == null) return null;
        try {
            String methodName = decrypt(encMethodName);
            return invoke(instance.getClass(), instance, methodName, args);
        } catch (Exception e) {
            throw new RuntimeException("Reflect instance call failed: " + e.getMessage(), e);
        }
    }



    public static Object invokeStaticType(String encClassName, String encMethodName,  Class<?>[] parameterTypes, Object... args) {

        try {
            String className = decrypt(encClassName);
            String methodName = decrypt(encMethodName);
            Class<?> clazz = Class.forName(className);
            return invoke(clazz, null, methodName, args,parameterTypes);
        } catch (Exception e) {
            throw new RuntimeException("Reflect static call failed: " + e.getMessage(), e);
        }
    }




    private static Object invoke(Class<?> clazz, Object target, String methodName, Object[] args,Class<?>[] parameterTypes) throws Exception {
        // 构建缓存 Key: 类名 + 方法名 + 参数类型列表
        String cacheKey = clazz.getName() + "#" + methodName + getParamTypesString(args);

        Log.i("AD_LOG==","cacheKey==="+cacheKey);

        Method method = METHOD_CACHE.get(cacheKey);

        if (method == null) {
            Log.i("AD_LOG==","创建新方法！！！！！");

            // 查找方法（包括私有方法）
            method = clazz.getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true); // 突破私有权限限制
            METHOD_CACHE.put(cacheKey, method);
        }

        return method.invoke(target, args);
    }









    private static Object invoke(Class<?> clazz, Object target, String methodName, Object[] args) throws Exception {
        // 构建缓存 Key: 类名 + 方法名 + 参数类型列表
        String cacheKey = clazz.getName() + "#" + methodName + getParamTypesString(args);

        Method method = METHOD_CACHE.get(cacheKey);

        if (method == null) {
            // 获取参数类型列表
            Class<?>[] parameterTypes = null;
            if (args != null && args.length > 0) {
                parameterTypes = new Class<?>[args.length];
                for (int i = 0; i < args.length; i++) {
                    parameterTypes[i] = getPrimitiveType(args[i].getClass());
                }
            }

            // 查找方法（包括私有方法）
            method = clazz.getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true); // 突破私有权限限制
            METHOD_CACHE.put(cacheKey, method);
        }

        return method.invoke(target, args);
    }


    private static Class<?> getPrimitiveType(Class<?> clazz) {
        if (clazz == Integer.class) return int.class;
        if (clazz == Long.class) return long.class;
        if (clazz == Boolean.class) return boolean.class;
        if (clazz == Double.class) return double.class;
        if (clazz == Float.class) return float.class;
        if (clazz == Byte.class) return byte.class;
        if (clazz == Character.class) return char.class;
        if (clazz == Short.class) return short.class;
        return clazz;
    }

    private static String getParamTypesString(Object[] args) {
        if (args == null || args.length == 0) return "()";
        StringBuilder sb = new StringBuilder("(");
        for (Object arg : args) {
            sb.append(arg == null ? "null" : arg.getClass().getName()).append(",");
        }
        return sb.append(")").toString();
    }


    public static String decrypt(String input) {

        String ss = null;
        try {
            ss = decryptOpenSSL(input,sSecretKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Log.i("AD_LOG","sSecretKey====="+sSecretKey);
        Log.i("AD_LOG","decryptae====="+ss);
        try {
            // 这里使用 Base64 作为演示，实际可使用 XOR 或更复杂的算法

           String s =  new String(Base64.getDecoder().decode(ss));
            Log.i("AD_LOG","解析方法是==="+s);

            return s;
        } catch (Exception e) {
            return input; // 如果不是 Base64，返回原字符串
        }
    }


    /**
     * 解密 OpenSSL aes-256-cbc -salt -a 加密的密文
     * @param base64Cipher 类似 "U2FsdGVkX19Kq0yujYZg7KTjAucgzc2ahBnxDWe6wFDqXr9P6nUJHIYWNVywsR9E"
     * @param password    你的 "bf1a5cb89d3c29c519da53cd9926d916"（当密码用，不是直接当 key）
     */
    public static String decryptOpenSSL(String base64Cipher, String password) throws Exception {
        byte[] data = android.util.Base64.decode(base64Cipher, android.util.Base64.NO_WRAP);

        if (data.length < 16 || data[0] != 'S' || data[1] != 'a' || data[2] != 'l' || data[3] != 't') {
            throw new IllegalArgumentException("不是 OpenSSL Salted 格式");
        }

        // 2. 取 8 字节 Salt
        byte[] salt = new byte[8];
        System.arraycopy(data, 8, salt, 0, 8);

        // 3. EVP_BytesToKey 派生 key(32) + iv(16)
        byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
        byte[][] keyAndIv = evpBytesToKey(passwordBytes, salt, 32, 16);
        byte[] key = keyAndIv[0];
        byte[] iv  = keyAndIv[1];

        // 4. 真正的密文（去掉前16字节：8 magic + 8 salt）
        byte[] cipherBytes = new byte[data.length - 16];
        System.arraycopy(data, 16, cipherBytes, 0, cipherBytes.length);

        // 5. AES-256-CBC 解密
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE,
                new SecretKeySpec(key, "AES"),
                new IvParameterSpec(iv));

        byte[] plain = cipher.doFinal(cipherBytes);
        return new String(plain, StandardCharsets.UTF_8);
    }


    private static byte[][] evpBytesToKey(byte[] password, byte[] salt, int keyLen, int ivLen) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] key = new byte[keyLen];
        byte[] iv  = new byte[ivLen];
        byte[] prev = new byte[0];
        int offset = 0;

        while (offset < keyLen + ivLen) {
            md5.reset();
            md5.update(prev);
            md5.update(password);
            if (salt != null && salt.length == 8) {
                md5.update(salt);
            }
            prev = md5.digest();

            int copyLen = Math.min(prev.length, keyLen + ivLen - offset);
            int needForKey = keyLen - offset;
            if (needForKey > 0) {
                int n = Math.min(copyLen, needForKey);
                System.arraycopy(prev, 0, key, offset, n);
                if (copyLen > n) System.arraycopy(prev, n, iv, 0, copyLen - n);
            } else {
                System.arraycopy(prev, 0, iv, offset - keyLen, Math.min(copyLen, ivLen));
            }
            offset += prev.length;
        }
        return new byte[][]{key, iv};
    }







    public static SecretKeySpec getSecretKey(String secretKey) {
        secretKey = secretKey.substring(0, 16);
        return new SecretKeySpec(secretKey.getBytes(), KEY_ALGORITHM);
    }
}
