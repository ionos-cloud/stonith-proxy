package com.oneandone.stonith.rest;

import com.oneandone.stonith.entities.ResponseMessage;
import com.oneandone.stonith.errors.RequestException;
import com.oneandone.stonith.errors.UnauthorizedException;
import com.oneandone.stonith.services.OperationService;
import com.oneandone.stonith.services.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/stonith/api/")
@Validated
public class StonithController {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private SecurityService securityService;
    @Autowired
    private OperationService operationService;

    @PostMapping("{serialNumber}/status")
    public ResponseEntity<ResponseMessage> statusServer(@RequestHeader("Authorization") String authorization,
                                                         @PathVariable String serialNumber)
            throws UnauthorizedException, RequestException {
        this.securityService.checkAuthorizationToken(serialNumber, authorization);
        String status = this.operationService.statusServer(serialNumber);
        return ResponseEntity.ok(new ResponseMessage(status));
    }

    @PostMapping("{serialNumber}/restart")
    public ResponseEntity<ResponseMessage> restartServer(@RequestHeader("Authorization") String authorization,
                                                         @PathVariable String serialNumber)
            throws UnauthorizedException, RequestException {
        this.securityService.checkAuthorizationToken(serialNumber, authorization);
        this.operationService.restartServer(serialNumber);
        return ResponseEntity.ok(new ResponseMessage("Server " + serialNumber + " successfully restarted."));
    }

    @PostMapping("{serialNumber}/shutdown")
    public ResponseEntity<ResponseMessage> shutdownServer(@RequestHeader("Authorization") String authorization,
                                                @PathVariable String serialNumber)
            throws UnauthorizedException, RequestException {
        this.securityService.checkAuthorizationToken(serialNumber, authorization);
        this.operationService.shutdownServer(serialNumber);
        return ResponseEntity.ok(new ResponseMessage("Server " + serialNumber + " successfully shutdown."));
    }

    @PostMapping("{serialNumber}/poweron")
    public ResponseEntity<ResponseMessage> poweronServer(@RequestHeader("Authorization") String authorization,
                                                @PathVariable String serialNumber)
            throws UnauthorizedException, RequestException {
        this.securityService.checkAuthorizationToken(serialNumber, authorization);
        this.operationService.poweronServer(serialNumber);
        return ResponseEntity.ok(new ResponseMessage("Server " + serialNumber + " successfully powered on."));
    }
}
