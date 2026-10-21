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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.model.Customer;
import minimart.engine.model.Jenis;
import minimart.engine.service.BarangService;
import minimart.engine.service.CustomerService;
import minimart.engine.service.JenisService;
import minimart.engine.service.PenjualanDetailService;
import minimart.engine.service.PenjualanService;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ReportPenjualanBarang2
extends JPanel {
    private JTextField barangField;
    private JLabel barangLbl;
    private JDateChooser endDateField;
    private JButton exitButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JScrollPane jScrollPane1;
    private JTextField jenisField;
    private JTable reportTable;
    private JDateChooser startDateField;
    private JButton tampilkanBtn;
    private JFormattedTextField totalPenjualanField;
    private String[] reportColumn = new String[]{"ID", "Nama Barang", "Total Penjualan", "Satuan", "Total Harga"};
    private String customerId;
    private Jenis jenis;
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private PenjualanService penjualanService = new PenjualanService();
    private CustomerService customerService = new CustomerService();
    private DefaultPanelListener defaultPanelListener;
    private SearchDialog sd;
    private int screenWidth;
    private int screenHeight;
    private Helper helper = Helper.getInstance();
    private JenisService jenisService = new JenisService();
    private BarangService barangService = new BarangService();
    private PenjualanDetailService penjualanDetailService = new PenjualanDetailService();
    private Barang barang;

    public ReportPenjualanBarang2() {
        this.initComponents();
        this.customerId = this.customerId;
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.startDateField.setDate(new Date());
        this.endDateField.setDate(new Date());
        this.reportTable.setSelectionMode(0);
        this.reportTable.setCellSelectionEnabled(true);
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
            Logger.getLogger(ReportPenjualanBarang2.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(ReportPenjualanBarang2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadData(Date startDate, Date endDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = simpleDateFormat.format(startDate);
        String endDateString = simpleDateFormat.format(endDate);
        int limit = 0;
        limit = this.helper.isInteger(this.jenisField.getText()) ? Integer.parseInt(this.jenisField.getText()) : 0;
        List<Object[]> objects = this.penjualanDetailService.getTotalKeuntunganByBarang(startDateString, endDateString, this.barang, this.jenisField.getText());
        long total = 0L;
        for (Object[] object : objects) {
            this.reportTableModel.addRow(object);
            this.totalPenjualanField.setValue(total += Long.parseLong(object[4] + ""));
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
        this.barangField = new JTextField();
        this.jLabel4 = new JLabel();
        this.jenisField = new JTextField();
        this.jLabel5 = new JLabel();
        this.barangLbl = new JLabel();
        this.jLabel6 = new JLabel();
        this.totalPenjualanField = new JFormattedTextField();
        this.jLabel7 = new JLabel();
        this.setPreferredSize(new Dimension(800, 600));
        this.reportTable.setModel(this.reportTableModel);
        this.jScrollPane1.setViewportView(this.reportTable);
        this.exitButton.setText("Exit");
        this.exitButton.setPreferredSize(new Dimension(83, 23));
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPenjualanBarang2.this.exitButtonActionPerformed(evt);
            }
        });
        this.jLabel1.setText("Dari Tanggal");
        this.jLabel2.setText("Sampai Tanggal");
        this.tampilkanBtn.setText("Tampilkan");
        this.tampilkanBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPenjualanBarang2.this.tampilkanBtnActionPerformed(evt);
            }
        });
        this.jLabel3.setText("Barang");
        this.barangField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPenjualanBarang2.this.barangFieldKeyPressed(evt);
            }
        });
        this.jLabel4.setText("Jenis");
        this.jenisField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPenjualanBarang2.this.jenisFieldKeyPressed(evt);
            }
        });
        this.jLabel5.setText("kosong = semua");
        this.barangLbl.setText("-");
        this.jLabel6.setText("Cari = F2");
        this.jLabel7.setText("Total Penjualan");
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, GroupLayout.Alignment.TRAILING, -1, 780, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3).addComponent(this.jLabel4)).addGap(0, 0, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.tampilkanBtn)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLabel5, -1, -1, Short.MAX_VALUE).addComponent(this.jenisField).addComponent((Component)this.startDateField, -1, 150, Short.MAX_VALUE).addComponent(this.barangField)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent((Component)this.endDateField, -2, 150, -2)).addComponent(this.barangLbl, -1, -1, Short.MAX_VALUE).addComponent(this.jLabel6))).addGroup(layout.createSequentialGroup().addComponent(this.exitButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jLabel7))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.totalPenjualanField, -1, 195, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel2).addComponent((Component)this.startDateField, -2, -1, -2)).addComponent((Component)this.endDateField, -2, -1, -2)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(12, 12, 12).addComponent(this.jLabel3)).addGroup(layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.barangField, -2, -1, -2).addComponent(this.barangLbl)))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.jenisField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5).addComponent(this.jLabel6)).addGap(9, 9, 9).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.exitButton, -2, -1, -2).addComponent(this.tampilkanBtn).addComponent(this.totalPenjualanField, -2, -1, -2).addComponent(this.jLabel7)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, -1, -2).addContainerGap()));
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }

    private void tampilkanBtnActionPerformed(ActionEvent evt) {
        this.clearTable();
        this.loadData(this.startDateField.getDate(), this.endDateField.getDate());
    }

    private void barangFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            this.sd = new SearchDialog(null, true, "Pencarian Data Barang");
            this.sd.addSearchDialogListener(new searchBarangDialogEventHandler());
            int w = this.sd.getWidth();
            int h = this.sd.getHeight();
            this.sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            this.sd.setVisible(true);
        }
    }

    private void jenisFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Jenis Barang");
            sd.addSearchDialogListener(new searchJenisDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        } else if (evt.getKeyCode() == 10) {
            if (this.jenisField.getText().equals("")) {
                SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Jenis Barang");
                sd.addSearchDialogListener(new searchJenisDialogEventHandler());
                int w = sd.getWidth();
                int h = sd.getHeight();
                sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                sd.setVisible(true);
            } else {
                Jenis jenisTemp = this.jenisService.getJenis(this.jenisField.getText());
                if (jenisTemp == null) {
                    JOptionPane.showMessageDialog(null, "Jenis Barang Tidak Ditemukan");
                } else {
                    this.tampilkanBtn.requestFocus();
                }
            }
        }
    }

    private void loadKodeBarang(String idBarang) {
        this.barangField.setText(idBarang);
        this.barang = this.barangService.getBarang(idBarang);
        this.barangLbl.setText(this.barang.getNama());
    }

    private int rataKanan(String price, int x) {
        int len = price.length();
        for (int i = 0; i < len; ++i) {
            x -= 4;
        }
        return x;
    }

    private class searchPanelBarangEventHandler
    implements SearchPanelListener {
        private searchPanelBarangEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            ReportPenjualanBarang2.this.loadKodeBarang(chooseValue);
        }
    }

    private class searchBarangDialogEventHandler
    implements SearchDialogListener {
        private searchBarangDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"KODE BARANG", "NAMA BARANG", "SATUAN", "JENIS"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Barang> barangs = ReportPenjualanBarang2.this.barangService.getBarangs("", searchWord, false, "", true);
            for (Barang barang : barangs) {
                Object[] temp = new String[]{barang.getId(), barang.getNama(), barang.getSatuan1(), barang.getJenis()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Barang");
            sp.addSearchPanelListener(new searchPanelBarangEventHandler());
            sp.setVisible(true);
        }
    }

    private class searchPanelJenisEventHandler
    implements SearchPanelListener {
        private searchPanelJenisEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            ReportPenjualanBarang2.this.jenis = ReportPenjualanBarang2.this.jenisService.getJenis(chooseValue);
            ReportPenjualanBarang2.this.jenisField.setText(chooseValue);
            ReportPenjualanBarang2.this.tampilkanBtn.requestFocus();
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
            List<Jenis> jenisses = ReportPenjualanBarang2.this.jenisService.getJenisses(searchWord);
            for (Jenis jenis : jenisses) {
                Object[] temp = new String[]{jenis.getJenis()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Jenis Barang");
            sp.addSearchPanelListener(new searchPanelJenisEventHandler());
            sp.setVisible(true);
        }

        public void dispose() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
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
            Customer customer = ReportPenjualanBarang2.this.customerService.getCustomerById(ReportPenjualanBarang2.this.customerId);
            if (customer != null) {
                g.drawString("Customer:" + customer.getNama(), x + 0, y + yK);
            } else {
                g.drawString("Customer: Semua", x + 0, y + yK);
            }
            g.drawString("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -", x + 0, y + (yK += yP));
            yK += yP;
            for (int a = 0; a < ReportPenjualanBarang2.this.reportTableModel.getRowCount(); ++a) {
                g.drawString(ReportPenjualanBarang2.this.reportTableModel.getValueAt(a, 0).toString() + "/" + ReportPenjualanBarang2.this.reportTableModel.getValueAt(a, 1).toString() + "/" + ReportPenjualanBarang2.this.reportTableModel.getValueAt(a, 2).toString(), x + 0, y + yK);
                int xTemp = ReportPenjualanBarang2.this.rataKanan(ReportPenjualanBarang2.this.helper.formatNumber(Long.parseLong(ReportPenjualanBarang2.this.reportTableModel.getValueAt(a, 5).toString().replace(".", "")) + ""), xRataKanan);
                g.drawString(ReportPenjualanBarang2.this.reportTableModel.getValueAt(a, 5).toString(), x + xTemp, y + yK);
                yK += yP;
            }
            return 0;
        }
    }
}

