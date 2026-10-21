/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import minimart.engine.component.UppercaseDocumentFilter;
import minimart.engine.controller.Helper;
import minimart.engine.model.Karyawan;
import minimart.engine.service.KaryawanService;
import minimart.engine.service.KonstantaService;

public class ChangePassDialog
extends JDialog {
    private JPasswordField alternatePass;
    private JLabel alternatePassLbl;
    private JButton cancelButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JPasswordField newPasswordField1;
    private JPasswordField newPasswordField2;
    private JPasswordField oldPasswordField;
    private JButton saveButton;
    private JTextField usernameField;
    private Helper helper = Helper.getInstance();
    private KaryawanService karyawanService = new KaryawanService();
    private DocumentFilter filter = new UppercaseDocumentFilter();
    private KonstantaService konstantaService = new KonstantaService();

    public ChangePassDialog(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        Karyawan karyawan = this.helper.getKaryawan();
        this.usernameField.setText(karyawan.getId());
        this.alternatePass.setText(this.konstantaService.getConstantValue("alternatifPas"));
        ((AbstractDocument)this.oldPasswordField.getDocument()).setDocumentFilter(this.filter);
        ((AbstractDocument)this.newPasswordField1.getDocument()).setDocumentFilter(this.filter);
        ((AbstractDocument)this.newPasswordField2.getDocument()).setDocumentFilter(this.filter);
        if (this.helper.getKaryawan().getRole().equals("DIREKTUR")) {
            this.alternatePass.setVisible(true);
            this.alternatePassLbl.setVisible(true);
        } else {
            this.alternatePass.setVisible(false);
            this.alternatePassLbl.setVisible(false);
        }
    }

    private void setDefault() {
        this.oldPasswordField.setText("");
        this.newPasswordField1.setText("");
        this.newPasswordField2.setText("");
        this.alternatePass.setText("");
    }

    private void saveClick() {
        int savePas = 0;
        int saveAlt = 0;
        if (this.oldPasswordField.getText().equals(this.helper.getKaryawan().getPassword())) {
            if (this.newPasswordField1.getText().equals(this.newPasswordField2.getText())) {
                Karyawan karyawan = this.helper.getKaryawan();
                karyawan.setPassword(this.newPasswordField1.getText());
                karyawan.setUpdated(new Date());
                savePas = this.karyawanService.updateKaryawanPassword(karyawan);
            } else {
                savePas = 0;
            }
        } else {
            savePas = 0;
        }
        System.out.println("newpas=" + this.alternatePass.getText());
        saveAlt = this.konstantaService.updateConstant("alternatifPas", this.alternatePass.getText());
        if (savePas == 1 && saveAlt == 1) {
            JOptionPane.showMessageDialog(null, "Penyimpanan Data Perhasil");
            this.setDefault();
        } else {
            JOptionPane.showMessageDialog(null, "Penyimpanan Data Gagal");
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.usernameField = new JTextField();
        this.jLabel4 = new JLabel();
        this.oldPasswordField = new JPasswordField();
        this.newPasswordField1 = new JPasswordField();
        this.newPasswordField2 = new JPasswordField();
        this.cancelButton = new JButton();
        this.saveButton = new JButton();
        this.alternatePassLbl = new JLabel();
        this.alternatePass = new JPasswordField();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Username");
        this.jLabel2.setText("Password Lama");
        this.jLabel3.setText("Password Baru");
        this.usernameField.setEditable(false);
        this.usernameField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ChangePassDialog.this.usernameFieldKeyPressed(evt);
            }
        });
        this.jLabel4.setText("Ulangi Password");
        this.oldPasswordField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ChangePassDialog.this.oldPasswordFieldKeyPressed(evt);
            }
        });
        this.newPasswordField1.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ChangePassDialog.this.newPasswordField1KeyPressed(evt);
            }
        });
        this.newPasswordField2.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ChangePassDialog.this.newPasswordField2KeyPressed(evt);
            }
        });
        this.cancelButton.setText("Cancel");
        this.cancelButton.setPreferredSize(new Dimension(73, 23));
        this.cancelButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ChangePassDialog.this.cancelButtonActionPerformed(evt);
            }
        });
        this.cancelButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ChangePassDialog.this.cancelButtonKeyPressed(evt);
            }
        });
        this.saveButton.setText("Save");
        this.saveButton.setPreferredSize(new Dimension(73, 23));
        this.saveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ChangePassDialog.this.saveButtonActionPerformed(evt);
            }
        });
        this.saveButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ChangePassDialog.this.saveButtonKeyPressed(evt);
            }
        });
        this.alternatePassLbl.setText("Alternate Pass");
        this.alternatePass.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ChangePassDialog.this.alternatePassKeyPressed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(33, 33, 33).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addComponent(this.saveButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cancelButton, -2, -1, -2)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel2).addComponent(this.jLabel3).addComponent(this.jLabel4).addComponent(this.alternatePassLbl)).addGap(27, 27, 27).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.usernameField).addComponent(this.oldPasswordField).addComponent(this.newPasswordField1).addComponent(this.newPasswordField2, -1, 167, Short.MAX_VALUE).addComponent(this.alternatePass, -1, 167, Short.MAX_VALUE)))).addContainerGap(58, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.usernameField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.oldPasswordField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.newPasswordField1, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.newPasswordField2, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.alternatePassLbl).addComponent(this.alternatePass, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.cancelButton, -2, -1, -2).addComponent(this.saveButton, -2, -1, -2)).addContainerGap()));
        this.pack();
    }

    private void oldPasswordFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.newPasswordField1.requestFocus();
            this.newPasswordField1.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void newPasswordField1KeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.newPasswordField2.requestFocus();
            this.newPasswordField2.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.oldPasswordField.requestFocus();
            this.oldPasswordField.selectAll();
        } else if (evt.getKeyCode() == 38) {
            this.oldPasswordField.requestFocus();
            this.oldPasswordField.selectAll();
        }
    }

    private void newPasswordField2KeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.saveButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.oldPasswordField.requestFocus();
            this.oldPasswordField.selectAll();
        } else if (evt.getKeyCode() == 38) {
            this.newPasswordField1.requestFocus();
            this.newPasswordField1.selectAll();
        }
    }

    private void saveButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.saveClick();
        } else if (evt.getKeyCode() == 27) {
            this.oldPasswordField.requestFocus();
            this.oldPasswordField.selectAll();
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 37) {
            this.newPasswordField2.requestFocus();
            this.newPasswordField2.selectAll();
        } else if (evt.getKeyCode() == 39) {
            this.cancelButton.requestFocus();
        }
    }

    private void cancelButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.dispose();
        } else if (evt.getKeyCode() == 27) {
            this.oldPasswordField.requestFocus();
            this.oldPasswordField.selectAll();
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 37) {
            this.saveButton.requestFocus();
        }
    }

    private void usernameFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.oldPasswordField.requestFocus();
            this.oldPasswordField.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void alternatePassKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.saveButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void saveButtonActionPerformed(ActionEvent evt) {
        this.saveClick();
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (!"Nimbus".equals(info.getName())) continue;
                UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(ChangePassDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex) {
            Logger.getLogger(ChangePassDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex) {
            Logger.getLogger(ChangePassDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(ChangePassDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                ChangePassDialog dialog = new ChangePassDialog((Frame)new JFrame(), true);
                dialog.addWindowListener(new WindowAdapter(){

                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
}

