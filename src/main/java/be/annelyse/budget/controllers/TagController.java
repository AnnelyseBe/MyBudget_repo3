package be.annelyse.budget.controllers;

import be.annelyse.budget.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j //lombok logging to use just by eg. log.debug("I'm in service")
@Controller
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @RequestMapping({"/", "", "/index", "/index.html"})
    public String listTags(Model model) {
        model.addAttribute("tags", tagService.findAll());
        return "tags/index";
    }
}
