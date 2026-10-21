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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.controller.Helper;
import minimart.engine.model.Customer;
import minimart.engine.model.PromoKhususMember;
import minimart.engine.model.PromoKhususMemberDetail;
import minimart.engine.service.BarangService;
import minimart.engine.service.CustomerService;
import minimart.engine.service.PenjualanDetailService;
import minimart.engine.service.PromoKhususMemberDetailService;
import minimart.engine.service.PromoKhususMemberService;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.listener.PromoHematDialogListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.PromoKhususPelangganDialog;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class PromoKhususPelanggan
extends JPanel {
    private JDateChooser endDateField;
    private JTextField idPelangganField;
    private JTextField idPromoField;
    private JButton jButton4;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JScrollPane jScrollPane1;
    private JTextField limitField;
    private JLabel namaPelangganLbl;
    private JLabel newLabel;
    private JTable promoTable;
    private JButton rekomBtn;
    private JButton simpanBtn;
    private JDateChooser startDateField;
    private JTextField tahunField;
    private int screenWidth;
    private int screenHeight;
    private CustomerService customerService = new CustomerService();
    private BarangService barangService = new BarangService();
    private PenjualanDetailService penjualanDetailService = new PenjualanDetailService();
    private Helper helper = Helper.getInstance();
    private Customer customer;
    private DefaultPanelListener defaultPanelListener;
    private String action;
    private String status;
    private PromoKhususMemberService promoKhususMemberService = new PromoKhususMemberService();
    private PromoKhususMemberDetailService promoKhususMemberDetailService = new PromoKhususMemberDetailService();
    private String[] promoColumn = new String[]{"ID BARANG", "NAMA BARANG", "SATUAN", "HARGA PROMO", "MINIMUM PEMBELIAN"};
    private DefaultTableModel promoTableModel = new DefaultTableModel(new String[0][1], this.promoColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public PromoKhususPelanggan() {
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setDefault();
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.idPelangganField = new JTextField();
        this.limitField = new JTextField();
        this.namaPelangganLbl = new JLabel();
        this.rekomBtn = new JButton();
        this.simpanBtn = new JButton();
        this.jButton4 = new JButton();
        this.jLabel5 = new JLabel();
        this.tahunField = new JTextField();
        this.jLabel6 = new JLabel();
        this.idPromoField = new JTextField();
        this.newLabel = new JLabel();
        this.jLabel8 = new JLabel();
        this.startDateField = new JDateChooser();
        this.jLabel9 = new JLabel();
        this.endDateField = new JDateChooser();
        this.jScrollPane1 = new JScrollPane();
        this.promoTable = new JTable();
        this.jLabel4 = new JLabel();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Id Pelanggan");
        this.jLabel2.setText("Nama Pelanggan");
        this.jLabel3.setText("Limit Rekomendasi");
        this.idPelangganField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoKhususPelanggan.this.idPelangganFieldKeyPressed(evt);
            }
        });
        this.namaPelangganLbl.setText("-");
        this.rekomBtn.setText("Rekomendasi Promo");
        this.rekomBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PromoKhususPelanggan.this.rekomBtnActionPerformed(evt);
            }
        });
        this.simpanBtn.setText("Simpan");
        this.simpanBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PromoKhususPelanggan.this.simpanBtnActionPerformed(evt);
            }
        });
        this.jButton4.setText("Keluar");
        this.jButton4.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PromoKhususPelanggan.this.jButton4ActionPerformed(evt);
            }
        });
        this.jLabel5.setText("Analisa Belanja (untuk tahun)");
        this.jLabel6.setText("ID Promo");
        this.idPromoField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoKhususPelanggan.this.idPromoFieldKeyPressed(evt);
            }
        });
        this.newLabel.setText("-");
        this.jLabel8.setText("Masa Berlaku Promo");
        this.jLabel9.setText("s/d");
        this.promoTable.setModel(this.promoTableModel);
        this.promoTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoKhususPelanggan.this.promoTableKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.promoTable);
        this.jLabel4.setText("*Kosong = Semua");
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jScrollPane1).addContainerGap()).addGroup(layout.createSequentialGroup().addComponent(this.jLabel3).addGap(72, 72, 72).addComponent(this.limitField, -1, 90, Short.MAX_VALUE).addGap(541, 541, 541)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel2)).addGap(80, 80, 80).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.idPelangganField, -2, 140, -2).addContainerGap(490, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addGap(10, 10, 10).addComponent(this.namaPelangganLbl, -1, -1, Short.MAX_VALUE)))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel5).addGap(18, 18, 18).addComponent(this.tahunField, -2, 90, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel4)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel6).addGap(126, 126, 126).addComponent(this.idPromoField, -2, 130, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.newLabel, -2, 65, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel8).addGap(64, 64, 64).addComponent((Component)this.startDateField, -2, 165, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel9).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent((Component)this.endDateField, -2, 165, -2))).addContainerGap(-1, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addComponent(this.rekomBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.simpanBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jButton4).addContainerGap()))));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel6, -2, 14, -2).addComponent(this.idPromoField, -2, -1, -2).addComponent(this.newLabel)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.idPelangganField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.namaPelangganLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel8).addComponent((Component)this.startDateField, -2, -1, -2).addComponent(this.jLabel9))).addComponent((Component)this.endDateField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5).addComponent(this.tahunField, -2, -1, -2).addComponent(this.jLabel4)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.limitField, -2, -1, -2)).addGap(14, 14, 14).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.rekomBtn).addComponent(this.simpanBtn).addComponent(this.jButton4)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jScrollPane1, -2, 320, -2).addContainerGap(118, Short.MAX_VALUE)));
    }

    private void idPromoFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.idPromoField.getText().equals("")) {
                this.status = "create";
                this.newData();
            } else {
                PromoKhususMember promoKhususMember = this.promoKhususMemberService.getPromoById(this.idPromoField.getText());
                if (promoKhususMember != null) {
                    this.status = "update";
                    this.clearTable();
                    this.idPelangganField.setText(promoKhususMember.getCustomerId());
                    this.customer = this.customerService.getCustomerById(promoKhususMember.getCustomerId());
                    this.namaPelangganLbl.setText(this.customer.getNama() + "-" + this.customer.getAlamat());
                    this.startDateField.setDate(promoKhususMember.getStartDate());
                    this.endDateField.setDate(promoKhususMember.getEndDate());
                    List<PromoKhususMemberDetail> promoKhususMemberDetails = this.promoKhususMemberDetailService.getPromoKhususMemberDetail(promoKhususMember.getId());
                    for (PromoKhususMemberDetail promoKhususMemberDetail : promoKhususMemberDetails) {
                        Object[] rowValue = new Object[]{promoKhususMemberDetail.getBarangId(), this.barangService.getBarang(promoKhususMemberDetail.getBarangId()).getNama(), promoKhususMemberDetail.getSatuan(), promoKhususMemberDetail.getHargaPromo(), promoKhususMemberDetail.getMinPembelian()};
                        this.promoTableModel.addRow(rowValue);
                    }
                    this.idPelangganField.requestFocus();
                    this.idPelangganField.selectAll();
                } else {
                    JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
                }
            }
        }
    }

    private void idPelangganFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 112 || evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Customer");
            sd.addSearchDialogListener(new searchCustomerDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        } else if (evt.getKeyCode() == 10) {
            if (this.idPelangganField.getText().equals("")) {
                SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Customer");
                sd.addSearchDialogListener(new searchCustomerDialogEventHandler());
                int w = sd.getWidth();
                int h = sd.getHeight();
                sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                sd.setVisible(true);
            }
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 27) {
            this.idPromoField.requestFocus();
            this.idPromoField.selectAll();
        }
    }

    private void jButton4ActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }

    private void rekomBtnActionPerformed(ActionEvent evt) {
        this.clearTable();
        if (this.helper.isInteger(this.tahunField.getText())) {
            String startDateString = this.tahunField.getText() + "-01-01";
            String endDateString = this.tahunField.getText() + "-12-31";
            int limit = 0;
            if (this.helper.isInteger(this.limitField.getText())) {
                limit = Integer.parseInt(this.limitField.getText());
                System.out.println(limit);
            }
            List<Object[]> dataRekom = this.penjualanDetailService.getBestSellerByCustomer(this.customer.getId(), startDateString, endDateString, limit);
            for (Object[] data : dataRekom) {
                this.promoTableModel.addRow(data);
            }
        } else if (this.tahunField.getText().equals("")) {
            int limit = 0;
            if (this.helper.isInteger(this.limitField.getText())) {
                limit = Integer.parseInt(this.limitField.getText());
            }
            List<Object[]> dataRekom = this.penjualanDetailService.getBestSellerByCustomer(this.customer.getId(), limit);
            for (Object[] data : dataRekom) {
                this.promoTableModel.addRow(data);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Masukkan Tahun dengan benar");
        }
    }

    private void simpanBtnActionPerformed(ActionEvent evt) {
        PromoKhususMember promoKhususMember = new PromoKhususMember();
        promoKhususMember.setCustomerId(this.customer.getId());
        promoKhususMember.setId(this.idPromoField.getText());
        promoKhususMember.setStartDate(this.startDateField.getDate());
        promoKhususMember.setEndDate(this.endDateField.getDate());
        int result = 0;
        if (this.status.equals("create")) {
            result = this.promoKhususMemberService.create(promoKhususMember);
        } else if (this.status.equals("update")) {
            result = this.promoKhususMemberService.update(promoKhususMember);
        }
        if (result == 1) {
            this.promoKhususMemberDetailService.delete(promoKhususMember.getId());
            for (int a = 0; a < this.promoTableModel.getRowCount(); ++a) {
                PromoKhususMemberDetail promoKM = new PromoKhususMemberDetail();
                promoKM.setPromoKhususMemberId(promoKhususMember.getId());
                promoKM.setBarangId(this.promoTableModel.getValueAt(a, 0).toString());
                promoKM.setSatuan(this.promoTableModel.getValueAt(a, 2).toString());
                promoKM.setHargaPromo(Integer.parseInt(this.promoTableModel.getValueAt(a, 3).toString()));
                promoKM.setMinPembelian(Integer.parseInt(this.promoTableModel.getValueAt(a, 4).toString()));
                this.promoKhususMemberDetailService.create(promoKM);
            }
            JOptionPane.showMessageDialog(null, "Penyimpanan Data Promo Berhasil");
        } else {
            JOptionPane.showMessageDialog(null, "Penyimpanan Data Promo Gagal");
        }
    }

    private void promoTableKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 127) {
            int a;
            if (this.promoTable.getSelectedRow() != -1 && (a = JOptionPane.showConfirmDialog(null, "Hapus Data Ini?", "Delete Data", 0, 3)) == 0) {
                this.promoTableModel.removeRow(this.promoTable.getSelectedRow());
            }
        } else if (evt.getKeyCode() == 10) {
            if (this.idPromoField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Tentukan Kode Promo Terlebih Dahulu");
            } else {
                PromoKhususPelangganDialog promoKhususPelangganDialog = new PromoKhususPelangganDialog(null, true, this.promoTableModel);
                if (this.promoTable.getSelectedRow() != -1) {
                    promoKhususPelangganDialog.loadKodeBarang(this.promoTableModel.getValueAt(this.promoTable.getSelectedRow(), 0).toString());
                    promoKhususPelangganDialog.loadPromoHematData(this.promoTable.getSelectedRow());
                }
                promoKhususPelangganDialog.addListener(new promoKhususPelangganDialogEventHandler());
                int w = promoKhususPelangganDialog.getWidth();
                int h = promoKhususPelangganDialog.getHeight();
                promoKhususPelangganDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                promoKhususPelangganDialog.setVisible(true);
            }
        }
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

    private void newData() {
        this.action = "create";
        this.newLabel.setText(this.action + " data");
        this.setDefault();
        this.clearTable();
        this.idPromoField.setText(Helper.getInstance().generateCodeGeneral(this.promoKhususMemberService.getLastId()));
        this.idPelangganField.requestFocus();
    }

    private void setDefault() {
        this.startDateField.setDate(new Date());
        this.endDateField.setDate(new Date());
        this.idPromoField.setText("");
        this.idPelangganField.setText("");
        this.namaPelangganLbl.setText("-");
        this.tahunField.setText("");
        this.limitField.setText("");
        this.idPromoField.requestFocus();
    }

    private class promoKhususPelangganDialogEventHandler
    implements PromoHematDialogListener {
        private promoKhususPelangganDialogEventHandler() {
        }

        @Override
        public void saveClick(Object[] rowValue) {
            int a;
            boolean newData = true;
            for (a = 0; a < PromoKhususPelanggan.this.promoTableModel.getRowCount(); ++a) {
                if (!PromoKhususPelanggan.this.promoTableModel.getValueAt(a, 0).equals(rowValue[0])) continue;
                newData = false;
                break;
            }
            if (newData) {
                PromoKhususPelanggan.this.promoTableModel.addRow(rowValue);
            } else {
                PromoKhususPelanggan.this.promoTableModel.removeRow(a);
                PromoKhususPelanggan.this.promoTableModel.addRow(rowValue);
            }
        }
    }

    private class searchPanelCustomerEventHandler
    implements SearchPanelListener {
        private searchPanelCustomerEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            PromoKhususPelanggan.this.customer = PromoKhususPelanggan.this.customerService.getCustomerById(chooseValue);
            PromoKhususPelanggan.this.idPelangganField.setText(PromoKhususPelanggan.this.customer.getId());
            PromoKhususPelanggan.this.namaPelangganLbl.setText(PromoKhususPelanggan.this.customer.getNama() + "-" + PromoKhususPelanggan.this.customer.getAlamat());
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
            List<Customer> customers = PromoKhususPelanggan.this.customerService.getCustomers(searchWord);
            for (Customer customer : customers) {
                Object[] temp = new String[]{customer.getId(), customer.getNama(), customer.getAlamat(), customer.getKota()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Customer");
            sp.addSearchPanelListener(new searchPanelCustomerEventHandler());
            sp.setVisible(true);
        }

        public void dispose() {
        }
    }
}

