package com.ep.custom_honor_library;

import android.app.Application;

import com.lx.c_interface_library.OnIntentListener;

/**
 * 宿主 App 侧的调用入口。
 *
 * <p>三个方法与原来 DEX 中的 {@code chlOrganizeUtils} 一一对应，调用方代码无需改动语义，
 * 只是把 {@code chlOrganizeUtils.xxx(...)} 换成 {@code NativeEntry.xxx(...)}。
 *
 * <p>真正的实现类藏在 libchlcore.so 的加密载荷里，运行时解密后用
 * {@code InMemoryDexClassLoader} 在内存中加载，不落盘。
 *
 * <p>注意：本类必须放在宿主 App 的源码中（而不是打进加密 DEX），
 * 否则 {@code System.loadLibrary} 无法触发。
 */
public final class NativeEntry {

    static {
        // 对应 protect/out/<abi>/libchlcore.so
//        System.loadLibrary("chlcore");
    }

    private NativeEntry() {
    }




    /**
     * 初始化，需在 Application 启动阶段调用。
     *
     * @param application 宿主 Application
     */
    public static void initDef(Application application) {
        nativeInitDef(application);
    }

    /** 初始化后的策略处理 */
    public static void handlerPostInitStrategy() {
        nativeHandlerPostInitStrategy();
    }

    /**
     * 设置中间层弹窗回调。
     *
     * @param onIntentListener 宿主实现的回调接口
     */
    public static void setLauncherMiddleListener(OnIntentListener onIntentListener) {
        nativeSetLauncherMiddleListener(onIntentListener);
    }

    /**
     * 承载加密 DEX 的 ClassLoader。
     *
     * <p>仅供 {@link ChlComponentFactory} 在系统实例化组件时使用：
     * 系统默认用 App 的 PathClassLoader 按类名加载，而 {@code MiddleAdActivity}
     * 只存在于动态 DEX 中，必须换成这个 ClassLoader 才能找到。
     *
     * <p>首次调用会触发解密与加载；失败会抛 {@link RuntimeException}。
     *
     * @return 动态 DEX 的 ClassLoader，不会为 null
     */
    public static ClassLoader getDexClassLoader() {
        return nativeGetDexClassLoader();
    }

    private static native void nativeInitDef(Application application);

    private static native void nativeHandlerPostInitStrategy();

    private static native void nativeSetLauncherMiddleListener(OnIntentListener onIntentListener);

    private static native ClassLoader nativeGetDexClassLoader();
}
