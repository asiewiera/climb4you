package com.asiewiera.climb4youapp.controllers.api;

import com.asiewiera.climb4youapp.dto.SizeGroupDto;
import com.asiewiera.climb4youapp.entities.Size;
import com.asiewiera.climb4youapp.services.SizeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, path = "/api/sizes")
@Validated
public class SizeController {

    private SizeService sizeService;

    @Autowired
    public SizeController(SizeService sizeService) {
        this.sizeService = sizeService;
    }


    @GetMapping()
    @ApiOperation(
            value = "Get all sizes",
            notes = "Returns all sizes.",
            response = List.class)
    List<Size> listAllSizes() {
        return sizeService.findAllSizes();
    }


    @GetMapping("/{id}")
    @ApiOperation(
            value = "Get selected size",
            notes = "Returns selected size by Id.",
            response = Size.class)
    Size findSizeById(@PathVariable Integer id) {
        return sizeService.findSize(id);
    }


    @GetMapping("/filter")
    @ApiOperation(
            value = "Get selected size",
            notes = "Returns selected size by name and category.",
            response = Size.class)
    Size findSizeByNameAndCategory(@RequestParam String name, @RequestParam String catName) {
        return sizeService.findSizeByNameAndCategory(name, catName);
    }

    @GetMapping(value = "/groups")
    @ApiOperation(
            value = "Get all size groups",
            notes = "Returns all size groups.",
            response = List.class)
    List<SizeGroupDto> listAllSizeGroups() {
        return sizeService.findAllSizeGroups();
    }

}
