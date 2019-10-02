package be.annelyse.budget.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j //lombok logging to use just by eg. log.debug("I'm in service")
@Controller
public class IndexController {

    @RequestMapping({"", "/", "index", "index.html"})
    public String index() {
        System.out.println("some text by annelyse");
        return "index";
    }

    @RequestMapping({"/oups"})
    public String oupsHandler() {
        return "notimplemented";
    }

    /*
    @RequestMapping("hello") //volgens mij hetzelfde als @RequestMapping(path="/hello")
    public ModelAndView handleHello() {
        String text = "joehoe, met het versimpeld voorbeeld van het boek lukt het hier";
        return new ModelAndView("helloView.html", "message", text);
    }

    @GetMapping({"home", "/home"})
    public String homepageMapping() {
        System.out.println("we geraken wel aan de homepagemapping");
        return "homepage";
    }

    @GetMapping({"", "/"})
    public String rootMapping() {
        return "index2";
    }

    @GetMapping({"helloView", "/helloView"})
    public String helloViewMapping(Model model) {
        model.addAttribute("message", "rosemary");
        return "helloView";
    }
    */
}
