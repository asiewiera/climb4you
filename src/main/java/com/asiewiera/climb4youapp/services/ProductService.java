package com.asiewiera.climb4youapp.services;

import com.asiewiera.climb4youapp.dto.ProductDto;
import com.asiewiera.climb4youapp.entities.Product;
import com.asiewiera.climb4youapp.exceptions.EntityNotFoundException;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Map;

public interface ProductService {

    List<ProductDto> findAllProducts();

    List<ProductDto> findAllProductsByCategory(String parent);

    List<ProductDto> findAllMatchingProductsInCategory(String parent, Map<String, String> map);

    List<ProductDto> findAllProductsByName(String name);

    ProductDto save(ProductDto productDto) throws ValidationException;

    ProductDto update(ProductDto productDto) throws ValidationException;

    void delete(Long id);

    ProductDto findById(Long Id);

    ProductDto updateQuantity(Long quantityChange, ProductDto productDto);
}
