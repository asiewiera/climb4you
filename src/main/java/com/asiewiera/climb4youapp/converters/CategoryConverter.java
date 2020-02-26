package com.asiewiera.climb4youapp.converters;

import com.asiewiera.climb4youapp.dto.CategoryDto;
import com.asiewiera.climb4youapp.entities.Category;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CategoryConverter implements Function<CategoryDto, Category> {

    @Override
    public Category apply(CategoryDto productCategory) {
        //return new ProductCategory(productCategory.getId(), productCategory.getName(), productCategory.getParentName());
        return new Category(productCategory.getId(), productCategory.getName(), productCategory.getParentName());
    }
}
