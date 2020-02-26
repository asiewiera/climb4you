package com.asiewiera.climb4youapp.services;

import com.asiewiera.climb4youapp.converters.CategoryConverter;
import com.asiewiera.climb4youapp.converters.ProductConverter;
import com.asiewiera.climb4youapp.converters.ProductDtoConverter;
import com.asiewiera.climb4youapp.dto.CategoryDto;
import com.asiewiera.climb4youapp.dto.ProductDto;
import com.asiewiera.climb4youapp.entities.Category;
import com.asiewiera.climb4youapp.entities.Product;
import com.asiewiera.climb4youapp.entities.Size;
import com.asiewiera.climb4youapp.enums.ColourEnum;
import com.asiewiera.climb4youapp.exceptions.EntityNotFoundException;
import com.asiewiera.climb4youapp.exceptions.ProductValidationException;
import com.asiewiera.climb4youapp.exceptions.UnknownParameterException;
import com.asiewiera.climb4youapp.repositories.ProductRepository;
import com.asiewiera.climb4youapp.repositories.specifications.ProductSpecification;
import com.asiewiera.climb4youapp.repositories.specifications.SearchCriteria;
import com.asiewiera.climb4youapp.repositories.specifications.SearchCriteriaList;
import com.asiewiera.climb4youapp.repositories.specifications.SearchOperation;
import com.asiewiera.climb4youapp.validators.ProductDtoValidator;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@NoArgsConstructor
@Data
@Service
public class ProductServiceImpl implements ProductService {

