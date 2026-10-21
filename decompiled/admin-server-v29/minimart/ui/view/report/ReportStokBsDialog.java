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
import minimart.ui.listener.ReportStokBsBarangDialogListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class ReportStokBsDialog
extends JDialog {
    private JButton cancelButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JTextField jenisBarangField;
    private JButton tampilkanButton;
    private JenisService jenisService = new JenisService();
    private DocumentFilter filter = new UppercaseDocumentFilter();
    private Jenis jenis;
    private int screenHeight;
    private int screenWidth;
    private ReportStokBsBarangDialogListener reportStokBsBarangDialogListener;

    public ReportStokBsDialog(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        ((AbstractDocument)this.jenisBarangField.getDocument()).setDocumentFilter(this.filter);
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setTitle("Report Stok BS Barang");
    }

    public void addListener(ReportStokBsBarangDialogListener listener) {
        this.reportStokBsBarangDialogListener = listener;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jenisBarangField = new JTextField();
        this.cancelButton = new JButton();
        this.tampilkanButton = new JButton();
        this.jLabel2 = new JLabel();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Jenis Barang");
        this.jenisBarangField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportStokBsDialog.this.jenisBarangFieldKeyPressed(evt);
            }
        });
        this.cancelButton.setText("Cancel");
        this.cancelButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportStokBsDialog.this.cancelButtonActionPerformed(evt);
            }
        });
        this.cancelButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportStokBsDialog.this.cancelButtonKeyPressed(evt);
            }
        });
        this.tampilkanButton.setText("Tampilkan");
        this.tampilkanButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportStokBsDialog.this.tampilkanButtonActionPerformed(evt);
            }
        });
        this.tampilkanButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportStokBsDialog.this.tampilkanButtonKeyPressed(evt);
            }
        });
        this.jLabel2.setText("Kosong=semua");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addComponent(this.tampilkanButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cancelButton)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addGap(47, 47, 47).addComponent(this.jenisBarangField, -2, 180, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel2).addContainerGap(31, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.jenisBarangField, -2, -1, -2).addComponent(this.jLabel2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.cancelButton).addComponent(this.tampilkanButton)).addContainerGap(-1, Short.MAX_VALUE)));
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
                this.tampilkanButton.requestFocus();
            } else {
                Jenis jenisTemp = this.jenisService.getJenis(this.jenisBarangField.getText());
                if (jenisTemp == null) {
                    JOptionPane.showMessageDialog(null, "Jenis Barang Tidak Ditemukan");
                } else {
                    this.tampilkanButton.requestFocus();
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
            this.reportStokBsBarangDialogListener.tampilkanClick(this.jenisBarangField.getText());
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
        this.reportStokBsBarangDialogListener.tampilkanClick(this.jenisBarangField.getText());
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private class searchPanelJenisEventHandler
    implements SearchPanelListener {
        private searchPanelJenisEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            ReportStokBsDialog.this.jenis = ReportStokBsDialog.this.jenisService.getJenis(chooseValue);
            ReportStokBsDialog.this.jenisBarangField.setText(chooseValue);
            ReportStokBsDialog.this.tampilkanButton.requestFocus();
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
            List<Jenis> jenisses = ReportStokBsDialog.this.jenisService.getJenisses(searchWord);
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

