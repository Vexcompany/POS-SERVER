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
import minimart.engine.dao.PromoHematOnlineDao;
import minimart.engine.model.PromoHemat;
import minimart.engine.model.PromoHematDetail;
import minimart.engine.rowmapper.PromoHematDetailRowMapper;
import minimart.engine.rowmapper.PromoHematOnlineRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PromoHematOnlineDaoImpl
extends SimpleJdbcDaoSupport
implements PromoHematOnlineDao {
    public PromoHematOnlineDaoImpl() {
        this.setDataSource((DataSource)OnlineConnManager.getInstance().getDataSource());
    }

    @Override
    public List<PromoHemat> getPromoHematOnline(String idCabang) {
        String sql = "SELECT * FROM promo_hemat WHERE id_cabang like ?";
        List promoHemats = null;
        try {
            idCabang = "%[" + idCabang + "]%";
            promoHemats = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PromoHematOnlineRowMapper(), new Object[]{idCabang});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoHemats;
    }

    @Override
    public List<PromoHematDetail> getPromoHematDetailOnlineByPromoHematId(String promoHematId) {
        String sql = "SELECT * FROM promo_hemat_detail WHERE promo_hemat_id in (" + promoHematId + ")";
        List promoHematDetails = null;
        try {
            promoHematDetails = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PromoHematDetailRowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return promoHematDetails;
    }
}

