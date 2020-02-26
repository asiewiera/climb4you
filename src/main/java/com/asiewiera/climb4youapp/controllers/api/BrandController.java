package com.asiewiera.climb4youapp.controllers.api;

import com.asiewiera.climb4youapp.entities.Brand;
import com.asiewiera.climb4youapp.services.BrandService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.util.List;


@RestController
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, path = "/api/brands")
@Validated
public class BrandController {

    private BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }


    @GetMapping
    @ApiOperation(
            value = "Get all brands",
            notes = "Returns all brands",
            response = Iterable.class)
    List<Brand> listBrands() {
        return brandService.findAllBrands();
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(
            value = "Get brand",
            notes = "Returns brand by id",
            response = Brand.class)
    Brand getBrand(@PathVariable Integer id) {
        return brandService.findById(id);
    }
}
