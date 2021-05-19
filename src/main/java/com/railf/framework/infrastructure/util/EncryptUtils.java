package com.railf.framework.infrastructure.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * @author : rain
 * @date : 2020/6/10 18:45
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EncryptUtils {

    /**
     * 加密密钥
     */
    private static final String key = "0123456789ABCDEF";
    private static final Key aesKey = new SecretKeySpec(key.getBytes(), "AES");

    public static byte[] encrypt(byte[] decrypted) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            return cipher.doFinal(decrypted);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            throw new RuntimeException("Encrypt failed: " + e.getMessage(), e);
        }
    }

    public static byte[] decrypt(byte[] encrypted) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            return cipher.doFinal(encrypted);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            throw new RuntimeException("Decrypt failed: " + e.getMessage(), e);
        }
    }

    public static String encryptString(String s) {
        byte[] b = s.getBytes(StandardCharsets.UTF_8);
        for (int i = 0; i < b.length; i++) {
            b[i] = i % 2 == 0 ? ++b[i] : --b[i];
        }
        return new String(b);
    }

    public static String decryptString(String s) {
        byte[] b = s.getBytes(StandardCharsets.UTF_8);
        for (int i = 0; i < b.length; i++) {
            b[i] = i % 2 == 0 ? --b[i] : ++b[i];
        }
        return new String(b);
    }
}
