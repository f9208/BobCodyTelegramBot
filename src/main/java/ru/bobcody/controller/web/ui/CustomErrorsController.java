package ru.bobcody.controller.web.ui;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.net.HttpURLConnection;

@Slf4j
@Controller
public class CustomErrorsController implements ErrorController {
    @GetMapping(value = "/error")
    public String handleError(Model model, HttpServletRequest httpServletRequest) {
        model.addAttribute("code", httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        int codeError = (int) httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        log.info("came to error page, error-code: {}", codeError);
        switch (codeError) {
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                return "errors/error-500";
            case HttpURLConnection.HTTP_NOT_FOUND:
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
