/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import minimart.engine.model.Barang;
import minimart.engine.model.PromoPeriode;
import minimart.engine.service.BarangService;
import minimart.engine.service.KonstantaService;
import minimart.engine.service.PromoPeriodeService;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class PromoPeriodePembelian
extends JDialog {
    private JCheckBox aktifkanCheck;
    private JTextArea areaPesan;
    private JButton cancelBtn;
    private JTextField idField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JScrollPane jScrollPane1;
    private JFormattedTextField minimalField;
    private JLabel namaLbl;
    private JButton saveBtn;
    private SearchDialog sd;
    private int screenHeight;
    private int screenWidth;
    private BarangService barangService = new BarangService();
    private Barang barang;
    private KonstantaService konstantaService = new KonstantaService();
    private PromoPeriodeService promoPeriodeService = new PromoPeriodeService();
    private PromoPeriode promoPeriode = new PromoPeriode();

    public PromoPeriodePembelian(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        if (this.promoPeriodeService.isNew()) {
            PromoPeriode promoPeriode = new PromoPeriode();
            promoPeriode.setId(1);
            promoPeriode.setAktifkan("NONAKTIF");
            promoPeriode.setBarangId("");
            promoPeriode.setPesan("");
            promoPeriode.setMinimal(0L);
            this.promoPeriodeService.createPromoPeriode(promoPeriode);
        }
        this.promoPeriode = this.promoPeriodeService.getPromoPeriode(1);
        if (this.promoPeriode != null) {
            if (this.promoPeriode.getAktifkan().equalsIgnoreCase("AKTIF")) {
                this.aktifkanCheck.setSelected(true);
            } else {
                this.aktifkanCheck.setSelected(false);
            }
            System.out.println(this.promoPeriode.getBarangId());
            this.barang = this.barangService.getBarang(this.promoPeriode.getBarangId());
            if (this.barang != null) {
                this.idField.setText(this.barang.getId());
                this.namaLbl.setText(this.barang.getNama());
            }
            this.areaPesan.setText(this.promoPeriode.getPesan());
            this.minimalField.setText(this.promoPeriode.getMinimal() + "");
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.idField = new JTextField();
        this.jLabel2 = new JLabel();
        this.namaLbl = new JLabel();
        this.aktifkanCheck = new JCheckBox();
        this.saveBtn = new JButton();
        this.cancelBtn = new JButton();
        this.jLabel3 = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.areaPesan = new JTextArea();
        this.jLabel4 = new JLabel();
        this.minimalField = new JFormattedTextField();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("ID Barang");
        this.idField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoPeriodePembelian.this.idFieldKeyPressed(evt);
            }
        });
        this.jLabel2.setText("Nama Barang");
        this.namaLbl.setText("-");
        this.aktifkanCheck.setText("Aktifkan");
        this.aktifkanCheck.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PromoPeriodePembelian.this.aktifkanCheckActionPerformed(evt);
            }
        });
        this.saveBtn.setText("Save");
        this.saveBtn.setPreferredSize(new Dimension(72, 23));
        this.saveBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PromoPeriodePembelian.this.saveBtnActionPerformed(evt);
            }
        });
        this.cancelBtn.setText("Cancel");
        this.cancelBtn.setPreferredSize(new Dimension(72, 23));
        this.cancelBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PromoPeriodePembelian.this.cancelBtnActionPerformed(evt);
            }
        });
        this.jLabel3.setText("Pesan");
        this.areaPesan.setColumns(20);
        this.areaPesan.setRows(5);
        this.jScrollPane1.setViewportView(this.areaPesan);
        this.jLabel4.setText("Minimal");
        this.minimalField.setText("0");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.aktifkanCheck).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addGap(18, 18, 18).addComponent(this.idField, -2, 177, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.namaLbl, -1, -1, Short.MAX_VALUE))).addGroup(layout.createSequentialGroup().addComponent(this.jLabel4).addGap(33, 33, 33).addComponent(this.minimalField, -2, 99, -2)).addGroup(layout.createSequentialGroup().addComponent(this.saveBtn, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.cancelBtn, -2, -1, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel3).addGap(38, 38, 38).addComponent(this.jScrollPane1, -2, 263, -2))).addContainerGap(84, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.idField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.namaLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3).addComponent(this.jScrollPane1, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.minimalField, -2, -1, -2).addComponent(this.jLabel4)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.aktifkanCheck).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.saveBtn, -2, -1, -2).addComponent(this.cancelBtn, -2, -1, -2)).addGap(20, 20, 20)));
        this.pack();
    }

    private void aktifkanCheckActionPerformed(ActionEvent evt) {
    }

    private void idFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            this.sd = new SearchDialog(null, true, "Pencarian Data Barang");
            this.sd.addSearchDialogListener(new searchBarangDialogEventHandler());
            int w = this.sd.getWidth();
            int h = this.sd.getHeight();
            this.sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            this.sd.setVisible(true);
        } else if (evt.getKeyCode() == 10 || evt.getKeyCode() == 9) {
            if (this.idField.getText().equals("")) {
                this.sd = new SearchDialog(null, true, "Pencarian Data Barang");
                this.sd.addSearchDialogListener(new searchBarangDialogEventHandler());
                int w = this.sd.getWidth();
                int h = this.sd.getHeight();
                this.sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                this.sd.setVisible(true);
            } else {
                this.loadKodeBarang(this.idField.getText());
                if (this.barang != null) {
                    this.saveBtn.requestFocus();
                } else {
                    this.idField.setText("");
                }
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void cancelBtnActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void saveBtnActionPerformed(ActionEvent evt) {
        int update1 = 0;
        System.out.println(this.aktifkanCheck.isSelected());
        if (this.aktifkanCheck.isSelected()) {
            this.promoPeriode.setAktifkan("AKTIF");
        } else {
            this.promoPeriode.setAktifkan("NONAKTIF");
        }
        this.promoPeriode.setBarangId(this.idField.getText());
        this.promoPeriode.setPesan(this.areaPesan.getText());
        this.promoPeriode.setMinimal(Integer.parseInt(this.minimalField.getText().replace(".", "")));
        update1 = this.promoPeriodeService.updatePromoPeriode(this.promoPeriode);
        if (update1 == 1) {
            JOptionPane.showMessageDialog(null, "Data Telah Tersimpan");
        }
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
            Logger.getLogger(PromoPeriodePembelian.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex) {
            Logger.getLogger(PromoPeriodePembelian.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex) {
            Logger.getLogger(PromoPeriodePembelian.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(PromoPeriodePembelian.class.getName()).log(Level.SEVERE, null, ex);
        }
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                PromoPeriodePembelian dialog = new PromoPeriodePembelian((Frame)new JFrame(), true);
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

    private void loadKodeBarang(String choseValue) {
        this.idField.setText(choseValue);
        this.barang = this.barangService.getBarang(choseValue);
        this.namaLbl.setText(this.barang.getNama());
        this.idField.setText(choseValue);
    }

    private class searchPanelBarangEventHandler
    implements SearchPanelListener {
        private searchPanelBarangEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            PromoPeriodePembelian.this.loadKodeBarang(chooseValue);
            PromoPeriodePembelian.this.saveBtn.requestFocus();
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
            List<Barang> barangs = PromoPeriodePembelian.this.barangService.getBarangs("", searchWord, false, "", true);
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

