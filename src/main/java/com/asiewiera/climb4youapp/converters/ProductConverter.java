package com.asiewiera.climb4youapp.converters;

import com.asiewiera.climb4youapp.dto.ProductDto;
import com.asiewiera.climb4youapp.entities.Product;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProductConverter implements Function<ProductDto, Product> {

    @Override
    public Product apply(ProductDto productDto) {
        //List<String> availableSizes = Arrays.asList(product.getSizeCategory().getAvailableSizes().split(","));

        return new Product(productDto.getId(), productDto.getName(), productDto.getBrand(), productDto.getGender(), productDto.getCategory(), productDto.getSize(), productDto.getPrice(), productDto.getColour(),
                productDto.getQuantity(), productDto.getDesc(), productDto.getPicture());


    }
}



