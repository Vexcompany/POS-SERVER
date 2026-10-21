/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.service.BarangService;
import minimart.engine.service.KonstantaService;
import minimart.engine.service.StokOpnamMandiriDetailService;
import minimart.ui.listener.SearchPanelListener;

public class SearchPanelBarang
extends JDialog {
    private JFormattedTextField grosirField;
    private JLabel grosirLbl;
    private JFormattedTextField harga1Field;
    private JFormattedTextField harga2Field;
    private JFormattedTextField harga3Field;
    private JLabel hargaLbl;
    private JScrollPane jScrollPane1;
    private JLabel labelSO;
    private JLabel minLbl;
    private JLabel sat1Lbl;
    private JLabel sat1StokLbl;
    private JLabel sat2Lbl;
    private JLabel sat2StokLbl;
    private JLabel sat3Lbl;
    private JLabel sat3StokLbl;
    private JTable searchTable;
    private JTextField stok1Field;
    private JTextField stok2Field;
    private JTextField stok3Field;
    private JLabel stokLbl;
    private DefaultTableModel dtm;
    private SearchPanelListener listener;
    private BarangService barangService = new BarangService();
    private Barang barang;
    private Helper helper = Helper.getInstance();
    private StokOpnamMandiriDetailService stokOpnamMandiriDetailService = new StokOpnamMandiriDetailService();
    private KonstantaService konstantaService = new KonstantaService();
    private static final int SOM_HIDE_N_DAYS = 1;

    public SearchPanelBarang(Frame parent, boolean modal, DefaultTableModel dtm, String title) {
        super(parent, modal);
        this.setTitle(title);
        this.initComponents();
        this.dtm = dtm;
        this.searchTable.setModel(dtm);
        this.sat1Lbl.setText("");
        this.sat2Lbl.setText("");
        this.sat3Lbl.setText("");
        this.sat1StokLbl.setText("");
        this.sat2StokLbl.setText("");
        this.sat3StokLbl.setText("");
        this.minLbl.setText("00.0");
        TableColumn col1 = this.searchTable.getColumnModel().getColumn(1);
        TableColumn col2 = this.searchTable.getColumnModel().getColumn(2);
        TableColumn col3 = this.searchTable.getColumnModel().getColumn(3);
        int width = 35;
        col1.setPreferredWidth(220);
        col2.setPreferredWidth(5);
        col3.setPreferredWidth(width);
    }

    public void addSearchPanelListener(SearchPanelListener listener) {
        this.listener = listener;
    }

    private void initComponents() {
        this.jScrollPane1 = new JScrollPane();
        this.searchTable = new JTable();
        this.hargaLbl = new JLabel();
        this.grosirLbl = new JLabel();
        this.sat1Lbl = new JLabel();
        this.minLbl = new JLabel();
        this.sat2Lbl = new JLabel();
        this.sat3Lbl = new JLabel();
        this.sat1StokLbl = new JLabel();
        this.sat2StokLbl = new JLabel();
        this.sat3StokLbl = new JLabel();
        this.stok1Field = new JTextField();
        this.stok2Field = new JTextField();
        this.stok3Field = new JTextField();
        this.stokLbl = new JLabel();
        this.harga1Field = new JFormattedTextField();
        this.harga2Field = new JFormattedTextField();
        this.harga3Field = new JFormattedTextField();
        this.grosirField = new JFormattedTextField();
        this.labelSO = new JLabel();
        this.setDefaultCloseOperation(2);
        this.searchTable.setModel(new DefaultTableModel(new Object[0][], new String[0]));
        this.searchTable.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                SearchPanelBarang.this.searchTableMouseClicked(evt);
            }
        });
        this.searchTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                SearchPanelBarang.this.searchTableKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.searchTable);
        this.hargaLbl.setFont(new Font("Tahoma", 0, 14));
        this.hargaLbl.setText("HARGA");
        this.grosirLbl.setFont(new Font("Tahoma", 0, 14));
        this.grosirLbl.setText("GROSIR");
        this.sat1Lbl.setFont(new Font("Tahoma", 0, 14));
        this.sat1Lbl.setText("SAT1");
        this.minLbl.setFont(new Font("Tahoma", 0, 14));
        this.minLbl.setText("MIN");
        this.sat2Lbl.setFont(new Font("Tahoma", 0, 14));
        this.sat2Lbl.setText("SAT2");
        this.sat3Lbl.setFont(new Font("Tahoma", 0, 14));
        this.sat3Lbl.setText("SAT3");
        this.sat1StokLbl.setText("SAT1");
        this.sat2StokLbl.setText("SAT2");
        this.sat3StokLbl.setText("SAT3");
        this.stok1Field.setEditable(false);
        this.stok1Field.setText("0");
        this.stok2Field.setEditable(false);
        this.stok2Field.setText("0");
        this.stok3Field.setEditable(false);
        this.stok3Field.setText("0");
        this.stokLbl.setText("STOK:");
        this.harga1Field.setText("0");
        this.harga1Field.setFont(new Font("Tahoma", 0, 14));
        this.harga2Field.setText("0");
        this.harga2Field.setFont(new Font("Tahoma", 0, 14));
        this.harga3Field.setText("0");
        this.harga3Field.setFont(new Font("Tahoma", 0, 14));
        this.grosirField.setText("0");
        this.grosirField.setFont(new Font("Tahoma", 0, 14));
        this.labelSO.setFont(new Font("Tahoma", 1, 18));
        this.labelSO.setForeground(new Color(255, 51, 0));
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 780, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.grosirLbl).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.minLbl)).addComponent(this.hargaLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.harga1Field).addComponent(this.grosirField, -1, 127, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.sat1Lbl).addGap(27, 27, 27).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.harga3Field).addComponent(this.harga2Field, -2, 123, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.sat2Lbl).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.stokLbl)).addComponent(this.sat3Lbl))).addGroup(layout.createSequentialGroup().addComponent(this.labelSO, -2, 437, -2).addGap(0, 0, Short.MAX_VALUE))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addGroup(layout.createSequentialGroup().addComponent(this.stok3Field).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.sat3StokLbl, -2, 66, -2)).addGroup(layout.createSequentialGroup().addComponent(this.stok1Field, -2, 48, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.sat1StokLbl, -2, 66, -2)).addGroup(layout.createSequentialGroup().addComponent(this.stok2Field, -2, 48, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.sat2StokLbl, -2, 66, -2))))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -2, 523, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.sat1StokLbl).addComponent(this.stok1Field, -2, -1, -2).addComponent(this.stokLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.sat2StokLbl).addComponent(this.stok2Field, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.sat3StokLbl).addComponent(this.stok3Field, -2, -1, -2)).addGap(0, 5, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.hargaLbl).addComponent(this.sat1Lbl).addComponent(this.harga1Field, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.grosirLbl).addComponent(this.minLbl))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.harga2Field, -2, -1, -2).addComponent(this.sat2Lbl)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.grosirField, -2, -1, -2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.harga3Field, -2, -1, -2).addComponent(this.sat3Lbl))))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.labelSO, -1, -1, Short.MAX_VALUE))).addContainerGap()));
        this.pack();
    }

    private void searchTableKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.listener.tableClick(this.dtm.getValueAt(this.searchTable.getSelectedRow(), 0).toString());
            this.dispose();
        } else if (evt.getKeyCode() == 40) {
            int row = 0;
            try {
                row = this.searchTable.getSelectedRow() + 1;
            }
            catch (Exception e) {
                row = 0;
            }
            this.loadField(row);
        } else if (evt.getKeyCode() == 38) {
            int row = 0;
            try {
                row = this.searchTable.getSelectedRow() - 1;
            }
            catch (Exception e) {
                row = 0;
            }
            this.loadField(row);
        }
        if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void searchTableMouseClicked(MouseEvent evt) {
        int row = this.searchTable.getSelectedRow();
        this.loadField(row);
    }

    private void loadField(int row) {
        this.barang = this.barangService.getBarang(this.dtm.getValueAt(row, 0).toString());
        this.sat1Lbl.setText(this.barang.getSatuan1());
        if (this.barang.getSatuan2().equals("")) {
            this.sat2Lbl.setText("");
            this.harga2Field.setValue(0);
        }
        if (this.barang.getSatuan3().equals("")) {
            this.sat3Lbl.setText("");
            this.harga3Field.setValue(0);
        }
        this.sat2Lbl.setText(this.barang.getSatuan2());
        this.sat3Lbl.setText(this.barang.getSatuan3());
        this.harga2Field.setValue(this.barang.getHargaJual2());
        this.harga3Field.setValue(this.barang.getHargaJual3());
        float stokTotal = 0.0f;
        String result = this.stokOpnamMandiriDetailService.getStokOpnamMandiriDetailOnProgress(this.barang.getId(), 1);
        String hide = this.konstantaService.getConstantValue("hideStock");
        String textHide = "Stok disembunyikan sedang SOM";
        if (hide.equals("YES")) {
            result = "YES";
            textHide = "Stok disembunyikan beberapa saat";
        }
        if (!result.equals("")) {
            this.labelSO.setText(textHide);
        } else {
            this.labelSO.setText("");
            stokTotal = this.barang.getStokToko();
        }
        float stok1 = 0.0f;
        float stok2 = 0.0f;
        float stok3 = 0.0f;
        if (this.barang.getSatuan2().equals("") && this.barang.getSatuan3().equals("")) {
            stok1 = stokTotal;
        } else if (this.barang.getSatuan3().equals("")) {
            stok2 = (long)stokTotal / (long)this.barang.getKonversi21();
            stok1 = this.helper.round(stokTotal % (float)this.barang.getKonversi21(), 2);
        } else {
            stok3 = (long)stokTotal / (long)(this.barang.getKonversi21() * this.barang.getKonversi32());
            stok2 = (long)(stokTotal % (float)(this.barang.getKonversi21() * this.barang.getKonversi32())) / (long)this.barang.getKonversi21();
            stok1 = this.helper.round(stokTotal % (float)(this.barang.getKonversi21() * this.barang.getKonversi32()) % (float)this.barang.getKonversi21(), 2);
        }
        this.stok1Field.setText(stok1 + "");
        this.stok2Field.setText((long)stok2 + "");
        this.stok3Field.setText((long)stok3 + "");
        this.sat1StokLbl.setText(this.barang.getSatuan1());
        this.sat2StokLbl.setText(this.barang.getSatuan2());
        this.sat3StokLbl.setText(this.barang.getSatuan3());
        if (this.barang.getMinPembelian() > 0.0f) {
            long resultValue = this.helper.roundPrice100((long)(this.barang.getMinPersen() / 100.0f * (float)this.barang.getHargaJual1()) + this.barang.getHargaJual1());
            this.harga1Field.setValue(resultValue);
            this.grosirField.setValue(this.barang.getHargaJual1());
            this.minLbl.setText(this.barang.getMinPembelian() + "");
        } else {
            this.harga1Field.setValue(this.barang.getHargaJual1());
            this.grosirField.setValue(0);
            this.minLbl.setText("00.0");
        }
    }
}

