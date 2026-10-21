/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CreationHelper
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.xssf.usermodel.XSSFWorkbook
 */
package minimart.ui.view.report;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.service.BarangService;
import minimart.engine.service.HistoryHargaService;
import minimart.engine.service.JenisService;
import minimart.engine.service.PromoHematDetailService;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;
import minimart.ui.view.report.ReportPoinPenjualanCustomer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReportPlanogram
extends JPanel {
    private JButton bersihkanBtn;
    private JButton cetakBtn;
    private JTable dataTable;
    private JDesktopPane jDesktopPane1;
    private JLabel jLabel1;
    private JScrollPane jScrollPane1;
    private JSplitPane jSplitPane1;
    private JButton keluarBtn;
    private JToggleButton noRakBtn;
    private JButton rakBtn;
    private JTextField rakField;
    private String[] dataColumn = new String[]{"AREA", "RAK", "SHELVING", "GAP", "SAT", "POSISI", "NO URUT", "OOS", "SKU", "NAMA BARANG", "KK", "AB", "DB", "MAX DISP", "MIN DISP", "TAG"};
    private DefaultTableModel dataTableModel = new DefaultTableModel(new String[0][1], this.dataColumn);
    private DefaultPanelListener listener;
    private SearchDialog sd;
    private int screenHeight;
    private int screenWidth;
    private BarangService barangService = new BarangService();
    private Helper helper = Helper.getInstance();
    private ButtonGroup buttonGroup = new ButtonGroup();
    private HistoryHargaService historyHargaService = new HistoryHargaService();
    private JenisService jenisService = new JenisService();
    private PromoHematDetailService promoHematDetailService = new PromoHematDetailService();
    private Barang barang = new Barang();

    public ReportPlanogram() {
        this.initComponents();
        this.jScrollPane1.setHorizontalScrollBarPolicy(30);
        this.dataTable.setAutoResizeMode(0);
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.buttonGroup.add(this.noRakBtn);
        this.noRakBtn.setSelected(true);
        this.rakField.setEnabled(true);
        this.rakBtn.setEnabled(true);
        TableColumn col1 = this.dataTable.getColumnModel().getColumn(8);
        TableColumn col2 = this.dataTable.getColumnModel().getColumn(9);
        col1.setPreferredWidth(100);
        col2.setPreferredWidth(200);
    }

    public void addListener(DefaultPanelListener listener) {
        this.listener = listener;
    }

    private void initComponents() {
        this.jSplitPane1 = new JSplitPane();
        this.jLabel1 = new JLabel();
        this.rakField = new JTextField();
        this.cetakBtn = new JButton();
        this.bersihkanBtn = new JButton();
        this.keluarBtn = new JButton();
        this.rakBtn = new JButton();
        this.jScrollPane1 = new JScrollPane();
        this.dataTable = new JTable();
        this.noRakBtn = new JToggleButton();
        this.jDesktopPane1 = new JDesktopPane();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Inputkan Data Barang Yang Akan Dicetak");
        this.cetakBtn.setText("Cetak Excel");
        this.cetakBtn.setPreferredSize(new Dimension(107, 25));
        this.cetakBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPlanogram.this.cetakBtnActionPerformed(evt);
            }
        });
        this.bersihkanBtn.setText("Bersihkan Tabel");
        this.bersihkanBtn.setMaximumSize(new Dimension(120, 23));
        this.bersihkanBtn.setPreferredSize(new Dimension(107, 25));
        this.bersihkanBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPlanogram.this.bersihkanBtnActionPerformed(evt);
            }
        });
        this.keluarBtn.setText("Keluar");
        this.keluarBtn.setPreferredSize(new Dimension(107, 25));
        this.keluarBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPlanogram.this.keluarBtnActionPerformed(evt);
            }
        });
        this.rakBtn.setText("Masukkan Data");
        this.rakBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPlanogram.this.rakBtnActionPerformed(evt);
            }
        });
        this.dataTable.setModel(this.dataTableModel);
        this.jScrollPane1.setViewportView(this.dataTable);
        this.noRakBtn.setText("Nomor Rak");
        this.noRakBtn.setPreferredSize(new Dimension(100, 23));
        this.noRakBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPlanogram.this.noRakBtnActionPerformed(evt);
            }
        });
        GroupLayout jDesktopPane1Layout = new GroupLayout(this.jDesktopPane1);
        this.jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(jDesktopPane1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
        jDesktopPane1Layout.setVerticalGroup(jDesktopPane1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 780, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addGroup(layout.createSequentialGroup().addComponent(this.noRakBtn, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.rakField, -2, 124, -2).addGap(70, 70, 70).addComponent(this.rakBtn)).addGroup(layout.createSequentialGroup().addComponent(this.cetakBtn, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.bersihkanBtn, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keluarBtn, -2, -1, -2))).addGap(0, 375, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.rakField, -2, -1, -2).addComponent(this.rakBtn).addComponent(this.noRakBtn, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.cetakBtn, -2, -1, -2).addComponent(this.bersihkanBtn, -2, -1, -2).addComponent(this.keluarBtn, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jScrollPane1, -1, 453, Short.MAX_VALUE).addGap(46, 46, 46)));
    }

    private void keluarBtnActionPerformed(ActionEvent evt) {
        this.listener.exitClick();
    }

    private void bersihkanBtnActionPerformed(ActionEvent evt) {
        this.resetData();
    }

    private void rakBtnActionPerformed(ActionEvent evt) {
        if (this.rakField.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Masukkan Nama Rak Terlebih Dahulu");
        } else {
            List<Barang> barangs = this.barangService.getBarangsByRak(this.rakField.getText());
            for (Barang barang : barangs) {
                Object[] rowValue;
                String rak = barang.getRakIdToko();
                String[] detailRak = rak.split("-");
                rowValue = new Object[]{detailRak[0], detailRak[1], detailRak[2], detailRak[3], detailRak[4], detailRak[5], detailRak[6], barang.getStokToko() <= 0.0f ? "x" : "v", barang.getId(), barang.getNama(), detailRak[7], detailRak[8], detailRak[9], Integer.parseInt(detailRak[7] + "") * Integer.parseInt(detailRak[8] + "") * Integer.parseInt(detailRak[9] + ""), Integer.parseInt(rowValue[13] + "") * 70 / 100, barang.getTag()};
                this.dataTableModel.addRow(rowValue);
            }
        }
    }

    private void cetakBtnActionPerformed(ActionEvent evt) {
        try {
            XSSFWorkbook wb = new XSSFWorkbook();
            CreationHelper createhelper = wb.getCreationHelper();
            Sheet sheet = wb.createSheet("new sheet");
            Row row = null;
            Cell cell = null;
            row = sheet.createRow(0);
            cell = row.createCell(0);
            cell.setCellValue("AREA");
            cell = row.createCell(1);
            cell.setCellValue("RAK");
            cell = row.createCell(2);
            cell.setCellValue("SHELV");
            cell = row.createCell(3);
            cell.setCellValue("GAP");
            cell = row.createCell(4);
            cell.setCellValue("SAT");
            cell = row.createCell(5);
            cell.setCellValue("POSISI");
            cell = row.createCell(6);
            cell.setCellValue("URUTAN");
            cell = row.createCell(7);
            cell.setCellValue("OOS");
            cell = row.createCell(8);
            cell.setCellValue("SKU");
            cell = row.createCell(9);
            cell.setCellValue("NAMA BARANG");
            cell = row.createCell(10);
            cell.setCellValue("KK");
            cell = row.createCell(11);
            cell.setCellValue("AB");
            cell = row.createCell(12);
            cell.setCellValue("DB");
            cell = row.createCell(13);
            cell.setCellValue("MAX");
            cell = row.createCell(14);
            cell.setCellValue("MIN");
            cell = row.createCell(15);
            cell.setCellValue("TAG");
            for (int i = 0; i < this.dataTableModel.getRowCount(); ++i) {
                row = sheet.createRow(i + 1);
                for (int j = 0; j < this.dataTableModel.getColumnCount(); ++j) {
                    cell = row.createCell(j);
                    if (this.dataTableModel.getValueAt(i, j) == null) {
                        cell.setCellValue("");
                        continue;
                    }
                    cell.setCellValue(this.dataTableModel.getValueAt(i, j) + "");
                }
            }
            String url = "docLabel/Planogram.xlsx";
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

    private void noRakBtnActionPerformed(ActionEvent evt) {
        this.rakField.setEnabled(true);
        this.rakBtn.setEnabled(true);
    }

    private void resetData() {
        int countRow = this.dataTableModel.getRowCount();
        for (int a = 0; a < countRow; ++a) {
            this.dataTableModel.removeRow(0);
        }
    }

    private void loadKodeBarang(String idBarang) {
        this.barang = this.barangService.getBarang(idBarang);
        if (this.barang != null) {
            Object[] rowValue = new Object[15];
            rowValue[0] = this.barang.getId();
            rowValue[1] = this.barang.getNama();
            rowValue[2] = this.barang.getHargaJual1();
            rowValue[3] = this.barang.getSatuan1();
            rowValue[4] = this.barang.getHargaJual2() > 0L ? Long.valueOf(this.barang.getHargaJual2()) : "";
            rowValue[5] = this.barang.getSatuan2();
            rowValue[6] = this.barang.getHargaJual3() > 0L ? Long.valueOf(this.barang.getHargaJual3()) : "";
            rowValue[7] = this.barang.getSatuan3();
            long resultValue = this.helper.roundPrice100((long)(this.barang.getMinPersen() / 100.0f * (float)this.barang.getHargaJual1()) + this.barang.getHargaJual1());
            rowValue[8] = resultValue;
            rowValue[9] = (long)this.barang.getMinPembelian();
            rowValue[10] = this.barang.getRakIdToko();
            try {
                rowValue[11] = this.barang.getTag();
            }
            catch (Exception e) {
                rowValue[11] = "";
            }
            rowValue[12] = this.barang.getExpDay();
            rowValue[13] = "";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String printDate = simpleDateFormat.format(new Date());
            rowValue[14] = printDate;
            this.dataTableModel.addRow(rowValue);
        } else if (this.barang == null) {
            JOptionPane.showMessageDialog(null, "Barang Tidak Ditemukan");
        }
    }

    private class searchPanelBarangEventHandler
    implements SearchPanelListener {
        private searchPanelBarangEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            ReportPlanogram.this.loadKodeBarang(chooseValue);
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
            List<Barang> barangs = ReportPlanogram.this.barangService.getBarangs("", searchWord, false, "", true);
            for (Barang barang : barangs) {
                Object[] temp = new String[]{barang.getId(), barang.getNama(), barang.getSatuan1(), barang.getJenis()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Barang");
            sp.addSearchPanelListener(new searchPanelBarangEventHandler());
            sp.setVisible(true);
        }
    }
}

