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
import minimart.engine.dao.PromoTebusMurahDetailDao;
import minimart.engine.model.PromoTebusMurahDetail;
import minimart.engine.rowmapper.PromoTebusMurahDetailRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PromoTebusMurahDetailDaoImpl
extends SimpleJdbcDaoSupport
implements PromoTebusMurahDetailDao {
    public PromoTebusMurahDetailDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int create(PromoTebusMurahDetail promoTebusMurahDetail) {
        String sql = "INSERT INTO promo_tebus_murah_detail (ID, PROMO_TEBUS_MURAH_ID, M_BARANG_ID, SATUAN, MINIMUM_BELANJA, HARGA_PROMO, MAKSIMAL_KUANTITI) VALUES (:id, :promoTebusMurahId, :barangId, :satuan, :minimumBelanja, :hargaPromo, :maksimalKuantiti)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)promoTebusMurahDetail);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int update(PromoTebusMurahDetail promoTebusMurahDetail) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<PromoTebusMurahDetail> getPromoTebusMurahDetail(String promoTebusMurahId) {
        String sql = "SELECT * FROM promo_tebus_murah_detail WHERE PROMO_TEBUS_MURAH_ID = ?";
        List promoTebusMurahDetails = null;
        try {
            promoTebusMurahDetails = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PromoTebusMurahDetailRowMapper(), new Object[]{promoTebusMurahId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoTebusMurahDetails;
    }

    @Override
    public int deletePromoTebusMurahDetail(String promoTebusMurahId) {
        String sql = "DELETE FROM promo_tebus_murah_detail WHERE PROMO_TEBUS_MURAH_ID = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{promoTebusMurahId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public PromoTebusMurahDetail getPromoTebusMurahDetailByBarangId(String barangId, String satuan, String startDate, String endDate) {
        String sql = "SELECT * FROM promo_tebus_murah_detail JOIN promo_tebus_murah ON (promo_tebus_murah_detail.PROMO_TEBUS_MURAH_ID = promo_tebus_murah.ID) WHERE M_BARANG_ID = ? AND SATUAN = ? AND STATUS = 'AKTIF' and tanggal_mulai <= ? and tanggal_berakhir >= ? order by 'CREATED' LIMIT 1";
        PromoTebusMurahDetail promoTebusMurahDetail = null;
        try {
            promoTebusMurahDetail = (PromoTebusMurahDetail)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PromoTebusMurahDetailRowMapper(), new Object[]{barangId, satuan, startDate, endDate});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoTebusMurahDetail;
    }

    @Override
    public PromoTebusMurahDetail getPromoTebusMurahDetailByBarangId(String barangId, String startDate, String endDate) {
        String sql = "SELECT * FROM promo_tebus_murah_detail JOIN promo_tebus_murah ON (promo_tebus_murah_detail.PROMO_TEBUS_MURAH_ID = promo_tebus_murah.ID) WHERE M_BARANG_ID = ? AND STATUS = 'AKTIF' and tanggal_mulai <= ? and tanggal_berakhir >= ? order by 'CREATED' limit 1";
        PromoTebusMurahDetail promoTebusMurahDetail = null;
        try {
            promoTebusMurahDetail = (PromoTebusMurahDetail)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PromoTebusMurahDetailRowMapper(), new Object[]{barangId, startDate, endDate});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoTebusMurahDetail;
    }

    @Override
    public List<Object[]> getPromoTebusMurahByNominal(String startDate, String endDate, long nominal) {
        String sql = "SELECT m_barang_id, nama, harga_promo, satuan, minimum_belanja FROM promo_tebus_murah_detail JOIN promo_tebus_murah ON (promo_tebus_murah_detail.PROMO_TEBUS_MURAH_ID = promo_tebus_murah.ID) JOIN m_barang ON (promo_tebus_murah_detail.m_barang_id = m_barang.id) WHERE minimum_belanja <= ? AND STATUS = 'AKTIF' and tanggal_mulai <= ? and tanggal_berakhir >= ? ";
        LinkedList<Object[]> objects = new LinkedList<Object[]>();
        try {
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{nominal, startDate, endDate});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("m_barang_id"), row.get("nama"), row.get("harga_promo"), row.get("satuan"), row.get("minimum_belanja")};
                objects.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return objects;
    }

    @Override
    public List<Object[]> getAvailableTebusMurah(String startDate, String endDate) {
        String sql = "SELECT distinct(minimum_belanja) as minimum_belanja FROM minimarket.promo_tebus_murah_detail join minimarket.promo_tebus_murah on (promo_tebus_murah_detail.promo_tebus_murah_id = promo_tebus_murah.id) where tanggal_mulai<=? and tanggal_berakhir>=? order by minimum_belanja desc";
        LinkedList<Object[]> objects = new LinkedList<Object[]>();
        try {
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{startDate, endDate});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("minimum_belanja")};
                objects.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return objects;
    }

    @Override
    public List<PromoTebusMurahDetail> getPromoTebusMurahDetailAll() {
        String sql = "SELECT * FROM promo_tebus_murah_detail";
        List promoTebusMurahDetails = null;
        try {
            promoTebusMurahDetails = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PromoTebusMurahDetailRowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoTebusMurahDetails;
    }

    @Override
    public int[] upsertPromoTebusMurahDetailBatch(List<PromoTebusMurahDetail> datas) {
        int[] results = null;
        String sql_upsert = "INSERT INTO promo_tebus_murah_detail (id, promo_tebus_murah_id, m_barang_id, satuan, minimum_belanja, harga_promo, maksimal_kuantiti) VALUES (:id, :promoTebusMurahId, :barangId, :satuan, :minimumBelanja, :hargaPromo, :maksimalKuantiti) ON DUPLICATE KEY UPDATE promo_tebus_murah_id = VALUES(promo_tebus_murah_id), m_barang_id = VALUES(m_barang_id), satuan = VALUES(satuan), minimum_belanja = VALUES(minimum_belanja), harga_promo = VALUES(harga_promo), maksimal_kuantiti = VALUES(maksimal_kuantiti)";
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
    public int deletePromoTebusMurahDetailByIds(String ids) {
        String sql = "DELETE FROM promo_tebus_murah_detail WHERE id in (" + ids + ")";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }
}

