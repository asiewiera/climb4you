package com.asiewiera.climb4youapp.entities;


import com.asiewiera.climb4youapp.enums.ColourEnum;
import com.asiewiera.climb4youapp.enums.GenderEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

//@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, name = "name")
    private String name;

    @OneToOne()
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Column(length = 10, name = "gender")
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @OneToOne
    @JoinColumn(name = "product_category_id")
    private Category category;

    @OneToOne()
    @JoinColumn(name = "size_id")
    private Size size;


    @Column(name = "price")
    private BigDecimal price;


    @Column(length = 50, name = "colour")
    @Enumerated(EnumType.STRING)
    private ColourEnum colour;

    @Column(name = "quantity")
    private Long quantity;


    @Column(name = "description", columnDefinition = "TEXT")
    private String desc;

    @Column(name = "picture_path")
    private String picture;


    public Product(Long id, String name, Brand brand, GenderEnum gender, Category category, Size size, BigDecimal price, ColourEnum colour, Long quantity, String desc, String picture) {
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
}
