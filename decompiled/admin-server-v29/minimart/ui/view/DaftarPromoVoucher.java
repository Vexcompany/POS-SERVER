/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import minimart.engine.component.UppercaseDocumentFilter;
import minimart.engine.model.Jenis;
import minimart.engine.model.PromoVoucher;
import minimart.engine.model.PromoVoucherDetail;
import minimart.engine.service.BarangService;
import minimart.engine.service.JenisService;
import minimart.engine.service.PromoVoucherDetailService;
import minimart.engine.service.PromoVoucherService;
import minimart.ui.listener.DaftarVoucherDialogListener;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.DaftarVoucherDialog;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class DaftarPromoVoucher
extends JPanel {
    private JCheckBox aktifCheck;
    private JButton detailBtn;
    private JButton exitBtn;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JScrollPane jScrollPane1;
    private JTextField jenisField;
    private JCheckBox khususCheck;
    private JFormattedTextField maxField;
    private JFormattedTextField minField;
    private JButton saveBtn;
    private JTable voucherTable;
    private DefaultPanelListener listener;
    private int screenWidth;
    private int screenHeight;
    private BarangService barangService = new BarangService();
    private PromoVoucherService promoVoucherService = new PromoVoucherService();
    private PromoVoucherDetailService promoVoucherDetailService = new PromoVoucherDetailService();
    private JenisService jenisService = new JenisService();
    private SearchDialog sd;
    private String[] daftarColumn = new String[]{"Kode Barang", "Nama Barang", "Maksimal pembelian", "Satuan"};
    private DocumentFilter filter = new UppercaseDocumentFilter();
    private DefaultTableModel daftarTableModel = new DefaultTableModel(new String[0][1], this.daftarColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public DaftarPromoVoucher() {
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        ((AbstractDocument)this.jenisField.getDocument()).setDocumentFilter(this.filter);
        this.loadData();
    }

    public void addListener(DefaultPanelListener dpl) {
        this.listener = dpl;
    }

    private void loadData() {
        PromoVoucher promoVoucher = this.promoVoucherService.getPromoVoucherById(1);
        if (promoVoucher != null) {
            this.maxField.setValue(promoVoucher.getMaximumVoucher());
            this.minField.setValue(promoVoucher.getMinimumTransaksi());
            if (promoVoucher.getKhususPelanggan().equals("YES")) {
                this.khususCheck.setSelected(true);
            } else {
                this.khususCheck.setSelected(false);
            }
            if (promoVoucher.getStatus().equals("AKTIF")) {
                this.aktifCheck.setSelected(true);
            } else {
                this.aktifCheck.setSelected(false);
            }
            List<PromoVoucherDetail> promoVoucherDetails = this.promoVoucherDetailService.getPromoVoucherDetailByPromoVoucherId(1);
            for (PromoVoucherDetail promoVoucherDetail : promoVoucherDetails) {
                Object[] data = new Object[]{promoVoucherDetail.getBarangId(), this.barangService.getBarang(promoVoucherDetail.getBarangId()).getNama(), promoVoucherDetail.getMaxPembelian(), promoVoucherDetail.getSatuan()};
                this.daftarTableModel.addRow(data);
            }
        } else {
            this.maxField.setValue(0);
            this.minField.setValue(0);
        }
    }

    private void initComponents() {
        this.jLabel4 = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.voucherTable = new JTable();
        this.jLabel1 = new JLabel();
        this.maxField = new JFormattedTextField();
        this.jLabel2 = new JLabel();
        this.minField = new JFormattedTextField();
        this.jLabel3 = new JLabel();
        this.khususCheck = new JCheckBox();
        this.detailBtn = new JButton();
        this.saveBtn = new JButton();
        this.exitBtn = new JButton();
        this.aktifCheck = new JCheckBox();
        this.jenisField = new JTextField();
        this.jLabel5 = new JLabel();
        this.jLabel4.setText("jLabel4");
        this.setPreferredSize(new Dimension(800, 600));
        this.voucherTable.setModel(this.daftarTableModel);
        this.voucherTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                DaftarPromoVoucher.this.voucherTableKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.voucherTable);
        this.jLabel1.setText("Maximum Voucher per Nota");
        this.maxField.setText("0");
        this.maxField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                DaftarPromoVoucher.this.maxFieldKeyPressed(evt);
            }
        });
        this.jLabel2.setText("Minimum nominal belanja");
        this.minField.setText("0");
        this.minField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                DaftarPromoVoucher.this.minFieldKeyPressed(evt);
            }
        });
        this.jLabel3.setText("Tambahkan Daftar Jenis");
        this.khususCheck.setText("Khusus Member");
        this.detailBtn.setText("Details");
        this.detailBtn.setPreferredSize(new Dimension(73, 23));
        this.detailBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                DaftarPromoVoucher.this.detailBtnActionPerformed(evt);
            }
        });
        this.detailBtn.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                DaftarPromoVoucher.this.detailBtnKeyPressed(evt);
            }
        });
        this.saveBtn.setText("Save");
        this.saveBtn.setPreferredSize(new Dimension(73, 23));
        this.saveBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                DaftarPromoVoucher.this.saveBtnActionPerformed(evt);
            }
        });
        this.saveBtn.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                DaftarPromoVoucher.this.saveBtnKeyPressed(evt);
            }
        });
        this.exitBtn.setText("Exit");
        this.exitBtn.setPreferredSize(new Dimension(73, 23));
        this.exitBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                DaftarPromoVoucher.this.exitBtnActionPerformed(evt);
            }
        });
        this.exitBtn.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                DaftarPromoVoucher.this.exitBtnKeyPressed(evt);
            }
        });
        this.aktifCheck.setText("Aktifkan Promo");
        this.jenisField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                DaftarPromoVoucher.this.jenisFieldKeyPressed(evt);
            }
        });
        this.jLabel5.setText("F2:Cari");
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.jLabel3, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE).addComponent(this.jLabel2, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE).addComponent(this.jLabel1, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.maxField).addComponent(this.minField, -1, 120, Short.MAX_VALUE).addComponent(this.jenisField)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.khususCheck).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.aktifCheck, -2, 114, -2).addGap(10, 10, 10)).addGroup(layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel5).addContainerGap(-1, Short.MAX_VALUE)))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.detailBtn, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.saveBtn, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.exitBtn, -2, -1, -2).addGap(0, 549, Short.MAX_VALUE)).addComponent(this.jScrollPane1, -1, 780, Short.MAX_VALUE)).addContainerGap()))));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.maxField, -2, -1, -2).addComponent(this.aktifCheck).addComponent(this.khususCheck)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.minField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.jenisField, -2, -1, -2).addComponent(this.jLabel5)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.detailBtn, -2, -1, -2).addComponent(this.saveBtn, -2, -1, -2).addComponent(this.exitBtn, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, -1, -2).addGap(76, 76, 76)));
    }

    private void exitBtnActionPerformed(ActionEvent evt) {
        this.listener.exitClick();
    }

    private void detailBtnKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.voucherTable.requestFocus();
        } else if (evt.getKeyCode() == 39) {
            this.saveBtn.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.minField.requestFocus();
            this.minField.setText(this.minField.getText().replaceAll(".", ""));
            this.minField.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.maxField.requestFocus();
            this.maxField.setText(this.maxField.getText().replaceAll(".", ""));
            this.maxField.selectAll();
        }
    }

    private void saveBtnKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.saveClick();
        } else if (evt.getKeyCode() == 38) {
            this.minField.requestFocus();
            this.minField.setText(this.minField.getText().replaceAll(".", ""));
            this.minField.selectAll();
        } else if (evt.getKeyCode() == 39) {
            this.exitBtn.requestFocus();
        } else if (evt.getKeyCode() == 37) {
            this.detailBtn.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.maxField.requestFocus();
            this.maxField.setText(this.maxField.getText().replaceAll(".", ""));
            this.maxField.selectAll();
        }
    }

    private void exitBtnKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.listener.exitClick();
        } else if (evt.getKeyCode() == 38) {
            this.minField.requestFocus();
            this.minField.setText(this.minField.getText().replaceAll(".", ""));
            this.minField.selectAll();
        } else if (evt.getKeyCode() == 37) {
            this.saveBtn.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.maxField.requestFocus();
            this.maxField.setText(this.maxField.getText().replaceAll(".", ""));
            this.maxField.selectAll();
        }
    }

    private void detailBtnActionPerformed(ActionEvent evt) {
        this.voucherTable.requestFocus();
        DaftarVoucherDialog daftarDialog = new DaftarVoucherDialog(null, true, this.daftarTableModel);
        if (this.voucherTable.getSelectedRow() != -1) {
            daftarDialog.loadKodeBarang(this.voucherTable.getValueAt(this.voucherTable.getSelectedRow(), 0).toString());
            daftarDialog.loadPenjualan(this.voucherTable.getSelectedRow());
        }
        daftarDialog.escapeClick();
        daftarDialog.addListener(new voucherDialogEventHandler());
        int w = daftarDialog.getWidth();
        int h = daftarDialog.getHeight();
        daftarDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
        daftarDialog.setVisible(true);
    }

    private void voucherTableKeyPressed(KeyEvent evt) {
        int a;
        if (evt.getKeyCode() == 10) {
            DaftarVoucherDialog daftarDialog = new DaftarVoucherDialog(null, true, this.daftarTableModel);
            if (this.voucherTable.getSelectedRow() != -1) {
                daftarDialog.loadKodeBarang(this.voucherTable.getValueAt(this.voucherTable.getSelectedRow(), 0).toString());
                daftarDialog.loadPenjualan(this.voucherTable.getSelectedRow());
            }
            daftarDialog.escapeClick();
            daftarDialog.addListener(new voucherDialogEventHandler());
            int w = daftarDialog.getWidth();
            int h = daftarDialog.getHeight();
            daftarDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            daftarDialog.setVisible(true);
        } else if (evt.getKeyCode() == 127 && this.voucherTable.getSelectedRow() != -1 && (a = JOptionPane.showConfirmDialog(null, "Hapus Data Ini?", "Delete Data", 0, 3)) == 0) {
            this.daftarTableModel.removeRow(this.voucherTable.getSelectedRow());
        }
    }

    private void maxFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.minField.requestFocus();
            this.minField.selectAll();
        }
    }

    private void minFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.detailBtn.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.maxField.requestFocus();
            this.maxField.selectAll();
        }
    }

    private void saveBtnActionPerformed(ActionEvent evt) {
        this.saveClick();
    }

    private void jenisFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.jenisField.getText().equals("")) {
                this.sd = new SearchDialog(null, true, "Pencarian Data Jenis Barang");
                this.sd.addSearchDialogListener(new searchJenisDialogEventHandler());
                int w = this.sd.getWidth();
                int h = this.sd.getHeight();
                this.sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                this.sd.setVisible(true);
            } else {
                Jenis jenis = this.jenisService.getJenis(this.jenisField.getText());
                if (jenis == null) {
                    JOptionPane.showMessageDialog(null, "Jenis Barang Tidak Ditemukan");
                } else {
                    this.detailBtn.requestFocus();
                }
            }
        } else if (evt.getKeyCode() == 113) {
            this.sd = new SearchDialog(null, true, "Pencarian Data Jenis Barang");
            this.sd.addSearchDialogListener(new searchJenisDialogEventHandler());
            int w = this.sd.getWidth();
            int h = this.sd.getHeight();
            this.sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            this.sd.setVisible(true);
        }
    }

    private void saveClick() {
        PromoVoucher promoVoucher = this.promoVoucherService.getPromoVoucherById(1);
        boolean create = false;
        if (promoVoucher == null) {
            create = true;
            promoVoucher = new PromoVoucher();
        }
        promoVoucher.setId(1);
        if (this.khususCheck.isSelected()) {
            promoVoucher.setKhususPelanggan("YES");
        } else {
            promoVoucher.setKhususPelanggan("NO");
        }
        promoVoucher.setMaximumVoucher(Integer.parseInt(this.maxField.getValue() + ""));
        promoVoucher.setMinimumTransaksi(Integer.parseInt(this.minField.getValue() + ""));
        if (this.aktifCheck.isSelected()) {
            promoVoucher.setStatus("AKTIF");
        } else {
            promoVoucher.setStatus("NONAKTIF");
        }
        int result1 = 0;
        if (create) {
            result1 = this.promoVoucherService.createPromoVoucher(promoVoucher);
        } else {
            result1 = this.promoVoucherService.updatePromoVoucher(promoVoucher);
            this.promoVoucherDetailService.deletePromoVoucherDetailByPromoVoucherId(1);
        }
        int result2 = 0;
        for (int a = 0; a < this.daftarTableModel.getRowCount(); ++a) {
            PromoVoucherDetail promoVoucherDetail = new PromoVoucherDetail();
            promoVoucherDetail.setPromoVoucherId(promoVoucher.getId());
            promoVoucherDetail.setBarangId(this.daftarTableModel.getValueAt(a, 0).toString());
            promoVoucherDetail.setMaxPembelian(Integer.parseInt(this.daftarTableModel.getValueAt(a, 2).toString()));
            promoVoucherDetail.setSatuan(this.daftarTableModel.getValueAt(a, 3).toString());
            result2 = this.promoVoucherDetailService.createPromoVoucherDetail(promoVoucherDetail);
        }
        if (result1 == 0) {
            JOptionPane.showMessageDialog(null, "Penyimpanan Promo Voucher Gagal");
        } else if (result2 == 0) {
            JOptionPane.showMessageDialog(null, "Penyimpanan Promo Voucher Detail Gagal");
        } else {
            JOptionPane.showMessageDialog(null, "Penyimpanan Promo Voucher Berhasil");
        }
    }

    private class voucherDialogEventHandler
    implements DaftarVoucherDialogListener {
        private voucherDialogEventHandler() {
        }

        @Override
        public void saveClick(Object[] rowValue) {
            int a;
            boolean newData = true;
            for (a = 0; a < DaftarPromoVoucher.this.daftarTableModel.getRowCount(); ++a) {
                if (!DaftarPromoVoucher.this.daftarTableModel.getValueAt(a, 0).equals(rowValue[0])) continue;
                newData = false;
                break;
            }
            if (newData) {
                DaftarPromoVoucher.this.daftarTableModel.addRow(rowValue);
            } else {
                DaftarPromoVoucher.this.daftarTableModel.removeRow(a);
                DaftarPromoVoucher.this.daftarTableModel.addRow(rowValue);
            }
        }
    }

    private class searchPanelJenisEventHandler
    implements SearchPanelListener {
        private searchPanelJenisEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            DaftarPromoVoucher.this.jenisField.setText(chooseValue);
            DaftarPromoVoucher.this.detailBtn.requestFocus();
        }
    }

    private class searchJenisDialogEventHandler
    implements SearchDialogListener {
        private searchJenisDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"JENIS"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Jenis> jenisses = DaftarPromoVoucher.this.jenisService.getJenisses(searchWord);
            for (Jenis jenis : jenisses) {
                Object[] temp = new String[]{jenis.getJenis()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Jenis Barang");
            sp.addSearchPanelListener(new searchPanelJenisEventHandler());
            sp.setVisible(true);
        }

        public void dispose() {
        }
    }
}

