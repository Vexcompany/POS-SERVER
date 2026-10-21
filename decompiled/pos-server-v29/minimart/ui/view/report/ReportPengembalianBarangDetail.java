/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view.report;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import minimart.engine.model.Barang;
import minimart.engine.model.Customer;
import minimart.engine.model.PengembalianBarang;
import minimart.engine.model.PengembalianBarangDetail;
import minimart.engine.service.BarangService;
import minimart.engine.service.BarcodeService;
import minimart.engine.service.CustomerService;
import minimart.engine.service.KaryawanService;
import minimart.engine.service.PengembalianBarangDetailService;
import minimart.engine.service.PengembalianBarangService;

public class ReportPengembalianBarangDetail
extends JDialog {
    private JTextField customerIdField;
    private JLabel customerNameField;
    private JButton exitBtn;
    private JFormattedTextField hppField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel5;
    private JScrollPane jScrollPane1;
    private JLabel karyawanField;
    private JTable reportTable;
    private JFormattedTextField totalField;
    private String[] reportColumn = new String[]{"Kode Barang", "Nama Barang", "Qty", "Sat", "Harga", "Total"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn);
    private BarangService barangService = new BarangService();
    private KaryawanService karyawanService = new KaryawanService();
    private CustomerService customerService = new CustomerService();
    private long idPengembalianBarang;
    private BarcodeService barcodeService = new BarcodeService();
    private PengembalianBarangDetailService pengembalianBarangDetailService = new PengembalianBarangDetailService();
    private PengembalianBarangService pengembalianBarangService = new PengembalianBarangService();

    public ReportPengembalianBarangDetail(Frame parent, boolean modal, long idPengembalianBarang) {
        super(parent, modal);
        this.initComponents();
        this.idPengembalianBarang = idPengembalianBarang;
        TableColumn col1 = this.reportTable.getColumnModel().getColumn(1);
        TableColumn col2 = this.reportTable.getColumnModel().getColumn(2);
        TableColumn col3 = this.reportTable.getColumnModel().getColumn(3);
        int width = 20;
        col1.setPreferredWidth(130);
        col2.setPreferredWidth(width);
        col3.setPreferredWidth(width);
        this.loadData(idPengembalianBarang);
    }

    private void loadData(long idPengembalianBarang) {
        PengembalianBarang pb = this.pengembalianBarangService.getPengembalianBarangById(idPengembalianBarang);
        this.totalField.setValue(pb.getTotalPengembalian());
        this.karyawanField.setText(this.karyawanService.getKaryawan(pb.getKaryawanId()).getNama());
        this.customerIdField.setText(pb.getCustomerId());
        Customer customer = this.customerService.getCustomerById(pb.getCustomerId());
        this.customerNameField.setText(customer.getNama() + "-" + customer.getAlamat());
        List<PengembalianBarangDetail> pbDetails = this.pengembalianBarangDetailService.getPengembalianBarangDetails(idPengembalianBarang + "");
        long totalHpp = 0L;
        for (PengembalianBarangDetail pengembalianBarangDetail : pbDetails) {
            Object[] rowValue = new Object[6];
            rowValue[0] = pengembalianBarangDetail.getBarangId();
            Barang tempBarang = this.barangService.getBarang(pengembalianBarangDetail.getBarangId());
            rowValue[1] = tempBarang.getNama();
            rowValue[2] = Float.valueOf(pengembalianBarangDetail.getJumlah());
            rowValue[3] = pengembalianBarangDetail.getSatuan();
            rowValue[4] = pengembalianBarangDetail.getHarga();
            rowValue[5] = pengembalianBarangDetail.getTotalHarga();
            long hpp = 0L;
            if (pengembalianBarangDetail.getSatuan().equals(tempBarang.getSatuan1())) {
                hpp = tempBarang.getHpp();
            } else if (pengembalianBarangDetail.getSatuan().equals(tempBarang.getSatuan2())) {
                hpp = tempBarang.getHpp() * (long)tempBarang.getKonversi21();
            } else if (pengembalianBarangDetail.getSatuan().equals(tempBarang.getSatuan3())) {
                hpp = tempBarang.getHpp() * (long)tempBarang.getKonversi21() * (long)tempBarang.getKonversi32();
            }
            totalHpp += hpp;
            this.reportTableModel.addRow(rowValue);
        }
        this.hppField.setValue(totalHpp);
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.customerIdField = new JTextField();
        this.karyawanField = new JLabel();
        this.customerNameField = new JLabel();
        this.totalField = new JFormattedTextField();
        this.jLabel5 = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.exitBtn = new JButton();
        this.hppField = new JFormattedTextField();
        this.jLabel3 = new JLabel();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Karyawan");
        this.jLabel2.setText("Customer");
        this.customerIdField.setEditable(false);
        this.customerIdField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPengembalianBarangDetail.this.customerIdFieldKeyPressed(evt);
            }
        });
        this.karyawanField.setText("-");
        this.customerNameField.setText("-");
        this.totalField.setEditable(false);
        this.totalField.setBackground(new Color(255, 255, 0));
        this.totalField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("\u00a4#,##0.--"))));
        this.totalField.setFont(new Font("Tahoma", 1, 14));
        this.totalField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPengembalianBarangDetail.this.totalFieldKeyPressed(evt);
            }
        });
        this.jLabel5.setFont(new Font("Tahoma", 1, 14));
        this.jLabel5.setText("Total");
        this.reportTable.setModel(this.reportTableModel);
        this.reportTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPengembalianBarangDetail.this.reportTableKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.reportTable);
        this.exitBtn.setText("Exit");
        this.exitBtn.setPreferredSize(new Dimension(75, 23));
        this.exitBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPengembalianBarangDetail.this.exitBtnActionPerformed(evt);
            }
        });
        this.hppField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("\u00a4#,##0.--"))));
        this.jLabel3.setFont(new Font("Tahoma", 1, 14));
        this.jLabel3.setText("HPP");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 780, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addGap(18, 18, 18).addComponent(this.karyawanField, -1, 114, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel2).addGap(18, 18, 18).addComponent(this.customerIdField))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.customerNameField, -2, 172, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLabel5, -1, 74, Short.MAX_VALUE).addComponent(this.jLabel3, -1, -1, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.hppField).addComponent(this.totalField, -1, 232, Short.MAX_VALUE))).addGroup(layout.createSequentialGroup().addComponent(this.exitBtn, -2, -1, -2).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.karyawanField)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.customerIdField, -2, -1, -2).addComponent(this.customerNameField)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.exitBtn, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.totalField, -2, -1, -2).addComponent(this.jLabel5, -1, -1, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.hppField, -2, -1, -2).addComponent(this.jLabel3)).addGap(30, 30, 30))).addComponent(this.jScrollPane1, -2, 499, -2).addContainerGap()));
        this.pack();
    }

    private void totalFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void reportTableKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void customerIdFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void exitBtnActionPerformed(ActionEvent evt) {
        this.dispose();
    }
}

