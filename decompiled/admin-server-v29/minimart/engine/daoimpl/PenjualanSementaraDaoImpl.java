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

public class PenjualanSementaraDaoImpl
extends SimpleJdbcDaoSupport
implements PenjualanSementaraDao {
    private Helper helper = Helper.getInstance();

    public PenjualanSementaraDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public long getTotalBelanja(String shortcut, int kasir) {
        String sql = "SELECT sum(total_harga) FROM minimarket.t_penjualan_sementara where shortcut = ? and kasir = ? group by kasir";
        long jumlah = 0L;
        try {
            jumlah = this.getSimpleJdbcTemplate().queryForObject(sql, Long.class, new Object[]{shortcut, kasir}) == null ? 0L : (Long)this.getSimpleJdbcTemplate().queryForObject(sql, Long.class, new Object[]{shortcut, kasir});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return jumlah;
    }

    @Override
    public int createPenjualanSementara(PenjualanSementara penjualanSementara) {
        String sql = "INSERT INTO t_penjualan_sementara (ID, KASIR, STATUS,SHORTCUT, M_CUSTOMER_ID, M_BARANG_ID, M_SALES_ID,JUMLAH_BARANG, SATUAN, HARGA_JUAL, DISCOUNT, TOTAL_HARGA, NOTA_KHUSUS, KETERANGAN, PICKINGLIST_ID, ID_TRANSAKSI_ONLINE, PROMO) VALUES (:id, :kasir, :status, :shortcut, :customerId, :barangId, :salesId, :jumlahBarang, :satuan, :hargaJual, :discount, :totalHarga, :notaKhusus, :keterangan, :pickinglistId, :idTransaksiOnline, :promo)";
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
        String sql = "INSERT INTO t_penjualan_sementara (ID, KASIR, STATUS,SHORTCUT, M_CUSTOMER_ID, M_BARANG_ID, M_SALES_ID,JUMLAH_BARANG, SATUAN, HARGA_JUAL, DISCOUNT, TOTAL_HARGA, NOTA_KHUSUS, KETERANGAN, PICKINGLIST_ID, ID_TRANSAKSI_ONLINE, PROMO) VALUES (:id, :kasir, :status, :shortcut, :customerId, :barangId, :salesId, :jumlahBarang, :satuan, :hargaJual, :discount, :totalHarga, :notaKhusus, :keterangan, :pickinglistId, :idTransaksiOnline, :promo)";
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
    public float cekJumlahByParentId(int kasir, String shortcut, String satuan, String parent_id) {
        String sql = "SELECT sum(jumlah_barang) FROM t_penjualan_sementara join m_barang on (t_penjualan_sementara.m_barang_id = m_barang.id) where kasir = ? AND shortcut = ? AND satuan = ? and parent_id = ?";
        float jumlah = 0.0f;
        try {
            jumlah = this.getSimpleJdbcTemplate().queryForObject(sql, Float.class, new Object[]{kasir, shortcut, satuan, parent_id}) == null ? 0.0f : ((Float)this.getSimpleJdbcTemplate().queryForObject(sql, Float.class, new Object[]{kasir, shortcut, satuan, parent_id})).floatValue();
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return jumlah;
    }

    @Override
    public int updateHargaPenjualanSementara(long harga, long totalHarga, int kasir, String shortcut, String satuan, String barang_id) {
        String sql = "UPDATE t_penjualan_sementara join m_barang on t_penjualan_sementara.m_barang_Id = m_barang.id SET HARGA_JUAL = ? , total_harga = ?, discount = 0 WHERE kasir = ? and shortcut = ? and satuan = ? and m_barang_id = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{harga, totalHarga, kasir, shortcut, satuan, barang_id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updateHargaPenjualanSementara(long harga, long totalHarga, long discount, int kasir, String shortcut, String satuan, String barang_id, Float jumlahBarang) {
        String sql = "UPDATE t_penjualan_sementara join m_barang on t_penjualan_sementara.m_barang_Id = m_barang.id SET HARGA_JUAL = ? , total_harga = ?, discount = ? WHERE kasir = ? and shortcut = ? and satuan = ? and m_barang_id = ? and jumlah_barang = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{harga, totalHarga, discount, kasir, shortcut, satuan, barang_id, jumlahBarang});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updatePenjualanSementara(PenjualanSementara penjualanSementara) {
        String sql = "UPDATE t_penjualan_sementara SET JUMLAH_BARANG=:jumlahBarang, SATUAN=:satuan, HARGA_JUAL=:hargaJual, DISCOUNT=:discount, TOTAL_HARGA=:totalHarga, NOTA_KHUSUS=:notaKhusus, KETERANGAN=:keterangan, PROMO=:promo WHERE ID=:id";
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
        String sql = "UPDATE t_penjualan_sementara SET M_CUSTOMER_ID = ? WHERE SHORTCUT = ? AND KASIR = ? AND STATUS = ?";
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
        String sql = "UPDATE t_penjualan_sementara SET M_SALES_ID = ? WHERE SHORTCUT = ? AND KASIR = ? AND STATUS = ?";
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
    public int deletePenjualanSementara(String shortcut, String barangId, float jumlahBarang, String satuan) {
        String sql = "DELETE FROM t_penjualan_sementara WHERE SHORTCUT = ? AND M_BARANG_ID = ? AND JUMLAH_BARANG = ? AND SATUAN = ? AND KASIR = ? AND STATUS = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{shortcut, barangId, Float.valueOf(jumlahBarang), satuan, this.helper.getKasir(), this.helper.getStatusProgram()});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int deletePenjualanSementara(String shortcut, String barangId) {
        String sql = "DELETE FROM t_penjualan_sementara WHERE SHORTCUT = ? AND M_BARANG_ID = ? AND KASIR = ? AND STATUS = ?";
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
        String sql = "DELETE FROM t_penjualan_sementara WHERE SHORTCUT = ? AND KASIR = ? AND STATUS = ?";
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
    public List<PenjualanSementara> getPenjualanSementaraByShortcut(String shortcut, String parent_id, String satuan) {
        String sql = "SELECT * FROM t_penjualan_sementara JOIN m_barang ON (t_penjualan_sementara.M_BARANG_ID = m_barang.ID) WHERE SHORTCUT = ? AND PARENT_ID = ? AND SATUAN = ? AND KASIR = ? AND STATUS = ? ORDER BY NAMA";
        List penjualanSementaras = null;
        try {
            penjualanSementaras = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PenjualanSementaraRowMapper(), new Object[]{shortcut, parent_id, satuan, this.helper.getKasir(), this.helper.getStatusProgram()});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return penjualanSementaras;
    }

    @Override
    public List<PenjualanSementara> getPenjualanSementaraByShortcutIdBarang(String shortcut, String barangId, String satuan) {
        String sql = "SELECT * FROM t_penjualan_sementara JOIN m_barang ON (t_penjualan_sementara.M_BARANG_ID = m_barang.ID) WHERE SHORTCUT = ? AND m_barang_id = ? AND KASIR = ? AND SATUAN = ? AND STATUS = ? ORDER BY NAMA";
        List penjualanSementaras = null;
        try {
            penjualanSementaras = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PenjualanSementaraRowMapper(), new Object[]{shortcut, barangId, this.helper.getKasir(), satuan, this.helper.getStatusProgram()});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return penjualanSementaras;
    }

    @Override
    public List<PenjualanSementara> getPenjualanSementaraByShortcut(String shortcut) {
        String sql = "SELECT * FROM t_penjualan_sementara JOIN m_barang ON (t_penjualan_sementara.M_BARANG_ID = m_barang.ID) WHERE SHORTCUT = ? AND KASIR = ? AND STATUS = ? ORDER BY NAMA";
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
    public List<Object[]> getPoinPerPenjualan(String shortcut) {
        String sql = "SELECT m_jenis_jenis, sum(total_harga) as total_harga, poin_sales, sum(total_harga)/poin_sales as total_poin FROM t_penjualan_sementara join m_barang on (t_penjualan_sementara.m_barang_id = m_barang.id) join m_jenis on (m_barang.m_jenis_jenis = m_jenis.jenis) where kasir = ? and shortcut = ? group by m_jenis_jenis";
        LinkedList<Object[]> objects = new LinkedList<Object[]>();
        try {
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{this.helper.getKasir(), shortcut});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("m_jenis_jenis"), row.get("total_harga"), row.get("poin_sales"), row.get("total_poin") == null ? Integer.valueOf(0) : row.get("total_poin")};
                objects.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return objects;
    }

    @Override
    public List<Object[]> getPenjualanSementaraByShortcut2(String shortcut) {
        String sql = "SELECT m_customer_id , m_sales_id, m_barang_id, nama, jumlah_barang, satuan, harga_jual, discount, total_harga, keterangan, m_jenis_jenis FROM t_penjualan_sementara JOIN m_barang ON (t_penjualan_sementara.M_BARANG_ID = m_barang.ID) WHERE SHORTCUT = ? AND KASIR = ? AND STATUS = ? ";
        LinkedList<Object[]> objects = new LinkedList<Object[]>();
        try {
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{shortcut, this.helper.getKasir(), this.helper.getStatusProgram()});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("m_customer_id"), row.get("m_sales_id"), row.get("m_barang_id"), row.get("nama"), row.get("jumlah_barang"), row.get("satuan"), row.get("harga_jual"), row.get("discount"), row.get("total_harga"), row.get("keterangan"), row.get("m_jenis_jenis")};
                objects.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return objects;
    }

    @Override
    public List<Object[]> getPenjualanSementaraByShortcut2OrderByName(String shortcut) {
        String sql = "SELECT m_customer_id , m_sales_id, m_barang_id, nama, jumlah_barang, satuan, harga_jual, discount, total_harga, keterangan, m_jenis_jenis, promo, t_penjualan_sementara.created as created FROM t_penjualan_sementara JOIN m_barang ON (t_penjualan_sementara.M_BARANG_ID = m_barang.ID) WHERE SHORTCUT = ? AND KASIR = ? AND STATUS = ? ORDER BY NAMA";
        LinkedList<Object[]> objects = new LinkedList<Object[]>();
        try {
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{shortcut, this.helper.getKasir(), this.helper.getStatusProgram()});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("m_customer_id"), row.get("m_sales_id"), row.get("m_barang_id"), row.get("nama"), row.get("jumlah_barang"), row.get("satuan"), row.get("harga_jual"), row.get("discount"), row.get("total_harga"), row.get("keterangan"), row.get("m_jenis_jenis"), row.get("promo"), row.get("created")};
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
        String sql = "UPDATE t_penjualan_sementara SET KETERANGAN = ? WHERE SHORTCUT = ? AND KASIR = ? AND STATUS = ?";
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
    public float getJumlahPenjualanSementara(int kasirId, String shortCut, String barangId, String satuan) {
        float result = 0.0f;
        String sql = "SELECT jumlah_barang FROM T_PENJUALAN_SEMENTARA WHERE kasir = ? AND shortcut = ? AND m_barang_id = ? AND satuan = ?";
        try {
            result = ((Float)this.getSimpleJdbcTemplate().queryForObject(sql, Float.class, new Object[]{kasirId, shortCut, barangId, satuan})).floatValue();
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int isVoid(int kasirId, String shortCut, String barangId) {
        int result = 0;
        String sql = "SELECT count(id) FROM t_penjualan_sementara where kasir = ? AND shortcut = ? AND m_barang_id = ? and jumlah_barang<0";
        try {
            result = (Integer)this.getSimpleJdbcTemplate().queryForObject(sql, Integer.class, new Object[]{kasirId, shortCut, barangId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int sumBarang(int kasirId, String shortCut, String barangId, String satuan) {
        int result = 0;
        String sql = "SELECT sum(jumlah_barang) FROM t_penjualan_sementara where kasir = ? AND shortcut = ? AND m_barang_id = ? and satuan = ?";
        try {
            try {
                result = (Integer)this.getSimpleJdbcTemplate().queryForObject(sql, Integer.class, new Object[]{kasirId, shortCut, barangId, satuan});
            }
            catch (Exception e) {
                result = 0;
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int getRecentTransaction(int kasirId) {
        int result = 0;
        String sql = "SELECT count(id) FROM T_PENJUALAN_SEMENTARA WHERE KASIR = ?";
        try {
            result = (Integer)this.getSimpleJdbcTemplate().queryForObject(sql, Integer.class, new Object[]{kasirId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }
}

