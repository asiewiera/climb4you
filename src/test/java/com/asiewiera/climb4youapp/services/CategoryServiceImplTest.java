package com.asiewiera.climb4youapp.services;

import com.asiewiera.climb4youapp.dto.CategoryDto;
import com.asiewiera.climb4youapp.repositories.ProductCategoryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {


    @Autowired(required = true)
    ProductCategoryRepository productCategoryRepository;

    @Autowired(required = true)
    CategoryService categoryService;


    @Test
    public void shouldReturnAllCategories() {
        List<CategoryDto> categoryDtos = categoryService.findAllCategories();

        categoryDtos.stream().forEach(System.out::println);
        Assertions.assertEquals(12, categoryDtos.size());
    }

    @Test
    public void shouldReturnOnlyDirectChildCategoriesByParentName() {

        String parentName = "Hardware";
        List<CategoryDto> categoryDtos = categoryService.findDirectChildCategories(parentName);
        categoryDtos.stream().forEach(System.out::println);
        Assertions.assertEquals(2, categoryDtos.size());

    }


    @Test
    public void shouldReturnAllChildCategoriesIncludingParent() {

        //String parentName = null;
        String parentName = "Climbing Gear";
        List<CategoryDto> categoryDtos = categoryService.findAllChildCategoriesWithParent(parentName);
        categoryDtos.stream().forEach(System.out::println);
        //Assertions.assertEquals(2, categoryDtos.size());

    }


    @Test
    public void shouldReturnCategorySelectedById() {
        Integer id = 5;
        CategoryDto categoryDto = categoryService.findById(id);
        System.out.println(categoryDto);
        Assert.assertEquals(id, categoryDto.getId());
    }


    @Test
    public void shouldReturnCategorySelectedByINameAndParent() {
        String name = "Hardware";
        String parent = "Climbing Gear";
        CategoryDto categoryDto = categoryService.findByNameAndParentName(name, parent);
        System.out.println(categoryDto);
        Assert.assertEquals(name, categoryDto.getName());
    }

    @Test
    public void shouldReturnCategoriesMenu() {
        List<CategoryDto> list = categoryService.findCategoriesMenu();
        System.out.println("List: " + list + " end");
    }

}