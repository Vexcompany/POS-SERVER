/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport
 */
package minimart.engine.daoimpl;

import java.util.List;
import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.dao.PointUndiDetailsDao;
import minimart.engine.model.PointUndiDetail;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PointUndiDetailsDaoImpl
extends SimpleJdbcDaoSupport
implements PointUndiDetailsDao {
    public PointUndiDetailsDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createPointUndiDetails(PointUndiDetail pud) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int updatePointUndiDetails(PointUndiDetail pud) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int deletePointUndiDetails(int pointUndiId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<PointUndiDetail> getPointUndiDetails(int pointUndiId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PointUndiDetail getPointUndiDetailByBarangId(String barangId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

