/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.xssf.usermodel.XSSFSheet
 *  org.apache.poi.xssf.usermodel.XSSFWorkbook
 */
package minimart.ui.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.model.Barang;
import minimart.engine.restengine.CommonEngine;
import minimart.engine.service.BarangService;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.view.StokOpnamPanel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class UpdateBarangPanel
extends JPanel {
    private JTable dataBarangTbl;
    private JTextField fileNameField;
    private JButton importBtn;
    private JScrollPane jScrollPane2;
    private JButton keluarBtn;
    private JButton updateBtn;
    private String[] barangTMColumn = new String[]{"ID Barang", "Nama Barang", "Rak Toko", "Rak Gudang", "Jenis", "Satuan1", "Satuan2", "Satuan3", "Konversi21", "Konversi32", "Harga Jual1", "Harga Jual2", "Harga Jual3", "Grosir Minimum", "Persentase Harga Ecer", "Batas Atas Stok", "Reorder Poin"};
    private DefaultTableModel barangTM = new DefaultTableModel(new String[0][1], this.barangTMColumn);
    private DefaultPanelListener defaultPanelListener;
    private BarangService barangService = new BarangService();
    private CommonEngine comm = new CommonEngine("https://admin.sinarmart.id");
    private ObjectMapper mapper = new ObjectMapper();

    public UpdateBarangPanel() {
        this.initComponents();
        this.dataBarangTbl.setAutoResizeMode(0);
    }

    private void initComponents() {
        this.importBtn = new JButton();
        this.fileNameField = new JTextField();
        this.keluarBtn = new JButton();
        this.jScrollPane2 = new JScrollPane();
        this.dataBarangTbl = new JTable();
        this.updateBtn = new JButton();
        this.setPreferredSize(new Dimension(800, 600));
        this.importBtn.setText("Import Excel");
        this.importBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                UpdateBarangPanel.this.importBtnActionPerformed(evt);
            }
        });
        this.keluarBtn.setText("Keluar");
        this.keluarBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                UpdateBarangPanel.this.keluarBtnActionPerformed(evt);
            }
        });
        this.dataBarangTbl.setModel(this.barangTM);
        this.jScrollPane2.setViewportView(this.dataBarangTbl);
        this.updateBtn.setText("Update");
        this.updateBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                UpdateBarangPanel.this.updateBtnActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane2).addGroup(layout.createSequentialGroup().addComponent(this.importBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.fileNameField, -2, 287, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 243, Short.MAX_VALUE).addComponent(this.updateBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keluarBtn, -2, 72, -2))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.importBtn).addComponent(this.fileNameField, -2, -1, -2).addComponent(this.keluarBtn).addComponent(this.updateBtn)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane2, -1, 549, Short.MAX_VALUE).addContainerGap()));
    }

    private void importBtnActionPerformed(ActionEvent evt) {
        JFileChooser jfc = new JFileChooser();
        jfc.showOpenDialog(null);
        File file = jfc.getSelectedFile();
        this.fileNameField.setText(file.getName());
        try {
            this.clearTable();
            FileInputStream fir = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook((InputStream)fir);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator rowIt = sheet.iterator();
            rowIt.next();
            while (rowIt.hasNext()) {
                Row row = (Row)rowIt.next();
                Iterator cellIterator = row.cellIterator();
                int a = 0;
                Object[] rowValue = new Object[17];
                while (cellIterator.hasNext()) {
                    Cell cell = (Cell)cellIterator.next();
                    rowValue[a] = cell.toString();
                    ++a;
                }
                this.barangTM.addRow(rowValue);
            }
            workbook.close();
            fir.close();
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(StokOpnamPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(StokOpnamPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void keluarBtnActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }

    private void updateBtnActionPerformed(ActionEvent evt) {
        int result = 0;
        for (int a = 0; a < this.barangTM.getRowCount(); ++a) {
            Barang barang = this.barangService.getBarang(this.barangTM.getValueAt(a, 0) + "");
            barang.setNama(this.barangTM.getValueAt(a, 1) + "");
            barang.setRakIdToko(this.barangTM.getValueAt(a, 2) + "");
            barang.setRakIdGudang(this.barangTM.getValueAt(a, 3) + "");
            barang.setJenis(this.barangTM.getValueAt(a, 4) + "");
            barang.setSatuan1(this.barangTM.getValueAt(a, 5) + "");
            barang.setSatuan2(this.barangTM.getValueAt(a, 6) + "");
            barang.setSatuan3(this.barangTM.getValueAt(a, 7) + "");
            barang.setKonversi21((int)Float.parseFloat(this.barangTM.getValueAt(a, 8) + ""));
            barang.setKonversi32((int)Float.parseFloat(this.barangTM.getValueAt(a, 9) + ""));
            barang.setHargaJual1((long)Float.parseFloat(this.barangTM.getValueAt(a, 10) + ""));
            barang.setHargaJual2((long)Float.parseFloat(this.barangTM.getValueAt(a, 11) + ""));
            barang.setHargaJual3((long)Float.parseFloat(this.barangTM.getValueAt(a, 12) + ""));
            barang.setMinPembelian(Float.parseFloat(this.barangTM.getValueAt(a, 13) + ""));
            barang.setMinPersen(Float.parseFloat(this.barangTM.getValueAt(a, 14) + ""));
            barang.setBatasAtasStock(Float.parseFloat(this.barangTM.getValueAt(a, 15) + ""));
            barang.setReorderPoint(Float.parseFloat(this.barangTM.getValueAt(a, 16) + ""));
            result = this.barangService.updateBarang2(barang, barang.getId());
        }
        if (result == 1) {
            JOptionPane.showMessageDialog(null, "Penyimpanan Data Berhasil");
        }
    }

    public void addListener(DefaultPanelListener listener) {
        this.defaultPanelListener = listener;
    }

    private void clearTable() {
        int size = this.barangTM.getRowCount();
        for (int a = 0; a < size; ++a) {
            this.barangTM.removeRow(0);
        }
    }
}

