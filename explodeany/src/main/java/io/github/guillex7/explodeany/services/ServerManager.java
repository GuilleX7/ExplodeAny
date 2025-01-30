package io.github.guillex7.explodeany.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import org.bukkit.Server;

import net.freeutils.httpserver.HTTPServer;

public class ServerManager {
    private static ServerManager instance;

    private HTTPServer server;
    private boolean isRunning;

    public static ServerManager getInstance() {
        if (instance == null) {
            instance = new ServerManager();
        }
        return instance;
    }

    private ServerManager() {
        this.server = new HTTPServer();
        this.server.setPort(8080);
        this.isRunning = false;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public boolean startServer() {
        if (this.isRunning) {
            return false;
        }

        try {
            this.server.getVirtualHost(null).addContext("/{*}", new JarResourceContextHandler("/"), "GET");
            this.server.start();
            this.isRunning = true;
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean stopServer() {
        if (!this.isRunning) {
            return false;
        }

        this.server.stop();
        this.isRunning = false;
        return true;
    }
}
