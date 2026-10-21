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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.controller.Helper;
import minimart.engine.dao.PromoHematDetailDao;
import minimart.engine.model.PromoHematDetail;
import minimart.engine.rowmapper.PromoHematDetailRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PromoHematDetailDaoImpl
extends SimpleJdbcDaoSupport
implements PromoHematDetailDao {
    private Helper helper = Helper.getInstance();

    public PromoHematDetailDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createPromoHemat(PromoHematDetail promoHematDetail) {
        String sql = "INSERT INTO promo_hemat_detail (ID, PROMO_HEMAT_ID, M_BARANG_ID, SATUAN, MINIMUM_PEMBELIAN, HARGA_PROMO,KHUSUS_PELANGGAN) VALUES (:id, :promoHematId, :barangId, :satuan, :minimumPembelian, :hargaPromo, :khususPelanggan)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)promoHematDetail);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<PromoHematDetail> getPromoHematDetailByPromoHematId(String promoHematId) {
        String sql = "SELECT * FROM promo_hemat_detail WHERE PROMO_HEMAT_ID = ?";
        List promoHematDetails = null;
        try {
            promoHematDetails = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PromoHematDetailRowMapper(), new Object[]{promoHematId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoHematDetails;
    }

    @Override
    public PromoHematDetail getPromoHematDetailByBarangId(String barangId, String startDate, String endDate) {
        String sql = "SELECT * FROM promo_hemat_detail JOIN promo_hemat ON (promo_hemat_detail.PROMO_HEMAT_ID = promo_hemat.ID) WHERE M_BARANG_ID = ? AND STATUS = 'AKTIF' and tanggal_mulai <= ? and tanggal_berakhir >= ? order by created limit 1";
        PromoHematDetail promoHematDetail = null;
        try {
            promoHematDetail = (PromoHematDetail)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PromoHematDetailRowMapper(), new Object[]{barangId, startDate, endDate});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoHematDetail;
    }

    @Override
    public PromoHematDetail getPromoHematDetailByBarangId(String barangId, String satuan, float minPembelian, String startDate, String endDate) {
        String sql = "SELECT * FROM promo_hemat_detail JOIN promo_hemat ON (promo_hemat_detail.PROMO_HEMAT_ID = promo_hemat.ID) WHERE M_BARANG_ID = ? AND SATUAN = ? AND MINIMUM_PEMBELIAN<=? AND STATUS = 'AKTIF' and tanggal_mulai <= ? and tanggal_berakhir >= ? order by MINIMUM_PEMBELIAN DESC limit 1";
        PromoHematDetail promoHematDetail = null;
        try {
            promoHematDetail = (PromoHematDetail)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PromoHematDetailRowMapper(), new Object[]{barangId, satuan, Float.valueOf(minPembelian), startDate, endDate});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoHematDetail;
    }

    @Override
    public int deletePromoHematDetailByPromoHematId(String promoHematId) {
        String sql = "DELETE FROM promo_hemat_detail WHERE PROMO_HEMAT_ID = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{promoHematId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updatePromoHematDetail(PromoHematDetail promoHematDetail) {
        String sql = "UPDATE promo_hemat_detail SET SATUAN=:satuan, MINIMUM_PEMBELIAN=:minimumPembelian, HARGA_PROMO=:hargaPromo, KHUSUS_PELANGGAN=:khususPelanggan WHERE M_BARANG_ID=:barangId";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)promoHematDetail);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int[] upsertPromoHematDetailBatch(List<PromoHematDetail> datas) {
        int[] results = null;
        String sql_upsert = "INSERT INTO promo_hemat_detail (id, promo_hemat_id, m_barang_id, satuan, minimum_pembelian, harga_promo, khusus_pelanggan) VALUES (:id, :promoHematId, :barangId, :satuan, :minimumPembelian, :hargaPromo, :khususPelanggan) ON DUPLICATE KEY UPDATE promo_hemat_id = VALUES(promo_hemat_id), m_barang_id = VALUES(m_barang_id), satuan = VALUES(satuan), minimum_pembelian = VALUES(minimum_pembelian), harga_promo = VALUES(harga_promo), khusus_pelanggan = VALUES(khusus_pelanggan)";
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
    public int deletePromoHematDetailByIds(String ids) {
        String sql = "DELETE FROM promo_hemat_detail WHERE id in (" + ids + ")";
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
    public List<PromoHematDetail> getPromoHematDetailAll() {
        String sql = "SELECT * FROM promo_hemat_detail";
        List promoHematDetails = null;
        try {
            promoHematDetails = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PromoHematDetailRowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoHematDetails;
    }

    @Override
    public List<Object[]> getAvailablePromo() {
        String sql = "SELECT m_barang_id, nama, harga_promo, promo_hemat_detail.satuan as satuan1, harga_jual_1, satuan_1 ,harga_jual_2, satuan_2, harga_jual_3, satuan_3, harga_jual_1 * (min_persen+100)/100 as min_persen, min_pembelian, rak_toko , curDate() as tanggal_cetak, tanggal_berakhir, tag, expDay FROM minimarket.promo_hemat_detail JOIN minimarket.promo_hemat ON (promo_hemat_detail.PROMO_HEMAT_ID = promo_hemat.ID) JOIN minimarket.m_barang on (promo_hemat_detail.m_barang_id = m_barang.id) WHERE STATUS = 'AKTIF' and tanggal_mulai <= curdate() and tanggal_berakhir >= curdate()";
        ArrayList<Object[]> barangs = new ArrayList<Object[]>();
        try {
            List rows = this.getJdbcTemplate().queryForList(sql);
            for (Map row : rows) {
                Object[] returnValue = new Object[15];
                returnValue[0] = row.get("m_barang_id");
                returnValue[1] = row.get("nama");
                returnValue[2] = row.get("harga_promo");
                returnValue[3] = row.get("satuan1");
                if (returnValue[3].equals(row.get("satuan_1"))) {
                    StringBuilder stringBuilder = new StringBuilder();
                    if (Float.parseFloat(stringBuilder.append(row.get("min_pembelian")).append("").toString()) > 0.0f) {
                        long resultValue = this.helper.roundPrice100(Math.round(Float.parseFloat(row.get("min_persen") + "")));
                        returnValue[4] = resultValue;
                    } else {
                        returnValue[4] = row.get("harga_jual_1");
                    }
                    returnValue[5] = row.get("satuan_1");
                } else if (returnValue[3].equals(row.get("satuan_2"))) {
                    returnValue[4] = row.get("harga_jual_2");
                    returnValue[5] = row.get("satuan_2");
                } else if (returnValue[3].equals(row.get("satuan_3"))) {
                    returnValue[4] = row.get("harga_jual_3");
                    returnValue[5] = row.get("satuan_3");
                }
                returnValue[6] = row.get("harga3");
                returnValue[7] = row.get("satuan3");
                returnValue[8] = row.get("harga_ecer");
                returnValue[9] = Math.round(Float.parseFloat(row.get("min_pembelian") + ""));
                returnValue[10] = row.get("rak_toko");
                returnValue[11] = row.get("tag") == null ? "" : row.get("tag");
                returnValue[12] = row.get("expDay");
                returnValue[13] = row.get("tanggal_berakhir");
                returnValue[14] = row.get("tanggal_cetak");
                barangs.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return barangs;
    }
}

