package com.pwc.faast.notesservice.entity;

import lombok.Data;

@Data
public class DBInfo {
    private String pGuid;
    private String dbUrl;
    private String userName;
    private String password;

    public DBInfo(String pGuid, String dbUrl, String userName, String password){
        this.pGuid = pGuid;
        this.dbUrl = dbUrl;
        this.userName = userName;
        this.password = password;
    }
}
