package com.ep.custom_honor_library;

import android.app.Application;

/**
 * 宿主 App 侧的调用入口。
 *
 * <p>方法与加密 DEX 中的 {@code GUtils} 一一对应，调用方代码无需改动语义，
 * 只是把 {@code GUtils.xxx(...)} 换成 {@code NativeEntry.xxx(...)}。
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
        System.loadLibrary("chlcore");
    }

    private NativeEntry() {
    }

    /**
     * 注入解密密钥（32 字节）。
     *
     * <p>推荐用法：宿主自己（含原生下载逻辑）拿到密钥后，在
     * {@link #initDef(Application)} 之前调用本方法；不内置密钥（{@code --no-embed-key}）
     * 的构建必须走这条路径，否则 native 解密会失败。
     *
     * <pre>
     * byte[] key = MyKeyFetcher.download();   // 宿主自己的下载实现
     * NativeEntry.setKey(key);
     * NativeEntry.initDef(this);
     * </pre>
     *
     * <p>纯 native 侧下载的场景可不经过本类，直接调用 so 导出的 C 接口：
     * {@code int chlcore_set_key(const uint8_t* key, size_t len)}。
     *
     * @param StringKey 32 字节密钥
     * @return 是否注入成功（密钥为空或长度不为 32 返回 false）
     */
    public static boolean setKey(String StringKey) {

        byte[] key = hexToBytes(StringKey);
        if (key == null || key.length != 32) {
            return false;
        }
        return nativeSetKey(key);
    }


    /** 64 位十六进制字符串 → 32 字节密钥，格式非法返回 null */
    private static byte[] hexToBytes(String hex) {
        if (hex == null || hex.length() != 64) return null;
        byte[] out = new byte[32];
        for (int i = 0; i < 32; i++) {
            int hi = Character.digit(hex.charAt(i * 2), 16);
            int lo = Character.digit(hex.charAt(i * 2 + 1), 16);
            if (hi < 0 || lo < 0) return null;   // 含非 hex 字符
            out[i] = (byte) ((hi << 4) | lo);
        }
        return out;
    }

    /**
     * 初始化，需在 Application 启动阶段调用。
     *
     * <p>不内置密钥（{@code --no-embed-key}）的构建，须先通过 {@link #setKey(byte[])}
     * 注入密钥，本方法内部只做解密与加载，不发起任何网络请求。
     *
     * <p>DEX 内的 {@code GUtils.setCommon} 会转调 {@code ControllerUtils.initDef}，依次执行
     * {@code MMKV.initialize} → {@code initActivityListener} → {@code initSDK}
     * → {@code handlerPostInitStrategy} → {@code initFe}，后续策略不需要宿主再单独触发。
     *
     * @param application 宿主 Application
     */
    public static void initDef(Application application) {
        nativeInitDef(application);
    }

    /**
     * 运行时自检结果位掩码，用于排查「静默降级」。
     *
     * <p>{@code 0} 表示正常；非 0 表示已命中检测、功能处于空实现状态。各位：
     * <ul>
     *   <li>{@code 1} 调试器（TracerPid）</li>
     *   <li>{@code 2} Frida（maps / 线程名 / 端口）</li>
     *   <li>{@code 4} Hook 框架（Xposed / Magisk / Riru / Zygisk …）</li>
     *   <li>{@code 8} so 自身完整性校验失败（.text 被改或摘要未写入）</li>
     * </ul>
     *
     * <p>建议仅在调试期调用并打印；结果与 {@code initDef} 无关，可随时调用。
     */
    public static int getHardenFlags() {
        return nativeGetHardenFlags();
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

    private static native ClassLoader nativeGetDexClassLoader();

    /** 运行时自检位掩码，0=正常 */
    private static native int nativeGetHardenFlags();

    /** 写入 32 字节解密密钥，须在 nativeInitDef 之前调用 */
    private static native boolean nativeSetKey(byte[] key);
}
