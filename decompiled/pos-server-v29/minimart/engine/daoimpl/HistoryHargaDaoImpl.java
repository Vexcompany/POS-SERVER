/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport
 */
package minimart.engine.daoimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.controller.Helper;
import minimart.engine.dao.HistoryHargaDao;
import minimart.engine.model.HistoryHarga;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class HistoryHargaDaoImpl
extends SimpleJdbcDaoSupport
implements HistoryHargaDao {
    private Helper helper = Helper.getInstance();

    public HistoryHargaDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createHistoryHarga(HistoryHarga historyHarga) {
        String sql = "INSERT INTO m_history_harga (ID, M_KARYAWAN_ID, M_BARANG_ID, SATUAN, HARGA_BARU, HARGA_LAMA, TANGGAL,CREATED) VALUES (:id, :karyawanId, :barangId, :satuan,:hargaBaru, :hargaLama,:tanggal, :created)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)historyHarga);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<Object[]> getIdHistoryHarga(String startDate, String endDate) {
        String sql = "SELECT id, nama,harga_jual_1,satuan_1,harga_jual_2,satuan_2,harga_jual_3,satuan_3, harga_jual_1 * (min_persen+100)/100 as min_persen , min_pembelian , rak_toko , tag, expDay, curdate() FROM minimarket.m_barang where id in (select  m_barang_id from minimarket.m_history_harga where tanggal >= ? and tanggal <=?)";
        ArrayList<Object[]> barangs = new ArrayList<Object[]>();
        try {
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{startDate, endDate});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("id"), row.get("nama"), row.get("harga_jual_1"), row.get("satuan_1"), row.get("satuan_2").equals("") ? "" : row.get("harga_jual_2"), row.get("satuan_2"), row.get("satuan_3").equals("") ? "" : row.get("harga_jual_3"), row.get("satuan_3"), this.helper.roundPrice100(Math.round((Double)row.get("min_persen"))), Math.round(Float.parseFloat(row.get("min_pembelian") + "")), row.get("rak_toko"), row.get("tag") == null ? "" : row.get("tag"), row.get("expDay"), "", row.get("curdate()")};
                barangs.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return barangs;
    }

    @Override
    public List<Object[]> getHistoryHarga(String startDate, String endDate, String idBarang) {
        String sql = "SELECT m_barang_id , nama, satuan, harga_lama, harga_baru, m_history_harga.created as created , m_karyawan_id FROM m_history_harga JOIN m_barang on (m_history_harga.m_barang_id = m_barang.id) WHERE TANGGAL>=? AND TANGGAL<=? AND M_BARANG_ID LIKE ? ORDER BY m_history_harga.CREATED";
        ArrayList<Object[]> historyHargas = new ArrayList<Object[]>();
        try {
            idBarang = '%' + idBarang + '%';
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{startDate, endDate, idBarang});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("m_barang_id"), row.get("nama"), row.get("satuan"), row.get("harga_lama"), row.get("harga_baru"), row.get("created"), row.get("m_karyawan_id")};
                historyHargas.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return historyHargas;
    }
}

