/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.service;

import java.util.List;
import minimart.engine.dao.RoleDao;
import minimart.engine.daoimpl.RoleDaoImpl;
import minimart.engine.model.Role;

public class RoleService {
    private RoleDao roleDao = new RoleDaoImpl();

    public int getAccess(String role, String menu) {
        return this.roleDao.getAccess(role, menu);
    }

    public int updateRole(List<Role> roles) {
        return this.roleDao.updateRole(roles);
    }

    public List<Role> getRoles(String role) {
        return this.roleDao.getRoles(role);
    }

    public int createRole(Role role) {
        return this.roleDao.createRole(role);
    }

    public int createRoles(List<Role> roles) {
        return this.roleDao.createRoles(roles);
    }

    public Role getRoleById(String id) {
        return this.roleDao.getRoleById(id);
    }
}

