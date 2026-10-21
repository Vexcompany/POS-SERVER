/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package minimart.engine.service;

import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.KonstantaDao;
import minimart.engine.dao.VersionOnlineDao;
import minimart.engine.daoimpl.KonstantaDaoImpl;
import minimart.engine.daoimpl.VersionOnlineDaoImpl;
import minimart.engine.model.Konstanta;
import org.apache.commons.lang3.StringUtils;

public class KonstantaService {
    public static String DEV_TOKEN = "sinarmartmjk01";
    private KonstantaDao konstantaDao = new KonstantaDaoImpl();
    private VersionOnlineDao versionOnlineDao = new VersionOnlineDaoImpl();

    private void ensureOnlineConnection() {
        OnlineConnManager.getInstance().touch();
    }

    public int createConstant(Konstanta konstanta) {
        return this.konstantaDao.createConstant(konstanta);
    }

    public int updateConstant(Konstanta konstanta) {
        return this.konstantaDao.updateConstant(konstanta);
    }

    public int updateConstant(String constant, String value) {
        return this.konstantaDao.updateConstant(constant, value);
    }

    public String getConstantValue(String constantName) {
        return this.konstantaDao.getConstantValue(constantName);
    }

    public String getTipeTokoFirstLetterCap() {
        String constantName = "tipeToko";
        String result = this.konstantaDao.getConstantValue(constantName);
        String resultLow = result.toLowerCase();
        String resultFirstCap = StringUtils.capitalize((String)resultLow);
        return resultFirstCap;
    }

    public int checkColumnExist(String db, String table_name, String column_name) {
        return this.konstantaDao.checkColumnExist(db, table_name, column_name);
    }

    public int getMajorVersionOnline() {
        this.ensureOnlineConnection();
        return this.versionOnlineDao.getOnlineMajorVersion();
    }

    public void addColumnIfNotExists(String db, String tableName, String columnName, String columnType) {
        this.konstantaDao.addColumnIfNotExists(db, tableName, columnName, columnType, null, null);
    }

    public void addColumnIfNotExists(String db, String tableName, String columnName, String columnType, Boolean notNull, String defaultValue) {
        this.konstantaDao.addColumnIfNotExists(db, tableName, columnName, columnType, notNull, defaultValue);
    }
}

