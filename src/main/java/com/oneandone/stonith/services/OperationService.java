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

    public void restartServer(String serialNumber, String dialect) throws RequestException, UnauthorizedException {
        LOG.info("Restart procedure for server {} using dialect {} started.", serialNumber, dialect);
        DialectConfiguration dialectConfiguration = this.fileReaderService.readFile(dialect, RESTART);
        Server server = this.databaseService.getServer(serialNumber);
        RequestConfiguration requestConfiguration = new RequestConfiguration(server, dialectConfiguration);
        this.requestService.executeRequest(requestConfiguration);
        LOG.info("Restart procedure for server {} using dialect {} finished successfully.", serialNumber, dialect);
    }

    public void shutdownServer(String serialNumber, String dialect) throws RequestException, UnauthorizedException {
        LOG.info("Shutdown procedure for server {} using dialect {} started.", serialNumber, dialect);
        DialectConfiguration dialectConfiguration = this.fileReaderService.readFile(dialect, SHUTDOWN);
        Server server = this.databaseService.getServer(serialNumber);
        RequestConfiguration requestConfiguration = new RequestConfiguration(server, dialectConfiguration);
        this.requestService.executeRequest(requestConfiguration);
        LOG.info("Shutdown procedure for server {} using dialect {} finished successfully.", serialNumber, dialect);

    }

    public void poweronServer(String serialNumber, String dialect) throws RequestException, UnauthorizedException {
        LOG.info("Poweron procedure for server {} using dialect {} started.", serialNumber, dialect);
        DialectConfiguration dialectConfiguration = this.fileReaderService.readFile(dialect, POWERON);
        Server server = this.databaseService.getServer(serialNumber);
        RequestConfiguration requestConfiguration = new RequestConfiguration(server, dialectConfiguration);
        this.requestService.executeRequest(requestConfiguration);
        LOG.info("Poweron procedure for server {} using dialect {} finished successfully.", serialNumber, dialect);
    }
}
