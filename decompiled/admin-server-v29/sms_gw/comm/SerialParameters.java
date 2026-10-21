/*
 * Decompiled with CFR 0.152.
 */
package sms_gw.comm;

public class SerialParameters {
    private String portName;
    private int baudRate;
    private int flowControlIn;
    private int flowControlOut;
    private int databits;
    private int stopbits;
    private int parity;

    public SerialParameters() {
        this("", 9600, 0, 0, 8, 1, 0);
    }

    public SerialParameters(String portName, int baudRate, int flowControlIn, int flowControlOut, int databits, int stopbits, int parity) {
        this.portName = portName;
        this.baudRate = baudRate;
        this.flowControlIn = flowControlIn;
        this.flowControlOut = flowControlOut;
        this.databits = databits;
        this.stopbits = stopbits;
        this.parity = parity;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public String getPortName() {
        return this.portName;
    }

    public void setBaudRate(int baudRate) {
        this.baudRate = baudRate;
    }

    public void setBaudRate(String baudRate) {
        this.baudRate = Integer.parseInt(baudRate);
    }

    public int getBaudRate() {
        return this.baudRate;
    }

    public String getBaudRateString() {
        return Integer.toString(this.baudRate);
    }

    public void setFlowControlIn(int flowControlIn) {
        this.flowControlIn = flowControlIn;
    }

    public void setFlowControlIn(String flowControlIn) {
        this.flowControlIn = this.stringToFlow(flowControlIn);
    }

    public int getFlowControlIn() {
        return this.flowControlIn;
    }

    public String getFlowControlInString() {
        return this.flowToString(this.flowControlIn);
    }

    public void setFlowControlOut(int flowControlOut) {
        this.flowControlOut = flowControlOut;
    }

    public void setFlowControlOut(String flowControlOut) {
        this.flowControlOut = this.stringToFlow(flowControlOut);
    }

    public int getFlowControlOut() {
        return this.flowControlOut;
    }

    public String getFlowControlOutString() {
        return this.flowToString(this.flowControlOut);
    }

    public void setDatabits(int databits) {
        this.databits = databits;
    }

    public void setDatabits(String databits) {
        if (databits.equals("5")) {
            this.databits = 5;
        }
        if (databits.equals("6")) {
            this.databits = 6;
        }
        if (databits.equals("7")) {
            this.databits = 7;
        }
        if (databits.equals("8")) {
            this.databits = 8;
        }
    }

    public int getDatabits() {
        return this.databits;
    }

    public String getDatabitsString() {
        switch (this.databits) {
            case 5: {
                return "5";
            }
            case 6: {
                return "6";
            }
            case 7: {
                return "7";
            }
            case 8: {
                return "8";
            }
        }
        return "8";
    }

    public void setStopbits(int stopbits) {
        this.stopbits = stopbits;
    }

    public void setStopbits(String stopbits) {
        if (stopbits.equals("1")) {
            this.stopbits = 1;
        }
        if (stopbits.equals("1.5")) {
            this.stopbits = 3;
        }
        if (stopbits.equals("2")) {
            this.stopbits = 2;
        }
    }

    public int getStopbits() {
        return this.stopbits;
    }

    public String getStopbitsString() {
        switch (this.stopbits) {
            case 1: {
                return "1";
            }
            case 3: {
                return "1.5";
            }
            case 2: {
                return "2";
            }
        }
        return "1";
    }

    public void setParity(int parity) {
        this.parity = parity;
    }

    public void setParity(String parity) {
        if (parity.equals("None")) {
            this.parity = 0;
        }
        if (parity.equals("Even")) {
            this.parity = 2;
        }
        if (parity.equals("Odd")) {
            this.parity = 1;
        }
    }

    public int getParity() {
        return this.parity;
    }

    public String getParityString() {
        switch (this.parity) {
            case 0: {
                return "None";
            }
            case 2: {
                return "Even";
            }
            case 1: {
                return "Odd";
            }
        }
        return "None";
    }

    private int stringToFlow(String flowControl) {
        if (flowControl.equals("None")) {
            return 0;
        }
        if (flowControl.equals("Xon/Xoff Out")) {
            return 8;
        }
        if (flowControl.equals("Xon/Xoff In")) {
            return 4;
        }
        if (flowControl.equals("RTS/CTS In")) {
            return 1;
        }
        if (flowControl.equals("RTS/CTS Out")) {
            return 2;
        }
        return 0;
    }

    String flowToString(int flowControl) {
        switch (flowControl) {
            case 0: {
                return "None";
            }
            case 8: {
                return "Xon/Xoff Out";
            }
            case 4: {
                return "Xon/Xoff In";
            }
            case 1: {
                return "RTS/CTS In";
            }
            case 2: {
                return "RTS/CTS Out";
            }
        }
        return "None";
    }
}

