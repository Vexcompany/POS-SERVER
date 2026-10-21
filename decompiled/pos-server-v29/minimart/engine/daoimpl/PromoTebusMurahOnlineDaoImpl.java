/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 *  org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport
 */
package minimart.engine.daoimpl;

import java.util.List;
import javax.sql.DataSource;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.PromoTebusMurahOnlineDao;
import minimart.engine.model.PromoTebusMurah;
import minimart.engine.model.PromoTebusMurahDetail;
import minimart.engine.rowmapper.PromoTebusMurahDetailRowMapper;
import minimart.engine.rowmapper.PromoTebusMurahOnlineRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PromoTebusMurahOnlineDaoImpl
extends SimpleJdbcDaoSupport
implements PromoTebusMurahOnlineDao {
    public PromoTebusMurahOnlineDaoImpl() {
        this.setDataSource((DataSource)OnlineConnManager.getInstance().getDataSource());
    }

    @Override
    public List<PromoTebusMurah> getPromoTebusMurahOnline(String idCabang) {
        String sql = "SELECT * FROM promo_tebus_murah WHERE id_cabang like ?";
        List promoTebusMurahs = null;
        try {
            idCabang = "%[" + idCabang + "]%";
            promoTebusMurahs = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PromoTebusMurahOnlineRowMapper(), new Object[]{idCabang});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoTebusMurahs;
    }

    @Override
    public List<PromoTebusMurahDetail> getPromoTebusMurahDetailOnlineByPromoTebusMurahId(String promoTebusMurahId) {
        String sql = "SELECT * FROM promo_tebus_murah_detail WHERE promo_tebus_murah_id in (" + promoTebusMurahId + ")";
        List promoTebusMurahDetails = null;
        try {
            promoTebusMurahDetails = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PromoTebusMurahDetailRowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoTebusMurahDetails;
    }
}

