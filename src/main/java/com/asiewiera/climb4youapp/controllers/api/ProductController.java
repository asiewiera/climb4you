package com.asiewiera.climb4youapp.controllers.api;

import com.asiewiera.climb4youapp.dto.ProductDto;
import com.asiewiera.climb4youapp.exceptions.EntityNotFoundException;
import com.asiewiera.climb4youapp.exceptions.NullDataArgumentException;
import com.asiewiera.climb4youapp.exceptions.UnknownParameterException;
import com.asiewiera.climb4youapp.services.ProductService;
import com.asiewiera.climb4youapp.validators.ProductDtoValidator;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, path = "/api/products")
@Validated //required for @Valid on method parameters such as @RequesParam, @PathVariable, @RequestHeader
public class ProductController {

    private ProductService productService;

/*    @Autowired
    private ProductDtoValidator productDtoValidator;*/


    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    @ApiOperation(
            value = "Get all products",
            notes = "Returns all products.",
            response = Iterable.class)
    Iterable<ProductDto> listProducts() {
        return productService.findAllProducts();
    }


    @GetMapping("/category/{category}")
    @ApiOperation(
            value = "Get all products inside category",
            notes = "Returns all products in selected category.",
            response = Iterable.class)
    Iterable<ProductDto> listProductsInCategory(@PathVariable String category) {
        return productService.findAllProductsByCategory(category);
    }

    @GetMapping("/category/{category}/filter")
    @ApiOperation(
            value = "Get all products inside category filtered by map of parameters",
            notes = "Returns all products in selected category which fulfill other requirements.",
            response = Iterable.class)
    Iterable<ProductDto> listFilteredProductsInCategory(@PathVariable String category, @RequestParam Map<String, String> searchParameters) {
        if (!searchParameters.isEmpty()) {
            //This is only check for Swagger Usage
            if (searchParameters.size() == 1 & searchParameters.containsKey("searchParameters")) {
                //brand=3&colour=Blue,Red
                String line = searchParameters.get("searchParameters");
                List<String> keyValueList = Arrays.stream(line.split("&")).collect(Collectors.toList());
                searchParameters = new HashMap<>();
                for (String el : keyValueList
                ) {
                    String[] keyValuePair = el.split("=");
                    if (keyValuePair.length == 2) {
                        searchParameters.put(keyValuePair[0], keyValuePair[1]);
                    } else {
                        throw new UnknownParameterException("It is not proper key value pair: " + keyValuePair);
                    }
                }
            }
            return productService.findAllMatchingProductsInCategory(category, searchParameters);
        } else {

            return productService.findAllProductsByCategory(category);
        }
    }


    @GetMapping(path = "/{id}")
    @ApiOperation(
            value = "Get product By ID",
            notes = "Returns product By Id.",
            response = ProductDto.class)
    public ProductDto getProduct(@PathVariable Long id) throws EntityNotFoundException {
        return productService.findById(id);

    }

    @PostMapping()
    @ApiOperation(
            value = "Add New product or update existing one",
            notes = "Returns product which will be added or updated. ",
            response = ProductDto.class)
    ProductDto create(@RequestBody ProductDto productDto) throws ValidationException {
        ProductDtoValidator.productDtoValidateNoNull(productDto);
        return productService.save(productDto);

    }

    @PutMapping()
    @ApiOperation(
            value = "Update Existing product",
            notes = "Returns product which will be updated. ",
            response = ProductDto.class)
    ProductDto update(@RequestBody ProductDto productDto) throws NullDataArgumentException, ValidationException {

        if (productDto.getId() == null) {
            throw new NullDataArgumentException("Product id cannot be null in update request");
        }
        ProductDtoValidator.productDtoValidateNoNull(productDto);
        return productService.save(productDto);
    }


    @DeleteMapping("/{id}")
    @ApiOperation(
            value = "Delete Existing product",
            notes = "Returns product which will be deleted. ",
            response = ProductDto.class)
    void delete(@PathVariable Long id) {
        productService.delete(id);

        //(EmptyResultDataAccessException

    }


}
