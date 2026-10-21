/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view.report;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.service.BarangService;
import minimart.engine.service.CustomerService;
import minimart.engine.service.PenjualanDetailService;
import minimart.ui.listener.DefaultPanelListener;

public class ReportKeuntunganPanel
extends JPanel {
    private JButton cetakButton;
    private JFormattedTextField endDateField;
    private JButton exitButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JScrollPane jScrollPane1;
    private JTable reportTable;
    private JFormattedTextField startDateField;
    private JTextField totalCustomerField;
    private JFormattedTextField totalHPPField;
    private JFormattedTextField totalKeuntunganField;
    private JLabel totalLbl;
    private JFormattedTextField totalPenjualanField;
    private String[] reportBarangColumn = new String[]{"ID", "Nama Barang", "Total Penjualan", "Satuan", "Total Harga", "Total HPP", "Total Keuntungan"};
    private String[] reportCustomerColumn = new String[]{"ID", "Nama Customer", "Total Penjualan", "Total Keuntungan"};
    private DefaultTableModel reportTableModel;
    private BarangService barangService = new BarangService();
    private PenjualanDetailService penjualanDetailService = new PenjualanDetailService();
    private CustomerService customerService = new CustomerService();
    private Date startDate;
    private Date endDate;
    private DefaultPanelListener defaultPanelListener;
    private int screenWidth;
    private int screenHeight;
    private Barang barang;
    private Helper helper = Helper.getInstance();
    private int limit = 0;

    public ReportKeuntunganPanel(Date startDate, Date endDate, String tipe, Barang barang, int limit) {
        if (tipe.equalsIgnoreCase("Customer")) {
            this.reportTableModel = new DefaultTableModel(new String[0][1], this.reportCustomerColumn);
        } else if (tipe.equalsIgnoreCase("Barang")) {
            this.reportTableModel = new DefaultTableModel(new String[0][1], this.reportBarangColumn);
        }
        this.initComponents();
        this.startDate = startDate;
        this.endDate = endDate;
        this.limit = limit;
        this.startDateField.setValue(startDate);
        this.endDateField.setValue(endDate);
        this.barang = barang;
        this.loadData(tipe);
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        if (!this.helper.getKaryawan().getRole().equals("DIREKTUR")) {
            this.reportTable.setSelectionMode(0);
            this.reportTable.setCellSelectionEnabled(true);
        }
    }

    public void addListener(DefaultPanelListener listener) {
        this.defaultPanelListener = listener;
    }

    private void loadData(String tipe) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = simpleDateFormat.format(this.startDate);
        String endDateString = simpleDateFormat.format(this.endDate);
        List<Object[]> objects = null;
        long totalKeuntungan = 0L;
        long totalHPP = 0L;
        long totalPenjualan = 0L;
        int totalItem = 0;
        if (tipe.equalsIgnoreCase("Customer")) {
            objects = this.penjualanDetailService.getTotalKeuntunganByCustomer(startDateString, endDateString, this.limit);
            this.totalLbl.setText("Total Customer");
            for (Object[] object : objects) {
                totalHPP = (totalPenjualan += ((BigDecimal)object[2]).longValue()) - (totalKeuntungan += ((Long)object[3]).longValue());
                ++totalItem;
                this.reportTableModel.addRow(object);
            }
        } else if (tipe.equalsIgnoreCase("Barang")) {
            objects = this.penjualanDetailService.getTotalKeuntunganByBarang(startDateString, endDateString, this.barang, "");
            this.totalLbl.setText("Total Barang");
            for (Object[] object : objects) {
                Object[] rowValue = new Object[7];
                rowValue[0] = object[0];
                rowValue[1] = object[1];
                rowValue[2] = object[2];
                rowValue[3] = object[3];
                rowValue[4] = object[4];
                rowValue[5] = object[5];
                if (object[5] == null) {
                    object[5] = new Double("0");
                } else {
                    totalHPP += ((Double)object[5]).longValue();
                }
                rowValue[6] = ((BigDecimal)object[4]).longValue() - ((Double)object[5]).longValue();
                totalPenjualan += ((BigDecimal)object[4]).longValue();
                ++totalItem;
                this.reportTableModel.addRow(rowValue);
            }
            totalKeuntungan = totalPenjualan - totalHPP;
        }
        this.totalKeuntunganField.setValue(totalKeuntungan);
        this.totalPenjualanField.setValue(totalPenjualan);
        this.totalHPPField.setValue(totalHPP);
        this.totalCustomerField.setText(totalItem + "");
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.startDateField = new JFormattedTextField();
        this.endDateField = new JFormattedTextField();
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.cetakButton = new JButton();
        this.exitButton = new JButton();
        this.jLabel3 = new JLabel();
        this.totalKeuntunganField = new JFormattedTextField();
        this.totalHPPField = new JFormattedTextField();
        this.jLabel4 = new JLabel();
        this.totalPenjualanField = new JFormattedTextField();
        this.jLabel5 = new JLabel();
        this.totalLbl = new JLabel();
        this.totalCustomerField = new JTextField();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Tanggal Awal");
        this.jLabel2.setText("Tanggal Akhir");
        this.startDateField.setEditable(false);
        this.startDateField.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(DateFormat.getDateInstance(1))));
        this.startDateField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportKeuntunganPanel.this.startDateFieldKeyPressed(evt);
            }
        });
        this.endDateField.setEditable(false);
        this.endDateField.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(DateFormat.getDateInstance(1))));
        this.reportTable.setModel(this.reportTableModel);
        this.jScrollPane1.setViewportView(this.reportTable);
        this.cetakButton.setText("Cetak");
        this.cetakButton.setPreferredSize(new Dimension(83, 23));
        this.cetakButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportKeuntunganPanel.this.cetakButtonActionPerformed(evt);
            }
        });
        this.exitButton.setText("Exit");
        this.exitButton.setPreferredSize(new Dimension(83, 23));
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportKeuntunganPanel.this.exitButtonActionPerformed(evt);
            }
        });
        this.jLabel3.setFont(new Font("Tahoma", 0, 14));
        this.jLabel3.setText("Total Keuntungan");
        this.totalKeuntunganField.setEditable(false);
        this.totalKeuntunganField.setBackground(new Color(255, 255, 0));
        this.totalKeuntunganField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("\u00a4 #,##0.--"))));
        this.totalKeuntunganField.setFont(new Font("Tahoma", 0, 14));
        this.totalHPPField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat(""))));
        this.totalHPPField.setFont(new Font("Tahoma", 0, 14));
        this.jLabel4.setFont(new Font("Tahoma", 0, 14));
        this.jLabel4.setText("Total HPP");
        this.jLabel4.setToolTipText("");
        this.totalPenjualanField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat(""))));
        this.totalPenjualanField.setFont(new Font("Tahoma", 0, 14));
        this.jLabel5.setFont(new Font("Tahoma", 0, 14));
        this.jLabel5.setText("Total Penjualan");
        this.totalLbl.setFont(new Font("Tahoma", 0, 14));
        this.totalLbl.setText("Total Customer");
        this.totalCustomerField.setFont(new Font("Tahoma", 0, 14));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, GroupLayout.Alignment.TRAILING, -1, 780, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel2)).addGap(24, 24, 24).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.startDateField, -1, 122, Short.MAX_VALUE).addComponent(this.endDateField)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel5, GroupLayout.Alignment.TRAILING).addComponent(this.jLabel4, GroupLayout.Alignment.TRAILING)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.totalPenjualanField, -1, 150, Short.MAX_VALUE).addComponent(this.totalHPPField))).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.totalLbl).addComponent(this.jLabel3)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.totalKeuntunganField, -1, 150, Short.MAX_VALUE).addComponent(this.totalCustomerField))).addGroup(layout.createSequentialGroup().addComponent(this.cetakButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.exitButton, -2, -1, -2).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(17, 17, 17).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.startDateField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.endDateField, -2, -1, -2))).addGroup(layout.createSequentialGroup().addGap(8, 8, 8).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.totalPenjualanField, -2, -1, -2).addComponent(this.jLabel5)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.totalHPPField, -2, -1, -2).addComponent(this.jLabel4)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.totalKeuntunganField, -2, -1, -2).addComponent(this.jLabel3)))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.totalLbl).addComponent(this.totalCustomerField, -2, -1, -2)).addGap(7, 7, 7).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.cetakButton, -2, -1, -2).addComponent(this.exitButton, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, -1, -2).addContainerGap()));
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }

    private void cetakButtonActionPerformed(ActionEvent evt) {
    }

    private void startDateFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.defaultPanelListener.exitClick();
        }
    }
}

