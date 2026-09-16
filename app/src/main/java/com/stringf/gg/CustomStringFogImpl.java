package com.stringf.gg;

import com.github.megatronking.stringfog.IStringFog;

import java.nio.charset.StandardCharsets;

/**
 * 自定义算法实现，此文件存储目录路径须和其包名一致
 *
 * @author Sundy
 * @since 2019/3/4 23:41
 */
public class CustomStringFogImpl implements IStringFog {

    @Override
    public byte[] encrypt(String data, byte[] key) {
        return xor(data.getBytes(StandardCharsets.UTF_8), key);
    }

    @Override
    public String decrypt(byte[] data, byte[] key) {
        return new String(xor(data, key), StandardCharsets.UTF_8);
    }

    @Override
    public boolean shouldFog(String dåata) {
        // 控制指定字符串是否加密
        // 建议过滤掉不重要或者过长的字符串
        return true;
    }

    private static byte[] xor(byte[] data, byte[] key) {
        int len = data.length;
        int lenKey = key.length;
        int i = 0;
        int j = 0;
        while (i < len) {
            if (j >= lenKey) {
                j = 0;
            }
            data[i] = (byte) (data[i] ^ key[j]);
            i++;
            j++;
        }
        return data;
    }

}
