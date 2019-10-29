package be.annelyse.budget.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j //lombok logging to use just by eg. log.debug("I'm in service")
@Controller
public class IndexController {

    @RequestMapping({"", "/", "index", "index.html"})
    public String index() {
        log.debug("some text by annelyse");
        return "index";
    }

    @RequestMapping({"/oups"})
    public String oupsHandler() {
        return "notimplemented";
    }

}
