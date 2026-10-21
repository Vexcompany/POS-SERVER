/*
 * Decompiled with CFR 0.152.
 */
package minimart;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

public class DisplayModeChanger
extends JFrame {
    private GraphicsDevice device;
    private static JButton changeDM = new JButton("800X600 @ 32 BIT 60HZ");
    private boolean isFullScreenSupported = false;

    public DisplayModeChanger(final GraphicsDevice device) {
        this.device = device;
        this.setDefaultCloseOperation(3);
        this.add(changeDM);
        changeDM.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                DisplayMode dm = new DisplayMode(800, 600, 32, 60);
                device.setDisplayMode(dm);
                DisplayModeChanger.this.setSize(new Dimension(dm.getWidth(), dm.getHeight()));
                DisplayModeChanger.this.validate();
            }
        });
    }

    public void goFullScreen() {
        this.isFullScreenSupported = this.device.isFullScreenSupported();
        this.setUndecorated(this.isFullScreenSupported);
        this.setResizable(!this.isFullScreenSupported);
        if (this.isFullScreenSupported) {
            this.device.setFullScreenWindow(this);
            this.validate();
        } else {
            this.pack();
            this.setVisible(true);
        }
    }
}

