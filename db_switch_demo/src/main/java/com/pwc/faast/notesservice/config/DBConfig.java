package com.pwc.faast.notesservice.config;

import com.pwc.faast.notesservice.entity.DBInfo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class DBConfig {

    public static LinkedHashMap<String, String> master = new LinkedHashMap<>();
    public  static ArrayList<Map> cluster = new ArrayList<>();

    public static void init() {
        initMaster();
        initCluster();
    }
    private static void initMaster() {
        master.put("key", "master");
        master.put("url", "jdbc:postgresql://localhost:5432/db_example");
        master.put("username", "postgres");
        master.put("password", "admin");
    }

    private static void initCluster() {
        LinkedHashMap<String, String> slave1 = new LinkedHashMap();
        slave1.put("key", "slave1"); //need to set pguid in future
        slave1.put("url", "jdbc:postgresql://localhost:5432/slave1");
        slave1.put("username", "postgres");
        slave1.put("password", "admin");
        cluster.add(slave1);


    }

}
