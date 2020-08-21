package com.demo.jwt.crypt;

import org.apache.commons.lang.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;

public class EncodeUtil {

    public static void main(String[] args) throws Exception {
        //key是16进制，需要转换为bytes，转换后bytes长度为16，即aes128，如果bytes长度是32则是aes256
        //也就是说keybytes.length须满足16的整数倍
        String key128 = "c4b84456c1379bec99c4d1b7e9f13173";
        String key256 = "c4b84456c1379bec99c4d1b7e9f13173c4b84456c1379bec99c4d1b7e9f13173";
        //iv.length须满足16的整数倍
        byte[] iv = "abcdefgh12345678".getBytes("UTF-8");
        String content_str = "helloworld 你好";
        byte[] contentbytes = content_str.getBytes("utf-8");

        //ecb128 bytes
        byte[] encryptbytes = EncodeUtil.aesEncryptToECB(contentbytes,key128);
        byte[] decryptbytes = EncodeUtil.aesDecryptToECB(encryptbytes,key128);
        System.out.println(new String(decryptbytes,"utf-8"));

        //ecb256 bytes
        encryptbytes = EncodeUtil.aesEncryptToECB(contentbytes,key256);
        decryptbytes = EncodeUtil.aesDecryptToECB(encryptbytes,key256);
        System.out.println(new String(decryptbytes,"utf-8"));

        //ecb128 String
        String encryptString = EncodeUtil.aesEncryptToECB(content_str,key128);
        String decryptString = EncodeUtil.aesDecryptToECB(encryptString,key128);
        System.out.println(decryptString);

        //ecb256 String
        encryptString = EncodeUtil.aesEncryptToECB(content_str,key256);
        decryptString = EncodeUtil.aesDecryptToECB(encryptString,key256);
        System.out.println(decryptString);

        //cbc128 bytes
        encryptbytes = EncodeUtil.aesEncryptToCBC(contentbytes,key128,iv);
        decryptbytes = EncodeUtil.aesDecryptToCBC(encryptbytes,key128,iv);
        System.out.println(new String(decryptbytes,"utf-8"));

        //cbc256 bytes
        encryptbytes = EncodeUtil.aesEncryptToCBC(contentbytes,key256,iv);
        decryptbytes = EncodeUtil.aesDecryptToCBC(encryptbytes,key256,iv);
        System.out.println(new String(decryptbytes,"utf-8"));

        //cbc128 String
        encryptString = EncodeUtil.aesEncryptToCBC(content_str,key128,iv);
        decryptString = EncodeUtil.aesDecryptToCBC(encryptString,key128,iv);
        System.out.println(decryptString);

        //cbc256 String
        encryptString = EncodeUtil.aesEncryptToCBC(content_str,key256,iv);
        decryptString = EncodeUtil.aesDecryptToCBC(encryptString,key256,iv);
        System.out.println(decryptString);

    }

    /**
     * base 64 encode
     *
     * @param bytes 待编码的byte[]
     * @return 编码后的base 64 code
     */
    public static String base64Encode(byte[] bytes) {
        return new BASE64Encoder().encode(bytes);
    }

