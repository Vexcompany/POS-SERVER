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

import java.util.List;
import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.dao.BarcodeDao;
import minimart.engine.model.Barcode;
import minimart.engine.rowmapper.BarcodeRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class BarcodeDaoImpl
extends SimpleJdbcDaoSupport
implements BarcodeDao {
    public BarcodeDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createBarcodeNoId(Barcode barcode) {
        System.out.println(String.valueOf(barcode.getId()));
        System.out.println(barcode.getM_barang_id());
        System.out.println(barcode.getBarcode());
        System.out.println(barcode.getSatuan());
        System.out.println(barcode.getCreated().toString());
        System.out.println(barcode.getUpdated().toString());
        String sql = "INSERT INTO m_barcode (M_BARANG_ID, SATUAN, BARCODE, CREATED, UPDATED) VALUES (:m_barang_id, :satuan, :barcode, :created, :updated)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)barcode);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int createBarcode(Barcode barcode) {
        System.out.println(String.valueOf(barcode.getId()));
        System.out.println(barcode.getM_barang_id());
        System.out.println(barcode.getBarcode());
        System.out.println(barcode.getSatuan());
        System.out.println(barcode.getCreated().toString());
        System.out.println(barcode.getUpdated().toString());
        String sql = "INSERT INTO m_barcode (ID, M_BARANG_ID, SATUAN, BARCODE, CREATED, UPDATED) VALUES (:id, :m_barang_id, :satuan, :barcode, :created, :updated)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)barcode);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<Barcode> getBarcodesAll() {
        String sql = "SELECT * FROM m_barcode";
        List barcodes = null;
        try {
            barcodes = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new BarcodeRowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return barcodes;
    }

    @Override
    public List<Barcode> getBarcodes(String m_barang_id, String satuan) {
        String sql = "SELECT * FROM m_barcode WHERE M_BARANG_ID LIKE ? AND SATUAN LIKE ?";
        List barcodes = null;
        try {
            barcodes = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new BarcodeRowMapper(), new Object[]{m_barang_id, satuan});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return barcodes;
    }

    @Override
    public Barcode getBarcode(String barcode) {
        String sql = "SELECT * FROM m_barcode WHERE BARCODE = ?";
        Barcode barcodeData = null;
        try {
            barcodeData = (Barcode)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new BarcodeRowMapper(), new Object[]{barcode});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return barcodeData;
    }

    @Override
    public int updateBarcode(Barcode barcode) {
        String sql = "UPDATE m_barcode SET M_BARANG_ID=:m_barang_id, SATUAN=:satuan, BARCODE=:barcode, UPDATED=:updated WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)barcode);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int deleteBarcode(Barcode barcode, String barangId) {
        String sql = "DELETE FROM m_barcode WHERE BARCODE = ? AND M_BARANG_ID = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{barcode.getBarcode(), barangId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public boolean isBarcodeHasCreated(String barcode) {
        String sql = "SELECT * FROM m_barcode WHERE BARCODE = ?";
        Barcode barcodeData = null;
        try {
            barcodeData = (Barcode)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new BarcodeRowMapper(), new Object[]{barcode});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return barcodeData != null;
    }
}

