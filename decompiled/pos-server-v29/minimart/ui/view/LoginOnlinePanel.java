/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.StandardOpenOption;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import minimart.engine.component.UppercaseDocumentFilter;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.controller.Controller;
import minimart.engine.controller.Helper;
import minimart.engine.json_response.JsonResponseLoginOnline;
import minimart.engine.model.DbCredential;
import minimart.engine.model.Karyawan;
import minimart.engine.model.Karyawan_1;
import minimart.engine.service.KaryawanService;
import minimart.engine.service.KonstantaService;
import minimart.ui.listener.LoginOnlineListener;

public class LoginOnlinePanel
extends JPanel {
    Helper helper = Helper.getInstance();
    private boolean loginProcess = false;
    Controller controller;
    private JButton exitButton;
    private JLabel jLabel1;
    private JButton okButton;
    private JLabel passwodLbl;
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JLabel usernameLbl;
    private JLabel versionLbl;
    private KaryawanService kService = new KaryawanService();
    private LoginOnlineListener listener;
    private DocumentFilter filter = new UppercaseDocumentFilter();
    private KonstantaService konstantaService = new KonstantaService();
    private int version;
    private String statusSoftware;

    public LoginOnlinePanel(int version1, Controller controller) {
        this.version = version1;
        this.controller = controller;
        this.initComponents();
        this.versionLbl.setText("version: " + version1);
        ((AbstractDocument)this.usernameField.getDocument()).setDocumentFilter(this.filter);
        ((AbstractDocument)this.passwordField.getDocument()).setDocumentFilter(this.filter);
        this.statusSoftware = "POS";
        String userHome = System.getProperty("user.home");
        File file = this.statusSoftware.equals("ADMIN") ? new File(userHome, "my.lock2") : new File(userHome, "my.lock1");
        try {
            FileChannel fc = FileChannel.open(file.toPath(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            FileLock lock = fc.tryLock();
            if (lock == null) {
                JOptionPane.showMessageDialog(null, "Tidak boleh membuka 2 program bersamaan");
                System.exit(0);
            }
        }
        catch (IOException e) {
            throw new Error(e);
        }
    }

    public void setDefaultFocus() {
        this.usernameField.requestFocus();
        this.usernameField.selectAll();
    }

    private void initComponents() {
        this.usernameLbl = new JLabel();
        this.passwodLbl = new JLabel();
        this.okButton = new JButton();
        this.exitButton = new JButton();
        this.usernameField = new JTextField();
        this.passwordField = new JPasswordField();
        this.versionLbl = new JLabel();
        this.jLabel1 = new JLabel();
        this.setPreferredSize(new Dimension(293, 100));
        this.usernameLbl.setText("Username");
        this.passwodLbl.setText("Password");
        this.okButton.setText("Ok");
        this.okButton.setPreferredSize(new Dimension(73, 28));
        this.okButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                LoginOnlinePanel.this.okButtonActionPerformed(evt);
            }
        });
        this.okButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                LoginOnlinePanel.this.okButtonKeyPressed(evt);
            }
        });
        this.exitButton.setText("Exit");
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                LoginOnlinePanel.this.exitButtonActionPerformed(evt);
            }
        });
        this.exitButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                LoginOnlinePanel.this.exitButtonKeyPressed(evt);
            }
        });
        this.usernameField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                LoginOnlinePanel.this.usernameFieldKeyPressed(evt);
            }
        });
        this.passwordField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                LoginOnlinePanel.this.passwordFieldKeyPressed(evt);
            }
        });
        this.versionLbl.setText("Version");
        this.jLabel1.setText("ONLINE");
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap(41, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.usernameLbl, -1, -1, Short.MAX_VALUE).addComponent(this.passwodLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.usernameField).addComponent(this.passwordField, -2, 145, -2))).addGroup(layout.createSequentialGroup().addGap(89, 89, 89).addComponent(this.okButton, -2, 77, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.exitButton, -2, 77, -2))).addGap(39, 39, 39)).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.versionLbl).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jLabel1).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap(-1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.usernameLbl).addComponent(this.usernameField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.passwodLbl).addComponent(this.passwordField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.okButton, -2, -1, -2).addComponent(this.exitButton)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.versionLbl).addComponent(this.jLabel1)).addContainerGap()));
    }

    private void okButtonActionPerformed(ActionEvent evt) {
        this.userCheck();
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        System.exit(0);
    }

    private void usernameFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.passwordField.requestFocus();
            this.passwordField.selectAll();
        }
    }

    private void passwordFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.okButton.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.usernameField.requestFocus();
            this.usernameField.selectAll();
        }
    }

    private void okButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.userCheck();
        } else if (evt.getKeyCode() == 39) {
            this.exitButton.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.passwordField.requestFocus();
            this.passwordField.selectAll();
        }
    }

    private void exitButtonKeyPressed(KeyEvent evt) {
        System.exit(0);
    }

    private void userCheck() {
        if (!this.loginProcess) {
            this.loginProcess = true;
            JsonResponseLoginOnline res = this.kService.authLoginOnline(this.usernameField.getText(), this.passwordField.getText());
            if (res.getStatus().equals("success")) {
                Karyawan_1 userOnline = res.getData().getKaryawan();
                DbCredential[] dbCreds = res.getData().getList_cabang();
                Karyawan user = new Karyawan();
                user.setId(userOnline.getId());
                user.setNama(userOnline.getNama());
                user.setRole(userOnline.getRole());
                user.setPassword(userOnline.getPassword());
                this.helper.setKaryawan(user);
                DbCredential selected = null;
                DbCredential cmConf = new DbCredential();
                while (true) {
                    Object[] cabangNames = new String[dbCreds.length];
                    for (int i = 0; i < dbCreds.length; ++i) {
                        cabangNames[i] = dbCreds[i].getNama_cabang();
                    }
                    String selectedName = (String)JOptionPane.showInputDialog(null, "Pilih Cabang", "Cabang", 3, null, cabangNames, cabangNames[0]);
                    if (selectedName == null) {
                        this.loginProcess = false;
                        this.listener.onLoginFailed();
                        System.exit(0);
                    }
                    for (int i = 0; i < dbCreds.length; ++i) {
                        if (!dbCreds[i].getNama_cabang().equals(selectedName)) continue;
                        selected = dbCreds[i];
                        break;
                    }
                    if (selected == null) {
                        JOptionPane.showMessageDialog(null, "Cabang tidak ditemukan.");
                        continue;
                    }
                    cmConf = selected;
                    if (this.helper.isIsServer()) {
                        cmConf.setServer_ip_address("localhost");
                    }
                    if ("Stationery".equals(cmConf.getTipe_toko())) {
                        this.controller.setTipeOutlet("ATK");
                    } else {
                        this.controller.setTipeOutlet("MART");
                    }
                    if (cmConf.isIs_gudang()) {
                        this.controller.setKeteranganSoftware("GUDANG");
                    } else {
                        this.controller.setKeteranganSoftware("TOKO");
                    }
                    ConnectionManager.getInstance().configure(cmConf);
                    if (ConnectionManager.getInstance().testConnection()) break;
                    JOptionPane.showMessageDialog(null, "Tidak dapat terhubung ke database cabang: " + selectedName + "\nSilakan pilih cabang yang lain.", "Koneksi Gagal", 0);
                }
                int versionOnline = this.konstantaService.getMajorVersionOnline();
                if (versionOnline == 0) {
                    this.loginProcess = false;
                    this.listener.onLoginSucceed(this.statusSoftware);
                } else if (this.version < versionOnline) {
                    this.loginProcess = false;
                    JOptionPane.showMessageDialog(null, "Login gagal, gunakan applikasi versi terbaru");
                    this.listener.onLoginFailed();
                } else {
                    this.loginProcess = false;
                    this.listener.onLoginSucceed(this.statusSoftware);
                }
                this.loginProcess = false;
            } else {
                this.loginProcess = false;
                this.listener.onLoginFailed();
            }
        }
    }

    public void setDefault() {
        this.usernameField.setText("");
        this.passwordField.setText("");
    }

    public void addLoginListener(LoginOnlineListener lis) {
        this.listener = lis;
    }

    public void backupFunc() {
    }
}

