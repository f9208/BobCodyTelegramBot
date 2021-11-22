package ru.bobcody.controller.ui;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Slf4j
@Controller
@Getter
@Setter
public class CustomErrorsController implements ErrorController {
    @GetMapping(value = "/error")
    public String handleError(Model model, HttpServletRequest httpServletRequest) {
        model.addAttribute("code", httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        int codeError = (int) httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        log.info("came to error page, error-code: {}", codeError);
        switch (codeError) {
            case 500:
                return "errors/error-500";
            case 404:
                return "errors/error-404";
            default:
                return "errors/error";
        }
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
