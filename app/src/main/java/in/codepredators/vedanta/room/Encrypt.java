package in.codepredators.vedanta.room;

import android.util.Base64;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt {
    public static String encrypt(String data) throws Exception{
        SecretKeySpec keySpec=generatekey("0");
        Cipher cipher=Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE,keySpec);
        byte[] encval=cipher.doFinal(data.getBytes());
        String encryptedvalue= Base64.encodeToString(encval,Base64.DEFAULT);
        return encryptedvalue;
    }
    public static SecretKeySpec generatekey(String key) throws Exception{
        MessageDigest messageDigest=MessageDigest.getInstance("SHA-256");
        byte[] bytes=key.getBytes("UTF-8");
        messageDigest.update(bytes,0,bytes.length);
        byte[] bytes1=messageDigest.digest();
        SecretKeySpec secretKeySpec=new SecretKeySpec(bytes1,"AES");
        return secretKeySpec;

    }
}
