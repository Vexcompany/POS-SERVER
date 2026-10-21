/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jasperreports.engine.JasperFillManager
 *  net.sf.jasperreports.engine.JasperPrint
 *  net.sf.jasperreports.view.JasperViewer
 *  org.apache.poi.hssf.usermodel.HSSFWorkbook
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CreationHelper
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 */
package minimart.ui.view.report;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.controller.Helper;
import minimart.engine.model.Customer;
import minimart.engine.service.CustomerService;
import minimart.engine.service.KaryawanService;
import minimart.engine.service.PenjualanService;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.view.report.ReportPenjualanDetail;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ReportPenjualanPanel
extends JPanel {
    private JButton cetakBtn;
    private JButton detailButton;
    private JFormattedTextField endDateField;
    private JButton excelBtn;
    private JButton exitButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JScrollPane jScrollPane1;
    private JTable reportTable;
    private JFormattedTextField startDateField;
    private JTextField strukField;
    private JFormattedTextField totalPenjualanField;
    private String[] reportColumn = new String[]{"ID", "Tanggal", "Jatuh Tempo", "Jam", "ID Customer", "Customer", "Karyawan", "Total", "Keterangan", "Voucher"};
    private String customerId;
    private String pembayaran;
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private PenjualanService penjualanService = new PenjualanService();
    private KaryawanService karyawanService = new KaryawanService();
    private CustomerService customerService = new CustomerService();
    private Date startDate;
    private Date endDate;
    private DefaultPanelListener defaultPanelListener;
    private int screenWidth;
    private int screenHeight;
    private Helper helper = Helper.getInstance();

    public ReportPenjualanPanel(Date startDate, Date endDate, String customerId, String pembayaran) {
        System.setProperty("java.awt.headless", "true");
        this.initComponents();
        this.startDate = startDate;
        this.endDate = endDate;
        this.pembayaran = pembayaran;
        this.startDateField.setValue(startDate);
        this.endDateField.setValue(endDate);
        this.customerId = customerId;
        this.loadData();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
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
            FileOutputStream out = new FileOutputStream("H:\\workbook.xls");
            wb.write((OutputStream)out);
            out.close();
            JOptionPane.showMessageDialog(null, "Data Telah Tersimpan Pada H:\\workbook.xls");
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(ReportPenjualanPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(ReportPenjualanPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadData() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = simpleDateFormat.format(this.startDate);
        String endDateString = simpleDateFormat.format(this.endDate);
        List<Object[]> penjualans = this.penjualanService.getPenjualansWithKeterangan(startDateString, endDateString, this.customerId, this.pembayaran);
        this.strukField.setText(penjualans.size() + "");
        long totalPenjualan = 0L;
        long totalHPP = 0L;
        for (Object[] penjualan : penjualans) {
            totalPenjualan += ((Long)penjualan[7]).longValue();
            this.reportTableModel.addRow(penjualan);
        }
        this.totalPenjualanField.setValue(totalPenjualan);
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.startDateField = new JFormattedTextField();
        this.endDateField = new JFormattedTextField();
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.detailButton = new JButton();
        this.exitButton = new JButton();
        this.jLabel3 = new JLabel();
        this.totalPenjualanField = new JFormattedTextField();
        this.jLabel4 = new JLabel();
        this.strukField = new JTextField();
        this.excelBtn = new JButton();
        this.cetakBtn = new JButton();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Tanggal Awal");
        this.jLabel2.setText("Tanggal Akhir");
        this.startDateField.setEditable(false);
        this.startDateField.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(DateFormat.getDateInstance(1))));
        this.endDateField.setEditable(false);
        this.endDateField.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(DateFormat.getDateInstance(1))));
        this.reportTable.setModel(this.reportTableModel);
        this.jScrollPane1.setViewportView(this.reportTable);
        this.detailButton.setText("Detail");
        this.detailButton.setPreferredSize(new Dimension(83, 23));
        this.detailButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPenjualanPanel.this.detailButtonActionPerformed(evt);
            }
        });
        this.exitButton.setText("Exit");
        this.exitButton.setPreferredSize(new Dimension(83, 23));
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPenjualanPanel.this.exitButtonActionPerformed(evt);
            }
        });
        this.jLabel3.setFont(new Font("Tahoma", 0, 18));
        this.jLabel3.setText("Total Penjualan");
        this.totalPenjualanField.setEditable(false);
        this.totalPenjualanField.setBackground(new Color(255, 255, 0));
        this.totalPenjualanField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("\u00a4 #,##0.--"))));
        this.totalPenjualanField.setFont(new Font("Tahoma", 0, 18));
        this.jLabel4.setFont(new Font("Tahoma", 1, 12));
        this.jLabel4.setText("Total Struk");
        this.strukField.setBackground(new Color(102, 255, 204));
        this.strukField.setFont(new Font("Tahoma", 1, 12));
        this.excelBtn.setText("Cetak Nota");
        this.excelBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPenjualanPanel.this.excelBtnActionPerformed(evt);
            }
        });
        this.cetakBtn.setText("Cetak");
        this.cetakBtn.setPreferredSize(new Dimension(83, 23));
        this.cetakBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPenjualanPanel.this.cetakBtnActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, GroupLayout.Alignment.TRAILING, -1, 780, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel2)).addGap(24, 24, 24).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.startDateField, -1, 122, Short.MAX_VALUE).addComponent(this.endDateField))).addGroup(layout.createSequentialGroup().addComponent(this.detailButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.exitButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.excelBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cetakBtn, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3, GroupLayout.Alignment.TRAILING).addComponent(this.jLabel4, GroupLayout.Alignment.TRAILING)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.totalPenjualanField, -1, 150, Short.MAX_VALUE).addComponent(this.strukField)))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(17, 17, 17).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.startDateField, -2, -1, -2).addComponent(this.jLabel4).addComponent(this.strukField, -2, -1, -2)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.endDateField, -2, -1, -2)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.detailButton, -2, -1, -2).addComponent(this.exitButton, -2, -1, -2).addComponent(this.excelBtn).addComponent(this.cetakBtn, -2, -1, -2))).addGroup(layout.createSequentialGroup().addGap(4, 4, 4).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3, GroupLayout.Alignment.TRAILING).addComponent(this.totalPenjualanField, GroupLayout.Alignment.TRAILING, -2, 36, -2)))).addGap(28, 28, 28).addComponent(this.jScrollPane1, -2, 428, -2).addGap(55, 55, 55)));
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }

    private void detailButtonActionPerformed(ActionEvent evt) {
        if (this.reportTable.getSelectedRow() != -1) {
            String id = this.reportTableModel.getValueAt(this.reportTable.getSelectedRow(), 0).toString();
            ReportPenjualanDetail rpd = new ReportPenjualanDetail(null, true, id);
            int w = rpd.getWidth();
            int h = rpd.getHeight();
            rpd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            rpd.setVisible(true);
        }
    }

    private void cetakBtnActionPerformed(ActionEvent evt) {
        if (this.reportTable.getSelectedRow() != -1) {
            String id = this.reportTableModel.getValueAt(this.reportTable.getSelectedRow(), 0).toString();
            try {
                File rear = new File("ireport/CetakNota.jasper");
                String path = rear.getAbsolutePath();
                HashMap<String, String> parameter = new HashMap<String, String>();
                System.out.println(id);
                parameter.put("t_penjualan_id", id);
                JasperPrint print = JasperFillManager.fillReport((String)path, parameter, (Connection)ConnectionManager.getInstance().getDataSource().getConnection("root", "root"));
                JasperViewer.viewReport((JasperPrint)print, (boolean)false);
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Dokumen Tidak Ada " + ex);
            }
        }
    }

    private void excelBtnActionPerformed(ActionEvent evt) {
        if (this.reportTable.getSelectedRow() != -1) {
            String id = this.reportTableModel.getValueAt(this.reportTable.getSelectedRow(), 0).toString();
            try {
                File rear = new File("ireport/PenjualanCabang.jasper");
                String path = rear.getAbsolutePath();
                HashMap<String, String> parameter = new HashMap<String, String>();
                System.out.println(id);
                parameter.put("t_penjualan_id", id);
                JasperPrint print = JasperFillManager.fillReport((String)path, parameter, (Connection)ConnectionManager.getInstance().getDataSource().getConnection("root", "root"));
                JasperViewer.viewReport((JasperPrint)print, (boolean)false);
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Dokumen Tidak Ada " + ex);
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

    class pembelianPrint
    implements Printable {
        pembelianPrint() {
        }

        @Override
        public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
            if (page > 0) {
                return 1;
            }
            Graphics2D g2d = (Graphics2D)g;
            g2d.translate(pf.getImageableX(), pf.getImageableY());
            int y = 0;
            int x = 5;
            int yK = 18;
            int yP = 11;
            int xRataKanan = 160;
            g.setFont(new Font("Times New Roman", 0, 8));
            Customer customer = ReportPenjualanPanel.this.customerService.getCustomerById(ReportPenjualanPanel.this.customerId);
            if (customer != null) {
                g.drawString("Customer:" + customer.getNama(), x + 0, y + yK);
            } else {
                g.drawString("Customer: Semua", x + 0, y + yK);
            }
            g.drawString("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -", x + 0, y + (yK += yP));
            yK += yP;
            for (int a = 0; a < ReportPenjualanPanel.this.reportTableModel.getRowCount(); ++a) {
                g.drawString(ReportPenjualanPanel.this.reportTableModel.getValueAt(a, 1).toString(), x + 0, y + yK);
                g.drawString(ReportPenjualanPanel.this.reportTableModel.getValueAt(a, 0).toString(), x + 50 + 0, y + yK);
                int xTemp = ReportPenjualanPanel.this.rataKanan(ReportPenjualanPanel.this.helper.formatNumber(Long.parseLong(ReportPenjualanPanel.this.reportTableModel.getValueAt(a, 7).toString().replace(".", "")) + ""), xRataKanan);
                g.drawString(ReportPenjualanPanel.this.reportTableModel.getValueAt(a, 7).toString(), x + xTemp, y + yK);
                yK += yP;
            }
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String tanggal = format.format(new Date());
            g.drawString("Tgl " + tanggal, x + 0, y + yK);
            g.drawString("Jam " + new Date().getHours() + ":" + new Date().getMinutes() + ":" + new Date().getSeconds(), x + 120, y + yK);
            return 0;
        }
    }
}

