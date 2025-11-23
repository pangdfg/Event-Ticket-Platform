package com.platform.tickets.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.platform.tickets.domain.dtos.ErrorDto;
import com.platform.tickets.exceptions.EventNotFoundException;
import com.platform.tickets.exceptions.EventUpdateException;
import com.platform.tickets.exceptions.QrCodeGenerationException;
import com.platform.tickets.exceptions.QrCodeNotFoundException;
import com.platform.tickets.exceptions.TicketNotFoundException;
import com.platform.tickets.exceptions.TicketSoldOutException;
import com.platform.tickets.exceptions.TicketTypeNotFoundException;
import com.platform.tickets.exceptions.UserNotFoundException;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j  
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception e){
        log.error("Caught Exception",e);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("An unknown error occurred");
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDto> handleConstraintViolation(ConstraintViolationException e){
        log.error("Caught ConstraintViolationException Exception",e);
        ErrorDto errorDto = new ErrorDto();
       String errorMessage = e.getConstraintViolations()
                .stream()
                .findFirst()
                .map(violation->
                             violation.getPropertyPath() + ": " + violation.getMessage())
                .orElse("Constraint violation occurred");
        errorDto.setError(errorMessage);
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error("Caught MethodArgumentNotValidException Exception",e);
        ErrorDto errorDto = new ErrorDto();
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        String errorMessage = fieldErrors.stream()
                .findFirst()
                .map(fieldError ->
                             fieldError.getField() + ": " + fieldError.getDefaultMessage()).orElse("Validation error occurred");
        errorDto.setError(errorMessage);
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException(UserNotFoundException e){
        log.error("Caught UserNotFoundException Exception",e);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("User not found");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ErrorDto> handleEventNotFoundException(EventNotFoundException e){
        log.error("Caught EventNotFoundException Exception",e);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("Event not found");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TicketTypeNotFoundException.class)
    public ResponseEntity<ErrorDto> handleTicketTypeNotFoundException(TicketTypeNotFoundException e){
        log.error("Caught TicketTypeNotFoundException Exception",e);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("TicketType not found");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EventUpdateException.class)
    public ResponseEntity<ErrorDto> handleEventUpdateException(EventUpdateException e){
        log.error("Caught EventUpdate Exception",e);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("Unable to update event");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(QrCodeGenerationException.class)
    public ResponseEntity<ErrorDto> handleQrCodeGenerationException(QrCodeGenerationException e){
        log.error("Caught QrCodeGenerationException",e);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("Unable to generate qr code");
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(QrCodeNotFoundException.class)
    public ResponseEntity<ErrorDto> handleQrCodeNotFoundException(QrCodeNotFoundException e){
        log.error("Caught QrCodeNotFoundException",e);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("QR code not found");
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TicketSoldOutException.class)
    public ResponseEntity<ErrorDto> handleTicketSoldOutException(TicketSoldOutException e){
        log.error("Caught TicketSoldOutException",e);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("Tickets are sold out for this ticket type");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<ErrorDto> handleTicketNotFoundException(TicketNotFoundException e){
        log.error("Caught TicketNotFoundException",e);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("Tickets not found");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }
}
