package be.annelyse.budget.controllers;

import be.annelyse.budget.service.CostPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j //lombok logging to use just by eg. log.debug("I'm in service")
@RequestMapping("/costposts")
@Controller
public class CostPostController {

    private final CostPostService costPostService;

    @Autowired
    public CostPostController(CostPostService costPostService) {
        this.costPostService = costPostService;
    }

    @RequestMapping({"/","","/index", "/index.html"})
    public String listCostPosts(Model model){
        model.addAttribute("costposts", costPostService.findAll());
        return "costposts/index";
    }

}
