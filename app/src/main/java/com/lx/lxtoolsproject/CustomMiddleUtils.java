package com.lx.lxtoolsproject;


import android.util.Log;

import java.lang.reflect.Method;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class CustomMiddleUtils {

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
        try {
            // 这里使用 Base64 作为演示，实际可使用 XOR 或更复杂的算法

           String s =  new String(Base64.getDecoder().decode(input));
            Log.i("AD_LOG","解析方法是==="+s);
            return s;
        } catch (Exception e) {
            return input; // 如果不是 Base64，返回原字符串
        }
    }

}
