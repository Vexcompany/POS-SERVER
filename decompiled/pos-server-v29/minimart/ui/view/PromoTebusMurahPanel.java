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
import java.util.Date;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.controller.Helper;
import minimart.engine.model.PromoTebusMurah;
import minimart.engine.model.PromoTebusMurahDetail;
import minimart.engine.service.BarangService;
import minimart.engine.service.PromoTebusMurahDetailService;
import minimart.engine.service.PromoTebusMurahService;
import minimart.ui.listener.ConfirmationDialogListener;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.listener.PromoHematDialogListener;
import minimart.ui.view.PromoTebusMurahDialog;

public class PromoTebusMurahPanel
extends JPanel {
    private JCheckBox aktifCheck;
    private JButton baruBtn;
    private JButton detailBtn;
    private JDateChooser endDateField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JScrollPane jScrollPane1;
    private JSeparator jSeparator1;
    private JButton keluarBtn;
    private JLabel keteranganLbl;
    private JTextField kodeField;
    private JTable promoTable;
    private JButton simpanBtn;
    private JDateChooser startDateField;
    private String[] promoColumn = new String[]{"ID BARANG", "NAMA BARANG", "SATUAN", "MINIMUM BELANJA", "HARGA PROMO", "MAKSIMAL KUANTITI"};
    private DefaultTableModel promoTableModel = new DefaultTableModel(new String[0][1], this.promoColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private PromoTebusMurahService tebusMurahService = new PromoTebusMurahService();
    private PromoTebusMurahDetailService tebusMurahDetailService = new PromoTebusMurahDetailService();
    private DefaultPanelListener defaultPanelListener;
    private int screenWidth;
    private int screenHeight;
    private Helper helper = Helper.getInstance();
    private boolean canGoThrough;
    private String status = "CREATE";
    private BarangService barangService = new BarangService();

    public PromoTebusMurahPanel() {
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    }

    private void loadPromoHemat() {
        this.setDefault();
        PromoTebusMurah promoTebusMurah = this.tebusMurahService.getPromoTebusMurahById(this.kodeField.getText());
        if (promoTebusMurah != null) {
            this.status = "UPDATE";
            this.startDateField.setDate(promoTebusMurah.getTanggalMulai());
            this.endDateField.setDate(promoTebusMurah.getTanggalBerakhir());
            if (promoTebusMurah.getStatus().equals("AKTIF")) {
                this.aktifCheck.setSelected(true);
            } else if (promoTebusMurah.getStatus().equals("NONAKTIF")) {
                this.aktifCheck.setSelected(false);
            }
            List<PromoTebusMurahDetail> promoTebusMurahDetails = this.tebusMurahDetailService.getPromoTebusMurahDetail(promoTebusMurah.getId() + "");
            for (PromoTebusMurahDetail promoTebusMurahDetail : promoTebusMurahDetails) {
                Object[] rowValue = new Object[]{promoTebusMurahDetail.getBarangId(), this.barangService.getBarang(promoTebusMurahDetail.getBarangId()).getNama(), promoTebusMurahDetail.getSatuan(), promoTebusMurahDetail.getMinimumBelanja(), promoTebusMurahDetail.getHargaPromo(), Float.valueOf(promoTebusMurahDetail.getMaksimalKuantiti())};
                this.promoTableModel.addRow(rowValue);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Data Promo Tidak Ditemukan");
        }
    }

    private void setDefault() {
        this.startDateField.setDate(new Date());
        this.endDateField.setDate(new Date());
        this.clearTable();
        this.aktifCheck.setSelected(true);
    }

    private void detailClick() {
        this.promoTable.requestFocus();
        PromoTebusMurahDialog promoTebusMurahDialog = new PromoTebusMurahDialog(null, true, this.promoTableModel, this.startDateField.getDate(), this.endDateField.getDate(), this.kodeField.getText());
        promoTebusMurahDialog.addListener(new promoTebusMurahDialogEventHandler());
        int w = promoTebusMurahDialog.getWidth();
        int h = promoTebusMurahDialog.getHeight();
        promoTebusMurahDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
        promoTebusMurahDialog.setVisible(true);
    }

    private void initComponents() {
        this.simpanBtn = new JButton();
        this.keluarBtn = new JButton();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.kodeField = new JTextField();
        this.startDateField = new JDateChooser();
        this.jLabel3 = new JLabel();
        this.endDateField = new JDateChooser();
        this.aktifCheck = new JCheckBox();
        this.jSeparator1 = new JSeparator();
        this.jScrollPane1 = new JScrollPane();
        this.promoTable = new JTable();
        this.detailBtn = new JButton();
        this.baruBtn = new JButton();
        this.keteranganLbl = new JLabel();
        this.setPreferredSize(new Dimension(800, 600));
        this.simpanBtn.setText("Simpan");
        this.simpanBtn.setPreferredSize(new Dimension(73, 23));
        this.simpanBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PromoTebusMurahPanel.this.simpanBtnActionPerformed(evt);
            }
        });
        this.keluarBtn.setText("Keluar");
        this.keluarBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PromoTebusMurahPanel.this.keluarBtnActionPerformed(evt);
            }
        });
        this.jLabel1.setText("Kode Promo");
        this.jLabel2.setText("Tgl Promo");
        this.kodeField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoTebusMurahPanel.this.kodeFieldKeyPressed(evt);
            }
        });
        this.jLabel3.setText("s/d");
        this.aktifCheck.setText("Aktifkan Promo");
        this.promoTable.setModel(this.promoTableModel);
        this.promoTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoTebusMurahPanel.this.promoTableKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.promoTable);
        this.detailBtn.setText("Detail");
        this.detailBtn.setPreferredSize(new Dimension(73, 23));
        this.detailBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PromoTebusMurahPanel.this.detailBtnActionPerformed(evt);
            }
        });
        this.detailBtn.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoTebusMurahPanel.this.detailBtnKeyPressed(evt);
            }
        });
        this.baruBtn.setText("Baru");
        this.baruBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PromoTebusMurahPanel.this.baruBtnActionPerformed(evt);
            }
        });
        this.keteranganLbl.setText("-");
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jSeparator1, GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addComponent(this.baruBtn, -2, 70, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.simpanBtn, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.aktifCheck, -2, 113, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keteranganLbl, -2, 195, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 254, Short.MAX_VALUE).addComponent(this.keluarBtn)).addComponent(this.jScrollPane1).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel2)).addGap(28, 28, 28).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.kodeField, GroupLayout.Alignment.LEADING).addComponent((Component)this.startDateField, GroupLayout.Alignment.LEADING, -1, 165, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel3).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent((Component)this.endDateField, -2, 165, -2)).addComponent(this.detailBtn, -2, -1, -2)).addGap(0, 342, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.simpanBtn, -2, -1, -2).addComponent(this.keluarBtn).addComponent(this.baruBtn).addComponent(this.keteranganLbl).addComponent(this.aktifCheck)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jSeparator1, -2, 10, -2).addGap(2, 2, 2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.kodeField, -2, -1, -2).addComponent(this.jLabel1)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent((Component)this.startDateField, -2, -1, -2).addComponent(this.jLabel3).addComponent((Component)this.endDateField, -2, -1, -2))).addComponent(this.jLabel2)).addGap(37, 37, 37).addComponent(this.detailBtn, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 372, -2).addContainerGap(64, Short.MAX_VALUE)));
    }

    private void keluarBtnActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }

    private void detailBtnActionPerformed(ActionEvent evt) {
        if (this.kodeField.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Tentukan Kode Promo Terlebih Dahulu");
        } else {
            this.detailClick();
        }
    }

    private void promoTableKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 127) {
            int a;
            if (this.promoTable.getSelectedRow() != -1 && (a = JOptionPane.showConfirmDialog(null, "Hapus Data Ini?", "Delete Data", 0, 3)) == 0) {
                this.promoTableModel.removeRow(this.promoTable.getSelectedRow());
            }
        } else if (evt.getKeyCode() == 10) {
            if (this.kodeField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Tentukan Kode Promo Terlebih Dahulu");
            } else {
                PromoTebusMurahDialog promoTebusMurahDialog = new PromoTebusMurahDialog(null, true, this.promoTableModel, this.startDateField.getDate(), this.endDateField.getDate(), this.kodeField.getText());
                if (this.promoTable.getSelectedRow() != -1) {
                    promoTebusMurahDialog.loadKodeBarang(this.promoTableModel.getValueAt(this.promoTable.getSelectedRow(), 0).toString());
                    promoTebusMurahDialog.loadPromoTebusMurahData(this.promoTable.getSelectedRow());
                }
                promoTebusMurahDialog.addListener(new promoTebusMurahDialogEventHandler());
                int w = promoTebusMurahDialog.getWidth();
                int h = promoTebusMurahDialog.getHeight();
                promoTebusMurahDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                promoTebusMurahDialog.setVisible(true);
            }
        }
    }

    private void kodeFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.loadPromoHemat();
            this.keteranganLbl.setText(this.status);
        }
    }

    private void detailBtnKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.detailClick();
        }
    }

    private void simpanBtnActionPerformed(ActionEvent evt) {
        PromoTebusMurah promoTebusMurah = new PromoTebusMurah();
        promoTebusMurah.setId(this.kodeField.getText());
        promoTebusMurah.setKaryawanId(this.helper.getKaryawan().getId());
        if (this.aktifCheck.isSelected()) {
            promoTebusMurah.setStatus("AKTIF");
        } else {
            promoTebusMurah.setStatus("NONAKTIF");
        }
        promoTebusMurah.setTanggalMulai(this.startDateField.getDate());
        promoTebusMurah.setTanggalBerakhir(this.endDateField.getDate());
        promoTebusMurah.setCreated(new Date());
        int result = 0;
        if (this.status.equals("CREATE")) {
            result = this.tebusMurahService.create(promoTebusMurah);
        } else if (this.status.equals("UPDATE")) {
            result = this.tebusMurahService.update(promoTebusMurah);
        }
        if (result == 1) {
            this.tebusMurahDetailService.deletePromoTebusMurahDetail(this.kodeField.getText());
            for (int a = 0; a < this.promoTableModel.getRowCount(); ++a) {
                PromoTebusMurahDetail promoTebusMurahDetail = new PromoTebusMurahDetail();
                promoTebusMurahDetail.setPromoTebusMurahId(promoTebusMurah.getId());
                promoTebusMurahDetail.setBarangId(this.promoTableModel.getValueAt(a, 0).toString());
                promoTebusMurahDetail.setSatuan(this.promoTableModel.getValueAt(a, 2).toString());
                promoTebusMurahDetail.setMinimumBelanja(Long.parseLong(this.promoTableModel.getValueAt(a, 3).toString()));
                promoTebusMurahDetail.setHargaPromo(Long.parseLong(this.promoTableModel.getValueAt(a, 4).toString()));
                promoTebusMurahDetail.setMaksimalKuantiti(Float.parseFloat(this.promoTableModel.getValueAt(a, 5).toString()));
                this.tebusMurahDetailService.create(promoTebusMurahDetail);
            }
            JOptionPane.showMessageDialog(null, "Penyimpanan Data Promo Berhasil");
        } else {
            JOptionPane.showMessageDialog(null, "Penyimpanan Data Gagal");
        }
    }

    private void baruBtnActionPerformed(ActionEvent evt) {
        this.setDefault();
        this.status = "CREATE";
        this.keteranganLbl.setText(this.status);
        this.kodeField.setText(Helper.getInstance().generateCodeGeneral(this.tebusMurahService.getLastId()));
        this.kodeField.requestFocus();
    }

    private void clearTable() {
        int size = this.promoTableModel.getRowCount();
        for (int a = 0; a < size; ++a) {
            this.promoTableModel.removeRow(0);
        }
    }

    public void addListener(DefaultPanelListener listener) {
        this.defaultPanelListener = listener;
    }

    private class confirmDialogEventHandler
    implements ConfirmationDialogListener {
        private confirmDialogEventHandler() {
        }

        @Override
        public void yesClick() {
            PromoTebusMurahPanel.this.canGoThrough = true;
        }

        @Override
        public void noClick() {
            PromoTebusMurahPanel.this.canGoThrough = false;
        }
    }

    private class promoTebusMurahDialogEventHandler
    implements PromoHematDialogListener {
        private promoTebusMurahDialogEventHandler() {
        }

        @Override
        public void saveClick(Object[] rowValue) {
            int a;
            boolean newData = true;
            for (a = 0; a < PromoTebusMurahPanel.this.promoTableModel.getRowCount(); ++a) {
                if (!PromoTebusMurahPanel.this.promoTableModel.getValueAt(a, 0).equals(rowValue[0]) || !PromoTebusMurahPanel.this.promoTableModel.getValueAt(a, 2).equals(rowValue[2])) continue;
                newData = false;
                break;
            }
            if (newData) {
                PromoTebusMurahPanel.this.promoTableModel.addRow(rowValue);
            } else {
                PromoTebusMurahPanel.this.promoTableModel.removeRow(a);
                PromoTebusMurahPanel.this.promoTableModel.addRow(rowValue);
            }
        }
    }
}

