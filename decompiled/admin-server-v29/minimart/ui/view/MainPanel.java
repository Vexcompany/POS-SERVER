/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.controller.Helper;
import minimart.engine.dao.CustomerDao;
import minimart.engine.daoimpl.CustomerDaoImpl;
import minimart.engine.model.PromoHemat;
import minimart.engine.model.PromoTebus;
import minimart.engine.restengine.CommonEngine;
import minimart.engine.service.BarangService;
import minimart.engine.service.CustomerPointCommitService;
import minimart.engine.service.CustomerService;
import minimart.engine.service.HistoryHargaService;
import minimart.engine.service.PembelianService;
import minimart.engine.service.PenjualanService;
import minimart.engine.service.PromoHematService;
import minimart.engine.service.PromoTebusService;
import minimart.engine.service.SupplierService;
import minimart.ui.listener.MainPanelListener;

public class MainPanel
extends JPanel {
    private JTable hutangTable;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JLabel jatuhTempoLbl;
    private JLabel kasirNumber;
    private JTable mainTable;
    private JLabel namaKasir;
    private JButton pHargaButton;
    private JLabel statusLbl;
    private Helper helper = Helper.getInstance();
    private HistoryHargaService hisHargaService = new HistoryHargaService();
    private MainPanelListener mainPanelListener;
    private CustomerService customerService = new CustomerService();
    private BarangService barangService = new BarangService();
    private CommonEngine comm = new CommonEngine();
    private CustomerPointCommitService customerPointCommitService = new CustomerPointCommitService();
    private CustomerDao cd = new CustomerDaoImpl();
    private PembelianService pembelianService = new PembelianService();
    private PromoTebusService promoTebusService = new PromoTebusService();
    private PromoHematService promoHematService = new PromoHematService();
    private SupplierService supplierService = new SupplierService();
    private String[] hutangColName = new String[]{"ID", "Supplier", "Tanggal", "Jatuh Tempo", "Jumlah"};
    private String[] columnName = new String[]{"ID", "Nama", "Tanggal", "Jatuh Tempo", "Jumlah"};
    private DefaultTableModel hutangTableModel = new DefaultTableModel(new String[0][1], this.hutangColName){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private DefaultTableModel mainPanelTableModel = new DefaultTableModel(new String[0][1], this.columnName){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private PenjualanService penjualanService = new PenjualanService();

    public MainPanel() {
        this.initComponents();
        this.checkPerubahanHarga();
        if (this.helper.getKasir() == 1) {
            this.checkPromo();
        }
        if (this.helper.getKaryawan().getRole().equals("KASIR")) {
            this.mainTable.setModel(new DefaultTableModel());
        } else {
            this.checkJatuhTempoTagihan();
            this.checkHutangJatuhTempo();
        }
        this.kasirNumber.setText(this.helper.getKasir() + ":");
        this.namaKasir.setText(this.helper.getKaryawan().getNama());
        this.statusLbl.setText(this.helper.getStatusProgram());
    }

    public void addListener(MainPanelListener listener) {
        this.mainPanelListener = listener;
    }

    private void checkPromo() {
        SimpleDateFormat simpleDateFormat;
        String startDateString;
        boolean aktif;
        PromoTebus promoTebus;
        SimpleDateFormat simpleDateFormat2;
        String startDateString2;
        boolean aktif2;
        PromoHemat promoHemat = this.promoHematService.getPromoHematById("1");
        if (promoHemat != null && !(aktif2 = this.promoHematService.isPromoAktif(startDateString2 = (simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd")).format(new Date()))) && promoHemat.getStatus().equals("AKTIF")) {
            promoHemat.setStatus("NONAKTIF");
            int result = this.promoHematService.updatePromoHemat(promoHemat);
            if (result == 1) {
                JOptionPane.showMessageDialog(null, "Tanggal Promo Hemat Telah Habis, Promo di Non-aktifkan");
            }
        }
        if ((promoTebus = this.promoTebusService.getPromoTebus("1")) != null && !(aktif = this.promoTebusService.isPromoAktif(startDateString = (simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd")).format(new Date()))) && promoTebus.getStatus().equals("AKTIF")) {
            promoTebus.setStatus("NONAKTIF");
            int result = this.promoTebusService.updatePromotebus(promoTebus);
            if (result == 1) {
                JOptionPane.showMessageDialog(null, "Tanggal Promo Hemat2 Telah Habis, Promo di Non-aktifkan");
            }
        }
    }

    private void checkHutangJatuhTempo() {
        List<Object[]> hutangs = this.pembelianService.getHutangSudahJatuhTempo("");
        if (hutangs.size() > 0) {
            for (Object[] hutang : hutangs) {
                Object[] data = new Object[]{hutang[0], hutang[4], hutang[1], hutang[3], hutang[5]};
                this.hutangTableModel.addRow(data);
            }
        }
    }

    private void checkJatuhTempoTagihan() {
        List<Object[]> piutangs = this.penjualanService.getPiutangJatuhTempo();
        if (piutangs.size() > 0) {
            this.mainTable.setVisible(true);
            for (Object[] piutang : piutangs) {
                Object[] row = new Object[]{piutang[0], piutang[1], piutang[2], piutang[3], piutang[5]};
                this.mainPanelTableModel.addRow(row);
            }
        } else {
            this.mainTable.setVisible(false);
        }
    }

    private void checkPerubahanHarga() {
        String endDateString;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = simpleDateFormat.format(new Date());
        List<Object[]> historyHargas = this.hisHargaService.getHistoryHarga(startDateString, endDateString = simpleDateFormat.format(new Date()), "");
        if (historyHargas.isEmpty()) {
            this.pHargaButton.setVisible(false);
        } else {
            this.pHargaButton.setVisible(true);
        }
    }

    private void initComponents() {
        this.pHargaButton = new JButton();
        this.jLabel1 = new JLabel();
        this.namaKasir = new JLabel();
        this.kasirNumber = new JLabel();
        this.statusLbl = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.mainTable = new JTable();
        this.jatuhTempoLbl = new JLabel();
        this.jScrollPane2 = new JScrollPane();
        this.hutangTable = new JTable();
        this.jLabel2 = new JLabel();
        this.setPreferredSize(new Dimension(800, 600));
        this.pHargaButton.setFont(new Font("Tahoma", 1, 18));
        this.pHargaButton.setForeground(new Color(255, 0, 0));
        this.pHargaButton.setText("Perubahan Harga");
        this.pHargaButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                MainPanel.this.pHargaButtonActionPerformed(evt);
            }
        });
        this.jLabel1.setFont(new Font("Tahoma", 0, 24));
        this.jLabel1.setText("Kasir");
        this.namaKasir.setFont(new Font("Tahoma", 0, 24));
        this.namaKasir.setText("jLabel2");
        this.kasirNumber.setFont(new Font("Tahoma", 0, 24));
        this.kasirNumber.setText("1");
        this.statusLbl.setFont(new Font("Tahoma", 0, 18));
        this.statusLbl.setText("jLabel2");
        this.mainTable.setModel(this.mainPanelTableModel);
        this.jScrollPane1.setViewportView(this.mainTable);
        this.jatuhTempoLbl.setText("PIUTANG JATUH TEMPO");
        this.hutangTable.setModel(this.hutangTableModel);
        this.jScrollPane2.setViewportView(this.hutangTable);
        this.jLabel2.setText("HUTANG JATUH TEMPO");
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.kasirNumber).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.namaKasir, -2, 139, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.pHargaButton, -2, 227, -2)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.statusLbl).addComponent(this.jatuhTempoLbl).addComponent(this.jScrollPane2, -2, 780, -2).addComponent(this.jLabel2)).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.namaKasir).addComponent(this.kasirNumber).addComponent(this.pHargaButton)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.statusLbl).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE).addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane2, -2, 218, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jatuhTempoLbl).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 196, -2).addContainerGap()));
    }

    private void pHargaButtonActionPerformed(ActionEvent evt) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(5, -1);
        this.mainPanelListener.tampilkanClick(c.getTime(), new Date(), "");
    }

    public MainPanel(JLabel jLabel1, JScrollPane jScrollPane1, JLabel kasirNumber, JTable mainTable, JLabel namaKasir, JButton pHargaButton, JLabel statusLbl, MainPanelListener mainPanelListener) {
        this.jLabel1 = jLabel1;
        this.jScrollPane1 = jScrollPane1;
        this.kasirNumber = kasirNumber;
        this.mainTable = mainTable;
        this.namaKasir = namaKasir;
        this.pHargaButton = pHargaButton;
        this.statusLbl = statusLbl;
        this.mainPanelListener = mainPanelListener;
    }
}

