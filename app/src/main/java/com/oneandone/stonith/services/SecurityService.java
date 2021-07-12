package com.oneandone.stonith.services;

import com.oneandone.stonith.entities.Server;
import com.oneandone.stonith.errors.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    private final Logger LOG = LoggerFactory.getLogger(SecurityService.class.getName());

    @Autowired
    private DatabaseService databaseService;

    public void checkAuthorizationToken(String serialNumber, String token) throws UnauthorizedException {
        if (token.length() != 50) {
            LOG.info("Token '{}' has {} charcters. 50 characters tokens are required.", token, token.length());
            throw new UnauthorizedException("Token '%s' has %s charcters. 50 characters tokens are required.", token, token.length());
        }
        Server server = this.databaseService.getServer(serialNumber);
        if (!server.getApiToken().equals(token)) {
            LOG.info("Token '{}' does not match the database token for server {}.", token, serialNumber);
            throw new UnauthorizedException("Token '%s' does not match the database token for server %s.", token, serialNumber);
        }
        LOG.info("Authorization check passed. Proceding with the request.");
    }
}
