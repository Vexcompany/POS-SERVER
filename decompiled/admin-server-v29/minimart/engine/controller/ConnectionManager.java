/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.datasource.DriverManagerDataSource
 */
package minimart.engine.controller;

import minimart.engine.model.DbCredential;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class ConnectionManager {
    private static ConnectionManager instance = new ConnectionManager();
    DriverManagerDataSource dataSource = new DriverManagerDataSource();

    public static ConnectionManager getInstance() {
        return instance;
    }

    public void setAutoCommit() {
    }

    public void configure(DbCredential cred) {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        StringBuilder urlSb = new StringBuilder();
        urlSb.append("jdbc:mysql://");
        urlSb.append(cred.getServer_ip_address());
        urlSb.append(":");
        urlSb.append(cred.getMysql_port());
        urlSb.append("/");
        urlSb.append(cred.getMysql_database());
        ds.setUrl(urlSb.toString());
        ds.setUsername(cred.getMysql_username());
        ds.setPassword(cred.getMysql_password());
        this.dataSource = ds;
        System.out.println(this.dataSource.getUrl());
    }

    public DriverManagerDataSource getDataSource() {
        return this.dataSource;
    }

    public boolean testConnection() {
        try {
            this.dataSource.getConnection().close();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

