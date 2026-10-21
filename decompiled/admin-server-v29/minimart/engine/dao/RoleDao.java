/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.dao;

import java.util.List;
import minimart.engine.model.Role;

public interface RoleDao {
    public int getAccess(String var1, String var2);

    public int updateRole(List<Role> var1);

    public List<Role> getRoles(String var1);

    public int createRole(Role var1);

    public int createRoles(List<Role> var1);

    public Role getRoleById(String var1);
}

