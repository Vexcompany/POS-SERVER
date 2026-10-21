/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.toedter.calendar.JDateChooser
 */
package minimart.ui.view;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.DocumentFilter;
import javax.swing.text.NumberFormatter;
import minimart.engine.component.UppercaseDocumentFilter;
import minimart.engine.controller.Helper;
import minimart.engine.model.BatchPengiriman;
import minimart.engine.model.BatchPengirimanDetail;
import minimart.engine.model.Customer;
import minimart.engine.model.Jenis;
import minimart.engine.model.Karyawan;
import minimart.engine.model.PenjualanSementara;
import minimart.engine.model.PromoPeriode;
import minimart.engine.model.PromoVoucher;
import minimart.engine.model.Sales;
import minimart.engine.service.BarangService;
import minimart.engine.service.BatchPengirimanService;
import minimart.engine.service.CustomerService;
import minimart.engine.service.JenisService;
import minimart.engine.service.KonstantaService;
import minimart.engine.service.MigrasiOnlineService;
import minimart.engine.service.PenjualanCabangSementaraService;
import minimart.engine.service.PromoPeriodeService;
import minimart.engine.service.PromoVoucherService;
import minimart.engine.service.SalesService;
import minimart.ui.listener.CetakNotaListener;
import minimart.ui.listener.CloseKasirListener;
import minimart.ui.listener.PasswordInputListener;
import minimart.ui.listener.PenjualanBarcodeListener;
import minimart.ui.listener.PenjualanDetailDialogListener;
import minimart.ui.listener.PenjualanPanelListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.CetakNota2;
import minimart.ui.view.CetakNotaCabang;
import minimart.ui.view.CloseKasir;
import minimart.ui.view.PenjualanBarcodeDialog;
import minimart.ui.view.PenjualanCabangDetailDialog;
import minimart.ui.view.ReprintDialogCabang;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class PenjualanCabangPanelUsingBatchPengiriman
extends JPanel {
    private JTextField customerField;
    private JLabel customerLbl;
    private JButton importPickingListButton;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel13;
    private JLabel jLabel14;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JLayeredPane jLayeredPane1;
    private JScrollPane jScrollPane1;
    private JDateChooser jatuhTempoField;
    private JLabel jatuhTempoLbl;
    private JLabel karyawanField;
    private JComboBox keteranganCmb;
    private JLabel keteranganLbl;
    private JTable penjualanTable;
    private JTextField pickingListIdField;
    private JButton reprintBtn;
    private JTextField salesField;
    private JLabel salesLbl;
    private JLabel salesNameField;
    private JLabel shortcutLbl;
    private JFormattedTextField totalField;
    private PenjualanPanelListener listener;
    private int screenWidth;
    private int screenHeight;
    private KonstantaService konstantaService = new KonstantaService();
    private CustomerService customerService = new CustomerService();
    private MigrasiOnlineService migrasiOnlineService = new MigrasiOnlineService();
    private Customer customer;
    private PromoVoucher promoVoucher;
    private Sales sales;
    private Integer idPickingList;
    private BatchPengiriman batchPengiriman;
    private List<BatchPengirimanDetail> batchPengirimanDetails = new ArrayList<BatchPengirimanDetail>();
    private BatchPengirimanService batchPengirimanService = new BatchPengirimanService();
    private SalesService salesService = new SalesService();
    private Integer cabangKirim;
    private boolean editTable = true;
    private PromoVoucherService promoVoucherService = new PromoVoucherService();
    private PromoPeriodeService promoPeriodeService = new PromoPeriodeService();
    private String[] transactionColumn = new String[]{"Kode Barang", "Nama Barang", "Qty", "Sat", "Harga", "Total", "Disc", "Harga Akhir"};
    private DefaultTableModel penjualanTableModel = new DefaultTableModel(new String[0][1], this.transactionColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private DocumentFilter filter = new UppercaseDocumentFilter();
    private Helper helper = Helper.getInstance();
    private PenjualanCabangSementaraService penjualanCabangSementaraService = new PenjualanCabangSementaraService();
    private BarangService barangService = new BarangService();
    private JenisService jenisService = new JenisService();
    private PromoPeriode promoPeriode;

    public PenjualanCabangPanelUsingBatchPengiriman(String shortcut) {
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        ((AbstractDocument)this.customerField.getDocument()).setDocumentFilter(this.filter);
        this.karyawanField.setText(this.helper.getKaryawan().getNama());
        this.shortcutLbl.setText(shortcut);
        this.jatuhTempoField.setEnabled(false);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(5, 14);
        this.jatuhTempoField.setDate(c.getTime());
        this.salesLbl.setVisible(true);
        this.salesField.setVisible(true);
        this.salesNameField.setVisible(true);
        this.keteranganLbl.setVisible(true);
        this.keteranganCmb.setVisible(true);
        this.jatuhTempoField.setVisible(true);
        this.jatuhTempoLbl.setVisible(true);
        this.promoVoucher = this.promoVoucherService.getPromoVoucherById(1);
        this.promoPeriode = this.promoPeriodeService.getPromoPeriode(1);
        TableColumn col1 = this.penjualanTable.getColumnModel().getColumn(1);
        TableColumn col2 = this.penjualanTable.getColumnModel().getColumn(2);
        TableColumn col3 = this.penjualanTable.getColumnModel().getColumn(3);
        int width = 35;
        col1.setPreferredWidth(170);
        col2.setPreferredWidth(width);
        col3.setPreferredWidth(width);
        this.customerField.setEditable(false);
        this.salesField.setEditable(false);
        this.loadData();
        this.keteranganCmb.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1) {
                    if (PenjualanCabangPanelUsingBatchPengiriman.this.keteranganCmb.getSelectedItem().equals("CASH")) {
                        PenjualanCabangPanelUsingBatchPengiriman.this.jatuhTempoField.setDate(new Date());
                        PenjualanCabangPanelUsingBatchPengiriman.this.penjualanCabangSementaraService.updateKeteranganPenjualanSementara("CASH", PenjualanCabangPanelUsingBatchPengiriman.this.shortcutLbl.getText());
                    } else if (PenjualanCabangPanelUsingBatchPengiriman.this.keteranganCmb.getSelectedItem().equals("KREDIT")) {
                        if (PenjualanCabangPanelUsingBatchPengiriman.this.customer == null) {
                            JOptionPane.showMessageDialog(null, "Pilih Cabang Terlebih Dahulu");
                            PenjualanCabangPanelUsingBatchPengiriman.this.keteranganCmb.setSelectedIndex(0);
                        } else {
                            PenjualanCabangPanelUsingBatchPengiriman.this.penjualanCabangSementaraService.updateKeteranganPenjualanSementara("KREDIT", PenjualanCabangPanelUsingBatchPengiriman.this.shortcutLbl.getText());
                            Calendar c = Calendar.getInstance();
                            c.setTime(new Date());
                            c.add(5, 14);
                            PenjualanCabangPanelUsingBatchPengiriman.this.jatuhTempoField.setDate(c.getTime());
                        }
                    }
                } else if (e.getStateChange() == 2) {
                    // empty if block
                }
            }
        });
    }

    private void insertPress() {
        CloseKasir closeKasir = new CloseKasir(null, true);
        closeKasir.setListener(new closeKasirEventHandler());
        int w = closeKasir.getWidth();
        int h = closeKasir.getHeight();
        closeKasir.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
        closeKasir.setVisible(true);
    }

    private void removeAllRow() {
        int rowCount = this.penjualanTableModel.getRowCount();
        for (int a = 0; a < rowCount; ++a) {
            this.penjualanTableModel.removeRow(0);
        }
    }

    private void loadData() {
        List<PenjualanSementara> penjualanSementaras = this.penjualanCabangSementaraService.getPenjualanSementaraByShortcut(this.shortcutLbl.getText());
        long total = 0L;
        if (penjualanSementaras.size() > 0) {
            if (!penjualanSementaras.get(0).getCustomerId().equals("")) {
                this.customer = this.customerService.getCustomerById(penjualanSementaras.get(0).getCustomerId());
                this.customerField.setText(this.customer.getId() + "");
                this.customerLbl.setText(this.customer.getNama() + "-" + this.customer.getAlamat());
            }
            if (penjualanSementaras.get(0).getPickinglistId() != null) {
                this.pickingListIdField.setText(penjualanSementaras.get(0).getPickinglistId());
                this.editTable = false;
            }
            this.sales = this.salesService.getSales(penjualanSementaras.get(0).getSalesId());
            if (this.sales != null) {
                this.salesField.setText(this.sales.getId());
                this.salesNameField.setText(this.sales.getNama());
            }
            this.keteranganCmb.setSelectedItem(penjualanSementaras.get(0).getKeterangan());
            if (penjualanSementaras.get(0).getKeterangan().equals("KREDIT")) {
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                c.add(5, 14);
                this.jatuhTempoField.setDate(c.getTime());
            }
            for (PenjualanSementara penjualanSementara : penjualanSementaras) {
                float tempValue;
                total += penjualanSementara.getTotalHarga();
                Object[] rowValue = new Object[]{penjualanSementara.getBarangId(), penjualanSementara.getNamaBarang(), (tempValue = penjualanSementara.getJumlahBarang()) == (float)((long)tempValue) ? (Number)((long)penjualanSementara.getJumlahBarang()) : (Number)Float.valueOf(penjualanSementara.getJumlahBarang()), penjualanSementara.getSatuan(), penjualanSementara.getHargaJual(), (long)((float)penjualanSementara.getHargaJual() * penjualanSementara.getJumlahBarang()), penjualanSementara.getDiscount(), penjualanSementara.getTotalHarga()};
                this.penjualanTableModel.addRow(rowValue);
            }
        }
        this.totalField.setValue(total);
    }

    public void setDefaultFocus() {
        this.penjualanTable.requestFocusInWindow();
    }

    private void initComponents() {
        this.jLabel13 = new JLabel();
        this.jLayeredPane1 = new JLayeredPane();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.penjualanTable = new JTable();
        this.jLabel5 = new JLabel();
        this.jLabel6 = new JLabel();
        this.jLabel7 = new JLabel();
        this.jLabel8 = new JLabel();
        this.jLabel9 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.jLabel10 = new JLabel();
        this.customerField = new JTextField();
        this.customerLbl = new JLabel();
        this.karyawanField = new JLabel();
        this.shortcutLbl = new JLabel();
        this.jLabel11 = new JLabel();
        this.totalField = new JFormattedTextField();
        this.jLabel12 = new JLabel();
        this.salesLbl = new JLabel();
        this.salesField = new JTextField();
        this.salesNameField = new JLabel();
        this.keteranganLbl = new JLabel();
        this.keteranganCmb = new JComboBox();
        this.jatuhTempoLbl = new JLabel();
        this.jatuhTempoField = new JDateChooser();
        this.pickingListIdField = new JTextField();
        this.jLabel14 = new JLabel();
        this.importPickingListButton = new JButton();
        this.reprintBtn = new JButton();
        this.jLabel13.setText("jLabel13");
        GroupLayout jLayeredPane1Layout = new GroupLayout(this.jLayeredPane1);
        this.jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(jLayeredPane1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 100, Short.MAX_VALUE));
        jLayeredPane1Layout.setVerticalGroup(jLayeredPane1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 100, Short.MAX_VALUE));
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Kasir/");
        this.jLabel2.setText("Cabang");
        this.penjualanTable.setModel(this.penjualanTableModel);
        this.penjualanTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanCabangPanelUsingBatchPengiriman.this.penjualanTableKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.penjualanTable);
        this.jLabel5.setText("F1:Customer");
        this.jLabel6.setText("F2:Print");
        this.jLabel7.setText("Enter:Edit");
        this.jLabel8.setText("Delete:Hapus");
        this.jLabel9.setText("ESC-Keluar");
        this.jLabel3.setText(":");
        this.jLabel4.setText(":");
        this.jLabel10.setText("F5-F12:PendingTransaksi");
        this.customerField.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PenjualanCabangPanelUsingBatchPengiriman.this.customerFieldActionPerformed(evt);
            }
        });
        this.customerField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanCabangPanelUsingBatchPengiriman.this.customerFieldKeyPressed(evt);
            }
        });
        this.karyawanField.setText("-");
        this.shortcutLbl.setFont(new Font("Tahoma", 1, 14));
        this.shortcutLbl.setForeground(new Color(204, 0, 0));
        this.shortcutLbl.setText("F5");
        this.jLabel11.setFont(new Font("Tahoma", 1, 24));
        this.jLabel11.setText("Total");
        this.totalField.setEditable(false);
        this.totalField.setBackground(new Color(255, 255, 0));
        this.totalField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("\u00a4#,##0.--"))));
        this.totalField.setText("0");
        this.totalField.setFont(new Font("Tahoma", 1, 24));
        this.jLabel12.setText("Space: InputBarcode");
        this.salesLbl.setText("Sales");
        this.salesField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanCabangPanelUsingBatchPengiriman.this.salesFieldKeyPressed(evt);
            }
        });
        this.salesNameField.setText("-");
        this.keteranganLbl.setText("Keterangan");
        this.keteranganCmb.setModel(new DefaultComboBoxModel<String>(new String[]{"KREDIT"}));
        this.keteranganCmb.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanCabangPanelUsingBatchPengiriman.this.keteranganCmbKeyPressed(evt);
            }
        });
        this.jatuhTempoLbl.setText("Jatuh Tempo");
        this.pickingListIdField.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PenjualanCabangPanelUsingBatchPengiriman.this.pickingListIdFieldActionPerformed(evt);
            }
        });
        this.pickingListIdField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanCabangPanelUsingBatchPengiriman.this.pickingListIdFieldKeyPressed(evt);
            }
        });
        this.jLabel14.setText("ID-Picking List");
        this.importPickingListButton.setText("Import");
        this.importPickingListButton.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                PenjualanCabangPanelUsingBatchPengiriman.this.importPickingListButtonMouseClicked(evt);
            }
        });
        this.reprintBtn.setText("REPRINT");
        this.reprintBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PenjualanCabangPanelUsingBatchPengiriman.this.reprintBtnActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.shortcutLbl)).addComponent(this.jLabel14)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLabel3, -1, -1, Short.MAX_VALUE).addComponent(this.jLabel4, -1, -1, Short.MAX_VALUE))).addComponent(this.salesLbl).addComponent(this.keteranganLbl).addComponent(this.jLabel2)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.salesField, -2, 86, -2).addComponent(this.keteranganCmb, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.salesNameField).addGap(0, 0, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addComponent(this.jatuhTempoLbl).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent((Component)this.jatuhTempoField, -2, 123, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.reprintBtn)))).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addComponent(this.customerField, -2, 124, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.customerLbl, -1, -1, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.pickingListIdField, -2, 124, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.importPickingListButton)).addComponent(this.karyawanField, -2, 69, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jLabel11))).addGap(18, 18, 18).addComponent(this.totalField, -2, 229, -2)))).addGroup(layout.createSequentialGroup().addComponent(this.jLabel5).addGap(36, 36, 36).addComponent(this.jLabel6).addGap(48, 48, 48).addComponent(this.jLabel10).addGap(29, 29, 29).addComponent(this.jLabel7).addGap(50, 50, 50).addComponent(this.jLabel8).addGap(30, 30, 30).addComponent(this.jLabel12).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jLabel9))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1, -1, -1, Short.MAX_VALUE).addComponent(this.shortcutLbl)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.karyawanField))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.jLabel14).addComponent(this.pickingListIdField, -2, -1, -2).addComponent(this.importPickingListButton))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.totalField, -2, 40, -2).addComponent(this.jLabel11, -1, -1, Short.MAX_VALUE)).addGap(28, 28, 28))).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.customerLbl, -1, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.customerField, -2, -1, -2).addComponent(this.jLabel2))).addGap(12, 12, 12).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.salesLbl).addComponent(this.salesField, -2, -1, -2).addComponent(this.salesNameField)).addGap(0, 0, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.keteranganLbl).addComponent(this.keteranganCmb, -2, -1, -2).addComponent(this.jatuhTempoLbl)).addComponent((Component)this.jatuhTempoField, -2, -1, -2))).addComponent(this.reprintBtn)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 360, -2).addGap(6, 6, 6).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5).addComponent(this.jLabel6).addComponent(this.jLabel10).addComponent(this.jLabel7).addComponent(this.jLabel8).addComponent(this.jLabel12).addComponent(this.jLabel9)).addGap(116, 116, 116)));
    }

    private void f2Press() {
        if (this.penjualanTableModel.getRowCount() > 0) {
            if (this.keteranganCmb.getSelectedItem().equals("KREDIT")) {
                if (this.customerField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Masukkan Tujuan Pengiriman Terlebih Dahulu");
                } else if (this.customerField.getText().equals(this.konstantaService.getConstantValue("idCabang"))) {
                    JOptionPane.showMessageDialog(null, "Tujuan Pengiriman Tidak Boleh ke Toko Sendiri");
                } else {
                    CetakNotaCabang cetakNota = new CetakNotaCabang(null, true, this.shortcutLbl.getText(), Float.valueOf(Float.parseFloat(this.totalField.getValue().toString())), this.jatuhTempoField.getDate(), this.keteranganCmb.getSelectedItem().toString(), this.pickingListIdField.getText());
                    cetakNota.addListener(new cetakNotaEventHandler());
                    int w = cetakNota.getWidth();
                    int h = cetakNota.getHeight();
                    cetakNota.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                    cetakNota.setVisible(true);
                    cetakNota.requestDefaultFocus();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Masukkan Data Terlebih Dahulu");
        }
    }

    private void customerFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.customerField.getText().equals("")) {
                SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Cabang");
                sd.addSearchDialogListener(new searchCustomerDialogEventHandler());
                int w = sd.getWidth();
                int h = sd.getHeight();
                sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                sd.setVisible(true);
            } else {
                this.customer = this.customerService.getCustomerById(this.customerField.getText());
                if (this.customer == null) {
                    JOptionPane.showMessageDialog(null, "Data Cabang Tidak Ditemukan");
                } else {
                    this.penjualanCabangSementaraService.updateCustomerPenjualanSementara(this.customer.getId() + "", this.shortcutLbl.getText());
                    this.customerLbl.setText(this.customer.getNama() + "-" + this.customer.getAlamat());
                    this.penjualanTable.requestFocus();
                }
            }
        } else if (evt.getKeyCode() == 112 || evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Cabang");
            sd.addSearchDialogListener(new searchCustomerDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        } else if (evt.getKeyCode() == 114) {
            this.listener.shortcutClick("F3");
        } else if (evt.getKeyCode() == 115) {
            this.listener.shortcutClick("F4");
        } else if (evt.getKeyCode() == 116) {
            this.listener.shortcutClick("F5");
        } else if (evt.getKeyCode() == 117) {
            this.listener.shortcutClick("F6");
        } else if (evt.getKeyCode() == 118) {
            this.listener.shortcutClick("F7");
        } else if (evt.getKeyCode() == 119) {
            this.listener.shortcutClick("F8");
        } else if (evt.getKeyCode() == 120) {
            this.listener.shortcutClick("F9");
        } else if (evt.getKeyCode() == 121) {
            this.listener.shortcutClick("F10");
        } else if (evt.getKeyCode() == 122) {
            this.listener.shortcutClick("F11");
        } else if (evt.getKeyCode() == 123) {
            this.listener.shortcutClick("F12");
        } else if (evt.getKeyCode() == 19 || evt.getKeyCode() == 36) {
            ReprintDialogCabang reprintDialog = new ReprintDialogCabang(null, true);
            int w = reprintDialog.getWidth();
            int h = reprintDialog.getHeight();
            reprintDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            reprintDialog.setVisible(true);
        } else if (evt.getKeyCode() == 27) {
            int a = JOptionPane.showConfirmDialog(null, "Transaksi Penjualan Telah Selesai?", "Keluar Transaksi Penjualan", 0, 3);
            if (a == 0) {
                this.listener.escapeClick();
            }
        } else if (evt.getKeyCode() == 155) {
            if (this.helper.getJenisProgram().equalsIgnoreCase("GUDANGSAJA")) {
                this.insertPress();
            } else if (this.helper.getJenisProgram().equalsIgnoreCase("GUDANGTOKO") && this.helper.getStatusProgram().equals("TOKO")) {
                this.insertPress();
            }
        }
    }

    private void penjualanTableKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 112) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Customer");
            sd.addSearchDialogListener(new searchCustomerDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        } else if (evt.getKeyCode() == 113) {
            this.f2Press();
        } else if (evt.getKeyCode() == 114) {
            this.listener.shortcutClick("F3");
        } else if (evt.getKeyCode() == 115) {
            this.listener.shortcutClick("F4");
        } else if (evt.getKeyCode() == 116) {
            this.listener.shortcutClick("F5");
        } else if (evt.getKeyCode() == 117) {
            this.listener.shortcutClick("F6");
        } else if (evt.getKeyCode() == 118) {
            this.listener.shortcutClick("F7");
        } else if (evt.getKeyCode() == 119) {
            this.listener.shortcutClick("F8");
        } else if (evt.getKeyCode() == 120) {
            this.listener.shortcutClick("F9");
        } else if (evt.getKeyCode() == 121) {
            this.listener.shortcutClick("F10");
        } else if (evt.getKeyCode() == 122) {
            this.listener.shortcutClick("F11");
        } else if (evt.getKeyCode() == 123) {
            this.listener.shortcutClick("F12");
        } else if (evt.getKeyCode() == 19 || evt.getKeyCode() == 36) {
            ReprintDialogCabang reprintDialog = new ReprintDialogCabang(null, true);
            int w = reprintDialog.getWidth();
            int h = reprintDialog.getHeight();
            reprintDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            reprintDialog.setVisible(true);
        } else if (evt.getKeyCode() == 35) {
            ReprintDialogCabang reprintDialog = new ReprintDialogCabang(null, true);
            int w = reprintDialog.getWidth();
            int h = reprintDialog.getHeight();
            reprintDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            reprintDialog.setVisible(true);
        } else if (evt.getKeyCode() == 27) {
            int a = JOptionPane.showConfirmDialog(null, "Transaksi Penjualan Telah Selesai?", "Keluar Transaksi Penjualan", 0, 3);
            if (a == 0) {
                this.listener.escapeClick();
            }
        } else if (evt.getKeyCode() == 10) {
            if (this.editTable) {
                PenjualanCabangDetailDialog penjualanDetailDialog = new PenjualanCabangDetailDialog(null, true, this.penjualanTableModel, this.shortcutLbl.getText(), true);
                if (this.penjualanTable.getSelectedRow() != -1) {
                    penjualanDetailDialog.loadKodeBarang(this.penjualanTableModel.getValueAt(this.penjualanTable.getSelectedRow(), 0).toString());
                    penjualanDetailDialog.loadPenjualan(this.penjualanTable.getSelectedRow());
                }
                penjualanDetailDialog.escapeClick();
                penjualanDetailDialog.addListener(new penjualanDetailDialogEventHandler());
                int w = penjualanDetailDialog.getWidth();
                int h = penjualanDetailDialog.getHeight();
                penjualanDetailDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                penjualanDetailDialog.setVisible(true);
            }
        } else if (evt.getKeyCode() == 127) {
            int a;
            if (this.editTable) {
                int a2;
                if (this.penjualanTable.getSelectedRow() != -1 && (a2 = JOptionPane.showConfirmDialog(null, "Hapus Data Ini?", "Delete Data", 0, 3)) == 0) {
                    this.penjualanCabangSementaraService.deletePenjualanSementara(this.shortcutLbl.getText(), this.penjualanTable.getValueAt(this.penjualanTable.getSelectedRow(), 0).toString());
                    this.totalField.setValue(Float.valueOf(Float.parseFloat(this.totalField.getValue().toString()) - Float.parseFloat(this.penjualanTableModel.getValueAt(this.penjualanTable.getSelectedRow(), 7).toString())));
                    this.penjualanTableModel.removeRow(this.penjualanTable.getSelectedRow());
                }
            } else if (!this.editTable && this.penjualanTable.getSelectedRow() != -1 && (a = JOptionPane.showConfirmDialog(null, "Hapus Semua Data?", "Delete Data", 0, 3)) == 0) {
                this.penjualanCabangSementaraService.deletePenjualanSementaraByShortcut(this.shortcutLbl.getText());
                this.removeAllRow();
                this.loadData();
                this.pickingListIdField.setText("");
                this.customerField.setText("");
                this.salesField.setText("");
                this.salesNameField.setText("");
                this.customerLbl.setText("");
            }
        } else if (evt.getKeyCode() == 32) {
            if (this.editTable && !this.shortcutLbl.getText().equals("F3") && !this.shortcutLbl.getText().equals("F4")) {
                PenjualanBarcodeDialog barcodeDialog = new PenjualanBarcodeDialog(null, true, this.penjualanTableModel, this.shortcutLbl.getText(), this.customer.getId() + "");
                barcodeDialog.addListener(new penjualanBarcodeEventHandler());
                int w = barcodeDialog.getWidth();
                int h = barcodeDialog.getHeight();
                barcodeDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                barcodeDialog.setVisible(true);
            }
        } else if (evt.getKeyCode() == 155) {
            if (this.helper.getJenisProgram().equalsIgnoreCase("GUDANGSAJA")) {
                this.insertPress();
            } else if (this.helper.getJenisProgram().equalsIgnoreCase("GUDANGTOKO") && this.helper.getStatusProgram().equals("TOKO")) {
                this.insertPress();
            }
        }
    }

    private void salesFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Sales");
            sd.addSearchDialogListener(new searchSalesDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        } else if (evt.getKeyCode() == 10) {
            if (this.salesField.getText().equals("")) {
                SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Sales");
                sd.addSearchDialogListener(new searchSalesDialogEventHandler());
                int w = sd.getWidth();
                int h = sd.getHeight();
                sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                sd.setVisible(true);
            } else {
                this.sales = this.salesService.getSales(this.salesField.getText());
                if (this.sales == null) {
                    JOptionPane.showMessageDialog(null, "Data Sales Tidak Ditemukan");
                } else {
                    this.salesNameField.setName(this.sales.getNama());
                    this.penjualanTable.requestFocus();
                }
            }
        } else if (evt.getKeyCode() == 114) {
            this.listener.shortcutClick("F3");
        } else if (evt.getKeyCode() == 115) {
            this.listener.shortcutClick("F4");
        } else if (evt.getKeyCode() == 116) {
            this.listener.shortcutClick("F5");
        } else if (evt.getKeyCode() == 117) {
            this.listener.shortcutClick("F6");
        } else if (evt.getKeyCode() == 118) {
            this.listener.shortcutClick("F7");
        } else if (evt.getKeyCode() == 119) {
            this.listener.shortcutClick("F8");
        } else if (evt.getKeyCode() == 120) {
            this.listener.shortcutClick("F9");
        } else if (evt.getKeyCode() == 121) {
            this.listener.shortcutClick("F10");
        } else if (evt.getKeyCode() == 122) {
            this.listener.shortcutClick("F11");
        } else if (evt.getKeyCode() == 123) {
            this.listener.shortcutClick("F12");
        } else if (evt.getKeyCode() == 19) {
            ReprintDialogCabang reprintDialog = new ReprintDialogCabang(null, true);
            int w = reprintDialog.getWidth();
            int h = reprintDialog.getHeight();
            reprintDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            reprintDialog.setVisible(true);
        } else if (evt.getKeyCode() == 27) {
            int a = JOptionPane.showConfirmDialog(null, "Transaksi Penjualan Telah Selesai?", "Keluar Transaksi Penjualan", 0, 3);
            if (a == 0) {
                this.listener.escapeClick();
            }
        } else if (evt.getKeyCode() == 155) {
            if (this.helper.getJenisProgram().equalsIgnoreCase("GUDANGSAJA")) {
                this.insertPress();
            } else if (this.helper.getJenisProgram().equalsIgnoreCase("GUDANGTOKO") && this.helper.getStatusProgram().equals("TOKO")) {
                this.insertPress();
            }
        }
    }

    private void keteranganCmbKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 112) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Customer");
            sd.addSearchDialogListener(new searchCustomerDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        } else if (evt.getKeyCode() == 113) {
            this.f2Press();
        } else if (evt.getKeyCode() == 114) {
            this.listener.shortcutClick("F3");
        } else if (evt.getKeyCode() == 115) {
            this.listener.shortcutClick("F4");
        } else if (evt.getKeyCode() == 116) {
            this.listener.shortcutClick("F5");
        } else if (evt.getKeyCode() == 117) {
            this.listener.shortcutClick("F6");
        } else if (evt.getKeyCode() == 118) {
            this.listener.shortcutClick("F7");
        } else if (evt.getKeyCode() == 119) {
            this.listener.shortcutClick("F8");
        } else if (evt.getKeyCode() == 120) {
            this.listener.shortcutClick("F9");
        } else if (evt.getKeyCode() == 121) {
            this.listener.shortcutClick("F10");
        } else if (evt.getKeyCode() == 122) {
            this.listener.shortcutClick("F11");
        } else if (evt.getKeyCode() == 123) {
            this.listener.shortcutClick("F12");
        } else if (evt.getKeyCode() == 19) {
            ReprintDialogCabang reprintDialog = new ReprintDialogCabang(null, true);
            int w = reprintDialog.getWidth();
            int h = reprintDialog.getHeight();
            reprintDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            reprintDialog.setVisible(true);
        } else if (evt.getKeyCode() == 27) {
            int a = JOptionPane.showConfirmDialog(null, "Transaksi Penjualan Telah Selesai?", "Keluar Transaksi Penjualan", 0, 3);
            if (a == 0) {
                this.listener.escapeClick();
            }
        } else if (evt.getKeyCode() == 155) {
            if (this.helper.getJenisProgram().equalsIgnoreCase("GUDANGSAJA")) {
                this.insertPress();
            } else if (this.helper.getJenisProgram().equalsIgnoreCase("GUDANGTOKO") && this.helper.getStatusProgram().equals("TOKO")) {
                this.insertPress();
            }
        } else if (evt.getKeyCode() == 10) {
            this.penjualanTable.requestFocus();
        } else if (evt.getKeyCode() == 155) {
            if (this.helper.getJenisProgram().equalsIgnoreCase("GUDANGSAJA")) {
                this.insertPress();
            } else if (this.helper.getJenisProgram().equalsIgnoreCase("GUDANGTOKO") && this.helper.getStatusProgram().equals("TOKO")) {
                this.insertPress();
            }
        }
    }

    private void pickingListIdFieldActionPerformed(ActionEvent evt) {
    }

    private void customerFieldActionPerformed(ActionEvent evt) {
    }

    private void importPickingListButtonMouseClicked(MouseEvent evt) {
        String idPickingListText = this.pickingListIdField.getText();
        System.out.println(idPickingListText);
        if (idPickingListText.equals("")) {
            JOptionPane.showMessageDialog(null, "ID Batch Pengiriman tidak boleh kosong");
        } else {
            String kodeBatch = idPickingListText;
            this.batchPengiriman = this.batchPengirimanService.getBatchPengirimanByKodeBatch(kodeBatch);
            if (this.batchPengiriman != null) {
                if (this.batchPengiriman.getStatus().equals("SUDAH KONFIRMASI")) {
                    System.out.println(System.currentTimeMillis() / 1000L);
                    this.customer = this.customerService.getCustomerByIdCabang(this.batchPengiriman.getIdCabangPenerima());
                    this.penjualanCabangSementaraService.updateCustomerPenjualanSementara(this.customer.getId(), this.shortcutLbl.getText());
                    this.customerField.setText(this.customer.getId());
                    this.customerLbl.setText(this.customer.getNama() + "-" + this.customer.getAlamat());
                    this.keteranganCmb.setSelectedIndex(0);
                    this.jatuhTempoField.setDate(new Date());
                    this.penjualanTable.requestFocus();
                    if (this.customer == null) {
                        JOptionPane.showMessageDialog(null, "Harap pilih cabang terima terlebih dahulu");
                    } else if (this.customer.getIdCabang() == -1) {
                        JOptionPane.showMessageDialog(null, "Harap pilih cabang terima terlebih dahulu");
                    } else if (this.customer.getIdCabang() == this.batchPengiriman.getIdCabangPenerima()) {
                        boolean canGo = true;
                        HashMap<String, Jenis> mapJenis = new HashMap<String, Jenis>();
                        List<Jenis> jenises = this.jenisService.getJenisses("");
                        for (Jenis jenis : jenises) {
                            mapJenis.put(jenis.getJenis(), jenis);
                        }
                        this.batchPengirimanDetails = this.batchPengirimanService.getBatchPengirimanDetailByIdBatchPengiriman(this.batchPengiriman.getId());
                        if (this.batchPengirimanDetails != null) {
                            if (!this.batchPengirimanDetails.isEmpty()) {
                                System.out.println(System.currentTimeMillis() / 1000L);
                                this.totalField.setValue(0);
                                int rowCount = this.penjualanTableModel.getRowCount();
                                for (int a = 0; a < rowCount; ++a) {
                                    this.penjualanTableModel.removeRow(0);
                                }
                                System.out.println(System.currentTimeMillis() / 1000L);
                                this.penjualanCabangSementaraService.deletePenjualanSementaraByShortcut(this.shortcutLbl.getText());
                                ArrayList<PenjualanSementara> penjualanSementarasPush = new ArrayList<PenjualanSementara>();
                                for (int i = 0; i < this.batchPengirimanDetails.size(); ++i) {
                                    BatchPengirimanDetail bpd = this.batchPengirimanDetails.get(i);
                                    Jenis jenisItem = (Jenis)mapJenis.get(bpd.getJenisItem());
                                    if (jenisItem == null) continue;
                                    Object[] table = new Object[]{bpd.getIdSku(), bpd.getNamaItem(), bpd.getQuantityTerima() / bpd.getKonversiSatuanTerima(), bpd.getSatuanTerima(), Float.valueOf((float)bpd.getHpp() * (100.0f + jenisItem.getMarginCabang()) / 100.0f * (float)bpd.getKonversiSatuanTerima()), Float.valueOf((float)bpd.getHpp() * (100.0f + jenisItem.getMarginCabang()) / 100.0f * (float)bpd.getQuantityTerima()), 0, Float.valueOf((float)bpd.getHpp() * (100.0f + jenisItem.getMarginCabang()) / 100.0f * (float)bpd.getQuantityTerima())};
                                    this.penjualanTableModel.addRow(table);
                                    this.totalField.setValue(Float.valueOf(Float.parseFloat(this.totalField.getValue().toString()) + Float.parseFloat(table[7].toString())));
                                    PenjualanSementara penjualanSementara = new PenjualanSementara();
                                    penjualanSementara.setKasir(this.helper.getKasir());
                                    penjualanSementara.setStatus(this.helper.getStatusProgram());
                                    penjualanSementara.setShortcut(this.shortcutLbl.getText());
                                    penjualanSementara.setCustomerId(this.customerField.getText());
                                    penjualanSementara.setSalesId(this.salesField.getText());
                                    penjualanSementara.setBarangId(table[0].toString());
                                    penjualanSementara.setJumlahBarang(Float.parseFloat(table[2].toString()));
                                    penjualanSementara.setSatuan(table[3].toString());
                                    penjualanSementara.setHargaJual((long)Float.parseFloat(table[4].toString()));
                                    penjualanSementara.setDiscount((long)Float.parseFloat(table[6].toString()));
                                    penjualanSementara.setTotalHarga((long)Float.parseFloat(table[7].toString()));
                                    penjualanSementara.setNotaKhusus(true);
                                    penjualanSementara.setKeterangan(this.keteranganCmb.getSelectedItem().toString());
                                    penjualanSementara.setPickinglistId(idPickingListText);
                                    penjualanSementarasPush.add(penjualanSementara);
                                }
                                this.penjualanCabangSementaraService.createPenjualanSementaraBatch(penjualanSementarasPush);
                                this.editTable = false;
                                System.out.println(System.currentTimeMillis() / 1000L);
                            } else {
                                canGo = false;
                                JOptionPane.showMessageDialog(null, "Pengiriman Kosong...");
                            }
                            if (canGo) {
                                // empty if block
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Penjualan untuk pengiriman ini sudah tercetak");
                        }
                    } else {
                        System.out.print(this.customer.getIdCabang() + " - " + this.batchPengiriman.getIdCabangPenerima());
                        JOptionPane.showMessageDialog(null, "Cabang terima yang dipilih tidak sama dengan Batch Pengiriman");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Batch Pengiriman belum dikonfirmasi, harap lakukan konfirmasi lewat web interface");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Batch pengiriman tidak ditemukan");
            }
        }
    }

    private void reprintBtnActionPerformed(ActionEvent evt) {
        ReprintDialogCabang reprintDialog = new ReprintDialogCabang(null, true);
        int w = reprintDialog.getWidth();
        int h = reprintDialog.getHeight();
        reprintDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
        reprintDialog.setVisible(true);
    }

    private void pickingListIdFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 114) {
            this.listener.shortcutClick("F3");
        } else if (evt.getKeyCode() == 115) {
            this.listener.shortcutClick("F4");
        } else if (evt.getKeyCode() == 116) {
            this.listener.shortcutClick("F5");
        } else if (evt.getKeyCode() == 117) {
            this.listener.shortcutClick("F6");
        } else if (evt.getKeyCode() == 118) {
            this.listener.shortcutClick("F7");
        } else if (evt.getKeyCode() == 119) {
            this.listener.shortcutClick("F8");
        } else if (evt.getKeyCode() == 120) {
            this.listener.shortcutClick("F9");
        } else if (evt.getKeyCode() == 121) {
            this.listener.shortcutClick("F10");
        } else if (evt.getKeyCode() == 122) {
            this.listener.shortcutClick("F11");
        } else if (evt.getKeyCode() == 123) {
            this.listener.shortcutClick("F12");
        } else if (evt.getKeyCode() == 19 || evt.getKeyCode() == 36) {
            ReprintDialogCabang reprintDialog = new ReprintDialogCabang(null, true);
            int w = reprintDialog.getWidth();
            int h = reprintDialog.getHeight();
            reprintDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            reprintDialog.setVisible(true);
        } else if (evt.getKeyCode() == 27) {
            int a = JOptionPane.showConfirmDialog(null, "Transaksi Penjualan Telah Selesai?", "Keluar Transaksi Penjualan", 0, 3);
            if (a == 0) {
                this.listener.escapeClick();
            }
        } else if (evt.getKeyCode() == 155) {
            if (this.helper.getJenisProgram().equalsIgnoreCase("GUDANGSAJA")) {
                this.insertPress();
            } else if (this.helper.getJenisProgram().equalsIgnoreCase("GUDANGTOKO") && this.helper.getStatusProgram().equals("TOKO")) {
                this.insertPress();
            }
        }
    }

    public void addTransaksiListener(PenjualanPanelListener transaksiListener) {
        this.listener = transaksiListener;
    }

    private class passwordInputDialogEventHandler
    implements PasswordInputListener {
        private passwordInputDialogEventHandler() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("admin") || karyawan.getRole().equalsIgnoreCase("manager")) {
                PenjualanCabangPanelUsingBatchPengiriman.this.insertPress();
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class closeKasirEventHandler
    implements CloseKasirListener {
        private closeKasirEventHandler() {
        }

        @Override
        public void keluarPenjualan() {
            PenjualanCabangPanelUsingBatchPengiriman.this.listener.escapeClick();
        }
    }

    private class penjualanBarcodeEventHandler
    implements PenjualanBarcodeListener {
        private penjualanBarcodeEventHandler() {
        }

        @Override
        public void saveClick(Object[] rowValue) {
            int a;
            Long currentTime = System.currentTimeMillis();
            boolean newData = true;
            for (a = 0; a < PenjualanCabangPanelUsingBatchPengiriman.this.penjualanTableModel.getRowCount(); ++a) {
                if (!PenjualanCabangPanelUsingBatchPengiriman.this.penjualanTableModel.getValueAt(a, 0).equals(rowValue[0])) continue;
                newData = false;
                break;
            }
            if (newData) {
                PenjualanCabangPanelUsingBatchPengiriman.this.penjualanTableModel.addRow(rowValue);
                PenjualanCabangPanelUsingBatchPengiriman.this.totalField.setValue(Float.valueOf(Float.parseFloat(PenjualanCabangPanelUsingBatchPengiriman.this.totalField.getValue().toString()) + Float.parseFloat(rowValue[7].toString())));
            } else {
                PenjualanCabangPanelUsingBatchPengiriman.this.totalField.setValue(Float.valueOf(Float.parseFloat(PenjualanCabangPanelUsingBatchPengiriman.this.totalField.getValue().toString()) - Float.parseFloat(PenjualanCabangPanelUsingBatchPengiriman.this.penjualanTableModel.getValueAt(a, 7).toString())));
                PenjualanCabangPanelUsingBatchPengiriman.this.totalField.setValue(Float.valueOf(Float.parseFloat(PenjualanCabangPanelUsingBatchPengiriman.this.totalField.getValue().toString()) + Float.parseFloat(rowValue[7].toString())));
                PenjualanCabangPanelUsingBatchPengiriman.this.penjualanTableModel.removeRow(a);
                PenjualanCabangPanelUsingBatchPengiriman.this.penjualanTableModel.addRow(rowValue);
            }
            PenjualanSementara penjualanSementara = new PenjualanSementara();
            penjualanSementara.setKasir(PenjualanCabangPanelUsingBatchPengiriman.this.helper.getKasir());
            penjualanSementara.setStatus(PenjualanCabangPanelUsingBatchPengiriman.this.helper.getStatusProgram());
            penjualanSementara.setShortcut(PenjualanCabangPanelUsingBatchPengiriman.this.shortcutLbl.getText());
            penjualanSementara.setCustomerId(PenjualanCabangPanelUsingBatchPengiriman.this.customerField.getText());
            penjualanSementara.setBarangId(rowValue[0].toString());
            penjualanSementara.setJumlahBarang(Float.parseFloat(rowValue[2].toString()));
            penjualanSementara.setSatuan(rowValue[3].toString());
            penjualanSementara.setHargaJual((long)Float.parseFloat(rowValue[4].toString()));
            penjualanSementara.setDiscount((long)Float.parseFloat(rowValue[6].toString()));
            penjualanSementara.setTotalHarga((long)Float.parseFloat(rowValue[7].toString()));
            penjualanSementara.setKeterangan(PenjualanCabangPanelUsingBatchPengiriman.this.keteranganCmb.getSelectedItem().toString());
            int status = PenjualanCabangPanelUsingBatchPengiriman.this.penjualanCabangSementaraService.deletePenjualanSementara(PenjualanCabangPanelUsingBatchPengiriman.this.shortcutLbl.getText(), penjualanSementara.getBarangId());
            PenjualanCabangPanelUsingBatchPengiriman.this.penjualanCabangSementaraService.createPenjualanSementara(penjualanSementara);
        }

        @Override
        public void refreshData() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class passwordSaveEventHandler
    implements PasswordInputListener {
        private passwordSaveEventHandler() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR")) {
                if (PenjualanCabangPanelUsingBatchPengiriman.this.promoVoucher.getStatus().equals("AKTIF")) {
                    CetakNota2 cetakNota = new CetakNota2(null, true, PenjualanCabangPanelUsingBatchPengiriman.this.shortcutLbl.getText(), Float.valueOf(Float.parseFloat(PenjualanCabangPanelUsingBatchPengiriman.this.totalField.getValue().toString())), PenjualanCabangPanelUsingBatchPengiriman.this.jatuhTempoField.getDate(), PenjualanCabangPanelUsingBatchPengiriman.this.keteranganCmb.getSelectedItem().toString());
                    cetakNota.addListener(new cetakNotaEventHandler());
                    int w = cetakNota.getWidth();
                    int h = cetakNota.getHeight();
                    cetakNota.setLocation((PenjualanCabangPanelUsingBatchPengiriman.this.screenWidth - w) / 2, (PenjualanCabangPanelUsingBatchPengiriman.this.screenHeight - h) / 2);
                    cetakNota.setVisible(true);
                    cetakNota.requestDefaultFocus();
                } else {
                    CetakNotaCabang cetakNota = new CetakNotaCabang(null, true, PenjualanCabangPanelUsingBatchPengiriman.this.shortcutLbl.getText(), Float.valueOf(Float.parseFloat(PenjualanCabangPanelUsingBatchPengiriman.this.totalField.getValue().toString())), PenjualanCabangPanelUsingBatchPengiriman.this.jatuhTempoField.getDate(), PenjualanCabangPanelUsingBatchPengiriman.this.keteranganCmb.getSelectedItem().toString(), PenjualanCabangPanelUsingBatchPengiriman.this.pickingListIdField.getText());
                    cetakNota.addListener(new cetakNotaEventHandler());
                    int w = cetakNota.getWidth();
                    int h = cetakNota.getHeight();
                    cetakNota.setLocation((PenjualanCabangPanelUsingBatchPengiriman.this.screenWidth - w) / 2, (PenjualanCabangPanelUsingBatchPengiriman.this.screenHeight - h) / 2);
                    cetakNota.setVisible(true);
                    cetakNota.requestDefaultFocus();
                }
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class cetakNotaEventHandler
    implements CetakNotaListener {
        private cetakNotaEventHandler() {
        }

        @Override
        public void printClick() {
            PenjualanCabangPanelUsingBatchPengiriman.this.totalField.setValue(0);
            PenjualanCabangPanelUsingBatchPengiriman.this.sales = null;
            PenjualanCabangPanelUsingBatchPengiriman.this.salesField.setText("");
            PenjualanCabangPanelUsingBatchPengiriman.this.salesNameField.setText("-");
            int rowCount = PenjualanCabangPanelUsingBatchPengiriman.this.penjualanTableModel.getRowCount();
            for (int a = 0; a < rowCount; ++a) {
                PenjualanCabangPanelUsingBatchPengiriman.this.penjualanTableModel.removeRow(0);
            }
            PenjualanCabangPanelUsingBatchPengiriman.this.penjualanCabangSementaraService.deletePenjualanSementaraByShortcut(PenjualanCabangPanelUsingBatchPengiriman.this.shortcutLbl.getText());
        }
    }

    private class penjualanDetailDialogEventHandler
    implements PenjualanDetailDialogListener {
        private penjualanDetailDialogEventHandler() {
        }

        @Override
        public void saveClick(Object[] rowValue, boolean removeRow) {
            int a;
            boolean newData = true;
            for (a = 0; a < PenjualanCabangPanelUsingBatchPengiriman.this.penjualanTableModel.getRowCount(); ++a) {
                if (!PenjualanCabangPanelUsingBatchPengiriman.this.penjualanTableModel.getValueAt(a, 0).equals(rowValue[0])) continue;
                newData = false;
                break;
            }
            if (newData) {
                PenjualanCabangPanelUsingBatchPengiriman.this.penjualanTableModel.addRow(rowValue);
                PenjualanCabangPanelUsingBatchPengiriman.this.totalField.setValue(Float.valueOf(Float.parseFloat(PenjualanCabangPanelUsingBatchPengiriman.this.totalField.getValue().toString()) + Float.parseFloat(rowValue[7].toString())));
            } else {
                PenjualanCabangPanelUsingBatchPengiriman.this.totalField.setValue(Float.valueOf(Float.parseFloat(PenjualanCabangPanelUsingBatchPengiriman.this.totalField.getValue().toString()) - Float.parseFloat(PenjualanCabangPanelUsingBatchPengiriman.this.penjualanTableModel.getValueAt(a, 7).toString())));
                PenjualanCabangPanelUsingBatchPengiriman.this.totalField.setValue(Float.valueOf(Float.parseFloat(PenjualanCabangPanelUsingBatchPengiriman.this.totalField.getValue().toString()) + Float.parseFloat(rowValue[7].toString())));
                PenjualanCabangPanelUsingBatchPengiriman.this.penjualanTableModel.removeRow(a);
                PenjualanCabangPanelUsingBatchPengiriman.this.penjualanTableModel.addRow(rowValue);
            }
            PenjualanSementara penjualanSementara = new PenjualanSementara();
            penjualanSementara.setKasir(PenjualanCabangPanelUsingBatchPengiriman.this.helper.getKasir());
            penjualanSementara.setStatus(PenjualanCabangPanelUsingBatchPengiriman.this.helper.getStatusProgram());
            penjualanSementara.setShortcut(PenjualanCabangPanelUsingBatchPengiriman.this.shortcutLbl.getText());
            penjualanSementara.setCustomerId(PenjualanCabangPanelUsingBatchPengiriman.this.customerField.getText());
            penjualanSementara.setSalesId(PenjualanCabangPanelUsingBatchPengiriman.this.salesField.getText());
            penjualanSementara.setBarangId(rowValue[0].toString());
            penjualanSementara.setJumlahBarang(Float.parseFloat(rowValue[2].toString()));
            penjualanSementara.setSatuan(rowValue[3].toString());
            penjualanSementara.setHargaJual((long)Float.parseFloat(rowValue[4].toString()));
            penjualanSementara.setDiscount((long)Float.parseFloat(rowValue[6].toString()));
            penjualanSementara.setTotalHarga((long)Float.parseFloat(rowValue[7].toString()));
            penjualanSementara.setNotaKhusus((Boolean)rowValue[8]);
            penjualanSementara.setKeterangan(PenjualanCabangPanelUsingBatchPengiriman.this.keteranganCmb.getSelectedItem().toString());
            PenjualanCabangPanelUsingBatchPengiriman.this.penjualanCabangSementaraService.deletePenjualanSementara(PenjualanCabangPanelUsingBatchPengiriman.this.shortcutLbl.getText(), penjualanSementara.getBarangId());
            PenjualanCabangPanelUsingBatchPengiriman.this.penjualanCabangSementaraService.createPenjualanSementara(penjualanSementara);
        }

        @Override
        public void deleteClick(String idBarang) {
            PenjualanSementara penjualanSementara = new PenjualanSementara();
            penjualanSementara.setBarangId(idBarang);
            PenjualanCabangPanelUsingBatchPengiriman.this.penjualanCabangSementaraService.deletePenjualanSementara(PenjualanCabangPanelUsingBatchPengiriman.this.shortcutLbl.getText(), penjualanSementara.getBarangId());
            PenjualanCabangPanelUsingBatchPengiriman.this.removeAllRow();
            PenjualanCabangPanelUsingBatchPengiriman.this.loadData();
        }

        @Override
        public void refreshData() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public DefaultTableModel getPenjualanTableModel() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void changeButtonPromoTebusMurah() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void changeButtonPromoTebus(String state, List<Object[]> data) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class searchPanelCustomerEventHandler
    implements SearchPanelListener {
        private searchPanelCustomerEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            PenjualanCabangPanelUsingBatchPengiriman.this.customer = PenjualanCabangPanelUsingBatchPengiriman.this.customerService.getCustomerById(chooseValue);
            if (("" + PenjualanCabangPanelUsingBatchPengiriman.this.customer.getIdCabang()).equals(PenjualanCabangPanelUsingBatchPengiriman.this.konstantaService.getConstantValue("idCabang"))) {
                JOptionPane.showMessageDialog(null, "Cabang yang dipilih salah");
            } else {
                PenjualanCabangPanelUsingBatchPengiriman.this.customer = PenjualanCabangPanelUsingBatchPengiriman.this.customerService.getCustomerById(chooseValue);
                PenjualanCabangPanelUsingBatchPengiriman.this.penjualanCabangSementaraService.updateCustomerPenjualanSementara(PenjualanCabangPanelUsingBatchPengiriman.this.customer.getId(), PenjualanCabangPanelUsingBatchPengiriman.this.shortcutLbl.getText());
                PenjualanCabangPanelUsingBatchPengiriman.this.customerField.setText(PenjualanCabangPanelUsingBatchPengiriman.this.customer.getId());
                PenjualanCabangPanelUsingBatchPengiriman.this.customerLbl.setText(PenjualanCabangPanelUsingBatchPengiriman.this.customer.getNama() + "-" + PenjualanCabangPanelUsingBatchPengiriman.this.customer.getAlamat());
                PenjualanCabangPanelUsingBatchPengiriman.this.keteranganCmb.setSelectedIndex(0);
                PenjualanCabangPanelUsingBatchPengiriman.this.jatuhTempoField.setDate(new Date());
                PenjualanCabangPanelUsingBatchPengiriman.this.penjualanTable.requestFocus();
            }
        }
    }

    private class searchCustomerDialogEventHandler
    implements SearchDialogListener {
        private searchCustomerDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"KODE CUSTOMER", "NAMA CUSTOMER", "ALAMAT", "KOTA"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Customer> customers = PenjualanCabangPanelUsingBatchPengiriman.this.customerService.getCustomers(PenjualanCabangPanelUsingBatchPengiriman.this.konstantaService.getConstantValue("tipeToko"));
            for (Customer customer : customers) {
                Object[] temp;
                if (PenjualanCabangPanelUsingBatchPengiriman.this.helper.getJenisProgram().equalsIgnoreCase("GUDANGTOKO")) {
                    if (PenjualanCabangPanelUsingBatchPengiriman.this.helper.getStatusProgram().equalsIgnoreCase("TOKO")) {
                        temp = new String[]{customer.getId(), customer.getNama(), customer.getAlamat(), customer.getKota()};
                        dtm.addRow(temp);
                        continue;
                    }
                    if (!PenjualanCabangPanelUsingBatchPengiriman.this.helper.getStatusProgram().equalsIgnoreCase("GUDANG") || !customer.getNama().equalsIgnoreCase("SINARMART")) continue;
                    temp = new String[]{customer.getId(), customer.getNama(), customer.getAlamat(), customer.getKota()};
                    dtm.addRow(temp);
                    continue;
                }
                if (!PenjualanCabangPanelUsingBatchPengiriman.this.helper.getJenisProgram().equalsIgnoreCase("GUDANGSAJA")) continue;
                temp = new String[]{customer.getId(), customer.getNama(), customer.getAlamat(), customer.getKota()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Customer");
            sp.addSearchPanelListener(new searchPanelCustomerEventHandler());
            sp.setVisible(true);
        }

        public void dispose() {
        }
    }

    private class searchSalesDialogEventHandler
    implements SearchDialogListener {
        private searchSalesDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"KODE SALES", "NAMA SALES", "ALAMAT", "TARGET"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Sales> saleses = PenjualanCabangPanelUsingBatchPengiriman.this.salesService.getSaleses(searchWord);
            for (Sales sales : saleses) {
                Object[] temp = new String[]{sales.getId(), sales.getNama(), sales.getAlamat(), sales.getTarget() + ""};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Sales");
            sp.addSearchPanelListener(new searchPanelSalesEventHandler());
            sp.setVisible(true);
        }

        public void dispose() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        private class searchPanelSalesEventHandler
        implements SearchPanelListener {
            private searchPanelSalesEventHandler() {
            }

            @Override
            public void tableClick(String chooseValue) {
                PenjualanCabangPanelUsingBatchPengiriman.this.salesField.setText(chooseValue);
                PenjualanCabangPanelUsingBatchPengiriman.this.sales = PenjualanCabangPanelUsingBatchPengiriman.this.salesService.getSales(PenjualanCabangPanelUsingBatchPengiriman.this.salesField.getText());
                PenjualanCabangPanelUsingBatchPengiriman.this.penjualanCabangSementaraService.updateSalesPenjualanSementara(PenjualanCabangPanelUsingBatchPengiriman.this.sales.getId(), PenjualanCabangPanelUsingBatchPengiriman.this.shortcutLbl.getText());
                PenjualanCabangPanelUsingBatchPengiriman.this.salesNameField.setText(PenjualanCabangPanelUsingBatchPengiriman.this.sales.getNama());
                PenjualanCabangPanelUsingBatchPengiriman.this.penjualanTable.requestFocus();
            }
        }
    }
}

