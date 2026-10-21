/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport
 */
package minimart.engine.daoimpl;

import javax.sql.DataSource;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.VersionOnlineDao;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class VersionOnlineDaoImpl
extends SimpleJdbcDaoSupport
implements VersionOnlineDao {
    public VersionOnlineDaoImpl() {
        this.setDataSource((DataSource)OnlineConnManager.getInstance().getDataSource());
    }

    @Override
    public int getOnlineMajorVersion() {
        String sql = "SELECT value FROM m_version WHERE id = 'major' LIMIT 1";
        int majorVersion = 0;
        try {
            majorVersion = (Integer)this.getSimpleJdbcTemplate().queryForObject(sql, Integer.class, new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return majorVersion;
    }
}

