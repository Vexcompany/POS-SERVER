/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.component;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class SwingProgressBar
extends JPanel {
    JProgressBar pbar = new JProgressBar();
    static final int MY_MINIMUM = 0;
    static final int MY_MAXIMUM = 100;

    public SwingProgressBar() {
        this.pbar.setMinimum(0);
        this.pbar.setMaximum(100);
        this.add(this.pbar);
    }

    public void updateBar(int newValue) {
        this.pbar.setValue(newValue);
    }
}

