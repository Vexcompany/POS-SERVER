/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 *  org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport
 */
package minimart.engine.daoimpl;

import java.util.List;
import javax.sql.DataSource;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.RegionOnlineDao;
import minimart.engine.model.CustomerAlamat;
import minimart.engine.model.Region;
import minimart.engine.rowmapper.CustomerAlamatRowMapper;
import minimart.engine.rowmapper.RegionRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class RegionOnlineDaoImpl
extends SimpleJdbcDaoSupport
implements RegionOnlineDao {
    public RegionOnlineDaoImpl() {
        this.setDataSource((DataSource)OnlineConnManager.getInstance().getDataSource());
    }

    @Override
    public List<Region> getRegion(String searchQuery) {
        String sql = "SELECT     p.id AS id_provinsi, p.name AS provinsi,     k.id AS id_kota, k.name AS kota,     kc.id AS id_kecamatan, kc.name AS kecamatan,     kl.id AS id_kelurahan, kl.name AS kelurahan FROM minimarket_online.reg_provinsi p STRAIGHT_JOIN reg_kota k ON p.id = k.provinsi_id STRAIGHT_JOIN reg_kecamatan kc ON k.id = kc.kota_id STRAIGHT_JOIN reg_kelurahan kl ON kc.id = kl.kecamatan_id WHERE     UPPER(p.name) LIKE ? OR     UPPER(k.name) LIKE ? OR     UPPER(kc.name) LIKE ? OR     UPPER(kl.name) LIKE ? ORDER BY p.name, k.name, kc.name, kl.name";
        String likeQuery = "%" + searchQuery.toUpperCase() + "%";
        List regions = null;
        try {
            regions = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new RegionRowMapper(), new Object[]{likeQuery, likeQuery, likeQuery, likeQuery});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return regions;
    }

    @Override
    public Region getRegionByIdKelurahan(String idKelurahan) {
        String sql = "SELECT     p.id AS id_provinsi, p.name AS provinsi,     k.id AS id_kota, k.name AS kota,     kc.id AS id_kecamatan, kc.name AS kecamatan,     kl.id AS id_kelurahan, kl.name AS kelurahan FROM minimarket_online.reg_provinsi p STRAIGHT_JOIN reg_kota k ON p.id = k.provinsi_id STRAIGHT_JOIN reg_kecamatan kc ON k.id = kc.kota_id STRAIGHT_JOIN reg_kelurahan kl ON kc.id = kl.kecamatan_id WHERE kl.id = ? ORDER BY p.name, k.name, kc.name, kl.name";
        try {
            return (Region)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new RegionRowMapper(), new Object[]{idKelurahan});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
            return null;
        }
    }

    @Override
    public CustomerAlamat getCustomerAlamatPrimaryByIdCustomer(String idCustomer) {
        String sql = "SELECT * FROM minimarket_online.m_customer_alamat WHERE id_customer = ? AND is_default = 1 AND deleted_at IS NULL LIMIT 1";
        try {
            return (CustomerAlamat)this.getSimpleJdbcTemplate().queryForObject(sql, (RowMapper)new CustomerAlamatRowMapper(), new Object[]{idCustomer});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
            return null;
        }
    }

    @Override
    public int insertCustomerAlamat(CustomerAlamat alamat) {
        String sql = "INSERT INTO minimarket_online.m_customer_alamat (id_customer, judul, penerima, alamat, notes_driver, telepon, latitude, longitude, is_default, id_reg_kelurahan, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
        try {
            this.getSimpleJdbcTemplate().update("UPDATE minimarket_online.m_customer_alamat SET is_default = 0 WHERE id_customer = ?", new Object[]{alamat.getIdCustomer()});
            return this.getSimpleJdbcTemplate().update(sql, new Object[]{alamat.getIdCustomer(), alamat.getJudul(), alamat.getPenerima(), alamat.getAlamat(), alamat.getNotesDriver(), alamat.getTelepon(), alamat.getLatitude(), alamat.getLongitude(), alamat.getIsDefault(), alamat.getIdRegKelurahan()});
        }
        catch (DataAccessException e) {
            System.out.println("Insert failed: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int updateCustomerAlamat(CustomerAlamat alamat) {
        String sql = "UPDATE minimarket_online.m_customer_alamat SET judul = ?, penerima = ?, alamat = ?, notes_driver = ?, telepon = ?, latitude = ?, longitude = ?, is_default = ?, id_reg_kelurahan = ?, updated_at = NOW() WHERE id = ?";
        try {
            this.getSimpleJdbcTemplate().update("UPDATE minimarket_online.m_customer_alamat SET is_default = 0 WHERE id_customer = ?", new Object[]{alamat.getIdCustomer()});
            return this.getSimpleJdbcTemplate().update(sql, new Object[]{alamat.getJudul(), alamat.getPenerima(), alamat.getAlamat(), alamat.getNotesDriver(), alamat.getTelepon(), alamat.getLatitude(), alamat.getLongitude(), alamat.getIsDefault(), alamat.getIdRegKelurahan(), alamat.getId()});
        }
        catch (DataAccessException e) {
            System.out.println("Update failed: " + e.getMessage());
            return 0;
        }
    }
}

