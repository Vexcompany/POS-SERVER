/*
 * Decompiled with CFR 0.152.
 */
package sms_gw.comm;

import java.util.Date;
import javax.swing.JOptionPane;
import sms_gw.comm.SerialConnection;
import sms_gw.comm.SerialParameters;

public class Sender
implements Runnable {
    private static final long STANDARD = 500L;
    private static final long LONG = 2000L;
    private static final long VERYLONG = 20000L;
    SerialConnection mySerial = null;
    private static final char cntrlZ = '\u001a';
    String in;
    String out;
    Thread aThread = null;
    private long delay = 500L;
    String recipient = null;
    String message = null;
    private SerialParameters defaultParameters;
    public int step;
    public int status = -1;
    public long messageNo = -1L;
    private String comPort = "COM28";
    private int baudrate = 115200;
    private String csca = "+6281100000";

    public Sender(String recipient, String message, String comPort, int baudrate, String csca) {
        this.recipient = recipient;
        this.message = message;
        this.comPort = comPort;
        this.baudrate = baudrate;
        this.csca = csca;
    }

    public int send() throws Exception {
        SerialParameters params = this.defaultParameters = new SerialParameters(this.comPort, this.baudrate, 0, 0, 8, 1, 0);
        System.out.println(this.defaultParameters);
        this.mySerial = new SerialConnection(params);
        this.mySerial.openConnection();
        this.aThread = new Thread(this);
        this.aThread.start();
        this.log("start");
        return 0;
    }

    @Override
    public void run() {
        boolean timeOut = false;
        long startTime = new Date().getTime();
        while (this.step < 7 && !timeOut) {
            this.log("" + (new Date().getTime() - startTime));
            boolean bl = timeOut = new Date().getTime() - startTime > this.delay;
            if (timeOut && this.step == 1) {
                this.step = -1;
                this.mySerial.send("\u001a");
            }
            String result = this.mySerial.getIncommingString();
            System.out.println("result=" + result);
            this.log("<- " + result + "\n--------");
            int expectedResult = -1;
            try {
                this.log("Step:" + this.step);
                switch (this.step) {
                    case 0: {
                        this.mySerial.send("atz");
                        this.delay = 2000L;
                        startTime = new Date().getTime();
                        break;
                    }
                    case 1: 
                    case 2: {
                        expectedResult = result.indexOf("OK");
                        this.log("received ok =" + expectedResult);
                        if (expectedResult > -1) {
                            this.mySerial.send("at+cmgf=1");
                            startTime = new Date().getTime();
                            break;
                        }
                        --this.step;
                        break;
                    }
                    case 3: {
                        expectedResult = result.indexOf("OK");
                        this.log("received ok =" + expectedResult);
                        if (expectedResult > -1) {
                            this.mySerial.send("at+csca=\"" + this.csca + "\"");
                            startTime = new Date().getTime();
                            break;
                        }
                        --this.step;
                        break;
                    }
                    case 4: {
                        expectedResult = result.indexOf("OK");
                        this.log("received ok =" + expectedResult);
                        if (expectedResult > -1) {
                            this.mySerial.send("at+cmgs=\"" + this.recipient + "\"");
                            startTime = new Date().getTime();
                            break;
                        }
                        --this.step;
                        break;
                    }
                    case 5: {
                        expectedResult = result.indexOf(">");
                        this.log("received ok =" + expectedResult);
                        if (expectedResult > -1) {
                            this.mySerial.send(this.message + '\u001a');
                            startTime = new Date().getTime();
                        } else {
                            --this.step;
                        }
                        this.delay = 20000L;
                        break;
                    }
                    case 6: {
                        expectedResult = result.indexOf("OK");
                        if (expectedResult > -1) {
                            int n = result.indexOf("CMGS:");
                            result = result.substring(n + 5);
                            n = result.indexOf("\n");
                            this.status = 0;
                            this.messageNo = Long.parseLong(result.substring(0, n).trim());
                            this.log("sent message no:" + this.messageNo);
                            break;
                        }
                        --this.step;
                    }
                }
                ++this.step;
                Thread.sleep(100L);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.mySerial.closeConnection();
        if (timeOut) {
            this.status = -2;
            this.log("*** time out at step " + this.step + "***");
            JOptionPane.showMessageDialog(null, "Time out at step " + this.step);
        }
    }

    private void log(String s) {
        System.out.println(new Date() + ":" + this.getClass().getName() + ":" + s);
    }
}

