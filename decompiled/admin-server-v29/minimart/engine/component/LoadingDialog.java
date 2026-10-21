/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.component;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import minimart.engine.controller.ConnectionManager;

public class LoadingDialog {
    private JDialog dialog;

    public LoadingDialog(Frame parent) {
        this.dialog = new JDialog(parent);
        this.dialog.setUndecorated(true);
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        JLabel label = new JLabel("Menguji koneksi...");
        JProgressBar bar = new JProgressBar();
        bar.setIndeterminate(true);
        panel.setLayout(new BorderLayout(10, 10));
        panel.add((Component)label, "North");
        panel.add((Component)bar, "Center");
        this.dialog.add(panel);
        this.dialog.pack();
        this.dialog.setLocationRelativeTo(parent);
        this.dialog.setModal(true);
    }

    public boolean showDialog(String selectedName) {
        this.dialog.setVisible(true);
        if (ConnectionManager.getInstance().testConnection()) {
            this.closeDialog();
            return true;
        }
        this.closeDialog();
        JOptionPane.showMessageDialog(null, "Tidak dapat terhubung ke database cabang: " + selectedName + "\nSilakan pilih cabang yang lain.", "Koneksi Gagal", 0);
        return false;
    }

    public void closeDialog() {
        this.dialog.setVisible(false);
    }
}

