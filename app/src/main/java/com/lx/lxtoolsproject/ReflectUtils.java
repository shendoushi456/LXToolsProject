package com.lx.lxtoolsproject;

import android.app.Application;
import android.util.Log;

import java.lang.reflect.Method;

public class ReflectUtils {
    private static final String TAG = "myjava";

    public static final String IMPL_CLASS = "com.ep.custom_honor_library.chlOrganizeUtils";

    public static void initDef(Application application) {
        ReflectUtils.callStaticVoid(
                IMPL_CLASS,
                "initDef",
                new Class[]{Application.class},
                application
        );
    }

    public static Object callStaticMethod(
            String className,
            String methodName,
            Class<?>[] parameterTypes,
            Object... args) {
        Log.e(TAG, "callStaticMethod: methodName:" + methodName );
        try {
            Class<?> clazz = Class.forName(className);

            Method method = clazz.getDeclaredMethod(
                    methodName,
                    parameterTypes == null ? new Class<?>[0] : parameterTypes);

            method.setAccessible(true);

            return method.invoke(null, args);

        } catch (Throwable e) {
            Log.e(TAG, "callStaticMethod: methodName:" + methodName +",error:"+e.getMessage() );
            return null;
        }
    }

    public static void callStaticVoid(
            String className,
            String methodName,
            Class<?>[] parameterTypes,
            Object... args) {

        callStaticMethod(className, methodName, parameterTypes, args);
    }

    public static void callStaticVoid(
            String className,
            String methodName) {

        callStaticMethod(className, methodName, null);
    }

    public static boolean callStaticBoolean(
            String className,
            String methodName,
            Class<?>[] parameterTypes,
            Object... args) {

        Object result = callStaticMethod(
                className,
                methodName,
                parameterTypes,
                args);

        return result instanceof Boolean && (Boolean) result;
    }
}
