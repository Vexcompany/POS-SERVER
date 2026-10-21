/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.opencsv.CSVReader
 */
package minimart.ui.view;

import com.opencsv.CSVReader;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.model.Barang;
import minimart.engine.model.Barcode;
import minimart.engine.model.StokOpnamCSV;
import minimart.engine.service.BarangService;
import minimart.engine.service.BarcodeService;
import minimart.engine.service.StokOpnamCsvService;
import minimart.ui.listener.ConfirmationDialogListener;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.view.ConfirmationDialog;
import minimart.ui.view.StokOpnamCsvDialog;

public class StokOpnamCSVPanel
extends JPanel {
    private JButton cariBtn;
    private JLabel jLabel1;
    private JScrollPane jScrollPane1;
    private JTable jTable1;
    private JButton keluarBtn;
    private JTextField locationFileTxt;
    private JButton simpanBtn;
    private JButton tampilkanBtn;
    private DefaultPanelListener defaultPanelListener;
    private String[] stokOpnamTMColumn = new String[]{"Barcode", "ID Barang", "Nama Barang", "Penanggung Jawab", "Lokasi", "Stok", "Satuan"};
    private DefaultTableModel stokOpnamTM = new DefaultTableModel(new String[0][1], this.stokOpnamTMColumn);
    private BarangService barangService = new BarangService();
    private BarcodeService barcodeService = new BarcodeService();
    private StokOpnamCsvService csvService = new StokOpnamCsvService();
    private int screenWidth = 0;
    private int screenHeight = 0;
    private boolean canGoThrough;
    List<Object[]> nullData;

    public StokOpnamCSVPanel() throws Exception {
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    }

    private void openCSVFile() throws Exception {
        CSVReader reader = new CSVReader((Reader)new FileReader(this.locationFileTxt.getText()));
        List myEntries = reader.readAll();
        reader.close();
        this.nullData = new LinkedList<Object[]>();
        for (String[] entry : myEntries) {
            if (entry[0].equalsIgnoreCase("Barcode") || entry.length != 7) continue;
            Barang tempBarang = this.barangService.getBarang(entry[0]);
            Object[] rowValue = new Object[7];
            rowValue[0] = entry[0];
            if (tempBarang != null) {
                rowValue[1] = tempBarang.getId();
                rowValue[2] = tempBarang.getNama();
            } else {
                rowValue[1] = "Tidak Ditemukan";
                rowValue[2] = "Tidak Ditemukan";
            }
            rowValue[3] = entry[2];
            rowValue[4] = entry[3];
            rowValue[5] = entry[4];
            Barcode tempBarcode = this.barcodeService.getBarcode(entry[0]);
            if (tempBarcode != null) {
                if (tempBarcode.getSatuan().equals(tempBarang.getSatuan1())) {
                    rowValue[5] = entry[4];
                } else if (tempBarcode.getSatuan().equals(tempBarang.getSatuan2())) {
                    rowValue[5] = Integer.parseInt(entry[4]) * tempBarang.getKonversi21();
                } else if (tempBarcode.getSatuan().equals(tempBarang.getSatuan3())) {
                    rowValue[5] = Integer.parseInt(entry[4]) * tempBarang.getKonversi21() * tempBarang.getKonversi32();
                }
                rowValue[6] = tempBarang.getSatuan1();
            }
            if (tempBarang != null) {
                this.stokOpnamTM.addRow(rowValue);
                continue;
            }
            if (tempBarang != null) continue;
            this.nullData.add(rowValue);
        }
        for (int a = 0; a < this.nullData.size(); ++a) {
            this.stokOpnamTM.addRow(this.nullData.get(a));
        }
    }

    public void addListener(DefaultPanelListener listener) {
        this.defaultPanelListener = listener;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.jTable1 = new JTable();
        this.locationFileTxt = new JTextField();
        this.cariBtn = new JButton();
        this.tampilkanBtn = new JButton();
        this.simpanBtn = new JButton();
        this.keluarBtn = new JButton();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Import CSV File");
        this.jTable1.setModel(this.stokOpnamTM);
        this.jScrollPane1.setViewportView(this.jTable1);
        this.cariBtn.setText("Cari");
        this.cariBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StokOpnamCSVPanel.this.cariBtnActionPerformed(evt);
            }
        });
        this.tampilkanBtn.setText("Tampilkan");
        this.tampilkanBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StokOpnamCSVPanel.this.tampilkanBtnActionPerformed(evt);
            }
        });
        this.simpanBtn.setText("Rekap");
        this.simpanBtn.setPreferredSize(new Dimension(73, 23));
        this.simpanBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StokOpnamCSVPanel.this.simpanBtnActionPerformed(evt);
            }
        });
        this.keluarBtn.setText("Keluar");
        this.keluarBtn.setPreferredSize(new Dimension(73, 23));
        this.keluarBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StokOpnamCSVPanel.this.keluarBtnActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.locationFileTxt, -2, 194, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cariBtn, -2, 73, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.tampilkanBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.simpanBtn, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 187, Short.MAX_VALUE).addComponent(this.keluarBtn, -2, -1, -2)).addComponent(this.jScrollPane1)).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.locationFileTxt).addComponent(this.cariBtn).addComponent(this.tampilkanBtn).addComponent(this.simpanBtn, -2, -1, -2).addComponent(this.keluarBtn, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jScrollPane1, -1, 544, Short.MAX_VALUE).addContainerGap()));
    }

    private void cariBtnActionPerformed(ActionEvent evt) {
        JFileChooser jfc = new JFileChooser();
        jfc.showOpenDialog(null);
        File file = jfc.getSelectedFile();
        String dir = file.getAbsolutePath();
        this.locationFileTxt.setText(dir);
    }

    private void tampilkanBtnActionPerformed(ActionEvent evt) {
        this.clearTable();
        try {
            this.openCSVFile();
        }
        catch (Exception ex) {
            Logger.getLogger(StokOpnamCSVPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void keluarBtnActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }

    private void simpanBtnActionPerformed(ActionEvent evt) {
        this.canGoThrough = true;
        if (!this.nullData.isEmpty()) {
            this.canGoThrough = false;
            ConfirmationDialog confirmationDialog = new ConfirmationDialog(null, true, "Masih ada data barcode yang belum ditemukan, Teruskan?");
            confirmationDialog.addListener(new confirmDialogEventHandler());
            int w = confirmationDialog.getWidth();
            int h = confirmationDialog.getHeight();
            confirmationDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            confirmationDialog.setVisible(true);
        }
        if (this.canGoThrough) {
            int result = 0;
            this.csvService.deleteStokOpnamCSV();
            for (int a = 0; a < this.stokOpnamTM.getRowCount(); ++a) {
                StokOpnamCSV cSV = new StokOpnamCSV();
                cSV.setBarcode(this.stokOpnamTM.getValueAt(a, 0).toString());
                cSV.setBarangId(this.stokOpnamTM.getValueAt(a, 1).toString());
                cSV.setPenanggungJawab(this.stokOpnamTM.getValueAt(a, 3).toString());
                cSV.setLokasi(this.stokOpnamTM.getValueAt(a, 4).toString());
                cSV.setStok(Float.parseFloat(this.stokOpnamTM.getValueAt(a, 5).toString()));
                result = this.csvService.createStokOpnamCSV(cSV);
            }
            if (result == 0) {
                JOptionPane.showMessageDialog(null, "Penyimpanan Data Gagal");
            }
            StokOpnamCsvDialog tabelBarang = new StokOpnamCsvDialog(null, true);
            int wT = tabelBarang.getWidth();
            int hT = tabelBarang.getHeight();
            tabelBarang.setLocation((this.screenWidth - wT) / 2, (this.screenHeight - hT) / 2);
            tabelBarang.setVisible(true);
        }
    }

    private void clearTable() {
        int size = this.stokOpnamTM.getRowCount();
        for (int a = 0; a < size; ++a) {
            this.stokOpnamTM.removeRow(0);
        }
    }

    private class confirmDialogEventHandler
    implements ConfirmationDialogListener {
        private confirmDialogEventHandler() {
        }

        @Override
        public void yesClick() {
            StokOpnamCSVPanel.this.canGoThrough = true;
        }

        @Override
        public void noClick() {
            StokOpnamCSVPanel.this.canGoThrough = false;
        }
    }
}

