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
import minimart.engine.dao.PickingListOnlineDao;
import minimart.engine.model.PickingList;
import minimart.engine.model.PickingListDetail;
import minimart.engine.rowmapper.PickingListDetailRowMapper;
import minimart.engine.rowmapper.PickingListOnlineRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PickingListOnlineDaoImpl
extends SimpleJdbcDaoSupport
implements PickingListOnlineDao {
    public PickingListOnlineDaoImpl() {
        this.setDataSource((DataSource)OnlineConnManager.getInstance().getDataSource());
    }

    @Override
    public PickingList getPickingListById(Integer id) {
        String sql = "SELECT * FROM t_picking_list_header WHERE id = ?";
        PickingList pl = null;
        try {
            pl = (PickingList)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PickingListOnlineRowMapper(), new Object[]{id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return pl;
    }

    @Override
    public List<PickingListDetail> getPickingListDetailByHeaderId(Integer id_header) {
        String sql = "SELECT * FROM v_picking_list_detail_nama_item WHERE id_picking_list_header = ?";
        List plds = null;
        try {
            plds = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PickingListDetailRowMapper(), new Object[]{id_header});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return plds;
    }
}

