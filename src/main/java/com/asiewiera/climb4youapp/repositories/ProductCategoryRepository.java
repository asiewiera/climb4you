package com.asiewiera.climb4youapp.repositories;

import com.asiewiera.climb4youapp.entities.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends CrudRepository<Category, Long> {


    List<Category> findAllByParentName(String parentName);

    List<Category> findAllByName(String name);
}
