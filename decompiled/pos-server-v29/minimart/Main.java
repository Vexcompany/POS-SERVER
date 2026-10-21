/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jasperreports.engine.JRException
 */
package minimart;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import minimart.engine.controller.Controller;
import net.sf.jasperreports.engine.JRException;

public class Main {
    public static void main(String[] args) throws JRException, UnsupportedLookAndFeelException {
        System.out.println("RUNNING...");
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if (!"Nimbus".equals(info.getName())) continue;
            try {
                UIManager.setLookAndFeel(info.getClassName());
            }
            catch (ClassNotFoundException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (InstantiationException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IllegalAccessException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (UnsupportedLookAndFeelException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
        }
        Controller c = new Controller();
    }
}

