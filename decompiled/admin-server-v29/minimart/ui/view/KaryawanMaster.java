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
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import minimart.engine.component.UppercaseDocumentFilter;
import minimart.engine.model.Karyawan;
import minimart.engine.service.KaryawanService;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class KaryawanMaster
extends JDialog {
    private JButton deleteButton;
    private JButton exitButton;
    private JTextField namaField;
    private JLabel namaLabel;
    private JPasswordField passwordField;
    private JLabel passwordLabel;
    private JComboBox roleBox;
    private JLabel roleLabel;
    private JButton saveButton;
    private JTextField usernameField;
    private JLabel usernameLabel;
    private int screenWidth;
    private int screenHeight;
    private DocumentFilter filter = new UppercaseDocumentFilter();
    private KaryawanService karyawanService = new KaryawanService();
    private String action = "update";
    private Karyawan karyawan = new Karyawan();

    public KaryawanMaster(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.initComponents2();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setTitle("Entry Tabel Karyawan");
    }

    private void escapeClick() {
        this.usernameField.requestFocus();
        this.usernameField.selectAll();
    }

    private void setDefault() {
        this.namaField.setText("");
        this.passwordField.setText("");
        this.roleBox.setSelectedIndex(0);
    }

    private void deleteKaryawan() {
        int confirm = JOptionPane.showConfirmDialog(null, "Data Akan Dihapus?", "Delete Data Confirmation", 2);
        if (confirm == 0) {
            int result = this.karyawanService.deleteKaryawan(this.usernameField.getText());
            if (result == 1) {
                JOptionPane.showMessageDialog(null, "Data Telah Terhapus");
                this.escapeClick();
                this.setDefault();
                this.usernameField.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Penghapusan Data Gagal");
                this.escapeClick();
            }
        }
    }

    private void saveKaryawan() {
        if (this.action.equals("create")) {
            this.karyawan = new Karyawan();
        }
        this.karyawan.setId(this.usernameField.getText());
        this.karyawan.setNama(this.namaField.getText());
        this.karyawan.setRole(this.roleBox.getSelectedItem() + "");
        this.karyawan.setGaji(0L);
        this.karyawan.setPassword(this.passwordField.getText());
        this.karyawan.setAvailable(true);
        this.karyawan.setUpdated(new Date());
        int result = 0;
        if (this.action.equals("create")) {
            this.karyawan.setCreated(new Date());
            result = this.karyawanService.createKaryawan(this.karyawan);
        } else if (this.action.equals("update")) {
            result = this.karyawanService.updateKaryawan(this.karyawan);
        }
        if (result == 1) {
            JOptionPane.showMessageDialog(null, "Data Telah Tersimpan");
            this.escapeClick();
            this.setDefault();
            this.usernameField.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "Penyimpanan Data Gagal");
            this.escapeClick();
        }
    }

    private void loadKodeKaryawan() {
        this.action = "update";
        this.karyawan = this.karyawanService.getKaryawan(this.usernameField.getText());
        if (this.karyawan == null) {
            this.namaField.requestFocus();
            this.setDefault();
            this.action = "create";
        } else {
            this.namaField.setText(this.karyawan.getNama());
            this.roleBox.setSelectedItem(this.karyawan.getRole());
            this.passwordField.setText(this.karyawan.getPassword());
            this.action = "update";
            this.namaField.requestFocus();
        }
    }

    private void initComponents2() {
        ((AbstractDocument)this.usernameField.getDocument()).setDocumentFilter(this.filter);
        ((AbstractDocument)this.namaField.getDocument()).setDocumentFilter(this.filter);
        ((AbstractDocument)this.passwordField.getDocument()).setDocumentFilter(this.filter);
    }

    private void initComponents() {
        this.usernameLabel = new JLabel();
        this.namaLabel = new JLabel();
        this.passwordLabel = new JLabel();
        this.roleLabel = new JLabel();
        this.usernameField = new JTextField();
        this.namaField = new JTextField();
        this.roleBox = new JComboBox();
        this.saveButton = new JButton();
        this.deleteButton = new JButton();
        this.exitButton = new JButton();
        this.passwordField = new JPasswordField();
        this.setDefaultCloseOperation(2);
        this.usernameLabel.setText("Username");
        this.namaLabel.setText("Nama");
        this.passwordLabel.setText("Password");
        this.roleLabel.setText("Role");
        this.usernameField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                KaryawanMaster.this.usernameFieldKeyPressed(evt);
            }
        });
        this.namaField.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                KaryawanMaster.this.namaFieldActionPerformed(evt);
            }
        });
        this.namaField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                KaryawanMaster.this.namaFieldKeyPressed(evt);
            }
        });
        this.roleBox.setModel(new DefaultComboBoxModel<String>(new String[]{"KASIR", "MANAGER", "ADMIN", "INVENTORY", "STAF INVENTORY", "ACCOUNTING", "DIREKTUR"}));
        this.roleBox.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                KaryawanMaster.this.roleBoxActionPerformed(evt);
            }
        });
        this.roleBox.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                KaryawanMaster.this.roleBoxKeyPressed(evt);
            }
        });
        this.saveButton.setText("Save");
        this.saveButton.setPreferredSize(new Dimension(73, 23));
        this.saveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                KaryawanMaster.this.saveButtonActionPerformed(evt);
            }
        });
        this.saveButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                KaryawanMaster.this.saveButtonKeyPressed(evt);
            }
        });
        this.deleteButton.setText("Delete");
        this.deleteButton.setPreferredSize(new Dimension(73, 23));
        this.deleteButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                KaryawanMaster.this.deleteButtonActionPerformed(evt);
            }
        });
        this.deleteButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                KaryawanMaster.this.deleteButtonKeyPressed(evt);
            }
        });
        this.exitButton.setText("Exit");
        this.exitButton.setPreferredSize(new Dimension(73, 23));
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                KaryawanMaster.this.exitButtonActionPerformed(evt);
            }
        });
        this.exitButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                KaryawanMaster.this.exitButtonKeyPressed(evt);
            }
        });
        this.passwordField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                KaryawanMaster.this.passwordFieldKeyPressed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(32, 32, 32).addComponent(this.saveButton, -2, -1, -2)).addGroup(layout.createSequentialGroup().addGap(22, 22, 22).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.usernameLabel).addComponent(this.namaLabel).addComponent(this.roleLabel).addComponent(this.passwordLabel)))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addComponent(this.deleteButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.exitButton, -2, -1, -2)).addComponent(this.roleBox, -2, 96, -2).addComponent(this.usernameField, -1, 171, Short.MAX_VALUE).addComponent(this.namaField).addComponent(this.passwordField)).addContainerGap(28, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.usernameLabel).addComponent(this.usernameField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.namaLabel).addComponent(this.namaField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.roleBox, -2, -1, -2).addComponent(this.roleLabel)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.passwordLabel).addComponent(this.passwordField, -2, -1, -2)).addGap(41, 41, 41).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.saveButton, -2, -1, -2).addComponent(this.deleteButton, -2, -1, -2).addComponent(this.exitButton, -2, -1, -2)).addContainerGap(-1, Short.MAX_VALUE)));
        this.pack();
    }

    private void saveButtonActionPerformed(ActionEvent evt) {
        this.saveKaryawan();
    }

    private void namaFieldActionPerformed(ActionEvent evt) {
    }

    private void roleBoxActionPerformed(ActionEvent evt) {
    }

    private void usernameFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Karyawan");
            sd.addSearchDialogListener(new searchKaryawanDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        } else if (evt.getKeyCode() == 10) {
            if (this.usernameField.getText().equals("")) {
                SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Karyawan");
                sd.addSearchDialogListener(new searchKaryawanDialogEventHandler());
                int w = sd.getWidth();
                int h = sd.getHeight();
                sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                sd.setVisible(true);
            } else {
                this.loadKodeKaryawan();
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void namaFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.roleBox.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.usernameField.requestFocus();
            this.usernameField.selectAll();
        }
    }

    private void roleBoxKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.passwordField.requestFocus();
            this.passwordField.selectAll();
        } else if (evt.getKeyCode() == 37) {
            this.namaField.requestFocus();
            this.namaField.selectAll();
        }
    }

    private void saveButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.saveKaryawan();
        } else if (evt.getKeyCode() == 39) {
            this.deleteButton.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.passwordField.requestFocus();
            this.passwordField.selectAll();
        }
    }

    private void deleteButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        }
        if (evt.getKeyCode() == 10) {
            this.deleteKaryawan();
        } else if (evt.getKeyCode() == 39) {
            this.exitButton.requestFocus();
        } else if (evt.getKeyCode() == 37) {
            this.saveButton.requestFocus();
        }
    }

    private void deleteButtonActionPerformed(ActionEvent evt) {
        this.deleteKaryawan();
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

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void passwordFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.saveButton.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.roleBox.requestFocus();
        }
    }

    private class searchPanelKaryawanEventHandler
    implements SearchPanelListener {
        private searchPanelKaryawanEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            KaryawanMaster.this.usernameField.setText(chooseValue);
            KaryawanMaster.this.loadKodeKaryawan();
        }
    }

    private class searchKaryawanDialogEventHandler
    implements SearchDialogListener {
        private searchKaryawanDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"USERNAME", "NAMA", "ROLE"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Karyawan> karyawans = KaryawanMaster.this.karyawanService.getKaryawans(searchWord);
            for (Karyawan karyawan : karyawans) {
                Object[] temp = new String[5];
                temp[0] = karyawan.getId();
                temp[1] = karyawan.getNama();
                temp[2] = karyawan.getRole();
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Karyawan");
            sp.addSearchPanelListener(new searchPanelKaryawanEventHandler());
            sp.setVisible(true);
        }

        public void dispose() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}

