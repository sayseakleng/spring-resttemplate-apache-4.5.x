package com.kdemo.rest.http.client;

import java.util.concurrent.TimeUnit;

import org.apache.http.conn.HttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IdleConnectionMonitorThread extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(IdleConnectionMonitorThread.class);
    
    private final HttpClientConnectionManager connMgr;
    private volatile boolean shutdown;
    private long idleTimeoutInMS;
    
    public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr, long idleTimeoutInMS) {
        super();
        this.connMgr = connMgr;
        this.idleTimeoutInMS = idleTimeoutInMS;
    }

    @Override
    public void run() {
    	
        try {
            while (!shutdown) {
                synchronized (this) {
                    wait(5000);
                    // Close expired connections
                    connMgr.closeExpiredConnections();
                    // Optionally, close connections
                    connMgr.closeIdleConnections(idleTimeoutInMS, TimeUnit.MILLISECONDS);
                }
            }
        } catch (InterruptedException ex) {
        	logger.error(ex.getMessage(), ex);
        }
    }
    
    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }
    
}