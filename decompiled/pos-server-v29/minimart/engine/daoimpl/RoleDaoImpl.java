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

import java.util.List;
import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.dao.RoleDao;
import minimart.engine.model.Role;
import minimart.engine.rowmapper.RoleRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class RoleDaoImpl
extends SimpleJdbcDaoSupport
implements RoleDao {
    public RoleDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int getAccess(String role, String menu) {
        int access = 0;
        String sql = "select access from m_role where role = ? and menu = ?;";
        try {
            access = (Integer)this.getJdbcTemplate().queryForObject(sql, Integer.class, new Object[]{role, menu});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return access;
    }

    @Override
    public int updateRole(List<Role> roles) {
        String sql = "UPDATE m_role SET access=:access WHERE role=:role and menu=:menu";
        int result = -1;
        int[] results = null;
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])roles.toArray());
        try {
            results = this.getSimpleJdbcTemplate().batchUpdate(sql, batch);
            if (results != null) {
                result = 1;
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<Role> getRoles(String role) {
        String sql = "SELECT * FROM m_role WHERE role like ?";
        List roles = null;
        try {
            role = '%' + role + '%';
            roles = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new RoleRowMapper(), new Object[]{role});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return roles;
    }

    @Override
    public Role getRoleById(String id) {
        String sql = "SELECT * FROM m_role WHERE id = ?";
        Role role = null;
        try {
            role = (Role)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new RoleRowMapper(), new Object[]{id});
        }
        catch (DataAccessException e) {
            System.out.println((Object)e);
        }
        return role;
    }

    @Override
    public int createRole(Role role) {
        String sql = "INSERT INTO m_role (id, role, menu, access, updated, updated_by) VALUES (:id, :role, :menu, :access, :updated, :updatedBy)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)role);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int createRoles(List<Role> roles) {
        String sql = "INSERT INTO m_role (id, role, menu, access, updated, updated_by) VALUES (:id, :role, :menu, :access, :updated, :updatedBy)";
        int[] results = null;
        int result = -1;
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])roles.toArray());
        try {
            results = this.getSimpleJdbcTemplate().batchUpdate(sql, batch);
            if (results != null) {
                result = 1;
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }
}

