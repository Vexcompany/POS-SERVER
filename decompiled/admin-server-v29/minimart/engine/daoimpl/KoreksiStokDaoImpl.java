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
import minimart.engine.dao.KoreksiStokDao;
import minimart.engine.model.Barang;
import minimart.engine.model.KoreksiStok;
import minimart.engine.rowmapper.KoreksiStokRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class KoreksiStokDaoImpl
extends SimpleJdbcDaoSupport
implements KoreksiStokDao {
    public KoreksiStokDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createKoreksiStok(KoreksiStok koreksiStok) {
        String sql = "INSERT INTO t_koreksi_stok (M_KARYAWAN_ID, M_BARANG_ID, STOK_AWAL,JUMLAH, SATUAN, JENIS, STATUS, TANGGAL) VALUES (:karyawanId,:barangId, :stokAwal,:jumlah,:satuan,:jenis,:status,:date)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)koreksiStok);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<KoreksiStok> getKoreksiStoks(String startDate, String endDate, Barang barang, String status) {
        String sql = "SELECT * FROM t_koreksi_stok WHERE TANGGAL>=? AND TANGGAL<=? AND M_BARANG_ID LIKE ? AND STATUS = ?";
        List penjualans = null;
        String idBarang = "%%";
        if (barang != null) {
            idBarang = '%' + barang.getId() + '%';
        }
        try {
            penjualans = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new KoreksiStokRowMapper(), new Object[]{startDate, endDate, idBarang, status});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return penjualans;
    }
}

