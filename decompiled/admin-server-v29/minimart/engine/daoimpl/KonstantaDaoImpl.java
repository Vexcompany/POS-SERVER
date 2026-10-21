/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 *  org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport
 */
package minimart.engine.daoimpl;

import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.dao.KonstantaDao;
import minimart.engine.model.Konstanta;
import minimart.engine.rowmapper.KonstantaRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class KonstantaDaoImpl
extends SimpleJdbcDaoSupport
implements KonstantaDao {
    public KonstantaDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createConstant(Konstanta konstanta) {
        String sql = "INSERT INTO m_constants (CONSTANT, VALUE) VALUES (:konstan, :value)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)konstanta);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updateConstant(String constant, String value) {
        String sql = "UPDATE m_constants SET value = ? WHERE constant = ?;";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{value, constant});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updateConstant(Konstanta konstanta) {
        String sql = "UPDATE m_constants SET CONSTANT=:konstan, VALUE=:value WHERE CONSTANT=:konstan";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)konstanta);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public String getConstantValue(String constantName) {
        String sql = "SELECT * FROM m_constants WHERE CONSTANT = ?";
        Konstanta konstanta = new Konstanta();
        try {
            konstanta = (Konstanta)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new KonstantaRowMapper(), new Object[]{constantName});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return konstanta.getValue();
    }

    @Override
    public int checkColumnExist(String db, String table_name, String column_name) {
        String sql = "SELECT COUNT(*) as exist FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ? AND COLUMN_NAME = ?";
        int exist = 0;
        try {
            exist = (Integer)this.getSimpleJdbcTemplate().queryForObject(sql, Integer.class, new Object[]{db, table_name, column_name});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        System.out.println("Exist: " + exist);
        return exist;
    }

    @Override
    public void addColumnIfNotExists(String db, String tableName, String columnName, String columnType, Boolean notNull, String defaultValue) {
        try {
            int exists = this.checkColumnExist(db, tableName, columnName);
            if (exists == 0) {
                StringBuilder sql = new StringBuilder();
                sql.append("ALTER TABLE ").append(tableName).append(" ADD COLUMN ").append(columnName).append(" ").append(columnType);
                if (Boolean.TRUE.equals(notNull)) {
                    sql.append(" NOT NULL");
                }
                if (defaultValue != null && !defaultValue.trim().isEmpty()) {
                    sql.append(" DEFAULT ").append(defaultValue);
                }
                this.getSimpleJdbcTemplate().getJdbcOperations().execute(sql.toString());
                System.out.println("Column '" + columnName + "' added to table '" + tableName + "'");
            } else {
                System.out.println("Column '" + columnName + "' already exists in table '" + tableName + "'");
            }
        }
        catch (DataAccessException e) {
            System.out.println("Error while altering table: " + e.getMessage());
        }
    }
}

