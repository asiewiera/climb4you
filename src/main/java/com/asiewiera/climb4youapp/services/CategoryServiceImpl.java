package com.asiewiera.climb4youapp.services;

import com.asiewiera.climb4youapp.converters.CategoryDtoConverter;
import com.asiewiera.climb4youapp.dto.CategoryDto;
import com.asiewiera.climb4youapp.entities.Category;
import com.asiewiera.climb4youapp.exceptions.EntityNotFoundException;
import com.asiewiera.climb4youapp.exceptions.NullDataArgumentException;
import com.asiewiera.climb4youapp.repositories.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    CategoryDtoConverter categoryDtoConverter;

    public CategoryServiceImpl() {
    }

    private Predicate<CategoryDto> checkParentName(String name) {
        return (e) -> {
            if (e.getParentName() != null) {
                return (e.getParentName().equals(name));
            } else {
                return false;
            }
        };
    }


    @Override
    public List<CategoryDto> findAllCategories() {
        Iterable<Category> productCategoryIterable = productCategoryRepository.findAll();
        return StreamSupport.stream(productCategoryIterable.spliterator(), true)
                .map(categoryDtoConverter)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> findDirectChildCategories(String parentCategory) {
        List<Category> childCategories = productCategoryRepository.findAllByParentName(parentCategory);
        return childCategories.stream().map(categoryDtoConverter).collect(Collectors.toList());
    }


    @Override
    public List<CategoryDto> findAllChildCategoriesWithParent(String parentCategory) throws EntityNotFoundException {
        List<CategoryDto> allCategories = findAllCategories();

        if (allCategories.isEmpty()) {
            throw new EntityNotFoundException("Category list is empty");
        }

        List<CategoryDto> parentsNow;
        if (parentCategory == null) {
            throw new NullDataArgumentException("Filter category cannot be empty");
        } else {
            parentsNow = allCategories.stream().filter(e -> e.getName().equals(parentCategory)).collect(Collectors.toList());
            if (parentsNow.isEmpty()) {
                throw new EntityNotFoundException("No such parent category: " + parentCategory);
            }
        }

        List<CategoryDto> parentsNext;
        List<CategoryDto> currentCategoriesFromOneParent;
        List<CategoryDto> children;
        List<CategoryDto> currentCategories = new ArrayList<>(parentsNow);


        while (parentsNow != null) {
            //System.out.println("Look for childs of:" + parentsNow);
            parentsNext = new ArrayList<>();
            for (CategoryDto parent : parentsNow
            ) {
                currentCategoriesFromOneParent = allCategories.stream().filter(checkParentName(parent.getName())).collect(Collectors.toList());
                if (!currentCategoriesFromOneParent.isEmpty()) {
                    //add information about parents path
                    currentCategoriesFromOneParent.stream().forEach(e -> e.addToParentPath(parent.getParentPath()));
                    currentCategories.addAll(currentCategoriesFromOneParent);
                    parentsNext.addAll(currentCategoriesFromOneParent);

                }
            }
            if (!parentsNext.isEmpty()) {
                parentsNow = new ArrayList<>(parentsNext);
            } else {
                parentsNow = null;
            }
        }
        return currentCategories;
    }


    @Override
    public CategoryDto findById(Integer id) {
        List<CategoryDto> categoryDtos = findAllCategories();
        Optional<CategoryDto> productCategoryDtoOpt = categoryDtos.stream().filter(e -> e.getId().equals(id)).findFirst();
        if (productCategoryDtoOpt.isPresent()) {
            return getProductDtoDetails(categoryDtos, productCategoryDtoOpt.get());
        } else {
            throw new NullDataArgumentException("Product category does not exists" + id);
        }
    }


    private CategoryDto getProductDtoDetails(List<CategoryDto> categoryDtos, CategoryDto categoryDto) {

        //checking all parents.
        String parentName = categoryDto.getParentName();
        Optional<CategoryDto> categoryParentOptional;
        //set parent path
        while (parentName != null) {
            categoryDto.addToParentPath(parentName);
            String name = parentName;
            categoryParentOptional = categoryDtos.stream().filter(e -> e.getName().equals(name)).findFirst();
            if (categoryParentOptional.isPresent()) {
                parentName = categoryParentOptional.get().getParentName();
            }
        }
        //list all children
        String categoryName = categoryDto.getName();
        Predicate<CategoryDto> productCategoryDtoPredicate = e -> {
            if (e.getParentName() != null) {
                return e.getParentName().equals(categoryName);
            }
            return false;
        };

        List<CategoryDto> children = categoryDtos.stream().filter(productCategoryDtoPredicate).collect(Collectors.toList());
        categoryDto.setChildren(children);
        return categoryDto;
    }

    @Override
    public CategoryDto findByNameAndParentName(String name, String parentName) {
        List<CategoryDto> categoryDtos = findAllCategories();
        Optional<CategoryDto> productCategoryDtoOpt = categoryDtos.stream().filter(e -> e.getName().equals(name)).filter(checkParentName(parentName)).findAny();
        if (productCategoryDtoOpt.isPresent()) {
            return getProductDtoDetails(categoryDtos, productCategoryDtoOpt.get());
        } else {
            String message = String.format("Product category with %s and %s does not exists", name, parentName);
            throw new NullDataArgumentException(message);
        }
    }

    @Override
    public List<CategoryDto> findCategoriesMenu() {
        List<CategoryDto> allCategories = findAllCategories();

        if (allCategories.isEmpty()) {
            throw new EntityNotFoundException("Category list is empty");
        }
        List<CategoryDto> parents = allCategories.stream().filter(e -> e.getParentName() == null).collect(Collectors.toList());
        allCategories.removeAll(parents);
        List<CategoryDto> parentsNow = parents;
        List<CategoryDto> parentsNext;
        List<CategoryDto> childrenNow;
        while (!parentsNow.isEmpty()) {
            parentsNext = new ArrayList<>();
            for (CategoryDto parent : parentsNow) {
                childrenNow = allCategories.stream().filter(e -> e.getParentName().equals(parent.getName())).collect(Collectors.toList());
                allCategories.removeAll(childrenNow);
                parent.setChildren(childrenNow);
                parentsNext.addAll(parent.getChildren());
            }
            parentsNow = parentsNext;
        }
        return parents;
    }
}
