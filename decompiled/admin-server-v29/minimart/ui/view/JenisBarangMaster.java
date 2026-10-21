/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.Date;
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
import minimart.engine.model.Jenis;
import minimart.engine.model.Karyawan;
import minimart.engine.service.JenisService;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class JenisBarangMaster
extends JDialog {
    private JButton deleteButton;
    private JButton exitButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JTextField jenisBarangField;
    private JLabel jenisBarangLbl;
    private JTextField marginField;
    private JTextField marginTempoField;
    private JFormattedTextField poinSalesField;
    private JButton saveButton;
    private JLabel statusLbl;
    private DocumentFilter filter = new UppercaseDocumentFilter();
    private JenisService jenisService = new JenisService();
    private int screenHeight;
    private int screenWidth;
    private Jenis jenis;
    private Helper helper = Helper.getInstance();

    public JenisBarangMaster(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        ((AbstractDocument)this.jenisBarangField.getDocument()).setDocumentFilter(this.filter);
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setTitle("Entry Tabel Jenis Barang");
        Karyawan karyawan = this.helper.getKaryawan();
        if (karyawan.getRole().equalsIgnoreCase("ADMIN")) {
            this.deleteButton.setEnabled(false);
        }
    }

    private void initComponents() {
        this.jenisBarangLbl = new JLabel();
        this.jenisBarangField = new JTextField();
        this.saveButton = new JButton();
        this.deleteButton = new JButton();
        this.exitButton = new JButton();
        this.statusLbl = new JLabel();
        this.jLabel1 = new JLabel();
        this.poinSalesField = new JFormattedTextField();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.marginField = new JTextField();
        this.jLabel4 = new JLabel();
        this.jLabel5 = new JLabel();
        this.marginTempoField = new JTextField();
        this.jLabel6 = new JLabel();
        this.setDefaultCloseOperation(2);
        this.addHierarchyBoundsListener(new HierarchyBoundsListener(){

            @Override
            public void ancestorMoved(HierarchyEvent evt) {
                JenisBarangMaster.this.formAncestorMoved(evt);
            }

            @Override
            public void ancestorResized(HierarchyEvent evt) {
            }
        });
        this.jenisBarangLbl.setText("Jenis Barang");
        this.jenisBarangField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                JenisBarangMaster.this.jenisBarangFieldKeyPressed(evt);
            }
        });
        this.saveButton.setText("Save");
        this.saveButton.setPreferredSize(new Dimension(73, 23));
        this.saveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JenisBarangMaster.this.saveButtonActionPerformed(evt);
            }
        });
        this.saveButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                JenisBarangMaster.this.saveButtonKeyPressed(evt);
            }
        });
        this.deleteButton.setText("Delete");
        this.deleteButton.setPreferredSize(new Dimension(73, 23));
        this.deleteButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JenisBarangMaster.this.deleteButtonActionPerformed(evt);
            }
        });
        this.deleteButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                JenisBarangMaster.this.deleteButtonKeyPressed(evt);
            }
        });
        this.exitButton.setText("Exit");
        this.exitButton.setMaximumSize(new Dimension(73, 23));
        this.exitButton.setPreferredSize(new Dimension(73, 23));
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                JenisBarangMaster.this.exitButtonActionPerformed(evt);
            }
        });
        this.exitButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                JenisBarangMaster.this.exitButtonKeyPressed(evt);
            }
        });
        this.statusLbl.setPreferredSize(new Dimension(34, 14));
        this.jLabel1.setText("Poin Sales");
        this.poinSalesField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.poinSalesField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                JenisBarangMaster.this.poinSalesFieldKeyPressed(evt);
            }
        });
        this.jLabel2.setText("/1Poin");
        this.jLabel3.setText("Margin Cabang");
        this.marginField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                JenisBarangMaster.this.marginFieldKeyPressed(evt);
            }
        });
        this.jLabel4.setText("%");
        this.jLabel5.setText("Margin Tempo");
        this.marginTempoField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                JenisBarangMaster.this.marginTempoFieldKeyPressed(evt);
            }
        });
        this.jLabel6.setText("%");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.saveButton, -2, -1, -2).addGap(18, 18, 18).addComponent(this.deleteButton, -2, 80, -2).addGap(18, 18, 18).addComponent(this.exitButton, -2, 79, -2).addGap(116, 116, 116)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jenisBarangLbl, -2, 106, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.poinSalesField).addGap(18, 18, 18).addComponent(this.jLabel2)).addComponent(this.jenisBarangField)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.statusLbl, -2, 86, -2).addContainerGap()).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3).addComponent(this.jLabel5)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.marginTempoField, -1, 50, Short.MAX_VALUE).addComponent(this.marginField)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel4).addComponent(this.jLabel6)).addGap(203, 203, 203)))));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jenisBarangLbl).addComponent(this.jenisBarangField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.poinSalesField, -2, -1, -2))).addGroup(layout.createSequentialGroup().addComponent(this.statusLbl, -2, -1, -2).addGap(28, 28, 28).addComponent(this.jLabel2))).addGap(13, 13, 13).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.marginField, -2, -1, -2).addComponent(this.jLabel4)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5).addComponent(this.marginTempoField, -2, -1, -2).addComponent(this.jLabel6)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.saveButton, -2, -1, -2).addComponent(this.deleteButton, -2, -1, -2).addComponent(this.exitButton, -2, -1, -2)).addContainerGap()));
        this.pack();
    }

    private void saveJenis() {
        if (this.jenis == null) {
            this.jenis = new Jenis();
            if (!this.jenisBarangField.getText().equals("")) {
                this.jenis.setJenis(this.jenisBarangField.getText());
                this.jenis.setPoinSales(Integer.parseInt(this.poinSalesField.getValue().toString()));
                this.jenis.setCreated(new Date());
                this.jenis.setUpdated(new Date());
                this.jenis.setMarginCabang(Float.parseFloat(this.marginField.getText()));
                this.jenis.setMarginTempo(Float.parseFloat(this.marginTempoField.getText()));
                int result = this.jenisService.createJenis(this.jenis);
                if (result == 1) {
                    JOptionPane.showMessageDialog(null, "Data Telah Tersimpan");
                    this.jenisBarangField.requestFocus();
                    this.jenisBarangField.setText("");
                    this.jenis = null;
                } else {
                    JOptionPane.showMessageDialog(null, "Penyimpanan Data Gagal");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Masukkan Jenis Barang Terlebih Dahulu");
            }
        } else {
            this.jenis.setJenis(this.jenisBarangField.getText());
            this.jenis.setPoinSales(Integer.parseInt(this.poinSalesField.getValue() + ""));
            this.jenis.setMarginCabang(Float.parseFloat(this.marginField.getText()));
            this.jenis.setMarginTempo(Float.parseFloat(this.marginTempoField.getText()));
            int update = this.jenisService.updateJenis(this.jenis);
            if (update == 1) {
                JOptionPane.showMessageDialog(null, "Penyimpanan Data Berhasil");
                this.clearData();
            } else if (update == 0) {
                JOptionPane.showMessageDialog(null, "Penyimpanan Data Gagal");
            }
        }
    }

    private void clearData() {
        this.jenisBarangField.setText("");
        this.poinSalesField.setText("");
        this.marginField.setText("");
        this.jenisBarangField.requestFocus();
        this.jenisBarangField.selectAll();
        this.marginTempoField.setText("");
    }

    private void deleteJenis() {
        int confirm = JOptionPane.showConfirmDialog(null, "Data Akan Dihapus?", "Delete Data Confirmation", 2);
        if (confirm == 0) {
            int result = this.jenisService.deleteJenis(this.jenisBarangField.getText());
            if (result == 1) {
                JOptionPane.showMessageDialog(null, "Data Telah Terhapus");
                this.jenisBarangField.requestFocus();
                this.jenisBarangField.setText("");
                this.jenis = null;
            } else {
                JOptionPane.showMessageDialog(null, "Penghapusan Data Gagal");
            }
        }
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
                this.jenis = this.jenisService.getJenis(this.jenisBarangField.getText());
                if (this.jenis == null) {
                    this.statusLbl.setText("Data Baru");
                } else {
                    this.statusLbl.setText("");
                    this.poinSalesField.setValue(this.jenis.getPoinSales());
                    this.marginField.setText(this.jenis.getMarginCabang() + "");
                }
                this.poinSalesField.requestFocus();
                this.poinSalesField.setText(this.poinSalesField.getText().replace(".", ""));
                this.poinSalesField.selectAll();
            }
        }
    }

    private void saveButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.saveJenis();
        } else if (evt.getKeyCode() == 27) {
            this.jenisBarangField.requestFocus();
            this.jenisBarangField.selectAll();
        } else if (evt.getKeyCode() == 38) {
            this.marginTempoField.requestFocus();
            this.marginTempoField.selectAll();
        } else if (evt.getKeyCode() == 39) {
            this.deleteButton.requestFocus();
        }
    }

    private void deleteButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 37) {
            this.poinSalesField.requestFocus();
            this.poinSalesField.setText(this.poinSalesField.getText().replace(".", ""));
            this.poinSalesField.selectAll();
        } else if (evt.getKeyCode() == 39) {
            this.exitButton.requestFocus();
        } else if (evt.getKeyCode() == 10) {
            this.deleteJenis();
        } else if (evt.getKeyCode() == 27) {
            this.jenisBarangField.requestFocus();
            this.jenisBarangField.selectAll();
        }
    }

    private void saveButtonActionPerformed(ActionEvent evt) {
        this.saveJenis();
    }

    private void deleteButtonActionPerformed(ActionEvent evt) {
        this.deleteJenis();
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void exitButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.dispose();
        } else if (evt.getKeyCode() == 37) {
            this.deleteButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.jenisBarangField.requestFocus();
            this.jenisBarangField.selectAll();
        }
    }

    private void formAncestorMoved(HierarchyEvent evt) {
    }

    private void poinSalesFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.marginField.requestFocus();
            this.marginField.selectAll();
        } else if (evt.getKeyCode() == 38) {
            this.jenisBarangField.requestFocus();
            this.jenisBarangField.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.jenisBarangField.requestFocus();
            this.jenisBarangField.selectAll();
        }
    }

    private void marginFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.helper.isFloat(this.marginField.getText())) {
                this.marginTempoField.requestFocus();
                this.marginTempoField.selectAll();
            } else {
                JOptionPane.showMessageDialog(null, "Masukkan Data Dengan benar");
            }
        } else if (evt.getKeyCode() == 38) {
            this.poinSalesField.requestFocus();
            this.poinSalesField.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.jenisBarangField.requestFocus();
            this.jenisBarangField.selectAll();
        }
    }

    private void marginTempoFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.helper.isFloat(this.marginField.getText())) {
                this.saveButton.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "Masukkan Data Dengan benar");
            }
        } else if (evt.getKeyCode() == 38) {
            this.marginField.requestFocus();
            this.marginField.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.jenisBarangField.requestFocus();
            this.jenisBarangField.selectAll();
        }
    }

    private class searchPanelJenisEventHandler
    implements SearchPanelListener {
        private searchPanelJenisEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            JenisBarangMaster.this.jenis = JenisBarangMaster.this.jenisService.getJenis(chooseValue);
            JenisBarangMaster.this.jenisBarangField.setText(chooseValue);
            JenisBarangMaster.this.poinSalesField.requestFocus();
            JenisBarangMaster.this.poinSalesField.setValue(JenisBarangMaster.this.jenis.getPoinSales());
            JenisBarangMaster.this.marginField.setText(JenisBarangMaster.this.jenis.getMarginCabang() + "");
            JenisBarangMaster.this.marginTempoField.setText(JenisBarangMaster.this.jenis.getMarginTempo() + "");
        }
    }

    private class searchJenisDialogEventHandler
    implements SearchDialogListener {
        private searchJenisDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"JENIS", "POIN SALES", "MARGIN CABANG", "MARGIN TEMPO"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Jenis> jenisses = JenisBarangMaster.this.jenisService.getJenisses(searchWord);
            for (Jenis jenis : jenisses) {
                Object[] temp = new Object[]{jenis.getJenis(), jenis.getPoinSales(), Float.valueOf(jenis.getMarginCabang()), Float.valueOf(jenis.getMarginTempo())};
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

