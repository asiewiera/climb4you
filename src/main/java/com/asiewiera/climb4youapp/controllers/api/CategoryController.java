package com.asiewiera.climb4youapp.controllers.api;

import com.asiewiera.climb4youapp.dto.CategoryDto;
import com.asiewiera.climb4youapp.dto.ProductDto;
import com.asiewiera.climb4youapp.exceptions.EntityNotFoundException;
import com.asiewiera.climb4youapp.services.CategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, path = "/api/categories")
@Validated
public class CategoryController {

    private CategoryService categoryService;


    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    @ApiOperation(
            value = "Get all product categories",
            notes = "Returns all all product categories",
            response = Iterable.class)
    List<CategoryDto> listCategories() {
        return categoryService.findAllCategories();
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(
            value = "Get category By ID",
            notes = "Returns category By Id.",
            response = CategoryDto.class)
    public CategoryDto getCategory(@PathVariable Integer id) throws EntityNotFoundException {
        return categoryService.findById(id);

    }


    @GetMapping(value = "/filter/selected")
    @ApiOperation(
            value = "Get category By name and parent name",
            notes = "Returns category By Id.",
            response = CategoryDto.class)
    public CategoryDto getCategoryByNameAndParentName(@RequestParam String name, @RequestParam String parentName) throws EntityNotFoundException {
        return categoryService.findByNameAndParentName(name, parentName);

    }


    @GetMapping(value = "/filter/list")
    @ApiOperation(
            value = "Get categories within Parent",
            notes = "Returns categories.",
            response = Iterable.class)
    public List<CategoryDto> getCategoriesWithParent(@RequestParam String parentName) throws EntityNotFoundException {
        return categoryService.findAllChildCategoriesWithParent(parentName);

    }


    @GetMapping(value = "/menu/tree")
    @ApiOperation(
            value = "Get all product categories in Menu structure",
            notes = "Returns all product categories",
            response = Iterable.class)
    List<CategoryDto> menuCategories() {
        return categoryService.findCategoriesMenu();
    }
}