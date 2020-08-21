package com.demo.jwt;

import com.demo.jwt.crypt.Crypto;
import com.demo.jwt.crypt.EncodeUtil;
import io.jsonwebtoken.Claims;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws Exception {
//        Security.setProperty("crypto.policy", "unlimited");
        //to enable unlimited crypto
        Crypto.fixKeyLength();
        Main main = new Main();
//        main.testGenerateAndVeriry();
//        Crypto.fixKeyLength();
//        main.testFaastToken();
//          main.testCrypto();
            main.testProjectDBUrlEncrypt();
    }

    public void testCrypto() throws Exception {
        String encryptptMessage = "2fc6c9bc071f4e7b8023d2b311da8e10";
        String result = Crypto.decrypt(encryptptMessage);
        System.out.println(result);
        encryptptMessage =  "44f19f0da48ab8ab92b53c8744695d6c";
        result = Crypto.decryptForNodejsDefault(encryptptMessage);
        System.out.println(result);

        String text = Crypto.encrypt("Hello, world");
        result = Crypto.decrypt(text);
        System.out.println(result);
    }


    public void testFaastToken() {
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        String token ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmaXJzdE5hbWUiOiI5NGNmNTA5MmFhYTM0YjYyYjU4MjkxOWI1NTdhODBhZiIsImxhc3ROYW1lIjoiNmRmZmU4Y2JlNjg1N2EwZTE3YzBhOWIyZDVhMjU5MTYiLCJlbWFpbCI6ImVlNTA2NjQ4MjA0OTFiYTczNDZhYTYxYzUzZDdkNDc0IiwidUd1aWQiOiIxZDhiYzVlNTkzMWFlZThmMmMwNTZlMjc4NDdiNGE4MzdkOTljN2FjYThmODdmNzMxNGI5ZDE4ODVlYWRjZjliMWQxNWJiZjc2YTgwNGNkOGM0MjIyMWYxMmIxOGUxNDMiLCJpbnRlcm5hbEd1aWQiOm51bGwsImlhdCI6MTU5NTg1MDU5NiwiZXhwIjoxNTk1ODc5Mzk2fQ.WQMDLsRVSsQHuCn5rU2t207-NkWYIspy1SjeS2bGSLM";
        token.replaceAll(" ", "");
        Claims claims= jwtTokenUtil.getAllClaimsFromToken(token);
        HashMap<String, Object> newClaims = new HashMap<>();
        claims.forEach((k,v)->{
            System.out.println(v);
            if(v instanceof String) {
                String newValue = null;
                try {
                    newValue = Crypto.decrypt((String) v);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                newClaims.put(k,newValue);
            } else {
                newClaims.put(k,v);
            }

        });
        System.out.println(newClaims.toString());
    }

    public void testGenerateAndVeriry() {
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        String token = jwtTokenUtil.generateToken("admin");
        Claims claims= jwtTokenUtil.getAllClaimsFromToken(token);
        System.out.println(claims.toString());
    }

    public void testProjectDBUrl() throws Exception {
        String encodedDBUrl = "MDhlNWIzMDJlNWNlZGYzOTg1OTM2N2YyNGNiYWEyODg6ODIyMWI5ZTZjNTljZjdiNDcyNjZmYmM0ZDY3Mzg2N2FhYTMxN2IwZGI0MDM2ZTE4MDgzMmY2NDQwNTQzODYyYTk5MDYxNGM0ZmVjNmQ2ZGQyOTQ2YzgyMjk5M2FjZWEzNmQwMjFhODA3YWNkOWRkMzMzMmY1NjczYTI0Y2Y1ZTE";
        String key256 = "abcdefghijklmnopqrstuvwxyz123456";
        String result = ProjectDBUtil.decryptDBUrl(encodedDBUrl, key256);
        System.out.println(result);
    }

    public void testProjectDBUrlEncrypt() {
        try {
            String dburl = "postgres://username:password@hostname:5432/dbname";
            String key256 = "abcdefghijklmnopqrstuvwxyz123456";

            String encodedDBUrl = ProjectDBUtil.encryptDBUrl(dburl,key256);
            System.out.println(encodedDBUrl);
            String decodedDBUrl = ProjectDBUtil.decryptDBUrl(encodedDBUrl, key256);
            System.out.println(decodedDBUrl);
        } catch(Exception e){
            e.printStackTrace();
        }

    }
}
