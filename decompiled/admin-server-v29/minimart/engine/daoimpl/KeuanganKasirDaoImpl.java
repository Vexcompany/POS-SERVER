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

import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.controller.Helper;
import minimart.engine.dao.KeuanganKasirDao;
import minimart.engine.model.KeuanganKasir;
import minimart.engine.rowmapper.KeuanganKasirRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class KeuanganKasirDaoImpl
extends SimpleJdbcDaoSupport
implements KeuanganKasirDao {
    Helper helper = Helper.getInstance();

    public KeuanganKasirDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createdKeuanganKasir(KeuanganKasir keuanganKasir) {
        String sql = "INSERT INTO m_keuangan_kasir (ID_KASIR, JUMLAH_UANG, STATUS_KASIR, OPENED_BY, OPENING) VALUES (:idKasir, :jumlahUang,:statusKasir, :openedBy, :opening)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)keuanganKasir);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updateKeuanganKasir(KeuanganKasir keuanganKasir) {
        String sql = "UPDATE m_keuangan_kasir SET JUMLAH_UANG=:jumlahUang , STATUS_KASIR =:statusKasir , OPENED_BY=:openedBy , OPENING=:opening WHERE ID_KASIR=:idKasir";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)keuanganKasir);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public KeuanganKasir getKeuanganKasirs(int kasir) {
        String sql = "SELECT * FROM m_keuangan_kasir WHERE ID_KASIR = ?";
        KeuanganKasir keuanganKasir = null;
        try {
            keuanganKasir = (KeuanganKasir)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new KeuanganKasirRowMapper(), new Object[]{kasir});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return keuanganKasir;
    }

    @Override
    public long getKeuanganKasirByTanggal(String tanggal, int kasir) {
        String sql = "SELECT SUM(TOTAL_PENJUALAN) FROM T_PENJUALAN WHERE TANGGAL = ? AND KASIR = ? AND STATUS = ?";
        long keuanganKasir = 0L;
        try {
            keuanganKasir = this.getSimpleJdbcTemplate().queryForLong(sql, new Object[]{tanggal, kasir, this.helper.getStatusProgram()});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return keuanganKasir;
    }
}

