package com.demo.jwt;

import com.demo.jwt.crypt.EncodeUtil;

import java.security.SecureRandom;
import java.util.Base64;

public class ProjectDBUtil {

    public static String decryptDBUrl(String base64DBurl, String key256){
        try {
            final Base64.Decoder decoder = Base64.getDecoder();
            byte[] bytes = decoder.decode(base64DBurl);
            String  hexUrl = new String(bytes);
            String[] parts = hexUrl.split(":");
            String ivHex = parts[0];
            String urlHex= parts[1];

            byte[] iv = org.apache.commons.codec.binary.Hex.decodeHex(ivHex.toCharArray());
            byte[] url = org.apache.commons.codec.binary.Hex.decodeHex(urlHex.toCharArray());
            //key need to transform to hex before call aesDecryptToCBC
            String key256Hex = org.apache.commons.codec.binary.Hex.encodeHexString(key256.getBytes());
            byte[] decryptbytes = EncodeUtil.aesDecryptToCBC(url,key256Hex,iv);
            return new String(decryptbytes);
        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException("decyprtDBurl error");
        }

    }

    public static String encryptDBUrl(String dbUrl, String key256) {
        try {
            byte[] iv = new byte[16];
            new SecureRandom().nextBytes(iv);
            byte[] contentbytes = dbUrl.getBytes();
            String key256Hex = org.apache.commons.codec.binary.Hex.encodeHexString(key256.getBytes());
            byte[] encryptbytes = EncodeUtil.aesEncryptToCBC(contentbytes,key256Hex,iv);
            char[] encryptbytesHex =org.apache.commons.codec.binary.Hex.encodeHex(encryptbytes);
            char[] ivHex =org.apache.commons.codec.binary.Hex.encodeHex(iv);
            String encoded = new String(ivHex) + ":" + new String(encryptbytesHex);
            final Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(encoded.getBytes());
        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException("encryptDBUrl error");
        }
    }
}
