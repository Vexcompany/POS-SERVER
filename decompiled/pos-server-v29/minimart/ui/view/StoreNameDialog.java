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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import minimart.engine.component.UppercaseDocumentFilter;
import minimart.engine.controller.Helper;
import minimart.engine.model.Konstanta;
import minimart.engine.service.KonstantaService;

public class StoreNameDialog
extends JDialog {
    private JTextField alamatField;
    private JButton exitButton;
    private JTextField footerNota1Field;
    private JTextField footerNota2Field;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JTextField namaTokoField;
    private JTextField npwpField;
    private JButton saveButton;
    private JTextField telp1Field;
    private JTextField telp2Field;
    private KonstantaService konstantaService = new KonstantaService();
    private String status;
    private DocumentFilter filter = new UppercaseDocumentFilter();
    private Helper helper = Helper.getInstance();

    public StoreNameDialog(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.status = this.loadData();
        ((AbstractDocument)this.namaTokoField.getDocument()).setDocumentFilter(this.filter);
        ((AbstractDocument)this.alamatField.getDocument()).setDocumentFilter(this.filter);
    }

    private String loadData() {
        String namaToko = "";
        String alamatToko = "";
        String telp1 = "";
        String telp2 = "";
        String npwp = "";
        String footerNota1 = "";
        String footerNota2 = "";
        if (this.helper.getStatusProgram().equals("TOKO")) {
            namaToko = this.konstantaService.getConstantValue("namaToko");
            alamatToko = this.konstantaService.getConstantValue("alamatToko");
            telp1 = this.konstantaService.getConstantValue("telpToko1");
            telp2 = this.konstantaService.getConstantValue("telpToko2");
            npwp = this.konstantaService.getConstantValue("npwp");
            footerNota1 = this.konstantaService.getConstantValue("footerNota1");
            footerNota2 = this.konstantaService.getConstantValue("footerNota2");
        } else if (this.helper.getStatusProgram().equals("GUDANG")) {
            namaToko = this.konstantaService.getConstantValue("namaGudang");
            alamatToko = this.konstantaService.getConstantValue("alamatGudang");
            telp1 = this.konstantaService.getConstantValue("telpGudang1");
            telp2 = this.konstantaService.getConstantValue("telpGudang2");
        }
        this.namaTokoField.setText(namaToko);
        this.alamatField.setText(alamatToko);
        this.telp1Field.setText(telp1);
        this.telp2Field.setText(telp2);
        this.npwpField.setText(npwp);
        this.footerNota1Field.setText(footerNota1);
        this.footerNota2Field.setText(footerNota2);
        if (namaToko.equals("") && alamatToko.equals("") && telp1.equals("") && telp2.equals("")) {
            return "create";
        }
        return "update";
    }

    private void saveClick() {
        Konstanta namaToko = new Konstanta();
        Konstanta alamatToko = new Konstanta();
        Konstanta telpToko1 = new Konstanta();
        Konstanta telpToko2 = new Konstanta();
        Konstanta npwp = new Konstanta();
        Konstanta footerNota1 = new Konstanta();
        Konstanta footerNota2 = new Konstanta();
        if (this.helper.getStatusProgram().equals("TOKO")) {
            namaToko.setKonstan("namaToko");
            alamatToko.setKonstan("alamatToko");
            telpToko1.setKonstan("telpToko1");
            telpToko2.setKonstan("telpToko2");
            npwp.setKonstan("npwp");
            footerNota1.setKonstan("footerNota1");
            footerNota2.setKonstan("footerNota2");
        }
        namaToko.setValue(this.namaTokoField.getText());
        alamatToko.setValue(this.alamatField.getText());
        telpToko1.setValue(this.telp1Field.getText());
        telpToko2.setValue(this.telp2Field.getText());
        npwp.setValue(this.npwpField.getText());
        footerNota1.setValue(this.footerNota1Field.getText());
        footerNota2.setValue(this.footerNota2Field.getText());
        int result1 = 0;
        int result2 = 0;
        int result3 = 0;
        int result4 = 0;
        int result5 = 0;
        int result6 = 0;
        int result7 = 0;
        if (this.status.equals("create")) {
            result1 = this.konstantaService.createConstant(namaToko);
            result2 = this.konstantaService.createConstant(alamatToko);
            result3 = this.konstantaService.createConstant(telpToko1);
            result4 = this.konstantaService.createConstant(telpToko2);
            result5 = this.konstantaService.createConstant(npwp);
            result6 = this.konstantaService.createConstant(footerNota1);
            result7 = this.konstantaService.createConstant(footerNota2);
        } else if (this.status.equals("update")) {
            result1 = this.konstantaService.updateConstant(namaToko);
            result2 = this.konstantaService.updateConstant(alamatToko);
            result3 = this.konstantaService.updateConstant(telpToko1);
            result4 = this.konstantaService.updateConstant(telpToko2);
            result5 = this.konstantaService.updateConstant(npwp);
            result6 = this.konstantaService.updateConstant(footerNota1);
            result7 = this.konstantaService.updateConstant(footerNota2);
        }
        if (result1 == 1 && result2 == 1 && result3 == 1 && result4 == 1) {
            JOptionPane.showMessageDialog(null, "Data Berhasil Tersimpan");
            this.exitButton.requestFocus();
        } else {
            JOptionPane.showMessageDialog(null, "Penyimpanan Gagal");
        }
    }

    private void initComponents() {
        this.jLabel5 = new JLabel();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.namaTokoField = new JTextField();
        this.alamatField = new JTextField();
        this.telp1Field = new JTextField();
        this.telp2Field = new JTextField();
        this.exitButton = new JButton();
        this.saveButton = new JButton();
        this.jLabel6 = new JLabel();
        this.npwpField = new JTextField();
        this.jLabel7 = new JLabel();
        this.jLabel8 = new JLabel();
        this.footerNota1Field = new JTextField();
        this.footerNota2Field = new JTextField();
        this.jLabel5.setText("jLabel5");
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Nama");
        this.jLabel2.setText("Alamat");
        this.jLabel3.setText("Nomor Telp 1");
        this.jLabel4.setText("Nomor Telp 2");
        this.namaTokoField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                StoreNameDialog.this.namaTokoFieldKeyPressed(evt);
            }
        });
        this.alamatField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                StoreNameDialog.this.alamatFieldKeyPressed(evt);
            }
        });
        this.telp1Field.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                StoreNameDialog.this.telp1FieldKeyPressed(evt);
            }
        });
        this.telp2Field.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                StoreNameDialog.this.telp2FieldKeyPressed(evt);
            }
        });
        this.exitButton.setText("Exit");
        this.exitButton.setPreferredSize(new Dimension(72, 23));
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StoreNameDialog.this.exitButtonActionPerformed(evt);
            }
        });
        this.exitButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                StoreNameDialog.this.exitButtonKeyPressed(evt);
            }
        });
        this.saveButton.setText("Save");
        this.saveButton.setMaximumSize(new Dimension(72, 23));
        this.saveButton.setMinimumSize(new Dimension(72, 23));
        this.saveButton.setPreferredSize(new Dimension(72, 23));
        this.saveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StoreNameDialog.this.saveButtonActionPerformed(evt);
            }
        });
        this.saveButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                StoreNameDialog.this.saveButtonKeyPressed(evt);
            }
        });
        this.jLabel6.setText("NPWP");
        this.npwpField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                StoreNameDialog.this.npwpFieldKeyPressed(evt);
            }
        });
        this.jLabel7.setText("FooterNota1");
        this.jLabel8.setText("FooterNota2");
        this.footerNota1Field.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                StoreNameDialog.this.footerNota1FieldKeyPressed(evt);
            }
        });
        this.footerNota2Field.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                StoreNameDialog.this.footerNota2FieldKeyPressed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 196, Short.MAX_VALUE).addComponent(this.saveButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.exitButton, -2, -1, -2)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel2).addComponent(this.jLabel3).addComponent(this.jLabel4).addComponent(this.jLabel6).addComponent(this.jLabel7).addComponent(this.jLabel8)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.namaTokoField, -1, 279, Short.MAX_VALUE).addComponent(this.alamatField).addComponent(this.telp1Field).addComponent(this.telp2Field).addComponent(this.npwpField).addComponent(this.footerNota1Field).addComponent(this.footerNota2Field)))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.namaTokoField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.alamatField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.telp1Field, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.telp2Field, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel6).addComponent(this.npwpField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel7).addComponent(this.footerNota1Field, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel8).addComponent(this.footerNota2Field, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.exitButton, -2, -1, -2).addComponent(this.saveButton, -2, -1, -2)).addContainerGap()));
        this.pack();
    }

    private void namaTokoFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.alamatField.requestFocus();
            this.alamatField.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void alamatFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.telp1Field.requestFocus();
            this.telp1Field.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.namaTokoField.requestFocus();
            this.namaTokoField.selectAll();
        } else if (evt.getKeyCode() == 38) {
            this.namaTokoField.requestFocus();
            this.namaTokoField.selectAll();
        }
    }

    private void telp1FieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.telp2Field.requestFocus();
            this.telp2Field.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.namaTokoField.requestFocus();
            this.namaTokoField.selectAll();
        } else if (evt.getKeyCode() == 38) {
            this.alamatField.requestFocus();
            this.alamatField.selectAll();
        }
    }

    private void telp2FieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.npwpField.requestFocus();
            this.npwpField.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.namaTokoField.requestFocus();
            this.namaTokoField.selectAll();
        } else if (evt.getKeyCode() == 38) {
            this.telp1Field.requestFocus();
            this.telp1Field.selectAll();
        }
    }

    private void saveButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.saveClick();
        } else if (evt.getKeyCode() == 27) {
            this.namaTokoField.requestFocus();
            this.namaTokoField.selectAll();
        } else if (evt.getKeyCode() == 38) {
            this.npwpField.requestFocus();
            this.npwpField.selectAll();
        } else if (evt.getKeyCode() == 39) {
            this.exitButton.requestFocus();
        }
    }

    private void exitButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.dispose();
        } else if (evt.getKeyCode() == 27) {
            this.namaTokoField.requestFocus();
            this.namaTokoField.selectAll();
        } else if (evt.getKeyCode() == 38) {
            this.npwpField.requestFocus();
            this.npwpField.selectAll();
        } else if (evt.getKeyCode() == 37) {
            this.saveButton.requestFocus();
        }
    }

    private void saveButtonActionPerformed(ActionEvent evt) {
        this.saveClick();
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void npwpFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.footerNota1Field.requestFocus();
            this.footerNota1Field.selectAll();
        } else if (evt.getKeyCode() == 38) {
            this.telp2Field.requestFocus();
            this.telp2Field.selectAll();
        }
    }

    private void footerNota1FieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.footerNota2Field.requestFocus();
            this.footerNota2Field.selectAll();
        } else if (evt.getKeyCode() == 38) {
            this.npwpField.requestFocus();
            this.npwpField.selectAll();
        }
    }

    private void footerNota2FieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.saveButton.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.footerNota1Field.requestFocus();
            this.footerNota1Field.selectAll();
        }
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
            Logger.getLogger(StoreNameDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex) {
            Logger.getLogger(StoreNameDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex) {
            Logger.getLogger(StoreNameDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(StoreNameDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                StoreNameDialog dialog = new StoreNameDialog((Frame)new JFrame(), true);
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

