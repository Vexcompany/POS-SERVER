/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.json_response;

import minimart.engine.json_response.DataResponseLoginOnline;

public class JsonResponseLoginOnline {
    String status;
    String message;
    DataResponseLoginOnline data;
    String code;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataResponseLoginOnline getData() {
        return this.data;
    }

    public void setData(DataResponseLoginOnline data) {
        this.data = data;
    }
}

