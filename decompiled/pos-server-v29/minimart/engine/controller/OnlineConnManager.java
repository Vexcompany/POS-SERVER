/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jcraft.jsch.JSch
 *  com.jcraft.jsch.Session
 *  org.springframework.jdbc.datasource.DriverManagerDataSource
 */
package minimart.engine.controller;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.sql.Connection;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class OnlineConnManager {
    private static OnlineConnManager instance;
    private DriverManagerDataSource dataSource = new DriverManagerDataSource();
    private Session sshSession;
    private static final String SSH_HOST = "admin.sinarmart.id";
    private static final int SSH_PORT = 22;
    private static final String SSH_USER = "tunneluser";
    private static final int LOCAL_PORT = 3307;
    private static final String REMOTE_HOST = "127.0.0.1";
    private static final int REMOTE_PORT = 3306;
    private static final String DB_NAME = "minimarket_online";
    private static final String DB_USER = "outlet_user";
    private static final String DB_PASS = "andrbr.sinarst2";
    private long lastConnectTime = 0L;
    private static final long MIN_RECONNECT_INTERVAL = 5000L;
    private static final String PRIVATE_KEY = "-----BEGIN RSA PRIVATE KEY-----\nMIIEpQIBAAKCAQEA61iR7vz3llT9TXSBYLReBcMC/N1iAe1wOT8UiFNlwI3Nu2kU\nQj40DZCVRuxQWsBPQ6xFBg1/TrxCFbg6BFEO3gmb5pBt3b9pekMMVoK4ue6NJk/x\ndIB1T3l4i+VE1BExzqauln/LUwNQ3I9DDDrL5v9nEaCBmSqof07r72eJkTJ4o74k\nertHU34rXHHIUlrdXgdmTnSgNTdPHPzQhTwxR72rje/RKeiXUvjrHQ3J1z8WY2td\nIEaqzg2PqoXFMuur3W28XUlmSO0mocakha8xZg7dfS8XeoXQZmkrgUA3j/xWv0O7\nvY7OLNWu9fAql3yxN9QmmCYbp2GX7BPmNA0wNwIDAQABAoIBAQDThPk0SJvlu2El\n+i5F0hYnK4xF5FsvzE1uxikYFYyAMXLUm66fkvSJtIbWBZJ63hjfg7dNp1fiVgU3\nLCrIsmmgWhaKdQipol2UiypNnpOUWLj31CX5RXMqCMy1P0xLJWEUkG6dySHe7D4C\n3aevGDEm3imuiAmytcpXEYGv2+iiW3cll3Drp0E2GbOlsV9DlA+/9wOIlxREau6R\nseLgHT5GqbNbH6q30b9rOy4RA2SuvMBEdgW7MCMtSsGtLAU8/tY/HngU43AHnFUq\naiZDW8+UbdGuwzRk5N437UVz9RcDNEP/AXH5sEplMBlBPBuT6naC/xNJ6iHJ4/TW\noXnLhGjxAoGBAPY4zTvdDLwYeJer+sBL7V0XTeW3LSYMjSfiEsoChAiqV/A1oGs6\nmnJ6kX9WZXpa6v4SEqJpvRpa2ivBsSNwdDd2114elT7xQFvWlhMDRRmb9eqZVjFU\nJDYSr0P8iFOMguQk+y3h/9MQqlEUEJRZmdEGJiFZXf61UBaEBx36J6brAoGBAPSx\nMv6p7/WKnTnHGHZn49D596WAJ52kt6pnjDSxgFoKTfvtCptwUxfdAiPhWqvXheF2\nosDGa0CT9nPKc7N6odwKW5xoOIJJW2SjJ82yoYC/ynbvDn3J2pQyCWG4N+zytbuj\nsOb1ZCT1ITHXPs8DAPMaNkpCNYIdytMK/xKGRqDlAoGBALsd7bJ5yezEOBkjxJsQ\n2KFvTW6u52vMDBXsM9U7VjXzpoLRbkkvLyKizSHVeacjowRUnb651tFJjBXGzCSf\nmGJyOTe4zDkaRIvGpyRpzsCoipmv0BYK2L4TMwjayIud8/cHt69l2fH/7z9QnUzw\nTvnPz3h2nv0rs2J5EaORcULjAoGBAMo+i/+hRgyU/U78GGQFim73rJDGU/ggZQ4b\nd+81pvjE+5/2VIYVWRioO1SctuzBvo6UGAFt/sfphBJAi1EVUyMkDIRCQDKvnzKX\nCBckRgi8fQPAoyGk7Vz/3nE+ajsWgM8OhPDgcl15nm4rkmZX/TjxuV23S7K4HSRC\nOoF9KnNhAoGARMcIAUlILfLHRsubn7BpL0nejDdo6tdlOA2TSyprGaXa00meZeP6\n4EV6Qcx3xNzDuZLxZLwhcmml1dOvywN43wEsqzcMPPH4juiN+HqiJ7IDA0np0poM\nU/ouOWO8FUQEg1iXB/ypSGT+DKb4Ynoh8buqyuju7Io8g8qHozJ9IrA=\n-----END RSA PRIVATE KEY-----";

    private void reconnectWithRetry() {
        int maxRetry = 6;
        for (int i = 1; i <= maxRetry; ++i) {
            try {
                this.initSSHTunnel();
                System.out.println("Reconnect success");
                return;
            }
            catch (Exception e) {
                System.out.println("Reconnect attempt " + i + " failed");
                try {
                    Thread.sleep(2000L);
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
                continue;
            }
        }
        throw new RuntimeException("SSH reconnect failed after retries");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean isDBAlive() {
        try (Connection conn = this.dataSource.getConnection();){
            boolean bl = conn.isValid(2);
            return bl;
        }
        catch (Exception e) {
            return false;
        }
    }

    private synchronized void ensureConnected() {
        block6: {
            try {
                long now = System.currentTimeMillis();
                if (now - this.lastConnectTime < 5000L) {
                    return;
                }
                if (this.sshSession != null && this.sshSession.isConnected() && this.isDBAlive()) break block6;
                System.out.println("SSH/DB not healthy. Reconnecting...");
                try {
                    if (this.sshSession != null) {
                        this.sshSession.disconnect();
                    }
                }
                catch (Exception exception) {
                    // empty catch block
                }
                this.initDataSource();
                this.reconnectWithRetry();
                this.lastConnectTime = now;
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("SSH reconnect failed", e);
            }
        }
    }

    public static OnlineConnManager getInstance() {
        if (instance == null) {
            instance = new OnlineConnManager();
        }
        return instance;
    }

    private void initSSHTunnel() {
        try {
            JSch jsch = new JSch();
            jsch.addIdentity("minimart-client", PRIVATE_KEY.getBytes("UTF-8"), null, null);
            this.sshSession = jsch.getSession(SSH_USER, SSH_HOST, 22);
            this.sshSession.setConfig("StrictHostKeyChecking", "no");
            this.sshSession.setConfig("PreferredAuthentications", "publickey");
            this.sshSession.setConfig("PubkeyAcceptedAlgorithms", "ssh-rsa");
            this.sshSession.setConfig("server_host_key", "ssh-rsa");
            this.sshSession.setConfig("PubkeyAcceptedAlgorithms", "ssh-rsa");
            this.sshSession.setConfig("PubkeyAcceptedKeyTypes", "ssh-rsa");
            this.sshSession.connect();
            int n = this.setupPortForwarding(this.sshSession, REMOTE_HOST, 3306);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to establish SSH Tunnel", e);
        }
    }

    public int setupPortForwarding(Session session, String remoteHost, int remotePort) {
        int startPort = 3307;
        int maxPort = 3400;
        for (int currentPort = startPort; currentPort <= maxPort; ++currentPort) {
            try {
                session.setPortForwardingL(currentPort, remoteHost, remotePort);
                System.out.println("[SSH] Tunnel established on port: " + currentPort);
                return currentPort;
            }
            catch (Exception e) {
                continue;
            }
        }
        throw new RuntimeException("No available port found for SSH tunnel (range " + startPort + "-" + maxPort + ")");
    }

    private void initDataSource() {
        this.dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        this.dataSource.setUrl("jdbc:mysql://127.0.0.1:3307/minimarket_online?serverTimezone=Asia/Jakarta");
        this.dataSource.setUsername(DB_USER);
        this.dataSource.setPassword(DB_PASS);
    }

    public DriverManagerDataSource getDataSource() {
        return this.dataSource;
    }

    public void touch() {
        this.ensureConnected();
    }

    public void close() {
        if (this.sshSession != null && this.sshSession.isConnected()) {
            this.sshSession.disconnect();
        }
    }
}

