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
import minimart.engine.dao.PromoTebusOnlineDao;
import minimart.engine.model.PromoTebus;
import minimart.engine.model.PromoTebusDetail;
import minimart.engine.rowmapper.PromoTebusDetailRowMapper;
import minimart.engine.rowmapper.PromoTebusOnlineRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PromoTebusOnlineDaoImpl
extends SimpleJdbcDaoSupport
implements PromoTebusOnlineDao {
    public PromoTebusOnlineDaoImpl() {
        this.setDataSource((DataSource)OnlineConnManager.getInstance().getDataSource());
    }

    @Override
    public List<PromoTebus> getPromoTebusOnline(String idCabang) {
        String sql = "SELECT * FROM promo_tebus WHERE id_cabang like ?";
        List promoTebuss = null;
        try {
            idCabang = "%[" + idCabang + "]%";
            promoTebuss = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PromoTebusOnlineRowMapper(), new Object[]{idCabang});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoTebuss;
    }

    @Override
    public List<PromoTebusDetail> getPromoTebusDetailOnlineByPromoTebusId(String promoTebusId) {
        String sql = "SELECT * FROM promo_tebus_detail WHERE promo_tebus_id in (" + promoTebusId + ")";
        List promoTebusDetails = null;
        try {
            promoTebusDetails = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PromoTebusDetailRowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoTebusDetails;
    }
}

