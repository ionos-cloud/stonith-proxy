package com.oneandone.stonith.entities;

public class RequestConfiguration {
    private Server server;
    private DialectConfiguration dialectConfiguration;

    public RequestConfiguration(Server server, DialectConfiguration dialectConfiguration) {
        this.server = server;
        this.dialectConfiguration = dialectConfiguration;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public DialectConfiguration getDialectConfiguration() {
        return dialectConfiguration;
    }

    public void setDialectConfiguration(DialectConfiguration dialectConfiguration) {
        this.dialectConfiguration = dialectConfiguration;
    }
}
