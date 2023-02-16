package com.github.danrog303.poketch.app;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AppErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(Model model, HttpServletRequest request) {
        String msg = switch(getErrorCode(request)) {
            case 400 -> "Error 400. Bad Request";
            case 403 -> "Error 403. Access Forbidden";
            case 404 -> "Error 404. Not Found";
            default -> "Error 500. Server error";
        };

        model.addAttribute("message", msg);
        return "pages/message-show";
    }

    private int getErrorCode(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            return Integer.parseInt(status.toString());
        } else {
            return 500;
        }
    }
}
