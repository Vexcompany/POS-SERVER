/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.toedter.calendar.JDateChooser
 */
package minimart.ui.view;

import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.controller.Helper;
import minimart.engine.model.StokOpnam;
import minimart.engine.model.StokOpnamDetail;
import minimart.engine.model.Supplier;
import minimart.engine.service.BarangService;
import minimart.engine.service.KaryawanService;
import minimart.engine.service.PengembalianBsDetailService;
import minimart.engine.service.PengembalianBsService;
import minimart.engine.service.SinkronisasiBarangService;
import minimart.engine.service.StokOpnamDetailService;
import minimart.engine.service.StokOpnamService;
import minimart.engine.service.SupplierService;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.listener.SoParsialDialogListener;
import minimart.ui.view.StokOpnamParsialDialog;

public class StokOpnamParsial
extends JPanel {
    private JDateChooser dateField;
    private JButton detailButton;
    private JButton exitButton;
    private JTextField idField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JScrollPane jScrollPane1;
    private JComboBox lokasiCmb;
    private JButton saveButton;
    private JTable soParsialTable;
    private String[] stokOpnamTMColumn = new String[]{"Kode Barang", "Nama Barang", "Lokasi", "Jenis", "Rak", "Konversi", "Stok Komputer", "Stok Repack", "Stok Fisik", "Selisih", "Satuan", "Total Hpp"};
    private DefaultTableModel stokOpnamTM = new DefaultTableModel(new String[0][1], this.stokOpnamTMColumn);
    private int screenWidth;
    private int screenHeight;
    private PengembalianBsService pengembalianBsService = new PengembalianBsService();
    private PengembalianBsDetailService pengembalianBsDetailService = new PengembalianBsDetailService();
    private KaryawanService karyawanService = new KaryawanService();
    private BarangService barangService = new BarangService();
    private StokOpnam stokOpnam = new StokOpnam();
    private Helper helper = Helper.getInstance();
    private DefaultPanelListener listener;
    private SupplierService supplierService = new SupplierService();
    private Supplier supplier;
    private SinkronisasiBarangService sinkronisasiBarangService = new SinkronisasiBarangService();
    private StokOpnamService stokOpnamService = new StokOpnamService();
    private StokOpnamDetailService stokOpnamDetailService = new StokOpnamDetailService();

    public StokOpnamParsial() {
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.resetData();
        if (this.helper.getStatusProgram().equals("TOKO")) {
            this.lokasiCmb.setEnabled(false);
        }
    }

    public void setDefaultFocus() {
        this.idField.requestFocusInWindow();
    }

    private void escapeClick() {
        this.idField.requestFocus();
        this.idField.selectAll();
    }

    private void simpanData() {
        this.stokOpnam = new StokOpnam();
        this.stokOpnam.setId(this.stokOpnamService.getLastId() + 1);
        this.stokOpnam.setKaryawanId(this.helper.getKaryawan().getId());
        this.stokOpnam.setLokasi(this.lokasiCmb.getSelectedItem().toString());
        this.stokOpnam.setTipe("SOP");
        this.stokOpnam.setTipeName("");
        this.stokOpnam.setStatusOpnam("BELUM DIPROSES");
        this.stokOpnam.setTanggal(new Date());
        this.stokOpnam.setUpdated(new Date());
        int statusOpnamSave = this.stokOpnamService.createStokOpnam(this.stokOpnam);
        if (statusOpnamSave == 1) {
            ArrayList<StokOpnamDetail> datas = new ArrayList<StokOpnamDetail>();
            for (int a = 0; a < this.stokOpnamTM.getRowCount(); ++a) {
                StokOpnamDetail stokOpnamDetail = new StokOpnamDetail();
                stokOpnamDetail.setStokOpnamId(this.stokOpnam.getId());
                stokOpnamDetail.setBarangId(this.stokOpnamTM.getValueAt(a, 0).toString());
                stokOpnamDetail.setJumlahKomp(Float.parseFloat(this.stokOpnamTM.getValueAt(a, 6).toString()));
                stokOpnamDetail.setJumlahToko(Float.parseFloat(this.stokOpnamTM.getValueAt(a, 8).toString()) - Float.parseFloat(this.stokOpnamTM.getValueAt(a, 7).toString()));
                stokOpnamDetail.setTotal((long)Float.parseFloat(this.stokOpnamTM.getValueAt(a, 11).toString()));
                datas.add(stokOpnamDetail);
            }
            this.stokOpnamDetailService.createStokOpnamDetailBatch(datas);
            JOptionPane.showMessageDialog(null, "Data Stok Opname Berhasil Tersimpan");
            this.resetData();
        }
    }

    private void resetData() {
        this.dateField.setDate(new Date());
        int countRow = this.stokOpnamTM.getRowCount();
        for (int a = 0; a < countRow; ++a) {
            this.stokOpnamTM.removeRow(0);
        }
    }

    public void addListener(DefaultPanelListener listener) {
        this.listener = listener;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.idField = new JTextField();
        this.dateField = new JDateChooser();
        this.detailButton = new JButton();
        this.saveButton = new JButton();
        this.exitButton = new JButton();
        this.jScrollPane1 = new JScrollPane();
        this.soParsialTable = new JTable();
        this.jLabel5 = new JLabel();
        this.lokasiCmb = new JComboBox();
        this.jLabel6 = new JLabel();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Tanggal");
        this.jLabel2.setText("ID Opname");
        this.jLabel3.setText(":");
        this.jLabel4.setText(":");
        this.idField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                StokOpnamParsial.this.idFieldKeyPressed(evt);
            }
        });
        this.detailButton.setText("Detail");
        this.detailButton.setPreferredSize(new Dimension(73, 23));
        this.detailButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StokOpnamParsial.this.detailButtonActionPerformed(evt);
            }
        });
        this.detailButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                StokOpnamParsial.this.detailButtonKeyPressed(evt);
            }
        });
        this.saveButton.setText("Save");
        this.saveButton.setEnabled(false);
        this.saveButton.setPreferredSize(new Dimension(73, 23));
        this.saveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StokOpnamParsial.this.saveButtonActionPerformed(evt);
            }
        });
        this.saveButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                StokOpnamParsial.this.saveButtonKeyPressed(evt);
            }
        });
        this.exitButton.setText("Exit");
        this.exitButton.setPreferredSize(new Dimension(73, 23));
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StokOpnamParsial.this.exitButtonActionPerformed(evt);
            }
        });
        this.exitButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                StokOpnamParsial.this.exitButtonKeyPressed(evt);
            }
        });
        this.soParsialTable.setModel(this.stokOpnamTM);
        this.soParsialTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                StokOpnamParsial.this.soParsialTableKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.soParsialTable);
        this.jLabel5.setText("Lokasi");
        this.lokasiCmb.setModel(new DefaultComboBoxModel<String>(new String[]{"Toko", "Gudang"}));
        this.lokasiCmb.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                StokOpnamParsial.this.lokasiCmbKeyPressed(evt);
            }
        });
        this.jLabel6.setText("Enter to Generate Id");
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 780, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2).addComponent(this.jLabel1)).addGap(54, 54, 54).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel4).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent((Component)this.dateField, -2, 155, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel3).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.idField, -2, 111, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel6)).addGroup(layout.createSequentialGroup().addGap(133, 133, 133).addComponent(this.detailButton, -2, -1, -2).addGap(18, 18, 18).addComponent(this.saveButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.exitButton, -2, -1, -2)))).addGroup(layout.createSequentialGroup().addComponent(this.jLabel5, -2, 73, -2).addGap(44, 44, 44).addComponent(this.lokasiCmb, -2, 136, -2))).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.jLabel4)).addComponent((Component)this.dateField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.jLabel3).addComponent(this.idField, -2, -1, -2).addComponent(this.jLabel6)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.lokasiCmb, -2, -1, -2).addGap(0, 2, Short.MAX_VALUE)).addComponent(this.jLabel5, -1, -1, Short.MAX_VALUE)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.detailButton, -2, -1, -2).addComponent(this.saveButton, -2, -1, -2).addComponent(this.exitButton, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -1, 457, Short.MAX_VALUE).addContainerGap()));
    }

    private void idFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.resetData();
            this.idField.setText(this.stokOpnamService.getLastId() + 1 + "");
            this.idField.requestFocus();
            this.saveButton.setEnabled(true);
            this.detailButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.listener.exitClick();
        }
    }

    private void detailButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.idField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Input ID Stok Opnam Terlebih Dahulu");
            } else {
                this.soParsialTable.requestFocus();
                if (this.stokOpnamTM.getRowCount() == 0) {
                    StokOpnamParsialDialog stokOpnamParsialDialog = new StokOpnamParsialDialog(null, true, this.stokOpnamTM, this.lokasiCmb.getSelectedItem().toString());
                    stokOpnamParsialDialog.addListener(new soParsialEventHandler());
                    int w = stokOpnamParsialDialog.getWidth();
                    int h = stokOpnamParsialDialog.getHeight();
                    stokOpnamParsialDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                    stokOpnamParsialDialog.setVisible(true);
                }
            }
        } else if (evt.getKeyCode() == 39) {
            this.saveButton.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.lokasiCmb.requestFocus();
        }
    }

    private void soParsialTableKeyPressed(KeyEvent evt) {
        int a;
        if (evt.getKeyCode() == 10) {
            StokOpnamParsialDialog stokOpnamParsialDialog = new StokOpnamParsialDialog(null, true, this.stokOpnamTM, this.lokasiCmb.getSelectedItem().toString());
            if (this.soParsialTable.getSelectedRow() != -1) {
                stokOpnamParsialDialog.loadKodeBarang(this.stokOpnamTM.getValueAt(this.soParsialTable.getSelectedRow(), 0).toString());
                stokOpnamParsialDialog.loadDataFromTable(this.soParsialTable.getSelectedRow());
            }
            stokOpnamParsialDialog.escapeClick();
            stokOpnamParsialDialog.addListener(new soParsialEventHandler());
            int w = stokOpnamParsialDialog.getWidth();
            int h = stokOpnamParsialDialog.getHeight();
            stokOpnamParsialDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            stokOpnamParsialDialog.setVisible(true);
        } else if (evt.getKeyCode() == 127 && this.soParsialTable.getSelectedRow() != -1 && (a = JOptionPane.showConfirmDialog(null, "Hapus Data Ini?", "Delete Data", 0, 3)) == 0) {
            this.stokOpnamTM.removeRow(this.soParsialTable.getSelectedRow());
        }
    }

    private void saveButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.simpanData();
        } else if (evt.getKeyCode() == 39) {
            this.exitButton.requestFocus();
        } else if (evt.getKeyCode() == 37) {
            this.detailButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.idField.requestFocus();
            this.idField.selectAll();
        }
    }

    private void exitButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.listener.exitClick();
        } else if (evt.getKeyCode() == 37) {
            this.saveButton.requestFocus();
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 27) {
            this.idField.requestFocus();
            this.idField.selectAll();
        }
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.listener.exitClick();
    }

    private void saveButtonActionPerformed(ActionEvent evt) {
        this.simpanData();
    }

    private void detailButtonActionPerformed(ActionEvent evt) {
        if (this.idField.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Input ID Stok Opnam Terlebih Dahulu");
        } else {
            this.soParsialTable.requestFocus();
            if (this.stokOpnamTM.getRowCount() == 0) {
                StokOpnamParsialDialog stokOpnamParsialDialog = new StokOpnamParsialDialog(null, true, this.stokOpnamTM, this.lokasiCmb.getSelectedItem().toString());
                stokOpnamParsialDialog.addListener(new soParsialEventHandler());
                int w = stokOpnamParsialDialog.getWidth();
                int h = stokOpnamParsialDialog.getHeight();
                stokOpnamParsialDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                stokOpnamParsialDialog.setVisible(true);
            }
        }
    }

    private void lokasiCmbKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.detailButton.requestFocus();
        }
    }

    private class soParsialEventHandler
    implements SoParsialDialogListener {
        private soParsialEventHandler() {
        }

        @Override
        public void saveClick(Object[] rowValue) {
            int a;
            StokOpnamParsial.this.lokasiCmb.setEnabled(false);
            boolean newData = true;
            for (a = 0; a < StokOpnamParsial.this.stokOpnamTM.getRowCount(); ++a) {
                if (!StokOpnamParsial.this.stokOpnamTM.getValueAt(a, 0).equals(rowValue[0])) continue;
                newData = false;
                break;
            }
            if (newData) {
                StokOpnamParsial.this.stokOpnamTM.addRow(rowValue);
            } else {
                StokOpnamParsial.this.stokOpnamTM.removeRow(a);
                StokOpnamParsial.this.stokOpnamTM.addRow(rowValue);
            }
        }
    }
}

