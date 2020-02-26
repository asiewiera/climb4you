package com.asiewiera.climb4youapp.controllers.web;

import com.asiewiera.climb4youapp.dto.CategoryDto;
import com.asiewiera.climb4youapp.dto.ProductDto;
import com.asiewiera.climb4youapp.services.CategoryService;
import com.asiewiera.climb4youapp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductListWebController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;


    @GetMapping(value = "/")
    public String viewProducts(Model model){
        List<ProductDto> productList = productService.findAllProducts();
        List<CategoryDto> mainCategories = categoryService.findCategoriesMenu();
        model.addAttribute("productList", productList);
        model.addAttribute("mainCategories",mainCategories);
        return "mainPage";
    }


}
