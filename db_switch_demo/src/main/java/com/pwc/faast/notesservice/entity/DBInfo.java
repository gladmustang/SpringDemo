package com.pwc.faast.notesservice.entity;

import lombok.Data;

@Data
public class DBInfo {
    private String key;
    private String dbUrl;
    private String userName;
    private String password;

    public DBInfo() {

    }
    public DBInfo(String pGuid, String dbUrl, String userName, String password){
        this.key = pGuid;
        this.dbUrl = dbUrl;
        this.userName = userName;
        this.password = password;
    }
}
