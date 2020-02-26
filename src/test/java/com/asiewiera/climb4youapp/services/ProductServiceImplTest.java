package com.asiewiera.climb4youapp.services;

import com.asiewiera.climb4youapp.converters.CategoryConverter;
import com.asiewiera.climb4youapp.dto.CategoryDto;
import com.asiewiera.climb4youapp.dto.ProductDto;
import com.asiewiera.climb4youapp.entities.Brand;
import com.asiewiera.climb4youapp.entities.Size;
import com.asiewiera.climb4youapp.enums.ColourEnum;
import com.asiewiera.climb4youapp.enums.GenderEnum;
import com.asiewiera.climb4youapp.exceptions.ProductValidationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.ValidationException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    BrandService brandService;

    @Autowired
    SizeService sizeService;

    @Test
    public void shouldReturnAllProductsByCategory() {
        String catName = "Ropes";
        List<ProductDto> productDtos = productService.findAllProductsByCategory(catName);
        System.out.println(productDtos);

    }

    @Test
    public void shouldFindAllProducts() {
        List<ProductDto> productDtos = productService.findAllProducts();
        Assert.assertFalse(productDtos.isEmpty());
        productDtos.stream().forEach(System.out::println);
    }


    @Test
    public void shouldDelete() {
    }

    @Test
    public void shouldFindById() {
        Long id = 1l;
        ProductDto productDto = productService.findById(id);
        Assert.assertTrue(productDto != null);

    }

    @Test
    public void shouldSaveProduct() {
        String name = "Carabiners";
        String parent = "Hardware";
        CategoryDto categoryDto = categoryService.findByNameAndParentName(name, parent);
        CategoryConverter categoryConverter = new CategoryConverter();
        Brand brand = brandService.findByName("Petzl");
        Size size = sizeService.findSizeByNameAndCategory("NA", "NA");
        ProductDto productDto = ProductDto.builder()
                .name("William")
                .brand(brand)
                .gender(GenderEnum.Uni)
                .category(categoryConverter.apply(categoryDto))
                .size(size)
                .price(BigDecimal.valueOf(55.34))
                .colour(ColourEnum.NA)
                .quantity(0l)
                .desc("The WILLIAM asymmetrical large capacity aluminum carabiner has a pear shape that is practical for easily connecting multiple items. Its ergonomics and Keylock system facilitate manipulations, even when wearing gloves. The WILLIAM carabiner is available in two locking systems: manual SCREW-LOCK or automatic BALL-LOCK.")
                .build();
        ProductDto savedProductDto = null;
        try {
            savedProductDto = productService.save(productDto);
        } catch (ValidationException e) {
            System.out.println(e);
        }
        Assert.assertTrue(savedProductDto != null);


    }


    @Test
    public void shouldUpdateQuantity() {
        ProductDto productDto = productService.findById(7l);
        ProductDto productUpdated = productService.updateQuantity(5l, productDto);
        System.out.println(productUpdated);
    }

    @Test
    public void shouldRaiseExceptionDuringUpdateQuantity() {
        ProductDto productDto = productService.findById(7l);
        String exceptionMessage = null;
        try {
            productService.updateQuantity(-15l, productDto);
        } catch (ProductValidationException e) {
            exceptionMessage = e.getMessage();
        }
        Assert.assertTrue(exceptionMessage != null);
    }


    @Test
    public void findAllMatchingProductsInCategory() {
        Map<String, String> map = new HashMap<>();
       // map.put("brand",3);
        map.put("colour","Blue");
        map.put("brand","3,4,5");
        String category = "Climbing Gear";
        List<ProductDto> productDtos = productService.findAllMatchingProductsInCategory(category, map);
        System.out.println(productDtos.size());
        for (ProductDto productDto: productDtos
             ) {
            System.out.println(productDto);

        }
    }
}