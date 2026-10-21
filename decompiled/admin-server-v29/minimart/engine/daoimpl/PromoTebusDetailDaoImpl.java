/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 *  org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport
 */
package minimart.engine.daoimpl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.dao.PromoTebusDetailDao;
import minimart.engine.model.PromoTebusDetail;
import minimart.engine.rowmapper.PromoTebusDetailRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PromoTebusDetailDaoImpl
extends SimpleJdbcDaoSupport
implements PromoTebusDetailDao {
    public PromoTebusDetailDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createPromoTebusDetail(PromoTebusDetail promoTebusDetail) {
        String sql = "INSERT INTO promo_tebus_detail (ID, PROMO_TEBUS_ID, M_BARANG_ID, JUMLAH, SATUAN, PROMO_BARANG_ID ,DISCOUNT, SATUAN_PROMO) VALUES (:id, :promoTebusId, :barangId, :jumlah, :satuan, :promoBarangId, :discount, :satuanPromo)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)promoTebusDetail);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<PromoTebusDetail> getPromoTebusDetailByPromoTebusId(String promoTebusId) {
        String sql = "SELECT * FROM promo_tebus_detail WHERE promo_tebus_id = ?";
        List promoTebusDetails = null;
        try {
            promoTebusDetails = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PromoTebusDetailRowMapper(), new Object[]{promoTebusId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoTebusDetails;
    }

    @Override
    public int deletePromoTebusDetailByPromoTebusId(String promoTebusId) {
        String sql = "DELETE FROM promo_tebus_detail WHERE promo_tebus_id = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{promoTebusId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updatePromoTebusDetail(PromoTebusDetail promoTebusDetail) {
        String sql = "UPDATE promo_tebus_detail SET JUMLAH=:jumlah, SATUAN=:satuan, PROMO_BARANG_ID=:promoBarangId, DISCOUNT=:discount, SATUAN_PROMO=:satuanPromo WHERE M_BARANG_ID=:barangId";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)promoTebusDetail);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<PromoTebusDetail> getPromoTebusDetailByIdPromoBarang(String barangId, String satuan) {
        String sql = "SELECT * FROM promo_tebus_detail JOIN promo_tebus ON (promo_tebus_detail.PROMO_TEBUS_ID = promo_tebus.ID) WHERE PROMO_BARANG_ID = ? AND SATUAN_PROMO = ? and STATUS = 'AKTIF' and tanggal_mulai < now() and DATE_ADD(tanggal_berakhir, INTERVAL 1 DAY) > now()";
        List promoTebusDetails = null;
        try {
            promoTebusDetails = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PromoTebusDetailRowMapper(), new Object[]{barangId, satuan});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoTebusDetails;
    }

    @Override
    public List<Object[]> getPromoTebusDetailByBarangId(String barangId, String satuan) {
        String sql = "SELECT promo_barang_id, nama, harga_jual_1 - discount as harga_promo, promo_tebus_detail.satuan, jumlah FROM promo_tebus_detail JOIN promo_tebus ON (promo_tebus_detail.PROMO_TEBUS_ID = promo_tebus.ID) JOIN m_barang ON (promo_tebus_detail.promo_barang_id = m_barang.id) WHERE STATUS = 'AKTIF' and tanggal_mulai < now() and DATE_ADD(tanggal_berakhir, INTERVAL 1 DAY) > now() and m_barang_id = ? and satuan = ?";
        LinkedList<Object[]> objects = new LinkedList<Object[]>();
        try {
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{barangId, satuan});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("promo_barang_id"), row.get("nama"), row.get("harga_promo"), row.get("satuan"), row.get("jumlah")};
                objects.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return objects;
    }

    @Override
    public PromoTebusDetail getPromoTebusDetailByBarangId(String barangId) {
        String sql = "SELECT * FROM promo_tebus_detail JOIN promo_tebus ON (promo_tebus_detail.PROMO_TEBUS_ID = promo_tebus.ID) WHERE M_BARANG_ID = ? OR PROMO_BARANG_ID = ? AND STATUS = 'AKTIF' and tanggal_mulai < now() and tanggal_berakhir > now()";
        PromoTebusDetail promoTebusDetail = null;
        try {
            promoTebusDetail = (PromoTebusDetail)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PromoTebusDetailRowMapper(), new Object[]{barangId, barangId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoTebusDetail;
    }

    @Override
    public PromoTebusDetail getPromoTebusDetailByBarangId2(String barangId) {
        String sql = "SELECT * FROM promo_tebus_detail JOIN promo_tebus ON (promo_tebus_detail.PROMO_TEBUS_ID = promo_tebus.ID) WHERE M_BARANG_ID = ?  AND STATUS = 'AKTIF' and tanggal_mulai <= now() and tanggal_berakhir >= now()";
        PromoTebusDetail promoTebusDetail = null;
        try {
            promoTebusDetail = (PromoTebusDetail)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PromoTebusDetailRowMapper(), new Object[]{barangId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoTebusDetail;
    }

    @Override
    public PromoTebusDetail getPromoTebusDetailByBarangId(String barangId, String satuan, String startDate, String endDate) {
        String sql = "SELECT * FROM promo_tebus_detail JOIN promo_tebus ON (promo_tebus_detail.PROMO_TEBUS_ID = promo_tebus.ID) WHERE M_BARANG_ID = ? AND SATUAN = ? AND STATUS = 'AKTIF' and tanggal_mulai <= ? and tanggal_berakhir >= ?";
        PromoTebusDetail promoHematDetail = null;
        try {
            promoHematDetail = (PromoTebusDetail)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PromoTebusDetailRowMapper(), new Object[]{barangId, satuan, startDate, endDate});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoHematDetail;
    }

    @Override
    public int[] upsertPromoTebusDetailBatch(List<PromoTebusDetail> datas) {
        int[] results = null;
        String sql_upsert = "INSERT INTO promo_tebus_detail (id, promo_tebus_id, m_barang_id, jumlah, satuan, promo_barang_id ,discount, satuan_promo)  VALUES (:id, :promoTebusId, :barangId, :jumlah, :satuan, :promoBarangId, :discount, :satuanPromo) ON DUPLICATE KEY UPDATE promo_tebus_id = VALUES(promo_tebus_id), m_barang_id = VALUES(m_barang_id), satuan = VALUES(satuan), jumlah = VALUES(jumlah), promo_barang_id = VALUES(promo_barang_id), discount = VALUES(discount), satuan_promo = VALUES(satuan_promo)";
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])datas.toArray());
        try {
            results = this.getSimpleJdbcTemplate().batchUpdate(sql_upsert, batch);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return results;
    }

    @Override
    public int deletePromoTebusDetailByIds(String ids) {
        String sql = "DELETE FROM promo_tebus_detail WHERE id in (" + ids + ")";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<PromoTebusDetail> getPromoTebusDetailAll() {
        String sql = "SELECT * FROM promo_tebus_detail";
        List promoTebusDetails = null;
        try {
            promoTebusDetails = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PromoTebusDetailRowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoTebusDetails;
    }
}

