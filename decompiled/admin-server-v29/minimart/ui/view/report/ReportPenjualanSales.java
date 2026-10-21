/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.toedter.calendar.JDateChooser
 *  org.apache.poi.hssf.usermodel.HSSFWorkbook
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CreationHelper
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 */
package minimart.ui.view.report;

import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import minimart.engine.controller.Helper;
import minimart.engine.model.Karyawan;
import minimart.engine.model.Sales;
import minimart.engine.service.BarangService;
import minimart.engine.service.CustomerService;
import minimart.engine.service.JenisService;
import minimart.engine.service.KaryawanService;
import minimart.engine.service.PenjualanDetailService;
import minimart.engine.service.PenjualanService;
import minimart.engine.service.SalesService;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.listener.PasswordInputListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ReportPenjualanSales
extends JPanel {
    private JDateChooser endDateField;
    private JButton exitButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel6;
    private JScrollPane jScrollPane1;
    private JComboBox keteranganCmb;
    private JTable reportTable;
    private JTextField salesIdField;
    private JLabel salesLbl;
    private JDateChooser startDateField;
    private JButton tampilkanBtn;
    private JFormattedTextField totalField;
    private JFormattedTextField totalMarginField;
    private String[] reportColumn = new String[]{"Jenis", "Jumlah", "Gros Margin"};
    private String[] reportColumn2 = new String[]{"ID", "Tanggal", "Jatuh Tempo", "Sales", "ID Customer", "Customer", "Karyawan", "Total", "Keterangan"};
    private KaryawanService karyawanService = new KaryawanService();
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private PenjualanService penjualanService = new PenjualanService();
    private CustomerService customerService = new CustomerService();
    private DefaultPanelListener defaultPanelListener;
    private int screenWidth;
    private int screenHeight;
    private Helper helper = Helper.getInstance();
    private JenisService jenisService = new JenisService();
    private BarangService barangService = new BarangService();
    private PenjualanDetailService penjualanDetailService = new PenjualanDetailService();
    private Sales sales = new Sales();
    private SalesService salesService = new SalesService();

    public ReportPenjualanSales() {
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.startDateField.setDate(new Date());
        this.endDateField.setDate(new Date());
    }

    public void addListener(DefaultPanelListener listener) {
        this.defaultPanelListener = listener;
    }

    private void createExcel() {
        try {
            HSSFWorkbook wb = new HSSFWorkbook();
            CreationHelper createhelper = wb.getCreationHelper();
            Sheet sheet = wb.createSheet("new sheet");
            Row row = null;
            Cell cell = null;
            for (int i = 0; i < this.reportTableModel.getRowCount(); ++i) {
                row = sheet.createRow(i);
                for (int j = 0; j < this.reportTableModel.getColumnCount(); ++j) {
                    cell = row.createCell(j);
                    cell.setCellValue(this.reportTableModel.getValueAt(i, j) + "");
                }
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String startDateString = simpleDateFormat.format(this.startDateField.getDate());
            String endDateString = simpleDateFormat.format(this.endDateField.getDate());
            String url = "docPenjualan/" + startDateString + "to" + endDateString + ".xls";
            FileOutputStream out = new FileOutputStream(url);
            wb.write((OutputStream)out);
            out.close();
            JOptionPane.showMessageDialog(null, "Data Telah Tersimpan Pada " + url);
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(ReportPenjualanSales.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(ReportPenjualanSales.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadData(Date startDate, Date endDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = simpleDateFormat.format(startDate);
        String endDateString = simpleDateFormat.format(endDate);
        if (this.keteranganCmb.getSelectedIndex() == 1) {
            this.reportTableModel.setColumnIdentifiers(this.reportColumn);
            List<Object[]> listData = this.penjualanService.getPenjualanSales(this.salesIdField.getText(), startDateString, endDateString);
            long total = 0L;
            long totalMargin = 0L;
            for (Object[] data : listData) {
                total += Long.parseLong(data[1].toString());
                Object[] rowData = new Object[]{data[0], data[1], data[2]};
                totalMargin += Long.parseLong(data[2] + "");
                this.reportTableModel.addRow(rowData);
            }
            if (this.helper.getKaryawan().getRole().equals("DIREKTUR") || this.helper.getKaryawan().getRole().equals("ACCOUNTING")) {
                this.totalMarginField.setValue(totalMargin);
            }
            this.totalField.setValue(total);
        } else if (this.keteranganCmb.getSelectedIndex() == 0) {
            this.reportTableModel.setColumnIdentifiers(this.reportColumn2);
            List<Object[]> penjualans = this.penjualanService.getPenjualans(startDateString, endDateString, "", this.salesIdField.getText());
            long totalPenjualan = 0L;
            for (Object[] penjualan : penjualans) {
                totalPenjualan += ((Long)penjualan[7]).longValue();
                this.reportTableModel.addRow(penjualan);
            }
            this.totalField.setValue(totalPenjualan);
        }
    }

    private void clearTable() {
        int size = this.reportTableModel.getRowCount();
        for (int a = 0; a < size; ++a) {
            this.reportTableModel.removeRow(0);
        }
    }

    private void initComponents() {
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.exitButton = new JButton();
        this.startDateField = new JDateChooser();
        this.endDateField = new JDateChooser();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.tampilkanBtn = new JButton();
        this.jLabel3 = new JLabel();
        this.salesIdField = new JTextField();
        this.salesLbl = new JLabel();
        this.jLabel4 = new JLabel();
        this.totalField = new JFormattedTextField();
        this.keteranganCmb = new JComboBox();
        this.totalMarginField = new JFormattedTextField();
        this.jLabel6 = new JLabel();
        this.setPreferredSize(new Dimension(800, 600));
        this.reportTable.setModel(this.reportTableModel);
        this.jScrollPane1.setViewportView(this.reportTable);
        this.exitButton.setText("Exit");
        this.exitButton.setPreferredSize(new Dimension(83, 23));
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPenjualanSales.this.exitButtonActionPerformed(evt);
            }
        });
        this.jLabel1.setText("Dari Tanggal");
        this.jLabel2.setText("Sampai Tanggal");
        this.tampilkanBtn.setText("Tampilkan");
        this.tampilkanBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPenjualanSales.this.tampilkanBtnActionPerformed(evt);
            }
        });
        this.jLabel3.setText("Sales");
        this.salesIdField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPenjualanSales.this.salesIdFieldKeyPressed(evt);
            }
        });
        this.salesLbl.setText("-");
        this.jLabel4.setFont(new Font("Tahoma", 0, 14));
        this.jLabel4.setText("TOTAL");
        this.totalField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.totalField.setFont(new Font("Tahoma", 0, 14));
        this.keteranganCmb.setModel(new DefaultComboBoxModel<String>(new String[]{"Per Transaksi", "Per Jenis"}));
        this.totalMarginField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.jLabel6.setText("TOTAL MARGIN");
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, GroupLayout.Alignment.TRAILING, -1, 780, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.tampilkanBtn)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(6, 6, 6).addComponent(this.exitButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jLabel4)).addGroup(layout.createSequentialGroup().addGap(24, 24, 24).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent((Component)this.startDateField, -1, 150, Short.MAX_VALUE).addComponent(this.salesIdField).addComponent(this.keteranganCmb, 0, -1, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLabel2, -1, -1, Short.MAX_VALUE).addComponent(this.salesLbl, -1, -1, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent((Component)this.endDateField, -2, 150, -2).addGap(0, 0, Short.MAX_VALUE)))).addGroup(layout.createSequentialGroup().addComponent(this.jLabel3).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jLabel6))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.totalField, -1, 147, Short.MAX_VALUE).addComponent(this.totalMarginField, GroupLayout.Alignment.TRAILING)))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel2).addComponent((Component)this.startDateField, -2, -1, -2)).addComponent((Component)this.endDateField, -2, -1, -2)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(12, 12, 12).addComponent(this.jLabel3)).addGroup(layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.salesIdField, -2, -1, -2).addComponent(this.salesLbl)))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keteranganCmb, -2, -1, -2)).addGroup(layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.totalMarginField, -2, -1, -2).addComponent(this.jLabel6)))).addGap(1, 1, 1).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.exitButton, -2, -1, -2).addComponent(this.tampilkanBtn).addComponent(this.jLabel4).addComponent(this.totalField, -2, -1, -2)).addGap(7, 7, 7).addComponent(this.jScrollPane1, -2, 440, -2).addGap(46, 46, 46)));
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }

    private void tampilkanBtnActionPerformed(ActionEvent evt) {
        this.clearTable();
        this.loadData(this.startDateField.getDate(), this.endDateField.getDate());
    }

    private void salesIdFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Sales");
            sd.addSearchDialogListener(new searchSalesDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        } else if (evt.getKeyCode() == 10) {
            if (this.salesIdField.getText().equals("")) {
                SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Sales");
                sd.addSearchDialogListener(new searchSalesDialogEventHandler());
                int w = sd.getWidth();
                int h = sd.getHeight();
                sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                sd.setVisible(true);
            } else {
                this.sales = this.salesService.getSales(this.salesIdField.getText());
                if (this.sales == null) {
                    JOptionPane.showMessageDialog(null, "Data Sales Tidak Ditemukan");
                } else {
                    this.salesLbl.setName(this.sales.getNama());
                    this.reportTable.requestFocus();
                }
            }
        }
    }

    private int rataKanan(String price, int x) {
        int len = price.length();
        for (int i = 0; i < len; ++i) {
            x -= 4;
        }
        return x;
    }

    private class passwordInputPenjualanJenis
    implements PasswordInputListener {
        private passwordInputPenjualanJenis() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("ACCOUNTING")) {
                ReportPenjualanSales.this.loadData(ReportPenjualanSales.this.startDateField.getDate(), ReportPenjualanSales.this.endDateField.getDate());
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
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
            List<Sales> saleses = ReportPenjualanSales.this.salesService.getSaleses(searchWord);
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
                ReportPenjualanSales.this.salesIdField.setText(chooseValue);
                ReportPenjualanSales.this.sales = ReportPenjualanSales.this.salesService.getSales(ReportPenjualanSales.this.salesIdField.getText());
                ReportPenjualanSales.this.salesLbl.setText(ReportPenjualanSales.this.sales.getNama());
                ReportPenjualanSales.this.reportTable.requestFocus();
            }
        }
    }
}

