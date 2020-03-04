package be.annelyse.budget.web.thymeleaf.controllers;

import be.annelyse.budget.domain.business.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/categories")
@Controller
@Slf4j //lombok logging to use just by eg. log.debug("I'm in service")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping({"/","","/index", "/index.html"})
    public String listCategories(Model model){
        model.addAttribute("categories",categoryService.findAll());
        return "categories/index";
    }
}
