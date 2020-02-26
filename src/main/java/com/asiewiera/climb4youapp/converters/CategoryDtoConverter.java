package com.asiewiera.climb4youapp.converters;

import com.asiewiera.climb4youapp.dto.CategoryDto;
import com.asiewiera.climb4youapp.entities.Category;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CategoryDtoConverter implements Function<Category, CategoryDto> {

    @Override
    public CategoryDto apply(Category category) {
        //return new ProductCategoryDto(productCategory.getId(), productCategory.getName(), productCategory.getParentName(),null);
        return new CategoryDto(category.getId(), category.getName(), category.getParentName());
    }
}
