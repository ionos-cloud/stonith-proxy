package com.oneandone.stonith.errors;

import com.oneandone.stonith.entities.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ErrorHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseMessage> onUnexpectedException(Exception e) {
        LOG.info("Encountered unexpected exception", e);
        ResponseMessage responseMessage = new ResponseMessage("Encountered unexpected exception. Please check logs.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseMessage> onCustomException(CustomException e) {
        if (e.getCause() != null) {
            LOG.info(e.getMessage(), e);
        } else {
            LOG.info(e.getMessage());
        }
        ResponseMessage responseMessage = new ResponseMessage(e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(responseMessage);
    }
}
