package com.demo.jwt;

import com.demo.jwt.crypt.EncodeUtil;

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
}