    @Value("${path.images:/img}")
    private String imagePath;
    private ProductRepository productRepository;
    private ProductDtoConverter productDtoConverter;
    private ProductConverter productConverter;
    private CategoryConverter categoryConverter;
    private CategoryService categoryService;
    private SizeService sizeService;

/*    private final Map<String, String> PRODUCT_CRITERIA = new HashMap<>(
            {"name", "value"},
    );*/


    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductDtoConverter productDtoConverter, ProductConverter productConverter, CategoryConverter categoryConverter, CategoryService categoryService, SizeService sizeService) {
        this.productRepository = productRepository;
        this.productDtoConverter = productDtoConverter;
        this.productConverter = productConverter;
        this.categoryConverter = categoryConverter;
        this.categoryService = categoryService;
        this.sizeService = sizeService;
    }

    @Override
    public List<ProductDto> findAllProducts() {
        Iterable<Product> productIterable = productRepository.findAll();
        List<ProductDto> products = StreamSupport.stream(productIterable.spliterator(), true)
                .map(productDtoConverter)
                .collect(Collectors.toList());
        return products;
    }


    @Override
    public List<ProductDto> findAllProductsByCategory(String parent) {
        List<CategoryDto> productCategoriesDtos = categoryService.findAllChildCategoriesWithParent(parent);
        List<Category> productCategories = productCategoriesDtos.stream().map(categoryConverter).collect(Collectors.toList());
        List<Product> products = productRepository.findProductsByCategoryIn(productCategories);

        return products.stream().map(productDtoConverter).collect(Collectors.toList());
    }


    @Override
    public List<ProductDto> findAllProductsByName(String name) {
        List<Product> products = productRepository.findProductsByName(name);
        List<ProductDto> productDtos = products.stream().map(productDtoConverter).collect(Collectors.toList());
        return productDtos;
    }


    @Override
    public ProductDto save(ProductDto productDto) throws ProductValidationException {
        ProductDtoValidator.productDtoValidateNoNull(productDto);

        Integer productCategoryId = productDto.getCategory().getId();
        CategoryDto categoryDto = categoryService.findById(productCategoryId);
        if (categoryDto == null) {
            throw new EntityNotFoundException("Product category not found");
        }
        if (!categoryDto.getChildren().isEmpty()) {
            throw new ProductValidationException("Product category cannot have child categories");
        }

        Integer sizeCategoryId = productDto.getSize().getId();
        Size size = sizeService.findSize(sizeCategoryId);
        if (size == null) {
            throw new EntityNotFoundException("Size not found");
        }

        //check if product with the same name exists and if yes check color and size
        List<ProductDto> existingProductDtos = findAllProductsByName(productDto.getName());
        Integer productListSize = existingProductDtos.size();
        ColourEnum productColor = productDto.getColour();
        if (productListSize > 0) {
            ProductDto firstProduct = existingProductDtos.get(0);
            String existingSizeCat = firstProduct.getSize().getName();
            Category existingProductCat = firstProduct.getCategory();

            if (!existingProductCat.equals(productDto.getCategory())) {
                if (!(productListSize == 1 & firstProduct.getId() == productDto.getId())) {
                    throw new ProductValidationException("Product Category must be consistent with other products. Product category should be: " + existingProductCat.getName());
                }

            }
            if (!size.getSizeCategory().equals(existingSizeCat)) {
                if (!(productListSize == 1 & firstProduct.getId() == productDto.getId())) {
                    throw new ProductValidationException("Size must be consistent with other products. Size category name should be: " + existingSizeCat);
                }
            }
            Optional<ProductDto> productDuplicateOptional = existingProductDtos.stream().filter(e -> e.getSize().equals(size)).filter(e -> e.getColour().equals(productColor)).findFirst();

            if (productDuplicateOptional.isPresent()) {
                Long productDuplicateId = productDuplicateOptional.get().getId();
                if (productDto.getId() != productDuplicateId) {
                    throw new ProductValidationException("You cannot create product. Product with the same name, colour and size already exists. Modify product with id: " + productDuplicateId);
                }
            }
        }
        String productName = productDto.getName().replaceAll("\\s+", "");
        if (!productColor.equals(ColourEnum.NA)) {
            productName = productName + "_" + productColor;
        }
        String productCatName = categoryDto.getName().replaceAll("\\s+", "");
        String reqPath = productCatName + "\\" + productName;
        String existingPath = productDto.getPicture();
        if (existingPath == null) {
            productDto.setPicture(reqPath);
        } else if (!existingPath.equals(reqPath)) {
            productDto.setPicture(reqPath);
        }

        Product product = productConverter.apply(productDto);
        productRepository.save(product);
        return productDto;

    }


    @Override
    public ProductDto update(ProductDto productDto) throws ProductValidationException {
        Long key = productDto.getId();

        if (productRepository.findById(key).isPresent()) {
            return save(productDto);
        } else {
            throw new EntityNotFoundException("Cannot update product which not exists" + productDto.getId());
        }
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDto findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            ProductDto productDto = productDtoConverter.apply(product.get());
            return productDto;
        } else {
            throw new EntityNotFoundException(ProductDto.class.getSimpleName() + "  object not found");
        }
    }

    @Override
    public ProductDto updateQuantity(Long quantityChange, ProductDto productDto) {
        ProductDto productToUpdate = findById(productDto.getId());
        Long quantityToUpdate = productToUpdate.getQuantity() + quantityChange;
        if (quantityToUpdate < 0) {
            throw new ProductValidationException("Quantity cannot be less then zero. Max change is: " + productToUpdate.getQuantity());
        }

        productToUpdate.setQuantity(quantityToUpdate + quantityChange);
        Product product = productConverter.apply(productToUpdate);
        product = productRepository.save(product);
        return productDtoConverter.apply(product);
    }


    @Override
    public List<ProductDto> findAllMatchingProductsInCategory(String parent, Map<String, String> map) {
        //filter by those :
       /* brand_id INT(11)
        gender VARCHAR(10)
        size_id INT(11)
        colour VARCHAR(50)
        price DECIMAL(19,2)*/

        ProductSpecification productSpecification = new ProductSpecification();
        List<Object> categoriesIds = categoryService.findAllChildCategoriesWithParent(parent).stream().map(CategoryDto::getId).collect(Collectors.toList());
        SearchCriteria searchCriteria = new SearchCriteria("category", SearchOperation.IN, categoriesIds, categoriesIds);
        SearchCriteria reqCriteria;
        productSpecification.add(searchCriteria);
        Map<String, SearchCriteria> reqParamNames = SearchCriteriaList.getProducts();
        Class specificClass = Object.class;

        for (Map.Entry<String, String> entry : map.entrySet()
        ) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (SearchCriteriaList.getProducts().containsKey(key) && value != null) {
                reqCriteria = reqParamNames.get(key);
                searchCriteria = new SearchCriteria(reqCriteria.getSpecificClass(), key, reqCriteria.getOperation(), value);
                productSpecification.add(searchCriteria);
            } else {
                throw new UnknownParameterException("Unknown parameter : " + key + " during products filtering");
            }
        }
        List<Product> products = productRepository.findAll(productSpecification);
        return products.stream().map(productDtoConverter).collect(Collectors.toList());
    }
}
