package in.codepredators.vedanta.room;

import android.util.Base64;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Decrypt {

    public String decrypt(String data) throws Exception{
        SecretKeySpec secretKeySpec=generatekey("0");
        Cipher cipher=Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
        byte[] decodevalue= Base64.decode(data,Base64.DEFAULT);
        byte[] d=cipher.doFinal(decodevalue);
        String output=new String(d);
        return output;
    }
    public SecretKeySpec generatekey(String key) throws Exception{
        MessageDigest messageDigest=MessageDigest.getInstance("SHA-256");
        byte[] bytes=key.getBytes("UTF-8");
        messageDigest.update(bytes,0,bytes.length);
        byte[] bytes1=messageDigest.digest();
        SecretKeySpec secretKeySpec=new SecretKeySpec(bytes1,"AES");
        return secretKeySpec;

    }
}
