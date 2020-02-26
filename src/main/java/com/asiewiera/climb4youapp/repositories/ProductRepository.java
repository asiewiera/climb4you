package com.asiewiera.climb4youapp.repositories;

import com.asiewiera.climb4youapp.entities.Brand;
import com.asiewiera.climb4youapp.entities.Category;
import com.asiewiera.climb4youapp.entities.Product;
import com.asiewiera.climb4youapp.entities.Size;
import com.asiewiera.climb4youapp.enums.ColourEnum;
import com.asiewiera.climb4youapp.enums.GenderEnum;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    List<Product> findProductsByCategoryIn(Collection<Category> category);

    List<Product> findProductsByName(String name);

    //("brand_id","gender","size_id","colour","price")


    List<Product> findProductsByCategoryInAndBrand(Collection<Category> category, Integer brand_id);

    List<Product> findProductsByBrand(Integer brand_id);

    List<Product> findAll(Specification<Product> specification);
}
