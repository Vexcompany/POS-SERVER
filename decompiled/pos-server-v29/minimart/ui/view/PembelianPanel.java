/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.toedter.calendar.JDateChooser
 */
package minimart.ui.view;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.swing.table.TableColumn;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.model.BarangHpp;
import minimart.engine.model.Pembelian;
import minimart.engine.model.PembelianDetail;
import minimart.engine.model.PembelianSementara;
import minimart.engine.model.RBarangSupplier;
import minimart.engine.model.Supplier;
import minimart.engine.service.BarangService;
import minimart.engine.service.PembelianDetailService;
import minimart.engine.service.PembelianSementaraService;
import minimart.engine.service.PembelianService;
import minimart.engine.service.RBarangSupplierService;
import minimart.engine.service.SinkronisasiBarangService;
import minimart.engine.service.SupplierService;
import minimart.engine.service.SyncService;
import minimart.ui.listener.DiscountPembelianDialogListener;
import minimart.ui.listener.PembelianDetailDialogListener;
import minimart.ui.listener.PembelianPanelListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.DiscountPembelianDialog;
import minimart.ui.view.PembelianDetailDialog;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class PembelianPanel
extends JPanel {
    private JDateChooser dateField;
    private JButton detailButton;
    private JButton exitButton;
    private JComboBox<String> inputCmb;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JScrollPane jScrollPane1;
    private JDateChooser jatuhTempoField;
    private JComboBox pembayaranCmb;
    private JButton saveButton;
    private JLabel shortcutLbl;
    private JTextField supplierField;
    private JLabel supplierLbl;
    private JFormattedTextField totalField;
    private JTextField totalItemField;
    private JTable transactionTable;
    private String[] transactionColumn = new String[]{"Kode Barang", "Nama Barang", "Qty", "Sat", "Harga", "Total", "Disc", "Harga Akhir"};
    private DefaultTableModel transactionTableModel = new DefaultTableModel(new String[0][1], this.transactionColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private KeyboardFocusManager manager;
    private keyDispatcher keyDispatcher;
    private int screenWidth;
    private int screenHeight;
    private PembelianService pembelianService = new PembelianService();
    private PembelianDetailService pembelianDetailService = new PembelianDetailService();
    private SupplierService supplierService = new SupplierService();
    private Supplier supplier = new Supplier();
    private BarangService barangService = new BarangService();
    private RBarangSupplierService rbss = new RBarangSupplierService();
    private Pembelian pembelian = new Pembelian();
    private Helper helper = Helper.getInstance();
    private DiscountPembelianDialog discountPembelianDialog;
    private SyncService syncService = new SyncService();
    private boolean isCreated = true;
    private PembelianPanelListener listener;
    private PembelianSementaraService pembelianSementaraService = new PembelianSementaraService();
    private SinkronisasiBarangService sinkronisasiBarangService = new SinkronisasiBarangService();

    public PembelianPanel(String shortcut) {
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.keyDispatcher = new keyDispatcher();
        this.manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        this.manager.addKeyEventDispatcher(this.keyDispatcher);
        this.resetData();
        TableColumn col1 = this.transactionTable.getColumnModel().getColumn(1);
        TableColumn col2 = this.transactionTable.getColumnModel().getColumn(2);
        TableColumn col3 = this.transactionTable.getColumnModel().getColumn(3);
        int width = 20;
        col1.setPreferredWidth(130);
        col2.setPreferredWidth(width);
        col3.setPreferredWidth(width);
        this.shortcutLbl.setText(shortcut);
        this.loadData();
        this.pembayaranCmb.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1) {
                    if (PembelianPanel.this.pembayaranCmb.getSelectedItem().equals("CASH")) {
                        PembelianPanel.this.jatuhTempoField.setDate(new Date());
                        PembelianPanel.this.jatuhTempoField.setEnabled(false);
                        PembelianPanel.this.pembelianSementaraService.updatePembayaranPembelianSementara("CASH", PembelianPanel.this.shortcutLbl.getText());
                    } else if (PembelianPanel.this.pembayaranCmb.getSelectedItem().equals("KREDIT")) {
                        if (PembelianPanel.this.supplierLbl.getText().equals("")) {
                            JOptionPane.showMessageDialog(null, "Pilih SUPPLIER Terlebih Dahulu");
                            PembelianPanel.this.pembayaranCmb.setSelectedIndex(0);
                        } else if (PembelianPanel.this.supplier.getTop() == 0) {
                            JOptionPane.showMessageDialog(null, "Tentukan Lama Kredit SUPPLIER Terlebih Dahulu di Tabel Supplier");
                            PembelianPanel.this.pembayaranCmb.setSelectedIndex(0);
                        } else {
                            PembelianPanel.this.jatuhTempoField.setEnabled(true);
                            PembelianPanel.this.pembelianSementaraService.updatePembayaranPembelianSementara("KREDIT", PembelianPanel.this.shortcutLbl.getText());
                            Calendar c = Calendar.getInstance();
                            c.setTime(PembelianPanel.this.dateField.getDate());
                            c.add(5, PembelianPanel.this.supplier.getTop());
                            PembelianPanel.this.jatuhTempoField.setDate(c.getTime());
                        }
                    }
                } else if (e.getStateChange() == 2) {
                    // empty if block
                }
            }
        });
    }

    private void removeAllRow() {
        int rowCount = this.transactionTableModel.getRowCount();
        for (int a = 0; a < rowCount; ++a) {
            this.transactionTableModel.removeRow(0);
        }
    }

    private void loadData() {
        List<PembelianSementara> pembelianSementaras = this.pembelianSementaraService.getPembelianSementaraByShortcut(this.shortcutLbl.getText());
        long total = 0L;
        if (pembelianSementaras.size() > 0) {
            this.supplier = this.supplierService.getSupplier(pembelianSementaras.get(0).getSupplierId());
            this.supplierField.setText(this.supplier.getId());
            this.supplierLbl.setText(this.supplier.getNama());
            this.pembayaranCmb.setSelectedItem(pembelianSementaras.get(0).getPembayaran());
            this.dateField.setDate(pembelianSementaras.get(0).getTanggalPembelian());
            this.jatuhTempoField.setDate(pembelianSementaras.get(0).getJatuhTempo());
            for (PembelianSementara pembelianSementara : pembelianSementaras) {
                float tempValue;
                total += pembelianSementara.getTotalHarga();
                Barang barang = this.barangService.getBarang(pembelianSementara.getBarangId());
                if (barang == null) continue;
                Object[] rowValue = new Object[]{barang.getId(), barang.getNama(), (tempValue = pembelianSementara.getJumlahBarang()) == (float)((long)tempValue) ? (Number)((long)pembelianSementara.getJumlahBarang()) : (Number)Float.valueOf(pembelianSementara.getJumlahBarang()), pembelianSementara.getSatuan(), pembelianSementara.getHargaBeli(), (long)((float)pembelianSementara.getHargaBeli() * pembelianSementara.getJumlahBarang()), pembelianSementara.getDiscount(), pembelianSementara.getTotalHarga()};
                this.transactionTableModel.addRow(rowValue);
            }
        }
        this.totalField.setValue(total);
        this.totalItemField.setText(pembelianSementaras.size() + "");
    }

    public void setDefault() {
        this.supplierField.requestFocusInWindow();
    }

    private void resetData() {
        this.dateField.setDate(new Date());
        this.jatuhTempoField.setDate(new Date());
        int countRow = this.transactionTableModel.getRowCount();
        for (int a = 0; a < countRow; ++a) {
            this.transactionTableModel.removeRow(0);
        }
        this.jatuhTempoField.setEnabled(false);
        this.totalField.setValue(0);
        this.supplierField.setText("");
        this.supplierLbl.setText("");
    }

    public void addListener(PembelianPanelListener listener) {
        this.listener = listener;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel4 = new JLabel();
        this.jLabel7 = new JLabel();
        this.jLabel8 = new JLabel();
        this.jLabel5 = new JLabel();
        this.jLabel6 = new JLabel();
        this.supplierField = new JTextField();
        this.dateField = new JDateChooser();
        this.detailButton = new JButton();
        this.saveButton = new JButton();
        this.exitButton = new JButton();
        this.jScrollPane1 = new JScrollPane();
        this.transactionTable = new JTable();
        this.supplierLbl = new JLabel();
        this.totalField = new JFormattedTextField();
        this.shortcutLbl = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.totalItemField = new JTextField();
        this.jLabel11 = new JLabel();
        this.jLabel10 = new JLabel();
        this.jLabel12 = new JLabel();
        this.jatuhTempoField = new JDateChooser();
        this.pembayaranCmb = new JComboBox();
        this.jLabel9 = new JLabel();
        this.inputCmb = new JComboBox();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Tanggal");
        this.jLabel4.setText(":");
        this.jLabel7.setFont(new Font("Tahoma", 1, 12));
        this.jLabel7.setText("Total");
        this.jLabel8.setText(":");
        this.jLabel5.setText("Supplier");
        this.jLabel6.setText(":");
        this.supplierField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PembelianPanel.this.supplierFieldKeyPressed(evt);
            }
        });
        this.dateField.addKeyListener((KeyListener)new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PembelianPanel.this.dateFieldKeyPressed(evt);
            }
        });
        this.detailButton.setText("Detail");
        this.detailButton.setPreferredSize(new Dimension(73, 23));
        this.detailButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PembelianPanel.this.detailButtonActionPerformed(evt);
            }
        });
        this.detailButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PembelianPanel.this.detailButtonKeyPressed(evt);
            }
        });
        this.saveButton.setText("Save");
        this.saveButton.setPreferredSize(new Dimension(73, 23));
        this.saveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PembelianPanel.this.saveButtonActionPerformed(evt);
            }
        });
        this.saveButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PembelianPanel.this.saveButtonKeyPressed(evt);
            }
        });
        this.exitButton.setText("Exit");
        this.exitButton.setPreferredSize(new Dimension(73, 23));
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PembelianPanel.this.exitButtonActionPerformed(evt);
            }
        });
        this.exitButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PembelianPanel.this.exitButtonKeyPressed(evt);
            }
        });
        this.transactionTable.setModel(this.transactionTableModel);
        this.transactionTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PembelianPanel.this.transactionTableKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.transactionTable);
        this.totalField.setEditable(false);
        this.totalField.setBackground(new Color(255, 255, 0));
        this.totalField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("\u00a4#,##0.--"))));
        this.totalField.setText("0");
        this.totalField.setFont(new Font("Tahoma", 1, 12));
        this.totalField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PembelianPanel.this.totalFieldKeyPressed(evt);
            }
        });
        this.shortcutLbl.setFont(new Font("Tahoma", 1, 14));
        this.shortcutLbl.setForeground(new Color(204, 0, 0));
        this.shortcutLbl.setText("F5");
        this.jLabel2.setText("Total Item");
        this.jLabel3.setText(":");
        this.totalItemField.setEditable(false);
        this.jLabel11.setText("/");
        this.jLabel10.setText("Pembayaran");
        this.jLabel12.setText("Jatuh Tempo");
        this.jatuhTempoField.addKeyListener((KeyListener)new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PembelianPanel.this.jatuhTempoFieldKeyPressed(evt);
            }
        });
        this.pembayaranCmb.setModel(new DefaultComboBoxModel<String>(new String[]{"CASH", "KREDIT", "TRANSFER"}));
        this.jLabel9.setText("Input Stok");
        this.inputCmb.setModel(new DefaultComboBoxModel<String>(new String[]{"TOKO", "GUDANG"}));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addGap(38, 38, 38).addComponent(this.jLabel4)).addComponent(this.jLabel10)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent((Component)this.dateField, -1, 155, Short.MAX_VALUE).addComponent((Component)this.jatuhTempoField, GroupLayout.Alignment.TRAILING, -1, 155, Short.MAX_VALUE).addComponent(this.pembayaranCmb, 0, -1, Short.MAX_VALUE)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel5).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel6).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.supplierField, -2, 112, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addComponent(this.supplierLbl, -1, -1, Short.MAX_VALUE).addGap(37, 37, 37))).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jLabel3)).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.jLabel7).addGap(38, 38, 38).addComponent(this.jLabel8))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.totalField, -2, 155, -2).addGroup(layout.createSequentialGroup().addComponent(this.totalItemField, -2, 68, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel11).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.shortcutLbl)))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel9).addGap(34, 34, 34).addComponent(this.inputCmb, -2, 155, -2).addGap(71, 71, 71).addComponent(this.detailButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.saveButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.exitButton, -2, -1, -2)).addComponent(this.jLabel12)).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.jLabel4)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel10).addComponent(this.pembayaranCmb, -2, -1, -2))).addComponent((Component)this.dateField, -2, -1, -2).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel7).addComponent(this.totalField, -2, -1, -2).addComponent(this.jLabel8).addComponent(this.jLabel5).addComponent(this.jLabel6).addComponent(this.supplierField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.jLabel3).addComponent(this.totalItemField, -2, -1, -2).addComponent(this.shortcutLbl).addComponent(this.jLabel11)).addComponent(this.supplierLbl, -2, 18, -2)))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel12).addComponent((Component)this.jatuhTempoField, -2, -1, -2)).addGap(3, 3, 3).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.detailButton, -2, -1, -2).addComponent(this.exitButton, -2, -1, -2).addComponent(this.saveButton, -2, -1, -2).addComponent(this.jLabel9).addComponent(this.inputCmb, -2, -1, -2)).addGap(0, 2, Short.MAX_VALUE).addComponent(this.jScrollPane1, -1, 472, Short.MAX_VALUE).addContainerGap()));
    }

    private void supplierFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.supplierField.getText().equals("")) {
                SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Supplier");
                sd.addSearchDialogListener(new searchSupplierEventHandler());
                int w = sd.getWidth();
                int h = sd.getHeight();
                sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                sd.setVisible(true);
            } else if (!this.supplierField.getText().equals("")) {
                this.supplier = this.supplierService.getSupplier(this.supplierField.getText());
                if (this.supplier == null) {
                    JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
                } else {
                    this.supplierLbl.setText(this.supplier.getNama());
                }
            }
        } else if (evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Supplier");
            sd.addSearchDialogListener(new searchSupplierEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
            this.detailButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.listener.escapeClick();
        } else if (evt.getKeyCode() == 116) {
            this.listener.shortcutClick("F5");
        } else if (evt.getKeyCode() == 117) {
            this.listener.shortcutClick("F6");
        } else if (evt.getKeyCode() == 118) {
            this.listener.shortcutClick("F7");
        } else if (evt.getKeyCode() == 119) {
            this.listener.shortcutClick("F8");
        } else if (evt.getKeyCode() == 120) {
            this.listener.shortcutClick("F9");
        } else if (evt.getKeyCode() == 121) {
            this.listener.shortcutClick("F10");
        } else if (evt.getKeyCode() == 122) {
            this.listener.shortcutClick("F11");
        } else if (evt.getKeyCode() == 123) {
            this.listener.shortcutClick("F12");
        }
    }

    private void detailClick() {
        this.transactionTable.requestFocus();
        if (this.transactionTableModel.getRowCount() == 0 && this.supplierField.getText().equals("")) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Supplier");
            sd.addSearchDialogListener(new searchSupplierEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
            this.detailButton.requestFocus();
        } else if (this.transactionTableModel.getRowCount() == 0 && !this.supplierField.getText().equals("")) {
            PembelianDetailDialog pembelianDetailDialog = new PembelianDetailDialog(null, true, this.transactionTableModel);
            pembelianDetailDialog.addListener(new pembelianDialogEventHandler());
            int w = pembelianDetailDialog.getWidth();
            int h = pembelianDetailDialog.getHeight();
            pembelianDetailDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            pembelianDetailDialog.setVisible(true);
        }
    }

    private void detailButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.detailClick();
        } else if (evt.getKeyCode() == 39) {
            this.saveButton.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.supplierField.requestFocus();
            this.supplierField.selectAll();
        } else if (evt.getKeyCode() == 116) {
            this.listener.shortcutClick("F5");
        } else if (evt.getKeyCode() == 117) {
            this.listener.shortcutClick("F6");
        } else if (evt.getKeyCode() == 118) {
            this.listener.shortcutClick("F7");
        } else if (evt.getKeyCode() == 119) {
            this.listener.shortcutClick("F8");
        } else if (evt.getKeyCode() == 120) {
            this.listener.shortcutClick("F9");
        } else if (evt.getKeyCode() == 121) {
            this.listener.shortcutClick("F10");
        } else if (evt.getKeyCode() == 122) {
            this.listener.shortcutClick("F11");
        } else if (evt.getKeyCode() == 123) {
            this.listener.shortcutClick("F12");
        }
    }

    private void transactionTableKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            PembelianDetailDialog pembelianDetailDialog = new PembelianDetailDialog(null, true, this.transactionTableModel);
            if (this.transactionTable.getSelectedRow() != -1) {
                pembelianDetailDialog.loadKodeBarang(this.transactionTableModel.getValueAt(this.transactionTable.getSelectedRow(), 0).toString());
                pembelianDetailDialog.loadPembelian(this.transactionTable.getSelectedRow());
            }
            pembelianDetailDialog.escapeClick();
            pembelianDetailDialog.addListener(new pembelianDialogEventHandler());
            int w = pembelianDetailDialog.getWidth();
            int h = pembelianDetailDialog.getHeight();
            pembelianDetailDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            pembelianDetailDialog.setVisible(true);
        } else if (evt.getKeyCode() == 27) {
            int a = JOptionPane.showConfirmDialog(null, "Akhiri Entry Data?", "Akhiri Entry Data", 0, 3);
            if (a == 0) {
                Float total = Float.valueOf(0.0f);
                for (int x = 0; x < this.transactionTableModel.getRowCount(); ++x) {
                    total = Float.valueOf(total.floatValue() + Float.parseFloat(this.transactionTableModel.getValueAt(x, 7).toString()));
                }
                this.saveButton.requestFocus();
                this.totalField.setValue(total);
                this.totalItemField.setText(this.transactionTableModel.getRowCount() + "");
            }
        } else if (evt.getKeyCode() == 127) {
            int a;
            if (this.transactionTable.getSelectedRow() != -1 && (a = JOptionPane.showConfirmDialog(null, "Hapus Data Ini?", "Delete Data", 0, 3)) == 0) {
                this.totalItemField.setText(Integer.parseInt(this.totalItemField.getText()) - 1 + "");
                this.totalField.setValue(Float.valueOf(Float.parseFloat(this.totalField.getValue().toString()) - Float.parseFloat(this.transactionTableModel.getValueAt(this.transactionTable.getSelectedRow(), 7).toString())));
                this.pembelianSementaraService.deletePembelianSementara(this.shortcutLbl.getText(), this.transactionTable.getValueAt(this.transactionTable.getSelectedRow(), 0).toString());
                this.transactionTableModel.removeRow(this.transactionTable.getSelectedRow());
            }
        } else if (evt.getKeyCode() == 116) {
            this.listener.shortcutClick("F5");
        } else if (evt.getKeyCode() == 117) {
            this.listener.shortcutClick("F6");
        } else if (evt.getKeyCode() == 118) {
            this.listener.shortcutClick("F7");
        } else if (evt.getKeyCode() == 119) {
            this.listener.shortcutClick("F8");
        } else if (evt.getKeyCode() == 120) {
            this.listener.shortcutClick("F9");
        } else if (evt.getKeyCode() == 121) {
            this.listener.shortcutClick("F10");
        } else if (evt.getKeyCode() == 122) {
            this.listener.shortcutClick("F11");
        } else if (evt.getKeyCode() == 123) {
            this.listener.shortcutClick("F12");
        }
    }

    private void saveClick() {
        float total = 0.0f;
        for (int x = 0; x < this.transactionTableModel.getRowCount(); ++x) {
            total += Float.parseFloat(this.transactionTableModel.getValueAt(x, 7).toString());
        }
        this.discountPembelianDialog = new DiscountPembelianDialog(null, true);
        this.discountPembelianDialog.setData((long)total, this.pembelian.getDiscount1(), this.pembelian.getPpn());
        this.discountPembelianDialog.addListener(new discountPembelianDialogEventHandler());
        int w = this.discountPembelianDialog.getWidth();
        int h = this.discountPembelianDialog.getHeight();
        this.discountPembelianDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
        this.discountPembelianDialog.setVisible(true);
    }

    private void saveButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (!this.jatuhTempoField.getDate().before(this.dateField.getDate())) {
                this.saveClick();
            } else {
                JOptionPane.showMessageDialog(null, "Masukkan tanggal dengan benar");
            }
        } else if (evt.getKeyCode() == 39) {
            this.exitButton.requestFocus();
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 37) {
            this.detailButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.supplierField.requestFocus();
            this.supplierField.selectAll();
        } else if (evt.getKeyCode() == 116) {
            this.listener.shortcutClick("F5");
        } else if (evt.getKeyCode() == 117) {
            this.listener.shortcutClick("F6");
        } else if (evt.getKeyCode() == 118) {
            this.listener.shortcutClick("F7");
        } else if (evt.getKeyCode() == 119) {
            this.listener.shortcutClick("F8");
        } else if (evt.getKeyCode() == 120) {
            this.listener.shortcutClick("F9");
        } else if (evt.getKeyCode() == 121) {
            this.listener.shortcutClick("F10");
        } else if (evt.getKeyCode() == 122) {
            this.listener.shortcutClick("F11");
        } else if (evt.getKeyCode() == 123) {
            this.listener.shortcutClick("F12");
        }
    }

    private void exitButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.listener.escapeClick();
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 37) {
            this.saveButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.supplierField.requestFocus();
            this.supplierField.selectAll();
        } else if (evt.getKeyCode() == 116) {
            this.listener.shortcutClick("F5");
        } else if (evt.getKeyCode() == 117) {
            this.listener.shortcutClick("F6");
        } else if (evt.getKeyCode() == 118) {
            this.listener.shortcutClick("F7");
        } else if (evt.getKeyCode() == 119) {
            this.listener.shortcutClick("F8");
        } else if (evt.getKeyCode() == 120) {
            this.listener.shortcutClick("F9");
        } else if (evt.getKeyCode() == 121) {
            this.listener.shortcutClick("F10");
        } else if (evt.getKeyCode() == 122) {
            this.listener.shortcutClick("F11");
        } else if (evt.getKeyCode() == 123) {
            this.listener.shortcutClick("F12");
        }
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.listener.escapeClick();
    }

    private void saveButtonActionPerformed(ActionEvent evt) {
        if (!this.jatuhTempoField.getDate().before(this.dateField.getDate())) {
            this.saveClick();
        } else {
            JOptionPane.showMessageDialog(null, "Masukkan tanggal dengan benar");
        }
    }

    private void dateFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.listener.escapeClick();
        }
    }

    private void totalFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.supplierField.requestFocus();
            this.supplierField.selectAll();
        }
    }

    private void detailButtonActionPerformed(ActionEvent evt) {
        this.detailClick();
    }

    private void jatuhTempoFieldKeyPressed(KeyEvent evt) {
    }

    private void jComboBox2ActionPerformed(ActionEvent evt) {
    }

    private void saveData(Float disc, Float ppn, long total) {
        float totalDiscount = (float)total * (disc.floatValue() / 100.0f);
        float totalPpn = ((float)total - totalDiscount) * (ppn.floatValue() / 100.0f);
        float netPrice = (float)total - totalDiscount + totalPpn;
        this.pembelian.setSupplierId(this.supplier.getId());
        this.pembelian.setKaryawanId(this.helper.getKaryawan().getId());
        this.pembelian.setFakturId("");
        this.pembelian.setId(this.helper.generateCodeGeneral(this.pembelianService.getLastId()));
        this.pembelian.setTanggal(this.dateField.getDate());
        this.pembelian.setPembayaran(this.pembayaranCmb.getSelectedItem().toString());
        if (this.pembelian.getPembayaran().equals("CASH") || this.pembelian.getPembayaran().equals("TRANSFER")) {
            this.pembelian.setPelunasan("LUNAS");
        } else if (this.pembelian.getPembayaran().equals("KREDIT")) {
            this.pembelian.setPelunasan("BELUM LUNAS");
        }
        this.pembelian.setDiscount1(disc.floatValue());
        this.pembelian.setPpn(ppn.floatValue());
        this.pembelian.setTotal((long)netPrice);
        this.pembelian.setKeterangan("BELUM DIPROSES");
        if (this.pembayaranCmb.getSelectedItem().equals("CASH")) {
            this.pembelian.setJatuhTempo(new Date());
        } else if (this.pembayaranCmb.getSelectedItem().equals("KREDIT")) {
            this.pembelian.setJatuhTempo(this.jatuhTempoField.getDate());
        } else if (this.pembayaranCmb.getSelectedItem().equals("TRANSFER")) {
            this.pembelian.setJatuhTempo(new Date());
        }
        if (this.inputCmb.getSelectedItem().equals("TOKO")) {
            this.pembelian.setStatus("TOKO");
        } else if (this.inputCmb.getSelectedItem().equals("GUDANG")) {
            this.pembelian.setStatus("GUDANG");
        }
        this.pembelian.setCreated(new Date());
        this.pembelian.setUpdated(new Date());
        int createPembelianStatus = this.pembelianService.createPembelian(this.pembelian);
        int createPembelianDetailStatus = 0;
        ArrayList<BarangHpp> listBarangHpp = new ArrayList<BarangHpp>();
        for (int a = 0; a < this.transactionTableModel.getRowCount(); ++a) {
            Barang tempBarang;
            PembelianDetail pembelianDetail = new PembelianDetail();
            pembelianDetail.setPembelianId(this.pembelian.getId());
            pembelianDetail.setBarangId(this.transactionTableModel.getValueAt(a, 0).toString());
            pembelianDetail.setJumlahBarang(Float.parseFloat(this.transactionTableModel.getValueAt(a, 2).toString()));
            pembelianDetail.setSatuan(this.transactionTableModel.getValueAt(a, 3).toString());
            pembelianDetail.setHargaBeli(Long.parseLong(this.transactionTableModel.getValueAt(a, 4).toString()));
            pembelianDetail.setDiscount2((long)Float.parseFloat(this.transactionTableModel.getValueAt(a, 6).toString()));
            pembelianDetail.setTotalHarga((long)Float.parseFloat(this.transactionTableModel.getValueAt(a, 7).toString()));
            pembelianDetail.setCreated(new Date());
            pembelianDetail.setUpdated(new Date());
            createPembelianDetailStatus = this.pembelianDetailService.createPembelianDetail(pembelianDetail);
            if (createPembelianDetailStatus != 1) continue;
            if (!this.rbss.isRBSHasCreated(pembelianDetail.getBarangId(), this.supplier.getId())) {
                RBarangSupplier rbs = new RBarangSupplier();
                rbs.setBarangId(pembelianDetail.getBarangId());
                rbs.setSupplierId(this.supplier.getId());
                this.rbss.createRBarangSupplier(rbs);
            }
            Barang tempBarang1 = tempBarang = this.barangService.getBarang(this.transactionTableModel.getValueAt(a, 0).toString());
            BarangHpp tempBarangHpp = new BarangHpp();
            long hargaBeliTerakhir = 0L;
            long hargaBeliTemp = pembelianDetail.getTotalHarga();
            if (this.pembelian.getDiscount1() > 0.0f) {
                float discountValue = this.pembelian.getDiscount1() / 100.0f * (float)hargaBeliTemp;
                hargaBeliTemp -= (long)discountValue;
            }
            if (this.pembelian.getPpn() > 0.0f) {
                float ppnValue = this.pembelian.getPpn() / 100.0f * (float)hargaBeliTemp;
                hargaBeliTemp += (long)ppnValue;
            }
            if (pembelianDetail != null) {
                if (pembelianDetail.getSatuan().equals(tempBarang.getSatuan3())) {
                    hargaBeliTerakhir = (long)((float)hargaBeliTemp / (pembelianDetail.getJumlahBarang() * (float)tempBarang.getKonversi21() * (float)tempBarang.getKonversi32()));
                } else if (pembelianDetail.getSatuan().equals(tempBarang.getSatuan2())) {
                    hargaBeliTerakhir = (long)((float)hargaBeliTemp / (pembelianDetail.getJumlahBarang() * (float)tempBarang.getKonversi21()));
                } else if (pembelianDetail.getSatuan().equals(tempBarang.getSatuan1())) {
                    hargaBeliTerakhir = (long)((float)hargaBeliTemp / pembelianDetail.getJumlahBarang());
                }
            }
            if (hargaBeliTerakhir != 0L) {
                tempBarang.setHpp(hargaBeliTerakhir);
            }
            System.out.println("HPP=" + tempBarang.getHpp());
            tempBarang.setUpdated(new Date());
            tempBarangHpp.setId_sku(tempBarang.getId());
            tempBarangHpp.setHpp(tempBarang.getHpp());
            tempBarangHpp.setHppUpdated(System.currentTimeMillis() / 1000L);
            listBarangHpp.add(tempBarangHpp);
            if (tempBarang.getHpp() != tempBarang1.getHpp()) {
                this.barangService.updateBarangHpp(tempBarang);
                continue;
            }
            this.barangService.updateBarang(tempBarang);
        }
        int resultPushHpp = 0;
        try {
            resultPushHpp = this.sinkronisasiBarangService.pushHpp(listBarangHpp);
        }
        catch (IOException ex) {
            Logger.getLogger(PembelianPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (createPembelianDetailStatus == 1 && createPembelianStatus == 1 && resultPushHpp == 1) {
            PrinterJob job = PrinterJob.getPrinterJob();
            PageFormat pf = job.defaultPage();
            Paper p = pf.getPaper();
            p.setSize(p.getWidth(), p.getHeight() + 1500.0);
            p.setImageableArea(15.0, 0.0, p.getWidth(), p.getHeight() + 1500.0);
            pf.setPaper(p);
            Book book = new Book();
            book.append(new pembelianPrint(), pf);
            job.setPageable(book);
            boolean ok = job.printDialog();
            if (ok) {
                try {
                    job.print();
                }
                catch (PrinterException printerException) {
                    // empty catch block
                }
            }
            this.pembelian = new Pembelian();
            this.syncService.doSyncPembelianAndPembelianDetailUnsynced();
        } else if (resultPushHpp == 1) {
            JOptionPane.showMessageDialog(null, "Penyimpanan Data Gagal");
        } else {
            JOptionPane.showMessageDialog(null, "Penyimpanan Data Online Gagal, Coba lagi...");
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
            g.setFont(new Font("Times New Roman", 0, 8));
            g.drawString("Pembelian:" + PembelianPanel.this.pembelian.getId(), x + 0, y + yK);
            g.drawString("Supplier:" + PembelianPanel.this.supplierService.getSupplier(PembelianPanel.this.pembelian.getSupplierId()).getNama(), x + 0, y + (yK += yP));
            g.drawString("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -", x + 0, y + (yK += yP));
            yK += yP;
            for (int a = 0; a < PembelianPanel.this.transactionTableModel.getRowCount(); ++a) {
                Barang barang = PembelianPanel.this.barangService.getBarang(PembelianPanel.this.transactionTableModel.getValueAt(a, 0).toString());
                g.drawString(barang.getNama(), x + 0, y + yK);
                yK += yP;
                float tempValue = Float.parseFloat(PembelianPanel.this.transactionTableModel.getValueAt(a, 2).toString());
                float tempValue2 = 0.0f;
                if (PembelianPanel.this.transactionTableModel.getValueAt(a, 3).toString().equals(barang.getSatuan2())) {
                    tempValue2 = tempValue * (float)barang.getKonversi21();
                } else if (PembelianPanel.this.transactionTableModel.getValueAt(a, 3).toString().equals(barang.getSatuan3())) {
                    tempValue2 = tempValue * (float)barang.getKonversi21() * (float)barang.getKonversi32();
                }
                g.drawString(tempValue + PembelianPanel.this.transactionTableModel.getValueAt(a, 3).toString() + " / " + (barang.getStokGudang() + tempValue2) + barang.getSatuan1(), x + 20, y + yK);
                yK += yP;
            }
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String tanggal = format.format(PembelianPanel.this.pembelian.getTanggal());
            g.drawString("Tgl " + tanggal, x + 0, y + yK);
            g.drawString("Jam " + PembelianPanel.this.pembelian.getTanggal().getHours() + ":" + PembelianPanel.this.pembelian.getTanggal().getMinutes() + ":" + PembelianPanel.this.pembelian.getTanggal().getSeconds(), x + 120, y + yK);
            return 0;
        }
    }

    private class discountPembelianDialogEventHandler
    implements DiscountPembelianDialogListener {
        private discountPembelianDialogEventHandler() {
        }

        @Override
        public void saveClick(Float disc, Float ppn, long total) {
            if (PembelianPanel.this.isCreated) {
                PembelianPanel.this.saveData(disc, ppn, total);
                PembelianPanel.this.resetData();
                PembelianPanel.this.pembelianSementaraService.deletePembelianSementaraByShortcut(PembelianPanel.this.shortcutLbl.getText());
                PembelianPanel.this.supplierField.setText("");
                PembelianPanel.this.setDefault();
            }
        }
    }

    private class pembelianDialogEventHandler
    implements PembelianDetailDialogListener {
        private pembelianDialogEventHandler() {
        }

        @Override
        public void saveClick(Object[] rowValue) {
            int a;
            boolean newData = true;
            for (a = 0; a < PembelianPanel.this.transactionTableModel.getRowCount(); ++a) {
                if (!PembelianPanel.this.transactionTableModel.getValueAt(a, 0).equals(rowValue[0])) continue;
                newData = false;
                break;
            }
            if (newData) {
                PembelianPanel.this.transactionTableModel.addRow(rowValue);
                PembelianPanel.this.totalField.setValue(Float.valueOf(Float.parseFloat(PembelianPanel.this.totalField.getValue().toString()) + Float.parseFloat(rowValue[7].toString())));
                PembelianPanel.this.totalItemField.setText(Integer.parseInt(PembelianPanel.this.totalItemField.getText()) + 1 + "");
            } else {
                PembelianPanel.this.totalField.setValue(Float.valueOf(Float.parseFloat(PembelianPanel.this.totalField.getValue().toString()) - Float.parseFloat(PembelianPanel.this.transactionTableModel.getValueAt(a, 7).toString())));
                PembelianPanel.this.totalField.setValue(Float.valueOf(Float.parseFloat(PembelianPanel.this.totalField.getValue().toString()) + Float.parseFloat(rowValue[7].toString())));
                PembelianPanel.this.transactionTableModel.removeRow(a);
                PembelianPanel.this.transactionTableModel.addRow(rowValue);
            }
            PembelianSementara pembelianSementara = new PembelianSementara();
            pembelianSementara.setSupplierId(PembelianPanel.this.supplier.getId());
            pembelianSementara.setShortcut(PembelianPanel.this.shortcutLbl.getText());
            pembelianSementara.setBarangId(rowValue[0].toString());
            pembelianSementara.setJumlahBarang(Float.parseFloat(rowValue[2].toString()));
            pembelianSementara.setSatuan(rowValue[3].toString());
            pembelianSementara.setHargaBeli((long)Float.parseFloat(rowValue[4].toString()));
            pembelianSementara.setDiscount((long)Float.parseFloat(rowValue[6].toString()));
            pembelianSementara.setTotalHarga((long)Float.parseFloat(rowValue[7].toString()));
            pembelianSementara.setStatus(PembelianPanel.this.helper.getStatusProgram());
            pembelianSementara.setTanggalPembelian(PembelianPanel.this.dateField.getDate());
            pembelianSementara.setPembayaran(PembelianPanel.this.pembayaranCmb.getSelectedItem() + "");
            pembelianSementara.setJatuhTempo(PembelianPanel.this.jatuhTempoField.getDate());
            PembelianPanel.this.pembelianSementaraService.deletePembelianSementara(PembelianPanel.this.shortcutLbl.getText(), pembelianSementara.getBarangId());
            PembelianPanel.this.pembelianSementaraService.createPembelianSementara(pembelianSementara);
        }

        @Override
        public void deleteClick(String idBarang) {
            PembelianSementara pembelianSementara = new PembelianSementara();
            pembelianSementara.setBarangId(idBarang);
            PembelianPanel.this.pembelianSementaraService.deletePembelianSementara(PembelianPanel.this.shortcutLbl.getText(), pembelianSementara.getBarangId());
            PembelianPanel.this.removeAllRow();
            PembelianPanel.this.loadData();
        }
    }

    private class searchPanelSupplierEventHandler
    implements SearchPanelListener {
        private searchPanelSupplierEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            PembelianPanel.this.supplier = PembelianPanel.this.supplierService.getSupplier(chooseValue);
            PembelianPanel.this.pembelianSementaraService.updateSupplierPembelianSementara(chooseValue, PembelianPanel.this.shortcutLbl.getText());
            PembelianPanel.this.supplierLbl.setText(PembelianPanel.this.supplier.getNama());
            PembelianPanel.this.supplierField.setText(PembelianPanel.this.supplier.getId());
            PembelianPanel.this.pembayaranCmb.setSelectedIndex(0);
        }
    }

    private class searchSupplierEventHandler
    implements SearchDialogListener {
        private searchSupplierEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"KODE SUPPLIER", "NAMA SUPPLIER", "ALAMAT", "KOTA"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Supplier> suppliers = PembelianPanel.this.supplierService.getSuppliers(searchWord);
            for (Supplier supplier : suppliers) {
                Object[] temp = new String[]{supplier.getId(), supplier.getNama(), supplier.getAlamat(), supplier.getKota()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Supplier");
            sp.addSearchPanelListener(new searchPanelSupplierEventHandler());
            sp.setVisible(true);
        }
    }

    private class keyDispatcher
    implements KeyEventDispatcher {
        private keyDispatcher() {
        }

        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == 401) {
                // empty if block
            }
            return false;
        }
    }
}