    /**
     * base 64 decode
     *
     * @param base64Code 待解码的base 64 code
     * @return 解码后的byte[]
     * @throws Exception
     */
    public static byte[] base64Decode(String base64Code) throws Exception {
        return StringUtils.isEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);
    }

    /**
     * 验证密钥长度是否有效
     *
     * @param key 密钥bytes
     * @throws Exception
     */
    public static void checkkey(byte[] key) throws Exception {

        if(key.length != 16 && key.length != 32) {
            throw new Exception("密钥长度错误，key byte[]必须是16或者32位");
        }
    }

    /**
     * AES加密 aes-128/256-ecb
     *
     * @param content    待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的byte[]
     * @throws Exception
     */
    public static byte[] aesEncryptToECB(byte[] content, String encryptKey) throws Exception {
        byte[] key = org.apache.commons.codec.binary.Hex.decodeHex(encryptKey.toCharArray());
        checkkey(key);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"));
        return cipher.doFinal(content);
    }



    /**
     * AES加密 aes-128/256-ecb
     *
     * @param content    待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的base64字符串
     * @throws Exception
     */
    public static String aesEncryptToECB(String content, String encryptKey) throws Exception {
        byte[] key = org.apache.commons.codec.binary.Hex.decodeHex(encryptKey.toCharArray());
        checkkey(key);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"));
        return base64Encode(cipher.doFinal(content.getBytes("utf-8")));
    }


    /**
     * AES解密 aes-128/256-ecb
     *
     * @param content 待解密的byte[]
     * @param decryptKey   解密密钥
     * @return 解密后的byte[]
     * @throws Exception
     */
    public static byte[] aesDecryptToECB(byte[] content, String decryptKey) throws Exception {
        byte[] key = org.apache.commons.codec.binary.Hex.decodeHex(decryptKey.toCharArray());
        checkkey(key);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"));
        byte[] decryptBytes = cipher.doFinal(content);
        return decryptBytes;
    }



    /**
     * AES解密 aes-128/256-ecb
     *
     * @param content 待解密的byte[]
     * @param decryptKey   解密密钥
     * @return 解密后的String
     * @throws Exception
     */
    public static String aesDecryptToECB(String content, String decryptKey) throws Exception {
        byte[] key = org.apache.commons.codec.binary.Hex.decodeHex(decryptKey.toCharArray());
        checkkey(key);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"));
        byte[] decryptBytes = cipher.doFinal(base64Decode(content));
        return new String(decryptBytes,"utf-8");
    }


    /**
     * AES加密 aes-128/256-cbc
     *
     * @param content 待解密的byte[]
     * @param encryptKey   加密密钥
     * @param iv 偏移
     * @return 解密后的byte[]
     * @throws Exception
     */
    public static byte[] aesEncryptToCBC(byte[] content, String encryptKey,byte[] iv) throws Exception {
        byte[] key = org.apache.commons.codec.binary.Hex.decodeHex(encryptKey.toCharArray());
        checkkey(key);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        //算法参数
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"),paramSpec);
        return cipher.doFinal(content);
    }

    /**
     * AES解密 aes-128/256-cbc
     *
     * @param content 待解密的byte[]
     * @param decryptKey   解密密钥
     * @param iv 偏移
     * @return 解密后的byte[]
     * @throws Exception
     */
    public static byte[] aesDecryptToCBC(byte[] content, String decryptKey,byte[] iv) throws Exception {
        byte[] key = org.apache.commons.codec.binary.Hex.decodeHex(decryptKey.toCharArray());
        checkkey(key);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        //算法参数
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"),paramSpec);
        return cipher.doFinal(content);
    }

    /**
     * AES加密 aes-128/256-cbc
     *
     * @param content 待解密的byte[]
     * @param encryptKey   加密密钥
     * @param iv 偏移
     * @return 解密后的byte[]
     * @throws Exception
     */
    public static String aesEncryptToCBC(String content, String encryptKey,byte[] iv) throws Exception {
        byte[] key = org.apache.commons.codec.binary.Hex.decodeHex(encryptKey.toCharArray());
        checkkey(key);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        //算法参数
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"),paramSpec);
        return base64Encode(cipher.doFinal(content.getBytes("utf-8")));
    }

    /**
     * AES解密 aes-128/256-cbc
     *
     * @param content 待解密的byte[]
     * @param decryptKey   解密密钥
     * @param iv 偏移
     * @return 解密后的byte[]
     * @throws Exception
     */
    public static String aesDecryptToCBC(String content, String decryptKey,byte[] iv) throws Exception {
        byte[] key = org.apache.commons.codec.binary.Hex.decodeHex(decryptKey.toCharArray());
        checkkey(key);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        //算法参数
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"),paramSpec);
        byte[] decryptBytes = cipher.doFinal(base64Decode(content));
        return new String(decryptBytes,"utf-8");
    }
}
