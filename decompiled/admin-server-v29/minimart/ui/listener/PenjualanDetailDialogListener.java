/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.listener;

import java.util.List;
import javax.swing.table.DefaultTableModel;

public interface PenjualanDetailDialogListener {
    public void saveClick(Object[] var1, boolean var2);

    public void deleteClick(String var1);

    public void refreshData();

    public DefaultTableModel getPenjualanTableModel();

    public void changeButtonPromoTebusMurah();

    public void changeButtonPromoTebus(String var1, List<Object[]> var2);
}

