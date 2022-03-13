package org.dddjourney.minisignpocbackend.application.openapi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OpenApiRedirectController {

    private static final String REDIRECT_SWAGGER_UI = "redirect:swagger-ui.html";

    @GetMapping("/")
    public String index() {
        return REDIRECT_SWAGGER_UI;
    }
}
