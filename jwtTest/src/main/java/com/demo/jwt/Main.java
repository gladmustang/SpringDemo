package com.demo.jwt;

import io.jsonwebtoken.Claims;

public class Main {
    public static void main(String[] args) {

        Main main = new Main();
        main.testGenerateAndVeriry();

    }

    public void testFaastToken() {
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        String token ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmaXJzdE5hbWUiOiI5NGNmNTA5MmFhYTM0YjYyYjU4MjkxOWI1NTdhODBhZiIsImxhc3ROYW1lIjoiNmRmZmU4Y2JlNjg1N2EwZTE3YzBhOWIyZDVhMjU5MTYiLCJlbWFpbCI6ImVlNTA2NjQ4MjA0OTFiYTczNDZhYTYxYzUzZDdkNDc0IiwidUd1aWQiOiIxZDhiYzVlNTkzMWFlZThmMmMwNTZlMjc4NDdiNGE4MzdkOTljN2FjYThmODdmNzMxNGI5ZDE4ODVlYWRjZjliMWQxNWJiZjc2YTgwNGNkOGM0MjIyMWYxMmIxOGUxNDMiLCJpbnRlcm5hbEd1aWQiOm51bGwsImlhdCI6MTU5NTg1MDU5NiwiZXhwIjoxNTk1ODc5Mzk2fQ.WQMDLsRVSsQHuCn5rU2t207-NkWYIspy1SjeS2bGSLM";
        token.replaceAll(" ", "");
        Claims claims= jwtTokenUtil.getAllClaimsFromToken(token);
        System.out.println(claims.toString());
    }

    public void testGenerateAndVeriry() {
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        String token = jwtTokenUtil.generateToken("admin");
        Claims claims= jwtTokenUtil.getAllClaimsFromToken(token);
        System.out.println(claims.toString());
    }
}
