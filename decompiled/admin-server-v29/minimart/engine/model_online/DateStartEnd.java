/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model_online;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateStartEnd {
    String start;
    String end;

    public String getStart() {
        return this.start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return this.end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setTodayAsEndDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        this.end = sdf.format(date);
    }
}

