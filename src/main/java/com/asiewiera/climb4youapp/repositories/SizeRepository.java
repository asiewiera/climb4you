package com.asiewiera.climb4youapp.repositories;

import com.asiewiera.climb4youapp.entities.Size;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SizeRepository extends CrudRepository<Size, Integer> {

    Iterable<Size> findAllBySizeCategory(String category);

    Size findByNameAndSizeCategory(String name, String sizeCategory);

    Size findByName(String name);

}
