/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.Role;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class RoleRowMapper
implements ParameterizedRowMapper<Role> {
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        Role role = new Role();
        role.setId(rs.getString("ID"));
        role.setRole(rs.getString("ROLE"));
        role.setMenu(rs.getString("MENU"));
        role.setAccess(rs.getInt("ACCESS"));
        role.setUpdated(rs.getDate("UPDATED"));
        role.setUpdatedBy(rs.getString("UPDATED_BY"));
        return role;
    }
}

