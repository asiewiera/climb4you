package com.asiewiera.climb4youapp.services;

import com.asiewiera.climb4youapp.entities.Brand;

import java.util.List;


public interface BrandService {
    Brand findById(Integer id);

    Brand findByName(String name);

    Brand findBrand(Brand brand);

    List<Brand> findAllBrands();
}
