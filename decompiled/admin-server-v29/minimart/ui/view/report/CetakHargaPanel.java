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
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.model.Jenis;
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

public class CetakHargaPanel
extends JPanel {
    private JTextField barangField;
    private JButton bersihkanBtn;
    private JButton cetakBtn;
    private JTable dataTable;
    private JDateChooser endDateField;
    private JToggleButton historyBtn;
    private JToggleButton idBarangBtn;
    private JDesktopPane jDesktopPane1;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel5;
    private JScrollPane jScrollPane1;
    private JSplitPane jSplitPane1;
    private JToggleButton jenisBtn;
    private JTextField jenisField;
    private JButton keluarBtn;
    private JToggleButton noRakBtn;
    private JToggleButton promoHematBtn;
    private JButton promoHematDataBtn;
    private JButton rakBtn;
    private JTextField rakField;
    private JDateChooser startDateField;
    private JButton tampilkanHistoryBtn;
    private JButton tampilkanJenisBtn;
    private String[] dataColumn = new String[]{"ID Barang", "Nama Barang", "Harga1", "Satuan1", "Harga2", "Satuan2", "Harga3", "Satuan3", "Harga Ecer", "Minimum Grosir", "Rak", "TAG", "Exp Day", "Tanggal Promo", "Tanggal Cetak"};
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

    public CetakHargaPanel() {
        this.initComponents();
        this.jScrollPane1.setHorizontalScrollBarPolicy(30);
        this.dataTable.setAutoResizeMode(0);
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.startDateField.setDate(new Date());
        this.endDateField.setDate(new Date());
        this.buttonGroup.add(this.noRakBtn);
        this.buttonGroup.add(this.idBarangBtn);
        this.buttonGroup.add(this.historyBtn);
        this.buttonGroup.add(this.jenisBtn);
        this.buttonGroup.add(this.promoHematBtn);
        this.noRakBtn.setSelected(true);
        this.startDateField.setEnabled(false);
        this.endDateField.setEnabled(false);
        this.tampilkanHistoryBtn.setEnabled(false);
        this.barangField.setEnabled(false);
        this.jenisField.setEnabled(false);
        this.tampilkanJenisBtn.setEnabled(false);
        this.rakField.setEnabled(true);
        this.rakBtn.setEnabled(true);
        this.promoHematDataBtn.setEnabled(false);
        this.dataTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CetakHargaPanel.this.dataTableKeyPressed(evt);
            }
        });
    }

    public void addListener(DefaultPanelListener listener) {
        this.listener = listener;
    }

    private void initComponents() {
        this.jSplitPane1 = new JSplitPane();
        this.jLabel1 = new JLabel();
        this.barangField = new JTextField();
        this.rakField = new JTextField();
        this.cetakBtn = new JButton();
        this.bersihkanBtn = new JButton();
        this.keluarBtn = new JButton();
        this.jLabel5 = new JLabel();
        this.rakBtn = new JButton();
        this.jScrollPane1 = new JScrollPane();
        this.dataTable = new JTable();
        this.noRakBtn = new JToggleButton();
        this.idBarangBtn = new JToggleButton();
        this.historyBtn = new JToggleButton();
        this.startDateField = new JDateChooser();
        this.endDateField = new JDateChooser();
        this.tampilkanHistoryBtn = new JButton();
        this.jDesktopPane1 = new JDesktopPane();
        this.jenisBtn = new JToggleButton();
        this.jenisField = new JTextField();
        this.tampilkanJenisBtn = new JButton();
        this.jLabel2 = new JLabel();
        this.promoHematBtn = new JToggleButton();
        this.promoHematDataBtn = new JButton();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Inputkan Data Barang Yang Akan Dicetak");
        this.barangField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CetakHargaPanel.this.barangFieldKeyPressed(evt);
            }
        });
        this.cetakBtn.setText("Cetak Excel");
        this.cetakBtn.setPreferredSize(new Dimension(107, 25));
        this.cetakBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CetakHargaPanel.this.cetakBtnActionPerformed(evt);
            }
        });
        this.bersihkanBtn.setText("Bersihkan Tabel");
        this.bersihkanBtn.setMaximumSize(new Dimension(120, 23));
        this.bersihkanBtn.setPreferredSize(new Dimension(107, 25));
        this.bersihkanBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CetakHargaPanel.this.bersihkanBtnActionPerformed(evt);
            }
        });
        this.keluarBtn.setText("Keluar");
        this.keluarBtn.setPreferredSize(new Dimension(107, 25));
        this.keluarBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CetakHargaPanel.this.keluarBtnActionPerformed(evt);
            }
        });
        this.jLabel5.setForeground(new Color(255, 51, 0));
        this.jLabel5.setText("Tekan F2: Pencarian Data");
        this.rakBtn.setText("Masukkan Data");
        this.rakBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CetakHargaPanel.this.rakBtnActionPerformed(evt);
            }
        });
        this.dataTable.setModel(this.dataTableModel);
        this.jScrollPane1.setViewportView(this.dataTable);
        this.noRakBtn.setText("Nomor Rak");
        this.noRakBtn.setPreferredSize(new Dimension(100, 23));
        this.noRakBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CetakHargaPanel.this.noRakBtnActionPerformed(evt);
            }
        });
        this.idBarangBtn.setSelected(true);
        this.idBarangBtn.setText("ID Barang");
        this.idBarangBtn.setPreferredSize(new Dimension(100, 23));
        this.idBarangBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CetakHargaPanel.this.idBarangBtnActionPerformed(evt);
            }
        });
        this.historyBtn.setText("History harga");
        this.historyBtn.setPreferredSize(new Dimension(100, 23));
        this.historyBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CetakHargaPanel.this.historyBtnActionPerformed(evt);
            }
        });
        this.tampilkanHistoryBtn.setText("Tampilkan Data");
        this.tampilkanHistoryBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CetakHargaPanel.this.tampilkanHistoryBtnActionPerformed(evt);
            }
        });
        GroupLayout jDesktopPane1Layout = new GroupLayout(this.jDesktopPane1);
        this.jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(jDesktopPane1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
        jDesktopPane1Layout.setVerticalGroup(jDesktopPane1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
        this.jenisBtn.setText("Jenis Barang");
        this.jenisBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CetakHargaPanel.this.jenisBtnActionPerformed(evt);
            }
        });
        this.jenisField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CetakHargaPanel.this.jenisFieldKeyPressed(evt);
            }
        });
        this.tampilkanJenisBtn.setText("Tampilkan Data");
        this.tampilkanJenisBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CetakHargaPanel.this.tampilkanJenisBtnActionPerformed(evt);
            }
        });
        this.jLabel2.setForeground(new Color(255, 0, 0));
        this.jLabel2.setText("Tekan DELETE: Hapus List Barang");
        this.promoHematBtn.setText("Promo Hemat");
        this.promoHematBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CetakHargaPanel.this.promoHematBtnActionPerformed(evt);
            }
        });
        this.promoHematDataBtn.setText("Tampilkan Data");
        this.promoHematDataBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CetakHargaPanel.this.promoHematDataBtnActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 780, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.idBarangBtn, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.barangField, -2, 124, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel5)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.noRakBtn, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.rakField, -2, 124, -2)).addGroup(layout.createSequentialGroup().addComponent(this.historyBtn, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent((Component)this.startDateField, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent((Component)this.endDateField, -2, -1, -2))).addGap(6, 6, 6).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.promoHematDataBtn, -1, -1, Short.MAX_VALUE).addComponent(this.rakBtn, -1, -1, Short.MAX_VALUE).addComponent(this.tampilkanHistoryBtn, -1, -1, Short.MAX_VALUE).addComponent(this.tampilkanJenisBtn, -1, -1, Short.MAX_VALUE)))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.promoHematBtn, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE).addComponent(this.jenisBtn, GroupLayout.Alignment.LEADING, -1, 100, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jenisField, -2, 124, -2)).addGroup(layout.createSequentialGroup().addComponent(this.cetakBtn, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.bersihkanBtn, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keluarBtn, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel2))).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.rakField, -2, -1, -2).addComponent(this.rakBtn).addComponent(this.noRakBtn, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.historyBtn, -2, -1, -2).addComponent((Component)this.startDateField, -2, -1, -2).addComponent(this.tampilkanHistoryBtn).addComponent((Component)this.endDateField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jenisBtn).addComponent(this.jenisField, -2, -1, -2).addComponent(this.tampilkanJenisBtn)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.promoHematBtn).addComponent(this.promoHematDataBtn)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel5).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.barangField, -2, -1, -2).addComponent(this.idBarangBtn, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.cetakBtn, -2, -1, -2).addComponent(this.bersihkanBtn, -2, -1, -2).addComponent(this.keluarBtn, -2, -1, -2).addComponent(this.jLabel2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 319, -2).addGap(62, 62, 62)));
    }

    private void barangFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            this.sd = new SearchDialog(null, true, "Pencarian Data Barang");
            this.sd.addSearchDialogListener(new searchBarangDialogEventHandler());
            int w = this.sd.getWidth();
            int h = this.sd.getHeight();
            this.sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            this.sd.setVisible(true);
        } else if (evt.getKeyCode() == 10) {
            Barang barang = this.barangService.getBarang(this.barangField.getText());
            if (barang == null) {
                JOptionPane.showMessageDialog(null, "Barang Tidak Ditemukan");
                this.barangField.selectAll();
            } else {
                JOptionPane.showMessageDialog(null, "Barang Berhasil Diinput");
                this.loadKodeBarang(this.barangField.getText());
                this.barangField.selectAll();
            }
        }
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
                Object[] rowValue = new Object[15];
                rowValue[0] = barang.getId();
                rowValue[1] = barang.getNama();
                rowValue[2] = barang.getHargaJual1();
                rowValue[3] = barang.getSatuan1();
                rowValue[4] = barang.getHargaJual2() > 0L ? Long.valueOf(barang.getHargaJual2()) : "";
                rowValue[5] = barang.getSatuan2();
                rowValue[6] = barang.getHargaJual3() > 0L ? Long.valueOf(barang.getHargaJual3()) : "";
                rowValue[7] = barang.getSatuan3();
                long resultValue = this.helper.roundPrice100((long)(barang.getMinPersen() / 100.0f * (float)barang.getHargaJual1()) + barang.getHargaJual1());
                rowValue[8] = resultValue;
                rowValue[9] = (long)barang.getMinPembelian();
                rowValue[10] = barang.getRakIdToko();
                rowValue[11] = barang.getTag();
                rowValue[12] = barang.getExpDay();
                rowValue[13] = "";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String printDate = simpleDateFormat.format(new Date());
                rowValue[14] = printDate;
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
            cell.setCellValue("IdBarang");
            cell = row.createCell(1);
            cell.setCellValue("NamaBarang");
            cell = row.createCell(2);
            cell.setCellValue("Harga1");
            cell = row.createCell(3);
            cell.setCellValue("Satuan1");
            cell = row.createCell(4);
            cell.setCellValue("Harga2");
            cell = row.createCell(5);
            cell.setCellValue("Satuan2");
            cell = row.createCell(6);
            cell.setCellValue("Harga3");
            cell = row.createCell(7);
            cell.setCellValue("Satuan3");
            cell = row.createCell(8);
            cell.setCellValue("HargaGrosir");
            cell = row.createCell(9);
            cell.setCellValue("MinimumGrosir");
            cell = row.createCell(10);
            cell.setCellValue("Rak");
            cell = row.createCell(11);
            cell.setCellValue("TAG");
            cell = row.createCell(12);
            cell.setCellValue("ExpDay");
            cell = row.createCell(13);
            cell.setCellValue("Tanggal Promo");
            cell = row.createCell(14);
            cell.setCellValue("Tanggal Cetak");
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
            String url = "docLabel/LabelHarga.xlsx";
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
        this.startDateField.setEnabled(false);
        this.endDateField.setEnabled(false);
        this.tampilkanHistoryBtn.setEnabled(false);
        this.barangField.setEnabled(false);
        this.jenisField.setEnabled(false);
        this.tampilkanJenisBtn.setEnabled(false);
        this.rakField.setEnabled(true);
        this.rakBtn.setEnabled(true);
        this.promoHematDataBtn.setEnabled(false);
    }

    private void historyBtnActionPerformed(ActionEvent evt) {
        this.promoHematDataBtn.setEnabled(false);
        this.rakField.setEnabled(false);
        this.rakBtn.setEnabled(false);
        this.jenisField.setEnabled(false);
        this.tampilkanJenisBtn.setEnabled(false);
        this.startDateField.setEnabled(true);
        this.endDateField.setEnabled(true);
        this.tampilkanHistoryBtn.setEnabled(true);
        this.barangField.setEnabled(false);
    }

    private void idBarangBtnActionPerformed(ActionEvent evt) {
        this.rakField.setEnabled(false);
        this.rakBtn.setEnabled(false);
        this.tampilkanJenisBtn.setEnabled(false);
        this.startDateField.setEnabled(false);
        this.endDateField.setEnabled(false);
        this.tampilkanHistoryBtn.setEnabled(false);
        this.barangField.setEnabled(true);
        this.jenisField.setEnabled(false);
        this.promoHematDataBtn.setEnabled(false);
    }

    private void tampilkanHistoryBtnActionPerformed(ActionEvent evt) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = simpleDateFormat.format(this.startDateField.getDate());
        String endDateString = simpleDateFormat.format(this.endDateField.getDate());
        List<Object[]> datas = this.historyHargaService.getIdHistoryHarga(startDateString, endDateString);
        for (Object[] data : datas) {
            this.dataTableModel.addRow(data);
        }
    }

    private void jenisFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            this.sd = new SearchDialog(null, true, "Pencarian Data Jenis Barang");
            this.sd.addSearchDialogListener(new searchJenisDialogEventHandler());
            int w = this.sd.getWidth();
            int h = this.sd.getHeight();
            this.sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            this.sd.setVisible(true);
        } else if (evt.getKeyCode() == 10) {
            Barang barang = this.barangService.getBarang(this.barangField.getText());
            if (barang == null) {
                JOptionPane.showMessageDialog(null, "Barang Tidak Ditemukan");
                this.barangField.selectAll();
            } else {
                JOptionPane.showMessageDialog(null, "Barang Berhasil Diinput");
                this.loadKodeBarang(this.barangField.getText());
                this.barangField.selectAll();
            }
        }
    }

    private void tampilkanJenisBtnActionPerformed(ActionEvent evt) {
        List<Object[]> datas = this.barangService.geDataLabelByJenis(this.jenisField.getText());
        for (Object[] data : datas) {
            this.dataTableModel.addRow(data);
        }
    }

    private void jenisBtnActionPerformed(ActionEvent evt) {
        this.promoHematDataBtn.setEnabled(false);
        this.rakField.setEnabled(false);
        this.rakBtn.setEnabled(false);
        this.startDateField.setEnabled(false);
        this.endDateField.setEnabled(false);
        this.tampilkanHistoryBtn.setEnabled(false);
        this.barangField.setEnabled(false);
        this.jenisField.setEnabled(true);
        this.tampilkanJenisBtn.setEnabled(true);
    }

    private void promoHematBtnActionPerformed(ActionEvent evt) {
        this.rakField.setEnabled(false);
        this.rakBtn.setEnabled(false);
        this.tampilkanJenisBtn.setEnabled(false);
        this.startDateField.setEnabled(false);
        this.endDateField.setEnabled(false);
        this.tampilkanHistoryBtn.setEnabled(false);
        this.barangField.setEnabled(false);
        this.jenisField.setEnabled(false);
        this.promoHematDataBtn.setEnabled(true);
    }

    private void promoHematDataBtnActionPerformed(ActionEvent evt) {
        List<Object[]> datas = this.promoHematDetailService.getAvailablePromo();
        for (Object[] data : datas) {
            this.dataTableModel.addRow(data);
        }
    }

    private void dataTableKeyPressed(KeyEvent evt) {
        int a;
        if (evt.getKeyCode() == 127 && this.dataTable.getSelectedRow() != -1 && (a = JOptionPane.showConfirmDialog(null, "Hapus Data Ini?", "Delete Data", 0, 3)) == 0) {
            this.dataTableModel.removeRow(this.dataTable.getSelectedRow());
        }
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

    private class searchPanelJenisEventHandler
    implements SearchPanelListener {
        private searchPanelJenisEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            CetakHargaPanel.this.jenisField.setText(chooseValue);
        }
    }

    private class searchJenisDialogEventHandler
    implements SearchDialogListener {
        private searchJenisDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"JENIS BARANG"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Jenis> jenisses = CetakHargaPanel.this.jenisService.getJenisses(searchWord);
            for (Jenis jenis : jenisses) {
                Object[] temp = new Object[]{jenis.getJenis()};
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

    private class searchPanelBarangEventHandler
    implements SearchPanelListener {
        private searchPanelBarangEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            CetakHargaPanel.this.loadKodeBarang(chooseValue);
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
            List<Barang> barangs = CetakHargaPanel.this.barangService.getBarangs("", searchWord, false, "", true);
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

