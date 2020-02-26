package com.asiewiera.climb4youapp.services;

import com.asiewiera.climb4youapp.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> findAllCategories();

    List<CategoryDto> findDirectChildCategories(String parentCategory);

    List<CategoryDto> findAllChildCategoriesWithParent(String parentCategory);

    CategoryDto findById(Integer id);

    CategoryDto findByNameAndParentName(String name, String ParentName);

    List<CategoryDto> findCategoriesMenu();


}
