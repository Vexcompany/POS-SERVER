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
import minimart.engine.controller.Helper;
import minimart.engine.dao.PenjualanSementaraDao;
import minimart.engine.model.PenjualanSementara;
import minimart.engine.rowmapper.PenjualanSementaraRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PenjualanCabangSementaraDaoImpl
extends SimpleJdbcDaoSupport
implements PenjualanSementaraDao {
    private Helper helper = Helper.getInstance();

    public PenjualanCabangSementaraDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createPenjualanSementara(PenjualanSementara penjualanSementara) {
        String sql = "INSERT INTO t_penjualan_cabang_sementara (ID, KASIR, STATUS,SHORTCUT, M_CUSTOMER_ID, M_BARANG_ID, M_SALES_ID,JUMLAH_BARANG, SATUAN, HARGA_JUAL, DISCOUNT, TOTAL_HARGA, NOTA_KHUSUS, KETERANGAN, PICKINGLIST_ID) VALUES (:id, :kasir, :status, :shortcut, :customerId, :barangId, :salesId, :jumlahBarang, :satuan, :hargaJual, :discount, :totalHarga, :notaKhusus, :keterangan, :pickinglistId)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)penjualanSementara);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int[] createPenjualanSementaraBatch(List<PenjualanSementara> penjualanSementaras) {
        String sql = "INSERT INTO t_penjualan_cabang_sementara (ID, KASIR, STATUS,SHORTCUT, M_CUSTOMER_ID, M_BARANG_ID, M_SALES_ID,JUMLAH_BARANG, SATUAN, HARGA_JUAL, DISCOUNT, TOTAL_HARGA, NOTA_KHUSUS, KETERANGAN, PICKINGLIST_ID) VALUES (:id, :kasir, :status, :shortcut, :customerId, :barangId, :salesId, :jumlahBarang, :satuan, :hargaJual, :discount, :totalHarga, :notaKhusus, :keterangan, :pickinglistId)";
        int[] results = null;
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])penjualanSementaras.toArray());
        try {
            results = this.getSimpleJdbcTemplate().batchUpdate(sql, batch);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return results;
    }

    @Override
    public int updatePenjualanSementara(PenjualanSementara penjualanSementara) {
        String sql = "UPDATE t_penjualan_cabang_sementara SET JUMLAH_BARANG=:jumlahBarang, SATUAN=:satuan, HARGA_JUAL=:hargaJual, DISCOUNT=:discount, TOTAL_HARGA=:totalHarga, NOTA_KHUSUS=:notaKhusus, KETERANGAN=:keterangan WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)penjualanSementara);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updateCustomerPenjualanSementara(String customerId, String shortcut) {
        String sql = "UPDATE t_penjualan_cabang_sementara SET M_CUSTOMER_ID = ? WHERE SHORTCUT = ? AND KASIR = ? AND STATUS = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{customerId, shortcut, this.helper.getKasir(), this.helper.getStatusProgram()});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updateSalesPenjualanSementara(String salesId, String shortcut) {
        String sql = "UPDATE t_penjualan_cabang_sementara SET M_SALES_ID = ? WHERE SHORTCUT = ? AND KASIR = ? AND STATUS = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{salesId, shortcut, this.helper.getKasir(), this.helper.getStatusProgram()});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int deletePenjualanSementara(String shortcut, String barangId) {
        String sql = "DELETE FROM t_penjualan_cabang_sementara WHERE SHORTCUT = ? AND M_BARANG_ID = ? AND KASIR = ? AND STATUS = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{shortcut, barangId, this.helper.getKasir(), this.helper.getStatusProgram()});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int deletePenjualanSementaraByShortcut(String shortcut) {
        String sql = "DELETE FROM t_penjualan_cabang_sementara WHERE SHORTCUT = ? AND KASIR = ? AND STATUS = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{shortcut, this.helper.getKasir(), this.helper.getStatusProgram()});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<PenjualanSementara> getPenjualanSementaraByShortcut(String shortcut) {
        String sql = "SELECT * FROM t_penjualan_cabang_sementara JOIN m_barang ON (t_penjualan_cabang_sementara.M_BARANG_ID = m_barang.ID) WHERE SHORTCUT = ? AND KASIR = ? AND STATUS = ? ORDER BY NAMA";
        List penjualanSementaras = null;
        try {
            penjualanSementaras = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PenjualanSementaraRowMapper(), new Object[]{shortcut, this.helper.getKasir(), this.helper.getStatusProgram()});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return penjualanSementaras;
    }

    @Override
    public List<Object[]> getPenjualanSementaraByShortcut2(String shortcut) {
        String sql = "SELECT t_penjualan_cabang_sementara.m_barang_id as m_barang_id, nama, jumlah_barang, satuan, harga_jual, discount, total_harga FROM t_penjualan_cabang_sementara JOIN m_barang ON (t_penjualan_cabang_sementara.M_BARANG_ID = m_barang.ID) WHERE SHORTCUT = ? AND KASIR = ? AND STATUS = ? ORDER BY NAMA";
        LinkedList<Object[]> objects = new LinkedList<Object[]>();
        try {
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{shortcut});
            for (Map row : rows) {
                Object[] returnValue = new Object[10];
                returnValue[0] = row.get("m_barang_id");
                returnValue[1] = row.get("nama");
                returnValue[2] = row.get("jumlah_barang");
                returnValue[3] = row.get("satuan");
                returnValue[4] = row.get("harga_jual");
                returnValue[5] = row.get("discount");
                returnValue[6] = row.get("total_harga");
                objects.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return objects;
    }

    @Override
    public int updateKeteranganPenjualanSementara(String keterangan, String shortcut) {
        String sql = "UPDATE t_penjualan_cabang_sementara SET KETERANGAN = ? WHERE SHORTCUT = ? AND KASIR = ? AND STATUS = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{keterangan, shortcut, this.helper.getKasir(), this.helper.getStatusProgram()});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public float cekJumlahByParentId(int kasir, String shortcut, String satuan, String parent_id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int updateHargaPenjualanSementara(long harga, long totalHarga, int kasir, String shortcut, String satuan, String parent_id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<PenjualanSementara> getPenjualanSementaraByShortcut(String shortcut, String parent_id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public float getJumlahPenjualanSementara(int kasirId, String shortCut, String barangId, String satuan) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getRecentTransaction(int kasirId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Object[]> getPoinPerPenjualan(String shortcut) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int updateHargaPenjualanSementara(long harga, long totalHarga, long discount, int kasir, String shortcut, String satuan, String barang_id, Float jumlahBarang) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int sumBarang(int kasirId, String shortCut, String barangId, String satuan) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int isVoid(int kasirId, String shortCut, String barangId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int deletePenjualanSementara(String shortcut, String barangId, float jumlahBarang, String satuan) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<PenjualanSementara> getPenjualanSementaraByShortcutIdBarang(String shortcut, String barangId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<PenjualanSementara> getPenjualanSementaraByShortcut(String shortcut, String parent_id, String satuan) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<PenjualanSementara> getPenjualanSementaraByShortcutIdBarang(String shortcut, String barangId, String satuan) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Object[]> getPenjualanSementaraByShortcut2OrderByName(String shortcut) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public long getTotalBelanja(String shortcut, int kasir) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

