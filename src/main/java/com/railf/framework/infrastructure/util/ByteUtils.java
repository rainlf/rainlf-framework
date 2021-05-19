package com.railf.framework.infrastructure.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;

/**
 * @author : rain
 * @date : 2020/7/30 15:36
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ByteUtils {
    /**
     * 16进制密码表
     */
    private static final char[] HEX_KEY = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 1 byte --> 2 char
     */
    public static String byteArr2HexString(byte[] data) {
        int len = data.length;
        char[] out = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            out[j++] = getChar((data[i] & 0xF0) >>> 4);
            out[j++] = getChar(data[i] & 0x0F);
        }
        return new String(out);
    }

    /**
     * 2 char -> 1 byte
     */
    public static byte[] hexString2ByteArr(String data) {
        char[] cArr = data.toCharArray();
        int len = cArr.length;
        if ((len & 0x01) != 0) {
            throw new RuntimeException("非法的Hex字符串");
        }

        byte[] out = new byte[len >> 1];
        int i = 0, j = 0;
        while (j < len) {
            int k = getIndex(cArr[j++]) << 4;
            k = k | getIndex(cArr[j++]);
            out[i++] = (byte) (k & 0xFF);
        }
        return out;
    }

    private static int getIndex(char c) {
        for (int i = 0; i < HEX_KEY.length; i++) {
            if (HEX_KEY[i] == c) {
                return i;
            }
        }
        throw new RuntimeException("非法的Hex字符: " + c);
    }

    private static char getChar(int i) {
        if (i < 0 || i > 15) {
            throw new RuntimeException("非法的Hex下标: " + i);
        }
        return HEX_KEY[i];
    }

    public static void main(String[] args) {
        String srcStr = "Hi I'am a test 艾欧尼亚 ……&%￥#&*（@！~·`+-";
        byte[] origin = srcStr.getBytes();
        String encodeStr = byteArr2HexString(origin);
        byte[] decodeOrigin = hexString2ByteArr(encodeStr);

        System.out.println("原始数据：" + srcStr);
        System.out.println("原始Byte：" + Arrays.toString(origin));
        System.out.println("Byte2String：" + encodeStr);
        System.out.println("还原Byte：" + Arrays.toString(decodeOrigin));
    }
}
