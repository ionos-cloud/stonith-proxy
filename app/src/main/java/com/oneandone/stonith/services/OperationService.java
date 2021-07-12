package com.oneandone.stonith.services;

import com.oneandone.stonith.entities.DialectConfiguration;
import com.oneandone.stonith.entities.RequestConfiguration;
import com.oneandone.stonith.entities.Server;
import com.oneandone.stonith.errors.RequestException;
import com.oneandone.stonith.errors.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.oneandone.stonith.entities.Constants.*;

@Service
public class OperationService {
    private final Logger LOG = LoggerFactory.getLogger(OperationService.class.getName());

    @Autowired
    private FileReaderService fileReaderService;
    @Autowired
    private DatabaseService databaseService;
    @Autowired
    private RequestService requestService;

    public void restartServer(String serialNumber) throws RequestException, UnauthorizedException {
        Server server = this.databaseService.getServer(serialNumber);
        LOG.info("Restart procedure for server {} using dialect {} started.", serialNumber, server.getDialect());
        DialectConfiguration dialectConfiguration = this.fileReaderService.readFile(server.getDialect(), RESTART);
        RequestConfiguration requestConfiguration = new RequestConfiguration(server, dialectConfiguration);
        this.requestService.executeRequest(requestConfiguration);
        LOG.info("Restart procedure for server {} using dialect {} finished successfully.", serialNumber, server.getDialect());
    }

    public void shutdownServer(String serialNumber) throws RequestException, UnauthorizedException {
        Server server = this.databaseService.getServer(serialNumber);
        LOG.info("Shutdown procedure for server {} using dialect {} started.", serialNumber, server.getDialect());
        DialectConfiguration dialectConfiguration = this.fileReaderService.readFile(server.getDialect(), SHUTDOWN);
        RequestConfiguration requestConfiguration = new RequestConfiguration(server, dialectConfiguration);
        this.requestService.executeRequest(requestConfiguration);
        LOG.info("Shutdown procedure for server {} using dialect {} finished successfully.", serialNumber, server.getDialect());

    }

    public void poweronServer(String serialNumber) throws RequestException, UnauthorizedException {
        Server server = this.databaseService.getServer(serialNumber);
        LOG.info("Poweron procedure for server {} using dialect {} started.", serialNumber, server.getDialect());
        DialectConfiguration dialectConfiguration = this.fileReaderService.readFile(server.getDialect(), POWERON);
        RequestConfiguration requestConfiguration = new RequestConfiguration(server, dialectConfiguration);
        this.requestService.executeRequest(requestConfiguration);
        LOG.info("Poweron procedure for server {} using dialect {} finished successfully.", serialNumber, server.getDialect());
    }
}
