/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.toedter.calendar.JDateChooser
 */
package minimart.ui.view.report;

import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import minimart.engine.model.Barang;
import minimart.engine.service.BarangService;
import minimart.ui.listener.ReportHistoryHargaDialogListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class ReportHistoryHargaDialog
extends JDialog {
    private JLabel barangNameField;
    private JDateChooser endDateField;
    private JTextField idBarangField;
    private JLabel jLabel1;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JButton keluarButton;
    private JDateChooser startDateField;
    private JButton tampilkanButton;
    private int screenWidth;
    private int screenHeight;
    private BarangService barangService = new BarangService();
    private ReportHistoryHargaDialogListener reportHistoryHargaDialogListener;

    public ReportHistoryHargaDialog(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.startDateField.setDate(new Date());
        this.endDateField.setDate(new Date());
    }

    private void escapeClick() {
        this.idBarangField.requestFocus();
        this.idBarangField.selectAll();
    }

    private void loadKodeBarang() {
        Barang supplier = this.barangService.getBarang(this.idBarangField.getText());
        if (supplier == null) {
            JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
        } else {
            this.barangNameField.setText(supplier.getNama());
            this.tampilkanButton.requestFocus();
        }
    }

    public void addListener(ReportHistoryHargaDialogListener reportHistoryHargaDialogListener) {
        this.reportHistoryHargaDialogListener = reportHistoryHargaDialogListener;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.idBarangField = new JTextField();
        this.barangNameField = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.startDateField = new JDateChooser();
        this.jLabel5 = new JLabel();
        this.endDateField = new JDateChooser();
        this.tampilkanButton = new JButton();
        this.keluarButton = new JButton();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Kode Barang");
        this.idBarangField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportHistoryHargaDialog.this.idBarangFieldKeyPressed(evt);
            }
        });
        this.barangNameField.setText("-");
        this.jLabel3.setText("Kosong=Tampilkan Semua");
        this.jLabel4.setText("Dari Tanggal");
        this.jLabel5.setText("Sampai Tanggal");
        this.tampilkanButton.setText("Tampilkan");
        this.tampilkanButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportHistoryHargaDialog.this.tampilkanButtonActionPerformed(evt);
            }
        });
        this.tampilkanButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportHistoryHargaDialog.this.tampilkanButtonKeyPressed(evt);
            }
        });
        this.keluarButton.setText("Keluar");
        this.keluarButton.setMaximumSize(new Dimension(79, 23));
        this.keluarButton.setMinimumSize(new Dimension(79, 23));
        this.keluarButton.setPreferredSize(new Dimension(79, 23));
        this.keluarButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportHistoryHargaDialog.this.keluarButtonActionPerformed(evt);
            }
        });
        this.keluarButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportHistoryHargaDialog.this.keluarButtonKeyPressed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(25, 25, 25).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel4)).addGap(24, 24, 24).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.idBarangField).addComponent(this.barangNameField, -1, -1, Short.MAX_VALUE).addComponent((Component)this.startDateField, -1, -1, Short.MAX_VALUE))).addGroup(layout.createSequentialGroup().addComponent(this.jLabel5).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent((Component)this.endDateField, -1, -1, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addComponent(this.tampilkanButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keluarButton, -2, -1, -2).addGap(0, 0, Short.MAX_VALUE))))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel3).addContainerGap(-1, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.idBarangField, -2, -1, -2).addComponent(this.jLabel3)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.barangNameField).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel4).addComponent((Component)this.startDateField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel5).addComponent((Component)this.endDateField, -2, -1, -2)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.tampilkanButton).addComponent(this.keluarButton, -2, -1, -2)).addContainerGap(-1, Short.MAX_VALUE)));
        this.pack();
    }

    private void keluarButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void idBarangFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Barang");
            sd.addSearchDialogListener(new searchBarangDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        } else if (evt.getKeyCode() == 10) {
            if (this.idBarangField.getText().equals("")) {
                this.tampilkanButton.requestFocus();
            } else {
                this.loadKodeBarang();
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void tampilkanButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 38) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 39) {
            this.keluarButton.requestFocus();
        } else if (evt.getKeyCode() == 10) {
            this.reportHistoryHargaDialogListener.tampilkanClick(this.startDateField.getDate(), this.endDateField.getDate(), this.idBarangField.getText());
            this.dispose();
        }
    }

    private void tampilkanButtonActionPerformed(ActionEvent evt) {
        this.reportHistoryHargaDialogListener.tampilkanClick(this.startDateField.getDate(), this.endDateField.getDate(), this.idBarangField.getText());
        this.dispose();
    }

    private void keluarButtonKeyPressed(KeyEvent evt) {
        this.dispose();
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
            Logger.getLogger(ReportHistoryHargaDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex) {
            Logger.getLogger(ReportHistoryHargaDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex) {
            Logger.getLogger(ReportHistoryHargaDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(ReportHistoryHargaDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                ReportHistoryHargaDialog dialog = new ReportHistoryHargaDialog((Frame)new JFrame(), true);
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

    private class searchPanelBarangEventHandler
    implements SearchPanelListener {
        private searchPanelBarangEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            ReportHistoryHargaDialog.this.idBarangField.setText(chooseValue);
            ReportHistoryHargaDialog.this.loadKodeBarang();
        }
    }

    private class searchBarangDialogEventHandler
    implements SearchDialogListener {
        private searchBarangDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"KODE BARANG", "NAMA BARANG", "SATUAN", "JENIS"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Barang> barangs = ReportHistoryHargaDialog.this.barangService.getBarangs("", searchWord, false, "", true);
            for (Barang barang : barangs) {
                Object[] temp = new String[]{barang.getId(), barang.getNama(), barang.getSatuan1(), barang.getJenis()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Barang");
            sp.addSearchPanelListener(new searchPanelBarangEventHandler());
            sp.setVisible(true);
        }
    }
}

