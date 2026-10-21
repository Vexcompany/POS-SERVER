/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class Role {
    private String id;
    private String role;
    private String menu;
    private int access;
    private Date updated;
    private String updatedBy;

    public Date getUpdated() {
        return this.updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMenu() {
        return this.menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public int getAccess() {
        return this.access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

