/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

public class JsonResponses {
    private String status;
    private String error;
    private String message;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

