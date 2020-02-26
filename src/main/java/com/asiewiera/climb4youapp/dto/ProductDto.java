package com.asiewiera.climb4youapp.dto;

import com.asiewiera.climb4youapp.entities.Brand;
import com.asiewiera.climb4youapp.entities.Category;
import com.asiewiera.climb4youapp.entities.Size;
import com.asiewiera.climb4youapp.enums.ColourEnum;
import com.asiewiera.climb4youapp.enums.GenderEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@NoArgsConstructor

@Data
@Component
public class ProductDto {
    private Long id;

    private String name;

    private Brand brand;

    private GenderEnum gender;

    private Category category;

    private Size size;

    private BigDecimal price;

    private ColourEnum colour;

    private Long quantity;

    private String desc;

    private String picture;


    public ProductDto(Long id, String name, Brand brand, GenderEnum gender, Category category, Size size, BigDecimal price, ColourEnum colour, Long quantity, String desc, String picture) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.gender = gender;
        this.category = category;
        this.size = size;
        this.price = price;
        this.colour = colour;
        this.quantity = quantity;
        this.desc = desc;
        this.picture = picture;
    }

    public static final class Builder {
        private Long id;

        private String name;

        private Brand brand;

        private GenderEnum gender;

        private Category category;

        private Size size;

        private BigDecimal price;

        private ColourEnum colour;

        private Long quantity;

        private String desc;

        private String picture;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder brand(Brand brand) {
            this.brand = brand;
            return this;
        }

        public Builder gender(GenderEnum gender) {
            this.gender = gender;
            return this;
        }

        public Builder category(Category category) {
            this.category = category;
            return this;
        }

        public Builder size(Size size) {
            this.size = size;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder colour(ColourEnum colour) {
            this.colour = colour;
            return this;
        }

        public Builder quantity(Long quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder desc(String desc) {
            this.desc = desc;
            return this;
        }

        public Builder picture(String picture) {
            this.picture = picture;
            return this;
        }

        public ProductDto build() {
            ProductDto productDto = new ProductDto();
            productDto.id = this.id;
            productDto.name = this.name;
            productDto.brand = this.brand;
            productDto.gender = this.gender;
            productDto.category = this.category;
            productDto.size = this.size;
            productDto.price = this.price;
            productDto.colour = this.colour;
            productDto.quantity = this.quantity;
            productDto.desc = this.desc;
            productDto.picture = this.picture;
            return productDto;
        }


    }

    public static Builder builder() {
        return new Builder();
    }

}



