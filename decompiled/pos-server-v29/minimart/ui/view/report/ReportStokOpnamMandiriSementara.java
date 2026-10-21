/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.toedter.calendar.JDateChooser
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CreationHelper
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.xssf.usermodel.XSSFWorkbook
 */
package minimart.ui.view.report;

import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.model.StokOpnamMandiri;
import minimart.engine.service.StokOpnamMandiriSementaraService;
import minimart.engine.service.StokOpnamMandiriService;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.report.ReportPoinPenjualanCustomer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReportStokOpnamMandiriSementara
extends JPanel {
    private JButton cetakExcelBtn;
    private JButton detail;
    private JDateChooser endDateField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JScrollPane jScrollPane1;
    private JButton keluarBtn;
    private JTable reportTable;
    private JComboBox<String> somIdCmb;
    private JDateChooser startDateField;
    private JDateChooser startDateField1;
    private JButton tampilkanBtn;
    private DefaultPanelListener listener;
    private SearchDialog sd;
    private String[] reportColumn = new String[]{"ID", "NAMA BARANG", "STOK_INPUT", "VOID", "CREATED", "LOKASI", "KARYAWAN", "INPUTAN KE"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn);
    private int screenWidth;
    private int screenHeight;
    private StokOpnamMandiriService somService = new StokOpnamMandiriService();
    private StokOpnamMandiriSementaraService somss = new StokOpnamMandiriSementaraService();

    public ReportStokOpnamMandiriSementara() {
        this.initComponents();
        this.startDateField1.setDate(new Date());
        this.endDateField.setDate(new Date());
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    }

    private void initComponents() {
        this.startDateField = new JDateChooser();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.startDateField1 = new JDateChooser();
        this.endDateField = new JDateChooser();
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.tampilkanBtn = new JButton();
        this.keluarBtn = new JButton();
        this.detail = new JButton();
        this.somIdCmb = new JComboBox();
        this.jLabel3 = new JLabel();
        this.cetakExcelBtn = new JButton();
        this.startDateField.addMouseListener((MouseListener)new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                ReportStokOpnamMandiriSementara.this.startDateFieldMouseClicked(evt);
            }
        });
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Dari");
        this.jLabel2.setText("Sampai ");
        this.reportTable.setModel(this.reportTableModel);
        this.jScrollPane1.setViewportView(this.reportTable);
        this.tampilkanBtn.setText("Tampilkan");
        this.tampilkanBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportStokOpnamMandiriSementara.this.tampilkanBtnActionPerformed(evt);
            }
        });
        this.keluarBtn.setText("Keluar");
        this.keluarBtn.setPreferredSize(new Dimension(73, 23));
        this.keluarBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportStokOpnamMandiriSementara.this.keluarBtnActionPerformed(evt);
            }
        });
        this.detail.setText("Detail");
        this.detail.setPreferredSize(new Dimension(73, 23));
        this.detail.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportStokOpnamMandiriSementara.this.detailActionPerformed(evt);
            }
        });
        this.jLabel3.setText("SOM ID");
        this.cetakExcelBtn.setText("Cetak Excel");
        this.cetakExcelBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportStokOpnamMandiriSementara.this.cetakExcelBtnActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 770, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel3)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.somIdCmb, -2, 150, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.detail, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cetakExcelBtn)).addGroup(layout.createSequentialGroup().addComponent((Component)this.startDateField1, -2, 150, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent((Component)this.endDateField, -2, 150, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.tampilkanBtn))).addGap(0, 0, Short.MAX_VALUE)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(this.keluarBtn, -2, 89, -2))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel2).addGap(0, 0, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLabel1, GroupLayout.Alignment.TRAILING).addComponent((Component)this.startDateField1, GroupLayout.Alignment.TRAILING, -1, -1, Short.MAX_VALUE).addComponent((Component)this.endDateField, GroupLayout.Alignment.TRAILING, -1, -1, Short.MAX_VALUE).addComponent(this.tampilkanBtn).addComponent(this.keluarBtn, GroupLayout.Alignment.TRAILING, -1, -1, Short.MAX_VALUE)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.somIdCmb, -2, -1, -2).addComponent(this.jLabel3)).addGroup(layout.createSequentialGroup().addGap(1, 1, 1).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.detail, -2, 26, -2).addComponent(this.cetakExcelBtn)))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jScrollPane1, -1, 479, Short.MAX_VALUE))).addContainerGap()));
    }

    public void addListener(DefaultPanelListener listener) {
        this.listener = listener;
    }

    private void startDateFieldMouseClicked(MouseEvent evt) {
    }

    private void keluarBtnActionPerformed(ActionEvent evt) {
        this.listener.exitClick();
    }

    private void clearRow() {
        int row = this.reportTableModel.getRowCount();
        for (int a = 0; a < row; ++a) {
            this.reportTableModel.removeRow(0);
        }
    }

    private void tampilkanBtnActionPerformed(ActionEvent evt) {
        this.somIdCmb.removeAllItems();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = simpleDateFormat.format(this.startDateField1.getDate());
        Calendar c = Calendar.getInstance();
        c.setTime(this.endDateField.getDate());
        c.add(5, 1);
        String endDateString = simpleDateFormat.format(c.getTime());
        this.clearRow();
        List<StokOpnamMandiri> stokOpnamMandiris = this.somService.getSomByTanggal(startDateString, endDateString);
        for (StokOpnamMandiri som : stokOpnamMandiris) {
            this.somIdCmb.addItem(som.getId() + "");
        }
    }

    private void detailActionPerformed(ActionEvent evt) {
        List<Object[]> datas = this.somss.geDetailBySomId(Integer.parseInt(this.somIdCmb.getSelectedItem().toString()));
        for (Object[] data : datas) {
            this.reportTableModel.addRow(data);
        }
    }

    private void cetakExcelBtnActionPerformed(ActionEvent evt) {
        this.createExcel();
    }

    private void createExcel() {
        try {
            XSSFWorkbook wb = new XSSFWorkbook();
            CreationHelper createhelper = wb.getCreationHelper();
            Sheet sheet = wb.createSheet("new sheet");
            Row row = null;
            Cell cell = null;
            row = sheet.createRow(0);
            cell = row.createCell(0);
            cell.setCellValue("ID");
            cell = row.createCell(1);
            cell.setCellValue("Nama Barang");
            cell = row.createCell(2);
            cell.setCellValue("Stok Input");
            cell = row.createCell(3);
            cell.setCellValue("Void");
            cell = row.createCell(4);
            cell.setCellValue("Created");
            cell = row.createCell(5);
            cell.setCellValue("Lokasi");
            cell = row.createCell(6);
            cell.setCellValue("Karyawan");
            cell = row.createCell(7);
            cell.setCellValue("Inputan Ke");
            for (int i = 0; i < this.reportTableModel.getRowCount(); ++i) {
                row = sheet.createRow(i + 1);
                for (int j = 0; j < this.reportTableModel.getColumnCount(); ++j) {
                    cell = row.createCell(j);
                    cell.setCellValue(this.reportTableModel.getValueAt(i, j) + "");
                }
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String nowDateString = simpleDateFormat.format(new Date());
            String url = "docStok/" + nowDateString + "SOMLOG.xls";
            FileOutputStream out = new FileOutputStream(url);
            wb.write((OutputStream)out);
            out.close();
            JOptionPane.showMessageDialog(null, "Data Telah Tersimpan Pada " + url);
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(ReportPoinPenjualanCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(ReportPoinPenjualanCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

