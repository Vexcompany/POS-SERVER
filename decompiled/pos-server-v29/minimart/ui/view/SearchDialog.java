/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import minimart.engine.component.UppercaseDocumentFilter;
import minimart.ui.listener.SearchDialogListener;

public class SearchDialog
extends JDialog {
    private JButton cancelButton;
    private JLabel jLabel1;
    private JButton okButton;
    private JTextField searchField;
    private SearchDialogListener listener;
    private DocumentFilter filter = new UppercaseDocumentFilter();

    public SearchDialog(Frame parent, boolean modal, String title) {
        super(parent, modal);
        this.setTitle(title);
        this.initComponents();
        this.initComponents2();
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.searchField = new JTextField();
        this.okButton = new JButton();
        this.cancelButton = new JButton();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Text to find ...");
        this.searchField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                SearchDialog.this.searchFieldKeyPressed(evt);
            }
        });
        this.okButton.setText("Ok");
        this.okButton.setPreferredSize(new Dimension(73, 23));
        this.okButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                SearchDialog.this.okButtonActionPerformed(evt);
            }
        });
        this.okButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                SearchDialog.this.okButtonKeyPressed(evt);
            }
        });
        this.cancelButton.setText("Cancel");
        this.cancelButton.setPreferredSize(new Dimension(73, 23));
        this.cancelButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                SearchDialog.this.cancelButtonActionPerformed(evt);
            }
        });
        this.cancelButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                SearchDialog.this.cancelButtonKeyPressed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.searchField, -2, 205, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.okButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cancelButton, -2, -1, -2)).addComponent(this.jLabel1)).addContainerGap(19, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.searchField, -2, -1, -2).addComponent(this.okButton, -2, -1, -2).addComponent(this.cancelButton, -2, -1, -2)).addContainerGap(-1, Short.MAX_VALUE)));
        this.pack();
    }

    private void initComponents2() {
        ((AbstractDocument)this.searchField.getDocument()).setDocumentFilter(this.filter);
    }

    public void addSearchDialogListener(SearchDialogListener listener) {
        this.listener = listener;
    }

    private void searchFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.okButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void okButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.dispose();
            this.listener.searchClick(this.searchField.getText());
        } else if (evt.getKeyCode() == 39) {
            this.cancelButton.requestFocus();
        } else if (evt.getKeyCode() == 37) {
            this.searchField.requestFocus();
            this.searchField.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.searchField.requestFocus();
            this.searchField.selectAll();
        }
    }

    private void cancelButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.dispose();
        } else if (evt.getKeyCode() == 27) {
            this.searchField.requestFocus();
            this.searchField.selectAll();
        } else if (evt.getKeyCode() == 37) {
            this.okButton.requestFocus();
        }
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void okButtonActionPerformed(ActionEvent evt) {
        this.dispose();
        this.listener.searchClick(this.searchField.getText());
    }
}

