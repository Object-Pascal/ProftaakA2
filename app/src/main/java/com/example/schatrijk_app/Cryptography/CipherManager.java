package com.example.schatrijk_app.Cryptography;

import android.util.Base64;
import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CipherManager {
    private static SecretKeySpec key;

    private static void init() {
        if (key == null) {
            byte[] bytes = {0x22, 0x4F, 0x45, 0x10, 0x77, 0x63, 0x2E, 0x63};
            key = new SecretKeySpec(bytes, "DES");
        }
    }

    public static String encryptString(String data) {
        init();

        try {
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] buffer = data.getBytes("UTF8");
            byte[] encryptedBuffer = cipher.doFinal(buffer);
            return Base64.encodeToString(encryptedBuffer, Base64.CRLF);
        }
        catch (Exception e) {
            Log.d("CRYPTO ERROR", e.getMessage());
            return "";
        }
    }

    public static String decryptString(String data) {
        init();

        try {
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] encryptedBuffer = Base64.decode(data.getBytes(), Base64.CRLF);
            byte[] decryptedData = cipher.doFinal(encryptedBuffer);
            return new String(decryptedData);
        }
        catch (Exception e) {
            Log.d("CRYPTO ERROR", e.getMessage());
            return "";
        }
    }
}
