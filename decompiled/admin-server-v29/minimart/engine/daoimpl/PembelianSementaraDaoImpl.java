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
import minimart.engine.controller.Helper;
import minimart.engine.dao.PembelianSementaraDao;
import minimart.engine.model.PembelianSementara;
import minimart.engine.rowmapper.PembelianSementaraRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PembelianSementaraDaoImpl
extends SimpleJdbcDaoSupport
implements PembelianSementaraDao {
    private Helper helper = Helper.getInstance();

    public PembelianSementaraDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createPembelianSementara(PembelianSementara pembelianSementara) {
        String sql = "INSERT INTO t_pembelian_sementara (ID, SHORTCUT, STATUS, M_SUPPLIER_ID, M_BARANG_ID, JUMLAH_BARANG, SATUAN, HARGA_BELI, DISCOUNT, TOTAL_HARGA, TANGGAL_PEMBELIAN, PEMBAYARAN, JATUH_TEMPO) VALUES (:id, :shortcut, :status, :supplierId, :barangId, :jumlahBarang, :satuan, :hargaBeli, :discount, :totalHarga, :tanggalPembelian, :pembayaran, :jatuhTempo)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)pembelianSementara);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updatePembelianSementara(PembelianSementara pembelianSementara) {
        String sql = "UPDATE t_pembelian_sementara SET JUMLAH_BARANG=:jumlahBarang, SATUAN=:satuan, HARGA_BELI=:hargaBeli, DISCOUNT=:discount, TOTAL_HARGA=:totalHarga, TANGGAL_PEMBELIAN =:tanggalPembelian, PEMBAYARAN :=pembayaran, JATUH_TEMPO =:jatuhTempo WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)pembelianSementara);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updatePembayaranPembelianSementara(String pembayaran, String shortcut) {
        String sql = "UPDATE t_pembelian_sementara SET PEMBAYARAN = ? WHERE SHORTCUT = ? ";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{pembayaran, shortcut});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int deletePembelianSementara(String shortcut, String barangId) {
        String sql = "DELETE FROM t_pembelian_sementara WHERE SHORTCUT = ? AND M_BARANG_ID = ? AND STATUS = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{shortcut, barangId, this.helper.getStatusProgram()});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int deletePembelianSementaraByShortcut(String shortcut) {
        String sql = "DELETE FROM t_pembelian_sementara WHERE SHORTCUT = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{shortcut});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<PembelianSementara> getPembelianSementaraByShortcut(String shortcut) {
        String sql = "SELECT * FROM t_pembelian_sementara WHERE SHORTCUT = ? AND STATUS = ?";
        List pembelianSementaras = null;
        try {
            pembelianSementaras = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PembelianSementaraRowMapper(), new Object[]{shortcut, this.helper.getStatusProgram()});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return pembelianSementaras;
    }

    @Override
    public int updateSupplierPembelianSementara(String supplierId, String shortcut) {
        String sql = "UPDATE t_pembelian_sementara SET M_SUPPLIER_ID = ? WHERE SHORTCUT = ? AND STATUS = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{supplierId, shortcut, this.helper.getStatusProgram()});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }
}

