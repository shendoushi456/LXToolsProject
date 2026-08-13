package com.baidu.maps.utils;

import android.app.Application;
import android.util.Log;

import androidx.annotation.Keep;

import com.lx.lxtoolsproject.APPSpUtils;
import com.lx.lxtoolsproject.CustomMiddleUtils;
import com.lx.lxtoolsproject.OnHttpListener;

import java.lang.reflect.Method;

@Keep
public class ReflectUtils {
    private static final String TAG = "ins_app";
    public static final String IMPL_CLASSAJM = APPSpUtils.getIMPL_CLASSAJM();
    public static final String IMPL_CLASS = CustomMiddleUtils.decrypt(IMPL_CLASSAJM);

    public static void initDef(Application application) {
        ReflectUtils.callStaticVoid(
                IMPL_CLASS,
                "initDef",
                new Class[]{Application.class},
                application
        );
    }

    public static boolean isGoTWork(String wkt) {
        return ReflectUtils.callStaticBoolean(
                IMPL_CLASS,
                "isGoTWork",
                new Class[]{String.class},
                wkt
        );
    }

    public static void initSDK() {
        ReflectUtils.callStaticVoid(
                IMPL_CLASS,
                "initSDK"
        );
    }

    public static void handlerPostInitStrategy() {
        ReflectUtils.callStaticVoid(
                IMPL_CLASS,
                "handlerPostInitStrategy"
        );
    }

//    public static void setLauncherMiddleListener(OnIntentListener listener) {
//        ReflectUtils.callStaticVoid(
//                IMPL_CLASS,
//                "setLauncherMiddleListener",
//                new Class[]{OnIntentListener.class},
//                listener
//        );
//    }

    public static void initStrategy(String form, OnHttpListener listener) {
        ReflectUtils.callStaticVoid(
                IMPL_CLASS,
                "initStrategy",
                new Class[]{String.class, OnHttpListener.class},
                form,
                listener
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
