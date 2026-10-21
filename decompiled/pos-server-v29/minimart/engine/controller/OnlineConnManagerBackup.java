/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.datasource.DriverManagerDataSource
 */
package minimart.engine.controller;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class OnlineConnManagerBackup {
    private static OnlineConnManagerBackup instance = new OnlineConnManagerBackup();
    DriverManagerDataSource dataSource = new DriverManagerDataSource();

    public static OnlineConnManagerBackup getInstance() {
        return instance;
    }

    public void setAutoCommit() {
    }

    public OnlineConnManagerBackup() {
        this.dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        this.dataSource.setUrl("jdbc:mysql://178.128.112.51:3306/minimarket_online?serverTimezone=Asia/Jakarta");
        this.dataSource.setUsername("asinkronus");
        this.dataSource.setPassword("asinkronus2017");
    }

    public DriverManagerDataSource getDataSource() {
        System.out.println("LOCAL DS USER: " + this.dataSource.getUsername());
        System.out.println("LOCAL DS URL: " + this.dataSource.getUrl());
        return this.dataSource;
    }
}

