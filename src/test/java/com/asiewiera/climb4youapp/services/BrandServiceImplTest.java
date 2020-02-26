package com.asiewiera.climb4youapp.services;

import com.asiewiera.climb4youapp.entities.Brand;
import com.asiewiera.climb4youapp.repositories.BrandRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BrandServiceImplTest {

    @Autowired
    BrandService brandService;

    @Test
    public void shouldFindBrandById() {
        Integer searchedId = 1;
        Brand brand = brandService.findById(searchedId);
        Assert.assertEquals(searchedId, brand.getId());
    }

    @Test
    public void shouldFindBrandByName() {
        String searchedName = "Elderid";
        Brand brand = brandService.findByName(searchedName);
        Assert.assertEquals(searchedName, brand.getName());
    }


    @Test
    public void shouldFindBrand() {
        Brand brand = new Brand(1, "Other");
        Brand searchedBrand = brandService.findBrand(brand);
        Assert.assertEquals(searchedBrand, brand);
    }

    @Test
    public void shouldFindAllBrands() {
        List<Brand> brands = brandService.findAllBrands();
        brands.stream().forEach(System.out::println);
        Assert.assertFalse(brands.isEmpty());
    }

}