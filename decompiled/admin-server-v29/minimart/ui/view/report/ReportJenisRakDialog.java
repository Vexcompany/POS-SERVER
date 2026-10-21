/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view.report;

import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import minimart.engine.component.UppercaseDocumentFilter;
import minimart.engine.model.Jenis;
import minimart.engine.service.JenisService;
import minimart.ui.listener.ReportJenisRakDialogListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class ReportJenisRakDialog
extends JDialog {
    private JButton cancelButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JTextField jenisBarangField;
    private JTextField rakField;
    private JButton tampilkanButton;
    private JenisService jenisService = new JenisService();
    private DocumentFilter filter = new UppercaseDocumentFilter();
    private Jenis jenis;
    private int screenHeight;
    private int screenWidth;
    private ReportJenisRakDialogListener reportStokTerakhirListener;

    public ReportJenisRakDialog(Frame parent, boolean modal, String title) {
        super(parent, modal);
        this.initComponents();
        ((AbstractDocument)this.jenisBarangField.getDocument()).setDocumentFilter(this.filter);
        ((AbstractDocument)this.rakField.getDocument()).setDocumentFilter(this.filter);
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setTitle(title);
    }

    public void addListener(ReportJenisRakDialogListener listener) {
        this.reportStokTerakhirListener = listener;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jenisBarangField = new JTextField();
        this.cancelButton = new JButton();
        this.tampilkanButton = new JButton();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.rakField = new JTextField();
        this.jLabel4 = new JLabel();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Jenis Barang");
        this.jenisBarangField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportJenisRakDialog.this.jenisBarangFieldKeyPressed(evt);
            }
        });
        this.cancelButton.setText("Cancel");
        this.cancelButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportJenisRakDialog.this.cancelButtonActionPerformed(evt);
            }
        });
        this.cancelButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportJenisRakDialog.this.cancelButtonKeyPressed(evt);
            }
        });
        this.tampilkanButton.setText("Tampilkan");
        this.tampilkanButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportJenisRakDialog.this.tampilkanButtonActionPerformed(evt);
            }
        });
        this.tampilkanButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportJenisRakDialog.this.tampilkanButtonKeyPressed(evt);
            }
        });
        this.jLabel2.setText("Kosong=semua");
        this.jLabel3.setText("Rak");
        this.rakField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportJenisRakDialog.this.rakFieldKeyPressed(evt);
            }
        });
        this.jLabel4.setText("Kosong=semua");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addComponent(this.tampilkanButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cancelButton)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addGap(47, 47, 47).addComponent(this.jenisBarangField, -2, 180, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel3).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.rakField, -2, 88, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel4))).addContainerGap(31, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.jenisBarangField, -2, -1, -2).addComponent(this.jLabel2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.rakField, -2, -1, -2).addComponent(this.jLabel4)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.cancelButton).addComponent(this.tampilkanButton)).addContainerGap()));
        this.pack();
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
                this.rakField.requestFocus();
                this.rakField.selectAll();
            } else {
                Jenis jenisTemp = this.jenisService.getJenis(this.jenisBarangField.getText());
                if (jenisTemp == null) {
                    JOptionPane.showMessageDialog(null, "Jenis Barang Tidak Ditemukan");
                    this.jenisBarangField.selectAll();
                } else {
                    this.rakField.requestFocus();
                    this.rakField.selectAll();
                }
            }
        }
    }

    private void tampilkanButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27 || evt.getKeyCode() == 38 || evt.getKeyCode() == 37) {
            this.jenisBarangField.requestFocus();
            this.jenisBarangField.selectAll();
        } else if (evt.getKeyCode() == 39) {
            this.cancelButton.requestFocus();
        } else if (evt.getKeyCode() == 10) {
            this.dispose();
            this.reportStokTerakhirListener.tampilkanClick(this.jenisBarangField.getText(), this.rakField.getText());
        }
    }

    private void cancelButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.jenisBarangField.requestFocus();
            this.jenisBarangField.selectAll();
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 37) {
            this.tampilkanButton.requestFocus();
        } else if (evt.getKeyCode() == 39) {
            this.cancelButton.requestFocus();
        } else if (evt.getKeyCode() == 10) {
            this.dispose();
        }
    }

    private void tampilkanButtonActionPerformed(ActionEvent evt) {
        this.dispose();
        this.reportStokTerakhirListener.tampilkanClick(this.jenisBarangField.getText(), this.rakField.getText());
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void rakFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.tampilkanButton.requestFocus();
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 37 || evt.getKeyCode() == 27) {
            this.jenisBarangField.requestFocus();
            this.jenisBarangField.selectAll();
        }
    }

    private class searchRakPanelEventHandler
    implements SearchPanelListener {
        private searchRakPanelEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            ReportJenisRakDialog.this.rakField.setText(chooseValue);
            ReportJenisRakDialog.this.tampilkanButton.requestFocus();
        }
    }

    private class searchPanelJenisEventHandler
    implements SearchPanelListener {
        private searchPanelJenisEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            ReportJenisRakDialog.this.jenis = ReportJenisRakDialog.this.jenisService.getJenis(chooseValue);
            ReportJenisRakDialog.this.jenisBarangField.setText(chooseValue);
            ReportJenisRakDialog.this.rakField.requestFocus();
            ReportJenisRakDialog.this.rakField.selectAll();
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
            List<Jenis> jenisses = ReportJenisRakDialog.this.jenisService.getJenisses(searchWord);
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

