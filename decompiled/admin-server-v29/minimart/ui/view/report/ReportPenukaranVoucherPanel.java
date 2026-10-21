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
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.model.Customer;
import minimart.engine.model.Voucher;
import minimart.engine.service.CustomerService;
import minimart.engine.service.VoucherService;
import minimart.ui.listener.DefaultPanelListener;

public class ReportPenukaranVoucherPanel
extends JPanel {
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JScrollPane jScrollPane1;
    private JTable jTable1;
    private JButton keluarBtn;
    private JButton tampilkanBtn;
    private JDateChooser tanggalField;
    private JDateChooser tanggalField2;
    private JFormattedTextField totalField;
    private String[] reportColumn = new String[]{"ID", "M_CUSTOMER_ID", "NAMA CUSTOMER", "JUMLAH", "AVAILABLE", "EXPIRED DATE", "EXCHANGE DATE"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn);
    private VoucherService voucherService = new VoucherService();
    private CustomerService customerService = new CustomerService();
    private DefaultPanelListener defaultPanelListener;
    private long total = 0L;

    public ReportPenukaranVoucherPanel() {
        this.initComponents();
    }

    private void clearRow() {
        this.total = 0L;
        int row = this.reportTableModel.getRowCount();
        for (int a = 0; a < row; ++a) {
            this.reportTableModel.removeRow(0);
        }
    }

    public void addListener(DefaultPanelListener listener) {
        this.defaultPanelListener = listener;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.tanggalField = new JDateChooser();
        this.tanggalField2 = new JDateChooser();
        this.tampilkanBtn = new JButton();
        this.keluarBtn = new JButton();
        this.totalField = new JFormattedTextField();
        this.jLabel4 = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.jTable1 = new JTable();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setFont(new Font("Tahoma", 1, 18));
        this.jLabel1.setText("Report Penukaran Voucher Belanja");
        this.jLabel2.setText("Dari");
        this.jLabel3.setText("Hingga");
        this.tampilkanBtn.setText("Tampilkan");
        this.tampilkanBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPenukaranVoucherPanel.this.tampilkanBtnActionPerformed(evt);
            }
        });
        this.keluarBtn.setText("Keluar");
        this.keluarBtn.setPreferredSize(new Dimension(79, 23));
        this.keluarBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPenukaranVoucherPanel.this.keluarBtnActionPerformed(evt);
            }
        });
        this.totalField.setFont(new Font("Tahoma", 1, 18));
        this.totalField.setPreferredSize(new Dimension(199, 28));
        this.jLabel4.setFont(new Font("Tahoma", 1, 18));
        this.jLabel4.setText("Total");
        this.jTable1.setModel(this.reportTableModel);
        this.jScrollPane1.setViewportView(this.jTable1);
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jScrollPane1).addContainerGap()).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent((Component)this.tanggalField, -2, 150, -2)).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.jLabel3).addGap(18, 18, 18).addComponent((Component)this.tanggalField2, -2, 150, -2))).addGap(18, 18, 18).addComponent(this.tampilkanBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keluarBtn, -2, -1, -2).addContainerGap(-1, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 203, Short.MAX_VALUE).addComponent(this.jLabel4).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.totalField, -2, -1, -2).addGap(18, 18, 18)))));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.totalField, -2, -1, -2).addComponent(this.jLabel4)).addGap(18, 18, 18).addComponent(this.jLabel2)).addComponent((Component)this.tanggalField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3).addComponent((Component)this.tanggalField2, -2, -1, -2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.tampilkanBtn).addComponent(this.keluarBtn, -2, -1, -2))).addGap(18, 18, 18).addComponent(this.jScrollPane1, -2, -1, -2).addContainerGap(75, Short.MAX_VALUE)));
    }

    private void keluarBtnActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }

    private void tampilkanBtnActionPerformed(ActionEvent evt) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = simpleDateFormat.format(this.tanggalField.getDate());
        String endDateString = simpleDateFormat.format(this.tanggalField2.getDate());
        List<Voucher> data = this.voucherService.getVoucher(startDateString, endDateString);
        this.clearRow();
        for (Voucher tempVoucher : data) {
            Object[] rowDatas = new Object[7];
            rowDatas[0] = tempVoucher.getId();
            rowDatas[1] = tempVoucher.getCustomerId();
            Customer tempCust = this.customerService.getCustomerById(tempVoucher.getCustomerId());
            rowDatas[2] = tempCust.getNama();
            rowDatas[3] = tempVoucher.getJumlah();
            rowDatas[4] = tempVoucher.getAvailable();
            rowDatas[5] = tempVoucher.getExpiredDate();
            rowDatas[6] = tempVoucher.getExchangeDate();
            this.reportTableModel.addRow(rowDatas);
            this.total += (long)tempVoucher.getJumlah();
        }
        this.totalField.setValue(this.total);
    }
}

