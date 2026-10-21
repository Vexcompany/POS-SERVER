/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.smslib.AGateway
 *  org.smslib.AGateway$GatewayStatuses
 *  org.smslib.AGateway$Protocols
 *  org.smslib.ICallNotification
 *  org.smslib.IGatewayStatusNotification
 *  org.smslib.IInboundMessageNotification
 *  org.smslib.IOrphanedMessageNotification
 *  org.smslib.InboundMessage
 *  org.smslib.InboundMessage$MessageClasses
 *  org.smslib.Library
 *  org.smslib.Message$MessageTypes
 *  org.smslib.Service
 *  org.smslib.crypto.AESKey
 *  org.smslib.crypto.AKey
 *  org.smslib.modem.SerialModemGateway
 */
package sms_gw.comm;

import java.util.ArrayList;
import javax.crypto.spec.SecretKeySpec;
import org.smslib.AGateway;
import org.smslib.ICallNotification;
import org.smslib.IGatewayStatusNotification;
import org.smslib.IInboundMessageNotification;
import org.smslib.IOrphanedMessageNotification;
import org.smslib.InboundMessage;
import org.smslib.Library;
import org.smslib.Message;
import org.smslib.Service;
import org.smslib.crypto.AESKey;
import org.smslib.crypto.AKey;
import org.smslib.modem.SerialModemGateway;

public class ReadMessages {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void doIt() throws Exception {
        InboundNotification inboundNotification = new InboundNotification();
        CallNotification callNotification = new CallNotification();
        GatewayStatusNotification statusNotification = new GatewayStatusNotification();
        OrphanedMessageNotification orphanedMessageNotification = new OrphanedMessageNotification();
        try {
            System.out.println("Example: Read messages from a serial gsm modem.");
            System.out.println(Library.getLibraryDescription());
            System.out.println("Version: " + Library.getLibraryVersion());
            SerialModemGateway gateway = new SerialModemGateway("modem.com1", "COM44", 115200, "Sierra", "875U");
            gateway.setProtocol(AGateway.Protocols.PDU);
            gateway.setInbound(true);
            gateway.setOutbound(true);
            gateway.setSimPin("0000");
            Service.getInstance().setInboundMessageNotification((IInboundMessageNotification)inboundNotification);
            Service.getInstance().setCallNotification((ICallNotification)callNotification);
            Service.getInstance().setGatewayStatusNotification((IGatewayStatusNotification)statusNotification);
            Service.getInstance().setOrphanedMessageNotification((IOrphanedMessageNotification)orphanedMessageNotification);
            Service.getInstance().addGateway((AGateway)gateway);
            Service.getInstance().startService();
            System.out.println();
            System.out.println("Modem Information:");
            System.out.println("  Manufacturer: " + gateway.getManufacturer());
            System.out.println("  Model: " + gateway.getModel());
            System.out.println("  Serial No: " + gateway.getSerialNo());
            System.out.println("  SIM IMSI: " + gateway.getImsi());
            System.out.println("  Signal Level: " + gateway.getSignalLevel() + " dBm");
            System.out.println("  Battery Level: " + gateway.getBatteryLevel() + "%");
            System.out.println();
            Service.getInstance().getKeyManager().registerKey("+306948494037", (AKey)new AESKey(new SecretKeySpec("0011223344556677".getBytes(), "AES")));
            ArrayList msgList = new ArrayList();
            Service.getInstance().readMessages(msgList, InboundMessage.MessageClasses.ALL);
            for (InboundMessage msg : msgList) {
                System.out.println(msg);
            }
            System.out.println("Now Sleeping - Hit <enter> to stop service.");
            System.in.read();
            System.in.read();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            Service.getInstance().stopService();
        }
    }

    public static void main(String[] args) {
        ReadMessages app = new ReadMessages();
        try {
            app.doIt();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class OrphanedMessageNotification
    implements IOrphanedMessageNotification {
        public boolean process(AGateway gateway, InboundMessage msg) {
            System.out.println(">>> Orphaned message part detected from " + gateway.getGatewayId());
            System.out.println(msg);
            return false;
        }
    }

    public class GatewayStatusNotification
    implements IGatewayStatusNotification {
        public void process(AGateway gateway, AGateway.GatewayStatuses oldStatus, AGateway.GatewayStatuses newStatus) {
            System.out.println(">>> Gateway Status change for " + gateway.getGatewayId() + ", OLD: " + oldStatus + " -> NEW: " + newStatus);
        }
    }

    public class CallNotification
    implements ICallNotification {
        public void process(AGateway gateway, String callerId) {
            System.out.println(">>> New call detected from Gateway: " + gateway.getGatewayId() + " : " + callerId);
        }
    }

    public class InboundNotification
    implements IInboundMessageNotification {
        public void process(AGateway gateway, Message.MessageTypes msgType, InboundMessage msg) {
            if (msgType == Message.MessageTypes.INBOUND) {
                System.out.println(">>> New Inbound message detected from Gateway: " + gateway.getGatewayId());
            } else if (msgType == Message.MessageTypes.STATUSREPORT) {
                System.out.println(">>> New Inbound Status Report message detected from Gateway: " + gateway.getGatewayId());
            }
            System.out.println(msg);
        }
    }
}

