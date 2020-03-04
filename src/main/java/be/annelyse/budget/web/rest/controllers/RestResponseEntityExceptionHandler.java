package be.annelyse.budget.web.rest.controllers;

import be.annelyse.budget.domain.business.exceptions.ActionNotAllowedException;
import be.annelyse.budget.domain.business.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(Exception exception, WebRequest request) {
        return exception.getMessage();
    }

    @ExceptionHandler({ActionNotAllowedException.class})
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public String handleActionNotAllowedException(Exception exception, WebRequest request) {
        return exception.getMessage();
    }
}
