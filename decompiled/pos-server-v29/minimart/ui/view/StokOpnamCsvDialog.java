/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import minimart.engine.model.Barang;
import minimart.engine.service.BarangService;
import minimart.engine.service.StokOpnamCsvService;

public class StokOpnamCsvDialog
extends JDialog {
    private JButton jButton1;
    private JScrollPane jScrollPane1;
    private JTable jTable1;
    private String[] stokOpnamTMColumn = new String[]{"ID Barang", "Nama Barang", "Stok", "Satuan"};
    private DefaultTableModel stokOpnamTM = new DefaultTableModel(new String[0][1], this.stokOpnamTMColumn);
    private StokOpnamCsvService csvService = new StokOpnamCsvService();
    private BarangService barangService = new BarangService();

    public StokOpnamCsvDialog(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.fillData();
    }

    public void fillData() {
        List<Object[]> datas = this.csvService.getStokOpnamCSVs();
        for (Object[] data : datas) {
            if (data[0].toString().equalsIgnoreCase("TIDAK DITEMUKAN")) continue;
            Barang barang = this.barangService.getBarang(data[0].toString());
            Object[] rowValue = new Object[6];
            rowValue[0] = data[0].toString();
            rowValue[1] = barang.getNama();
            rowValue[2] = data[1].toString();
            rowValue[3] = barang.getSatuan1();
            this.stokOpnamTM.addRow(rowValue);
        }
    }

    private void initComponents() {
        this.jScrollPane1 = new JScrollPane();
        this.jTable1 = new JTable();
        this.jButton1 = new JButton();
        this.setDefaultCloseOperation(2);
        this.setPreferredSize(new Dimension(700, 500));
        this.jTable1.setModel(this.stokOpnamTM);
        this.jScrollPane1.setViewportView(this.jTable1);
        this.jButton1.setText("Keluar");
        this.jButton1.setPreferredSize(new Dimension(73, 23));
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, GroupLayout.Alignment.TRAILING, -1, 600, Short.MAX_VALUE).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(this.jButton1, -2, -1, -2))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -1, 384, Short.MAX_VALUE).addGap(18, 18, 18).addComponent(this.jButton1, -2, -1, -2).addContainerGap()));
        this.pack();
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (!"Nimbus".equals(info.getName())) continue;
                UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(StokOpnamCsvDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex) {
            Logger.getLogger(StokOpnamCsvDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex) {
            Logger.getLogger(StokOpnamCsvDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(StokOpnamCsvDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                StokOpnamCsvDialog dialog = new StokOpnamCsvDialog((Frame)new JFrame(), true);
                dialog.addWindowListener(new WindowAdapter(){

                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
}

