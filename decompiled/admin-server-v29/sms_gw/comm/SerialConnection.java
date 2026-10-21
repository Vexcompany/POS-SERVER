/*
 * Decompiled with CFR 0.152.
 */
package sms_gw.comm;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;
import javax.comm.CommPortIdentifier;
import javax.comm.CommPortOwnershipListener;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;
import sms_gw.comm.SerialConnectionException;
import sms_gw.comm.SerialParameters;

public class SerialConnection
implements SerialPortEventListener,
CommPortOwnershipListener {
    private SerialParameters parameters;
    private OutputStream os;
    private InputStream is;
    private KeyHandler keyHandler;
    private CommPortIdentifier portId;
    private SerialPort sPort;
    private boolean open;
    private String receptionString = "";

    public String getIncommingString() {
        byte[] bVal = this.receptionString.getBytes();
        this.receptionString = "";
        return new String(bVal);
    }

    public SerialConnection(SerialParameters parameters) {
        this.parameters = parameters;
        this.open = false;
    }

    public void openConnection() throws SerialConnectionException {
        try {
            System.out.println("PortName= " + this.parameters.getPortName());
            this.portId = CommPortIdentifier.getPortIdentifier(this.parameters.getPortName());
        }
        catch (NoSuchPortException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            this.sPort = (SerialPort)this.portId.open("SMSConnector", 30000);
        }
        catch (PortInUseException e) {
            throw new SerialConnectionException(e.getMessage());
        }
        this.sPort.sendBreak(1000);
        try {
            this.setConnectionParameters();
        }
        catch (SerialConnectionException e) {
            this.sPort.close();
            throw e;
        }
        try {
            this.os = this.sPort.getOutputStream();
            this.is = this.sPort.getInputStream();
        }
        catch (IOException e) {
            this.sPort.close();
            throw new SerialConnectionException("Error opening i/o streams");
        }
        try {
            this.sPort.addEventListener(this);
        }
        catch (TooManyListenersException e) {
            this.sPort.close();
            throw new SerialConnectionException("too many listeners added");
        }
        this.sPort.notifyOnDataAvailable(true);
        this.sPort.notifyOnBreakInterrupt(true);
        try {
            this.sPort.enableReceiveTimeout(30);
        }
        catch (UnsupportedCommOperationException unsupportedCommOperationException) {
            // empty catch block
        }
        this.portId.addPortOwnershipListener(this);
        this.open = true;
    }

    public void setConnectionParameters() throws SerialConnectionException {
        int oldBaudRate = this.sPort.getBaudRate();
        int oldDatabits = this.sPort.getDataBits();
        int oldStopbits = this.sPort.getStopBits();
        int oldParity = this.sPort.getParity();
        int oldFlowControl = this.sPort.getFlowControlMode();
        try {
            this.sPort.setSerialPortParams(this.parameters.getBaudRate(), this.parameters.getDatabits(), this.parameters.getStopbits(), this.parameters.getParity());
        }
        catch (UnsupportedCommOperationException e) {
            this.parameters.setBaudRate(oldBaudRate);
            this.parameters.setDatabits(oldDatabits);
            this.parameters.setStopbits(oldStopbits);
            this.parameters.setParity(oldParity);
            throw new SerialConnectionException("Unsupported parameter");
        }
        try {
            this.sPort.setFlowControlMode(this.parameters.getFlowControlIn() | this.parameters.getFlowControlOut());
        }
        catch (UnsupportedCommOperationException e) {
            throw new SerialConnectionException("Unsupported flow control");
        }
    }

    public void closeConnection() {
        if (!this.open) {
            return;
        }
        if (this.sPort != null) {
            try {
                this.os.close();
                this.is.close();
            }
            catch (IOException e) {
                System.err.println(e);
            }
            this.sPort.close();
            this.portId.removePortOwnershipListener(this);
        }
        this.open = false;
    }

    public void sendBreak() {
        this.sPort.sendBreak(1000);
    }

    public boolean isOpen() {
        return this.open;
    }

    @Override
    public void serialEvent(SerialPortEvent e) {
        StringBuffer inputBuffer = new StringBuffer();
        int newData = 0;
        switch (e.getEventType()) {
            case 1: {
                while (newData != -1) {
                    try {
                        newData = this.is.read();
                        if (newData == -1) break;
                        if ('\r' == (char)newData) {
                            inputBuffer.append('\n');
                            continue;
                        }
                        inputBuffer.append((char)newData);
                    }
                    catch (IOException ex) {
                        System.err.println(ex);
                        return;
                    }
                }
                this.receptionString = this.receptionString + new String(inputBuffer);
                break;
            }
            case 10: {
                this.receptionString = this.receptionString + "\n--- BREAK RECEIVED ---\n";
            }
        }
    }

    @Override
    public void ownershipChange(int type) {
    }

    public void send(String message) {
        byte[] theBytes = (message + "\n").getBytes();
        for (int i = 0; i < theBytes.length; ++i) {
            int newCharacter = theBytes[i];
            if (newCharacter == 10) {
                newCharacter = 13;
            }
            try {
                this.os.write(newCharacter);
                continue;
            }
            catch (IOException e) {
                System.err.println("OutputStream write error: " + e);
            }
        }
    }

    class KeyHandler
    extends KeyAdapter {
        OutputStream os;

        public KeyHandler(OutputStream os) {
            this.os = os;
        }

        @Override
        public void keyTyped(KeyEvent evt) {
            int newCharacter = evt.getKeyChar();
            if (newCharacter == 10) {
                newCharacter = 13;
            }
            System.out.println(newCharacter);
            try {
                this.os.write(newCharacter);
            }
            catch (IOException e) {
                System.err.println("OutputStream write error: " + e);
            }
        }
    }
}

