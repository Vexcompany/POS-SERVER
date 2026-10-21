/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.component;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JTextFieldLimit
extends PlainDocument {
    private int limit;
    private boolean toUppercase = true;

    public JTextFieldLimit(int limit) {
        this.limit = limit;
    }

    public JTextFieldLimit(int limit, boolean upper) {
        this.limit = limit;
        this.toUppercase = upper;
    }

    @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null) {
            return;
        }
        if (this.getLength() + str.length() <= this.limit) {
            if (this.toUppercase) {
                str = str.toUpperCase();
            }
            super.insertString(offset, str, attr);
        }
    }
}

