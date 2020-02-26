package com.asiewiera.climb4youapp.repositories;

import com.asiewiera.climb4youapp.entities.Brand;
import org.springframework.data.repository.CrudRepository;

public interface BrandRepository extends CrudRepository<Brand, Integer> {

    Brand findBrandByName(String name);

    Brand findBrandByIdAndName(Integer id, String name);
}
