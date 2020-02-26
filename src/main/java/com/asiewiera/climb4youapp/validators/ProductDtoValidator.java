package com.asiewiera.climb4youapp.validators;

import com.asiewiera.climb4youapp.dto.ProductDto;
import com.asiewiera.climb4youapp.exceptions.NullDataArgumentException;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoValidator {


    public static void productDtoValidateNoNull(ProductDto productDto) {
        if (productDto.getName() == null) {
            throw new NullDataArgumentException("Name of the product can not be null");
        }

        if (productDto.getBrand() == null) {
            throw new NullDataArgumentException("Brand of the product can not be null");
        }


        if (productDto.getCategory() == null) {
            throw new NullDataArgumentException("Category of the product can not be null");
        }
    }
}



/*
    private String name;

    private BrandEnum brand;

    private BigDecimal price;

    private List<String> sizes_available;

    private String size;

    private ColourEnum colour;

    private Long quantity;

    private String desc;

    private ProductCategory category;

    private String picture;*/
