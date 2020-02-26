package com.asiewiera.climb4youapp.controllers.web;

import com.asiewiera.climb4youapp.services.CategoryService;
import com.asiewiera.climb4youapp.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ProductWebController {

    @Autowired
    private CategoryService categoryService;



    @Autowired
    private ProductService productService;

/*    @GetMapping("product/add")
    public String addNewProduct(Model model){
        model.addAttribute("brandTypes", BrandEnum.values());
        List<ProductCategoryDto> deadEndCategories = productCategoryService.findAllCategories();
        model.addAttribute("categories", deadEndCategories);
    *//*    List<SizeGroupDto> sizeCategories = sizeCategoryService.findAllSizeGroups();
        model.addAttribute("sizeCategories", sizeCategories);*//*
        model.addAttribute("product", new ProductDto());
        return "productEdit";

    }*/


}
