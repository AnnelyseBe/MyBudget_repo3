package be.annelyse.budget.web.thymeleaf.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    private static final String VIEW_400_ERROR = "errors/400Error";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public String handleBadRequest(Exception exception, Model model){

        log.debug("ControllerExceptionHandler - handleBadRequest Reached");
        log.error("Bad Request");
        log.error(exception.getMessage());
        model.addAttribute("exception", exception);

        return VIEW_400_ERROR;
    }
}
