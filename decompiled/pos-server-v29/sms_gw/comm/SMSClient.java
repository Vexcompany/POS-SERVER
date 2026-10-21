/*
 * Decompiled with CFR 0.152.
 */
package sms_gw.comm;

import sms_gw.comm.Sender;

public class SMSClient
implements Runnable {
    public static final int SYNCHRONOUS = 0;
    public static final int ASYNCHRONOUS = 1;
    private Thread myThread = null;
    private int mode = -1;
    private String recipient = null;
    private String message = null;
    private String comPort = null;
    private int baudrate = 0;
    private String csca = null;
    public int status = -1;
    public long messageNo = -1L;

    public SMSClient(int mode) {
        this.mode = mode;
    }

    public int sendMessage(String recipient, String message, String comPort, int baudrate, String csca) {
        this.recipient = recipient;
        this.message = message;
        this.comPort = comPort;
        this.baudrate = baudrate;
        this.csca = csca;
        System.out.println("recipient: " + recipient + " message: " + message);
        this.myThread = new Thread(this);
        this.myThread.start();
        return this.status;
    }

    @Override
    public void run() {
        Sender aSender = new Sender(this.recipient, this.message, this.comPort, this.baudrate, this.csca);
        try {
            aSender.send();
            System.out.println("Sending ... ");
            if (this.mode == 0) {
                while (aSender.status == -1) {
                    Thread.sleep(1000L);
                }
            }
            if (aSender.status == 0) {
                this.messageNo = aSender.messageNo;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.status = aSender.status;
        aSender = null;
    }
}

