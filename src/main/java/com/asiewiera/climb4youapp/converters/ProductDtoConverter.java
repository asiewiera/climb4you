package com.asiewiera.climb4youapp.converters;

import com.asiewiera.climb4youapp.dto.ProductDto;
import com.asiewiera.climb4youapp.entities.Product;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProductDtoConverter implements Function<Product, ProductDto> {


    @Override
    public ProductDto apply(Product product) {

        //List<String> availableSizes = Arrays.asList(product.getSizeCategory().getAvailableSizes().split(","));
        return new ProductDto(product.getId(), product.getName(), product.getBrand(), product.getGender(), product.getCategory(),
                product.getSize(), product.getPrice(), product.getColour(), product.getQuantity(), product.getDesc(), product.getPicture());
    }
}


