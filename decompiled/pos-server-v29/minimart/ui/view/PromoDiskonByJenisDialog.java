/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.controller.Helper;
import minimart.engine.model.Jenis;
import minimart.engine.service.JenisService;
import minimart.ui.listener.PromoHematDialogListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class PromoDiskonByJenisDialog
extends JDialog {
    private JButton cancelBtn;
    private JTextField discField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JTextField jenisBarangField;
    private JCheckBox pelangganCheck;
    private JButton saveBtn;
    private PromoHematDialogListener listener;
    private JenisService jenisService = new JenisService();
    private DefaultTableModel promoTableModel;
    private int screenWidth = 0;
    private int screenHeight = 0;
    private SearchDialog sd;
    private Jenis jenis;
    private Helper helper = Helper.getInstance();

    public PromoDiskonByJenisDialog(Frame parent, boolean modal, DefaultTableModel promoTableModel) {
        super(parent, modal);
        this.initComponents();
        this.promoTableModel = promoTableModel;
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    }

    public void addListener(PromoHematDialogListener pudl) {
        this.listener = pudl;
    }

    protected void loadPromoData(int row) {
        this.jenisBarangField.setText(this.promoTableModel.getValueAt(row, 0).toString());
        this.discField.setText(this.promoTableModel.getValueAt(row, 1).toString());
        if (this.promoTableModel.getValueAt(row, 2).toString().equals("YA")) {
            this.pelangganCheck.setSelected(true);
        } else if (this.promoTableModel.getValueAt(row, 2).toString().equals("TIDAK")) {
            this.pelangganCheck.setSelected(false);
        }
    }

    protected void loadJenisBarang(String data) {
        this.jenis = this.jenisService.getJenis(data);
        if (this.jenis != null) {
            this.discField.requestFocus();
        } else {
            JOptionPane.showMessageDialog(this, "Barang Tidak Ditemukan");
        }
    }

    private void setDefaultData() {
        this.jenisBarangField.setText("");
        this.discField.setText("");
        this.pelangganCheck.setSelected(false);
    }

    private void searchDataPromo(String jenis) {
        boolean find = false;
        for (int a = 0; a < this.promoTableModel.getRowCount(); ++a) {
            String tempJenis = this.promoTableModel.getValueAt(a, 0).toString();
            if (!jenis.equals(tempJenis)) continue;
            find = true;
            this.loadPromoData(a);
            break;
        }
        if (!find) {
            this.discField.requestFocus();
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jenisBarangField = new JTextField();
        this.discField = new JTextField();
        this.cancelBtn = new JButton();
        this.saveBtn = new JButton();
        this.pelangganCheck = new JCheckBox();
        this.jLabel4 = new JLabel();
        this.setDefaultCloseOperation(2);
        this.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoDiskonByJenisDialog.this.formKeyPressed(evt);
            }
        });
        this.jLabel1.setText("Jenis Barang");
        this.jLabel2.setText("Diskon");
        this.jLabel3.setText("Khsusu Member");
        this.jenisBarangField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoDiskonByJenisDialog.this.jenisBarangFieldKeyPressed(evt);
            }
        });
        this.discField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoDiskonByJenisDialog.this.discFieldKeyPressed(evt);
            }
        });
        this.cancelBtn.setText("Cancel");
        this.cancelBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PromoDiskonByJenisDialog.this.cancelBtnActionPerformed(evt);
            }
        });
        this.saveBtn.setText("Save");
        this.saveBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PromoDiskonByJenisDialog.this.saveBtnActionPerformed(evt);
            }
        });
        this.saveBtn.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoDiskonByJenisDialog.this.saveBtnKeyPressed(evt);
            }
        });
        this.pelangganCheck.setText("YA");
        this.pelangganCheck.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoDiskonByJenisDialog.this.pelangganCheckKeyPressed(evt);
            }
        });
        this.jLabel4.setText("%");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addGap(34, 34, 34).addComponent(this.jenisBarangField, -2, 162, -2)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2).addComponent(this.jLabel3)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.discField, -2, 162, -2).addComponent(this.pelangganCheck)))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel4).addContainerGap(44, Short.MAX_VALUE)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap(-1, Short.MAX_VALUE).addComponent(this.saveBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cancelBtn).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.jenisBarangField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.discField, -2, -1, -2).addComponent(this.jLabel4)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.pelangganCheck)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.cancelBtn).addComponent(this.saveBtn)).addContainerGap(-1, Short.MAX_VALUE)));
        this.pack();
    }

    private void formKeyPressed(KeyEvent evt) {
    }

    private void jenisBarangFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.dispose();
        } else if (evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Jenis Barang");
            sd.addSearchDialogListener(new searchJenisDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        } else if (evt.getKeyCode() == 10) {
            if (this.jenisBarangField.getText().equals("")) {
                SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Jenis Barang");
                sd.addSearchDialogListener(new searchJenisDialogEventHandler());
                int w = sd.getWidth();
                int h = sd.getHeight();
                sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                sd.setVisible(true);
            } else {
                this.loadJenisBarang(this.jenisBarangField.getText());
                if (this.jenis != null) {
                    this.searchDataPromo(this.jenis.getJenis());
                    this.discField.requestFocus();
                } else {
                    this.jenisBarangField.setText("");
                }
            }
        }
    }

    private void saveBtnActionPerformed(ActionEvent evt) {
        this.saveClick();
    }

    private void saveClick() {
        Object[] rowValue = new Object[3];
        rowValue[0] = this.jenis.getJenis();
        rowValue[1] = this.discField.getText();
        if (this.pelangganCheck.isSelected()) {
            rowValue[2] = "YA";
        } else if (!this.pelangganCheck.isSelected()) {
            rowValue[2] = "TIDAK";
        }
        if (this.helper.isFloat(rowValue[2] + "")) {
            JOptionPane.showMessageDialog(null, "Masukkan Angka");
        } else {
            this.listener.saveClick(rowValue);
        }
    }

    private void escapeClick() {
        this.jenisBarangField.requestFocus();
        this.jenisBarangField.selectAll();
    }

    private void saveBtnKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.saveClick();
            this.escapeClick();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 38) {
            this.discField.requestFocus();
            this.discField.selectAll();
        }
    }

    private void discFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.saveBtn.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 38) {
            this.escapeClick();
        }
    }

    private void pelangganCheckKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.saveBtn.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 38) {
            this.escapeClick();
        }
    }

    private void cancelBtnActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private class searchPanelJenisEventHandler
    implements SearchPanelListener {
        private searchPanelJenisEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            PromoDiskonByJenisDialog.this.jenis = PromoDiskonByJenisDialog.this.jenisService.getJenis(chooseValue);
            PromoDiskonByJenisDialog.this.jenisBarangField.setText(chooseValue);
            PromoDiskonByJenisDialog.this.discField.requestFocus();
            PromoDiskonByJenisDialog.this.discField.setText(PromoDiskonByJenisDialog.this.discField.getText().replace(".", ""));
            PromoDiskonByJenisDialog.this.discField.selectAll();
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
            List<Jenis> jenisses = PromoDiskonByJenisDialog.this.jenisService.getJenisses(searchWord);
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
}

