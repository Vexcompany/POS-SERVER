/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.DocumentFilter;
import javax.swing.text.NumberFormatter;
import minimart.engine.component.UppercaseDocumentFilter;
import minimart.engine.controller.Helper;
import minimart.engine.model.Sales;
import minimart.engine.service.SalesService;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class SalesMaster
extends JDialog {
    private JTextField alamatField;
    private JLabel alamatLbl;
    private JButton dataBaruButton;
    private JButton deleteButton;
    private JButton exitButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel kodeCustLbl;
    private JTextField kodeSalestField;
    private JLabel kotaLbl;
    private JTextField ktpField;
    private JTextField namaCustField;
    private JLabel namaCustLbl;
    private JLabel noTelpLbl;
    private JButton saveButton;
    private JFormattedTextField targetField;
    private JTextField telpField;
    private DocumentFilter filter = new UppercaseDocumentFilter();
    private String action = "update";
    private SalesService salesService = new SalesService();
    private Sales sales = new Sales();
    private int screenWidth;
    private int screenHeight;
    private Helper helper = Helper.getInstance();

    public SalesMaster(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.initComponents2();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.targetField.setValue(0);
        this.setTitle("Entry Tabel Customer");
    }

    private void initComponents2() {
        ((AbstractDocument)this.kodeSalestField.getDocument()).setDocumentFilter(this.filter);
        ((AbstractDocument)this.namaCustField.getDocument()).setDocumentFilter(this.filter);
        ((AbstractDocument)this.alamatField.getDocument()).setDocumentFilter(this.filter);
        ((AbstractDocument)this.telpField.getDocument()).setDocumentFilter(this.filter);
    }

    private void setDefault() {
        this.ktpField.setText("");
        this.namaCustField.setText("");
        this.alamatField.setText("");
        this.telpField.setText("");
    }

    private void escapeClick() {
        this.kodeSalestField.requestFocus();
        this.kodeSalestField.selectAll();
    }

    private void saveSales() {
        if (this.action.equals("create")) {
            this.sales = new Sales();
        }
        this.sales.setId(this.kodeSalestField.getText());
        this.sales.setKtp(this.ktpField.getText());
        this.sales.setNama(this.namaCustField.getText());
        this.sales.setAlamat(this.alamatField.getText());
        this.sales.setTelepon(this.telpField.getText());
        this.sales.setTarget(Integer.parseInt(this.targetField.getValue().toString()));
        int result = 0;
        if (this.action.equals("create")) {
            result = this.salesService.createSales(this.sales);
        } else if (this.action.equals("update")) {
            result = this.salesService.updateSales(this.sales);
        }
        if (result == 1) {
            JOptionPane.showMessageDialog(null, "Data Telah Tersimpan");
            this.escapeClick();
            this.setDefault();
            this.kodeSalestField.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "Penyimpanan Data Gagal");
            this.escapeClick();
        }
    }

    private void deleteSales() {
        int confirm = JOptionPane.showConfirmDialog(null, "Data Akan Dihapus?", "Delete Data Confirmation", 2);
        if (confirm == 0) {
            int result = this.salesService.deleteSales(this.kodeSalestField.getText());
            if (result == 1) {
                JOptionPane.showMessageDialog(null, "Data Telah Terhapus");
                this.escapeClick();
                this.setDefault();
                this.kodeSalestField.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Penghapusan Data Gagal");
                this.escapeClick();
            }
        }
    }

    private void loadKodeSales() {
        this.action = "update";
        this.sales = this.salesService.getSales(this.kodeSalestField.getText());
        if (this.sales == null) {
            JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
        } else {
            this.ktpField.setText(this.sales.getKtp());
            this.namaCustField.setText(this.sales.getNama());
            this.alamatField.setText(this.sales.getAlamat());
            this.telpField.setText(this.sales.getTelepon());
            this.targetField.setText(this.sales.getTarget() + "");
            this.ktpField.requestFocus();
        }
    }

    private void newData() {
        this.action = "create";
        this.setDefault();
        this.kodeSalestField.setText(Helper.getInstance().generateCodeGeneral(this.salesService.getLastId()));
        this.ktpField.requestFocus();
    }

    private void initComponents() {
        this.jLabel3 = new JLabel();
        this.kodeCustLbl = new JLabel();
        this.namaCustLbl = new JLabel();
        this.alamatLbl = new JLabel();
        this.kotaLbl = new JLabel();
        this.noTelpLbl = new JLabel();
        this.kodeSalestField = new JTextField();
        this.namaCustField = new JTextField();
        this.alamatField = new JTextField();
        this.telpField = new JTextField();
        this.saveButton = new JButton();
        this.exitButton = new JButton();
        this.dataBaruButton = new JButton();
        this.deleteButton = new JButton();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.ktpField = new JTextField();
        this.targetField = new JFormattedTextField();
        this.jLabel4 = new JLabel();
        this.jLabel3.setText("jLabel3");
        this.setDefaultCloseOperation(2);
        this.kodeCustLbl.setText("Kode Sales");
        this.namaCustLbl.setText("Nama Sales");
        this.alamatLbl.setText("Alamat");
        this.kotaLbl.setText("No Telp");
        this.noTelpLbl.setText("Target");
        this.kodeSalestField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                SalesMaster.this.kodeSalestFieldKeyPressed(evt);
            }
        });
        this.namaCustField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                SalesMaster.this.namaCustFieldKeyPressed(evt);
            }
        });
        this.alamatField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                SalesMaster.this.alamatFieldKeyPressed(evt);
            }
        });
        this.telpField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                SalesMaster.this.telpFieldKeyPressed(evt);
            }
        });
        this.saveButton.setText("Save");
        this.saveButton.setPreferredSize(new Dimension(73, 23));
        this.saveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                SalesMaster.this.saveButtonActionPerformed(evt);
            }
        });
        this.saveButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                SalesMaster.this.saveButtonKeyPressed(evt);
            }
        });
        this.exitButton.setText("Exit");
        this.exitButton.setPreferredSize(new Dimension(73, 23));
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                SalesMaster.this.exitButtonActionPerformed(evt);
            }
        });
        this.exitButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                SalesMaster.this.exitButtonKeyPressed(evt);
            }
        });
        this.dataBaruButton.setText("Data Baru");
        this.dataBaruButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                SalesMaster.this.dataBaruButtonActionPerformed(evt);
            }
        });
        this.deleteButton.setText("Delete");
        this.deleteButton.setPreferredSize(new Dimension(73, 23));
        this.deleteButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                SalesMaster.this.deleteButtonActionPerformed(evt);
            }
        });
        this.deleteButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                SalesMaster.this.deleteButtonKeyPressed(evt);
            }
        });
        this.jLabel1.setText("F1");
        this.jLabel2.setText("No.KTP");
        this.ktpField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                SalesMaster.this.ktpFieldKeyPressed(evt);
            }
        });
        this.targetField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.targetField.setText("0");
        this.targetField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                SalesMaster.this.targetFieldKeyPressed(evt);
            }
        });
        this.jLabel4.setText("Poin");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(26, 26, 26).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.kodeCustLbl).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE).addComponent(this.kodeSalestField, -2, 216, -2)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.namaCustLbl).addComponent(this.alamatLbl).addComponent(this.jLabel2).addComponent(this.noTelpLbl).addComponent(this.kotaLbl)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.targetField, -2, 116, -2).addGap(14, 14, 14).addComponent(this.jLabel4)).addComponent(this.namaCustField, -1, 223, Short.MAX_VALUE).addComponent(this.alamatField, -1, 223, Short.MAX_VALUE).addComponent(this.telpField, -1, 223, Short.MAX_VALUE).addComponent(this.ktpField)))).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.dataBaruButton)).addGroup(layout.createSequentialGroup().addGap(40, 40, 40).addComponent(this.jLabel1))).addGap(10, 10, 10)).addGroup(layout.createSequentialGroup().addGap(60, 60, 60).addComponent(this.saveButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.deleteButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.exitButton, -2, -1, -2).addContainerGap(128, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.kodeCustLbl).addComponent(this.kodeSalestField, -2, -1, -2).addComponent(this.dataBaruButton)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.ktpField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.namaCustLbl).addComponent(this.namaCustField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.alamatLbl).addComponent(this.alamatField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.kotaLbl).addComponent(this.telpField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.noTelpLbl).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.targetField, -2, -1, -2).addComponent(this.jLabel4))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.saveButton, -2, -1, -2).addComponent(this.deleteButton, -2, -1, -2).addComponent(this.exitButton, -2, -1, -2))).addComponent(this.jLabel1)).addContainerGap()));
        this.pack();
    }

    private void kodeSalestFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Sales");
            sd.addSearchDialogListener(new searchSalesDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        } else if (evt.getKeyCode() == 10) {
            if (this.kodeSalestField.getText().equals("")) {
                SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Sales");
                sd.addSearchDialogListener(new searchSalesDialogEventHandler());
                int w = sd.getWidth();
                int h = sd.getHeight();
                sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                sd.setVisible(true);
            } else {
                this.loadKodeSales();
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        } else if (evt.getKeyCode() == 112) {
            this.newData();
        }
    }

    private void namaCustFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.alamatField.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.ktpField.requestFocus();
            this.ktpField.selectAll();
        }
    }

    private void alamatFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.telpField.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.namaCustField.requestFocus();
            this.namaCustField.selectAll();
        }
    }

    private void dataBaruButtonActionPerformed(ActionEvent evt) {
        this.newData();
    }

    private void telpFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.targetField.requestFocus();
            this.targetField.setText(this.targetField.getValue().toString().replace(".", ""));
            this.targetField.selectAll();
        } else if (evt.getKeyCode() == 38) {
            this.alamatField.requestFocus();
            this.alamatField.selectAll();
        }
    }

    private void saveButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.saveSales();
        } else if (evt.getKeyCode() == 39) {
            this.deleteButton.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.telpField.requestFocus();
            this.telpField.selectAll();
        }
    }

    private void deleteButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.deleteSales();
        } else if (evt.getKeyCode() == 39) {
            this.exitButton.requestFocus();
        } else if (evt.getKeyCode() == 37) {
            this.saveButton.requestFocus();
        }
    }

    private void exitButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.dispose();
        } else if (evt.getKeyCode() == 37) {
            this.deleteButton.requestFocus();
        }
    }

    private void saveButtonActionPerformed(ActionEvent evt) {
        this.saveSales();
    }

    private void deleteButtonActionPerformed(ActionEvent evt) {
        this.deleteSales();
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void ktpFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.namaCustField.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.kodeSalestField.requestFocus();
            this.kodeSalestField.selectAll();
        }
    }

    private void targetFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.saveButton.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.telpField.requestFocus();
            this.telpField.selectAll();
        }
    }

    private class searchPanelSalesEventHandler
    implements SearchPanelListener {
        private searchPanelSalesEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            SalesMaster.this.kodeSalestField.setText(chooseValue);
            SalesMaster.this.loadKodeSales();
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
            List<Sales> saleses = SalesMaster.this.salesService.getSaleses(searchWord);
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
    }
}

