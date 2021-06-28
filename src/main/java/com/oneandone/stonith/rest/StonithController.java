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

import static com.oneandone.stonith.entities.Constants.DIALECT_NOT_NULL;

@RestController
@RequestMapping("/stonith/api/")
@Validated
public class StonithController {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private SecurityService securityService;
    @Autowired
    private OperationService operationService;

    @PostMapping("{serialNumber}/restart")
    public ResponseEntity<ResponseMessage> restartServer(@RequestHeader("Authorization") String authorization,
                                                         @PathVariable String serialNumber,
                                                         @RequestParam("dialect") @NotNull(message = DIALECT_NOT_NULL) String dialect)
            throws UnauthorizedException, RequestException {
        this.securityService.checkAuthorizationToken(serialNumber, authorization);
        this.operationService.restartServer(serialNumber, dialect);
        return ResponseEntity.ok(new ResponseMessage("Server " + serialNumber + " successfully restarted."));
    }

    @PostMapping("{serialNumber}/shutdown")
    public ResponseEntity<ResponseMessage> shutdownServer(@RequestHeader("Authorization") String authorization,
                                                @PathVariable String serialNumber,
                                                 @RequestParam("dialect") @NotNull(message = DIALECT_NOT_NULL) String dialect)
            throws UnauthorizedException, RequestException {
        this.securityService.checkAuthorizationToken(serialNumber, authorization);
        this.operationService.shutdownServer(serialNumber, dialect);
        return ResponseEntity.ok(new ResponseMessage("Server " + serialNumber + " successfully shutdown."));
    }

    @PostMapping("{serialNumber}/poweron")
    public ResponseEntity<ResponseMessage> poweronServer(@RequestHeader("Authorization") String authorization,
                                                @PathVariable String serialNumber,
                                                @RequestParam("dialect") @NotNull(message = DIALECT_NOT_NULL) String dialect)
            throws UnauthorizedException, RequestException {
        this.securityService.checkAuthorizationToken(serialNumber, authorization);
        this.operationService.poweronServer(serialNumber, dialect);
        return ResponseEntity.ok(new ResponseMessage("Server " + serialNumber + " successfully powered on."));
    }
}
