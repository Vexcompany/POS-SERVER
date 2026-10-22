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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.controller.Helper;
import minimart.engine.dao.BarangDao;
import minimart.engine.model.Barang;
import minimart.engine.rowmapper.BarangRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class BarangDaoImpl
extends SimpleJdbcDaoSupport
implements BarangDao {
    private Helper helper = Helper.getInstance();

    public BarangDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createBarang(Barang barang) {
        String sql = "INSERT INTO m_barang (ID, M_JENIS_JENIS, RAK_GUDANG, RAK_TOKO, NAMA, SATUAN_1, SATUAN_2, SATUAN_3, KONVERSI_21, KONVERSI_32, BATAS_ATAS_STOCK, REORDER_POINT ,HARGA_JUAL_1, HARGA_JUAL_2, HARGA_JUAL_3, MIN_PEMBELIAN, MIN_PERSEN, STOK_TOKO, STOK_GUDANG, STOK_BS, UPDATE_HARGA, HPP, HPP_UPDATED, CREATED, UPDATED, STOK_PUSAT, PARENT_ID, TAG, EXPDAY) VALUES (:id, :jenis, :rakIdGudang, :rakIdToko, :nama, :satuan1, :satuan2, :satuan3, :konversi21, :konversi32, :batasAtasStock, :reorderPoint, :hargaJual1, :hargaJual2, :hargaJual3, :minPembelian, :minPersen, :stokToko, :stokGudang, :stokBs, :updateHarga, :hpp, UNIX_TIMESTAMP(),:created, :updated, :stokPusat, :parentId, :tag, :expDay)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)barang);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public Barang getBarang(String id) {
        String sql = "SELECT * FROM m_barang LEFT JOIN m_barcode ON (m_barang.ID = m_barcode.M_BARANG_ID) WHERE BARCODE = ? OR m_barang.ID = ? ORDER BY BARCODE ASC LIMIT 1";
        Barang barang = null;
        try {
            barang = (Barang)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new BarangRowMapper(), new Object[]{id, id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return barang;
    }

    @Override
    public List<Barang> getBarangsByMultiRak(String raks, String lokasi) {
        String[] rakName = raks.split(",");
        String sqlPlus = "";
        for (int i = 0; i < rakName.length; ++i) {
            String rakName1 = rakName[i];
            if (i < rakName.length - 1) {
                if (lokasi.equals("Toko")) {
                    sqlPlus = sqlPlus + " RAK_TOKO = '" + rakName1 + "' OR";
                    continue;
                }
                if (!lokasi.equals("Gudang")) continue;
                sqlPlus = sqlPlus + " RAK_GUDANG = '" + rakName1 + "' OR";
                continue;
            }
            if (lokasi.equals("Toko")) {
                sqlPlus = sqlPlus + " RAK_TOKO = '" + rakName1 + "'";
                continue;
            }
            if (!lokasi.equals("Gudang")) continue;
            sqlPlus = sqlPlus + " RAK_GUDANG = '" + rakName1 + "'";
        }
        String sql = "SELECT * FROM m_barang WHERE" + sqlPlus + " ORDER BY NAMA";
        List barangs = null;
        try {
            barangs = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new BarangRowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return barangs;
    }

    @Override
    public List<Object[]> geDataLabelByJenis(String jenis) {
        String sql = "SELECT id, nama,harga_jual_1,satuan_1,harga_jual_2,satuan_2,harga_jual_3,satuan_3, harga_jual_1 * (min_persen+100)/100 as min_persen , min_pembelian , rak_toko , curdate(), '' as tanggal, tag, expDay FROM m_barang where m_jenis_jenis = ? and stok_toko > 0 order by nama";
        ArrayList<Object[]> barangs = new ArrayList<Object[]>();
        try {
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{jenis});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("id"), row.get("nama"), row.get("harga_jual_1"), row.get("satuan_1"), row.get("satuan_2").equals("") ? "" : row.get("harga_jual_2"), row.get("satuan_2"), row.get("satuan_3").equals("") ? "" : row.get("harga_jual_3"), row.get("satuan_3"), this.helper.roundPrice100(Math.round((Double)row.get("min_persen"))), Math.round(Float.parseFloat(row.get("min_pembelian") + "")), row.get("rak_toko"), row.get("tag") == null ? "" : row.get("tag"), row.get("expDay"), row.get("tanggal"), row.get("curdate()")};
                barangs.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return barangs;
    }

    @Override
    public List<Barang> getBarangs(String jenis, String nama, boolean orderByRak, String lokasi, boolean allStock) {
        String sqlPlus = "";
        String sqlAllStock = "";
        if (!allStock) {
            sqlAllStock = "AND STOK_" + lokasi + " <> 0";
        }
        if (orderByRak) {
            sqlPlus = "RAK_TOKO, ";
        }
        jenis = jenis.equals("") ? "''" : "'" + jenis + "'";
        String sql = "SELECT * FROM m_barang WHERE M_JENIS_JENIS NOT LIKE " + jenis + " AND NAMA LIKE ? " + sqlAllStock + " ORDER BY " + sqlPlus + "M_JENIS_JENIS, NAMA";
        List barangs = null;
        try {
            nama = '%' + nama + '%';
            barangs = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new BarangRowMapper(), new Object[]{nama});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return barangs;
    }

    @Override
    public List<Barang> getBarangs(String parent) {
        String sql = "SELECT * FROM m_barang WHERE PARENT_ID = ? ORDER BY NAMA";
        List barangs = null;
        try {
            barangs = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new BarangRowMapper(), new Object[]{parent});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return barangs;
    }

    @Override
    public List<Barang> getBsBarangs(String jenis) {
        String sql = "SELECT * FROM m_barang WHERE M_JENIS_JENIS LIKE ? AND STOK_BS > 0 ORDER BY NAMA";
        List barangs = null;
        try {
            jenis = '%' + jenis + '%';
            barangs = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new BarangRowMapper(), new Object[]{jenis});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return barangs;
    }

    @Override
    public List<Barang> getBarangsUpdateHarga(int updateStatus) {
        String sql = "SELECT * FROM m_barang WHERE UPDATE_HARGA = ?";
        List barangs = null;
        try {
            barangs = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new BarangRowMapper(), new Object[]{updateStatus});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return barangs;
    }

    @Override
    public List<Barang> getBarangsByRak(String rak) {
        String sql = "SELECT * FROM m_barang WHERE rak_toko LIKE ? ORDER BY rak_toko";
        List barangs = null;
        rak = rak + "%";
        try {
            barangs = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new BarangRowMapper(), new Object[]{rak});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return barangs;
    }

    @Override
    public List<Barang> getBarangsByJenisRakNama(String jenis, String rak, String nama, String lokasi, boolean allStock) {
        String sqlAllStock = "";
        if (!allStock) {
            sqlAllStock = "AND STOK_" + lokasi + " <> 0";
        }
        String sql = "SELECT * FROM m_barang WHERE M_JENIS_JENIS LIKE ? AND NAMA LIKE ? " + sqlAllStock + " ORDER BY M_JENIS_JENIS, NAMA";
        List barangs = null;
        try {
            jenis = '%' + jenis + '%';
            if (rak.equals("")) {
                rak = " ";
            }
            if (jenis.equals("")) {
                jenis = " ";
            }
            nama = '%' + nama + '%';
            barangs = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new BarangRowMapper(), new Object[]{jenis, nama});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return barangs;
    }

    @Override
    public List<Barang> getBarangsMinToko(String jenis) {
        String sql = "SELECT * FROM m_barang WHERE M_JENIS_JENIS LIKE ? AND (STOK_GUDANG)<REORDER_POINT";
        List barangs = null;
        try {
            jenis = '%' + jenis + '%';
            barangs = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new BarangRowMapper(), new Object[]{jenis});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return barangs;
    }

    @Override
    public int updateBarang(Barang barang) {
        String sql = "UPDATE m_barang SET M_JENIS_JENIS=:jenis, RAK_TOKO=:rakIdToko, RAK_GUDANG=:rakIdGudang, NAMA=:nama, SATUAN_1=:satuan1, SATUAN_2=:satuan2, SATUAN_3=:satuan3, KONVERSI_21=:konversi21, KONVERSI_32=:konversi32, BATAS_ATAS_STOCK=:batasAtasStock, REORDER_POINT=:reorderPoint, HARGA_JUAL_1=:hargaJual1, HARGA_JUAL_2=:hargaJual2, HARGA_JUAL_3=:hargaJual3, MIN_PEMBELIAN=:minPembelian, MIN_PERSEN=:minPersen, STOK_TOKO=:stokToko, STOK_GUDANG=:stokGudang,STOK_BS=:stokBs,UPDATE_HARGA=:updateHarga, HPP=:hpp, UPDATED=:updated, PARENT_ID=:parentId ,TAG=:tag, EXPDAY=:expDay WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)barang);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updateBarangHpp(Barang barang) {
        String sql = "UPDATE m_barang SET M_JENIS_JENIS=:jenis, RAK_TOKO=:rakIdToko, RAK_GUDANG=:rakIdGudang, NAMA=:nama, SATUAN_1=:satuan1, SATUAN_2=:satuan2, SATUAN_3=:satuan3, KONVERSI_21=:konversi21, KONVERSI_32=:konversi32, BATAS_ATAS_STOCK=:batasAtasStock, REORDER_POINT=:reorderPoint, HARGA_JUAL_1=:hargaJual1, HARGA_JUAL_2=:hargaJual2, HARGA_JUAL_3=:hargaJual3, MIN_PEMBELIAN=:minPembelian, MIN_PERSEN=:minPersen, STOK_TOKO=:stokToko, STOK_GUDANG=:stokGudang,STOK_BS=:stokBs,UPDATE_HARGA=:updateHarga, HPP=:hpp, UPDATED=:updated, HPP_UPDATED = UNIX_TIMESTAMP(), STOK_PUSAT=:stokPusat, PARENT_ID=:parentId, TAG=:tag ,EXPDAY=:expDay WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)barang);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updateBarang2(Barang barang, String idBaru) {
        String sql = "UPDATE m_barang SET ID=?, M_JENIS_JENIS=?, RAK_TOKO=?, RAK_GUDANG=?, NAMA=?, SATUAN_1=?, SATUAN_2=?, SATUAN_3=?, KONVERSI_21=?, KONVERSI_32=?, BATAS_ATAS_STOCK=?, REORDER_POINT=?, HARGA_JUAL_1=?, HARGA_JUAL_2=?, HARGA_JUAL_3=?, MIN_PEMBELIAN=?, MIN_PERSEN=?,STOK_TOKO=?, STOK_GUDANG=?, STOK_BS=?,UPDATE_HARGA=?, HPP=? ,UPDATED=? , STOK_PUSAT=?, PARENT_ID=? ,TAG=?,EXPDAY=? WHERE ID=?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{idBaru, barang.getJenis(), barang.getRakIdToko(), barang.getRakIdGudang(), barang.getNama(), barang.getSatuan1(), barang.getSatuan2(), barang.getSatuan3(), barang.getKonversi21(), barang.getKonversi32(), Float.valueOf(barang.getBatasAtasStock()), Float.valueOf(barang.getReorderPoint()), barang.getHargaJual1(), barang.getHargaJual2(), barang.getHargaJual3(), Float.valueOf(barang.getMinPembelian()), Float.valueOf(barang.getMinPersen()), Float.valueOf(barang.getStokToko()), Float.valueOf(barang.getStokGudang()), Float.valueOf(barang.getStokBs()), barang.getUpdateHarga(), barang.getHpp(), barang.getUpdated(), Float.valueOf(barang.getStokPusat()), barang.getParentId(), barang.getTag(), barang.getExpDay(), barang.getId()});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updateBarang2Hpp(Barang barang, String idBaru) {
        String sql = "UPDATE m_barang SET ID=?, M_JENIS_JENIS=?, RAK_TOKO=?, RAK_GUDANG=?, NAMA=?, SATUAN_1=?, SATUAN_2=?, SATUAN_3=?, KONVERSI_21=?, KONVERSI_32=?, BATAS_ATAS_STOCK=?, REORDER_POINT=?, HARGA_JUAL_1=?, HARGA_JUAL_2=?, HARGA_JUAL_3=?, MIN_PEMBELIAN=?, MIN_PERSEN=?,STOK_TOKO=?, STOK_GUDANG=?, STOK_BS=?,UPDATE_HARGA=?, HPP=? ,UPDATED=?, HPP_UPDATED = UNIX_TIMESTAMP() , STOK_PUSAT=?, PARENT_ID=?, TAG=?,EXPDAY=? WHERE ID=?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{idBaru, barang.getJenis(), barang.getRakIdToko(), barang.getRakIdGudang(), barang.getNama(), barang.getSatuan1(), barang.getSatuan2(), barang.getSatuan3(), barang.getKonversi21(), barang.getKonversi32(), Float.valueOf(barang.getBatasAtasStock()), Float.valueOf(barang.getReorderPoint()), barang.getHargaJual1(), barang.getHargaJual2(), barang.getHargaJual3(), Float.valueOf(barang.getMinPembelian()), Float.valueOf(barang.getMinPersen()), Float.valueOf(barang.getStokToko()), Float.valueOf(barang.getStokGudang()), Float.valueOf(barang.getStokBs()), barang.getUpdateHarga(), barang.getHpp(), barang.getUpdated(), Float.valueOf(barang.getStokPusat()), barang.getParentId(), barang.getTag(), barang.getExpDay(), barang.getId()});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int deleteBarang(String id) {
        String sql = "DELETE FROM m_barang WHERE ID = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public String getLastId() {
        String sql = "SELECT ID FROM m_barang ORDER BY ID DESC LIMIT ?";
        String id = "";
        try {
            id = (String)this.getSimpleJdbcTemplate().queryForObject(sql, String.class, new Object[]{1});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return id;
    }

    @Override
    public int updateStokBs(float jumlah, String idBarang) {
        String sql = "UPDATE m_barang SET STOK_BS = STOK_BS+? WHERE ID = ?;";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{Float.valueOf(jumlah), idBarang});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updateStokBarangToko(float jumlah, String idBarang) {
        String sql = "UPDATE m_barang SET STOK_TOKO = STOK_TOKO+? WHERE ID = ?;";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{Float.valueOf(jumlah), idBarang});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updateStokBarangGudang(float jumlah, String idBarang) {
        String sql = "UPDATE m_barang SET STOK_GUDANG = STOK_GUDANG+? WHERE ID = ?;";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{Float.valueOf(jumlah), idBarang});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }
}

