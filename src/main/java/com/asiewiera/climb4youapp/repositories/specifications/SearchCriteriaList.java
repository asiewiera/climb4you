package com.asiewiera.climb4youapp.repositories.specifications;

import com.asiewiera.climb4youapp.enums.ColourEnum;
import com.asiewiera.climb4youapp.enums.GenderEnum;

import java.math.BigDecimal;
import java.util.*;

public class SearchCriteriaList {
    private static Map<String, SearchCriteria> products = new HashMap<>();

    static {
        products.put("category", new SearchCriteria(Integer.class, "category", SearchOperation.IN, null));
        products.put("brand", new SearchCriteria(Integer.class, "brand", SearchOperation.IN, null));
        products.put("gender", new SearchCriteria(GenderEnum.class, "gender", SearchOperation.IN, null));
        products.put("size", new SearchCriteria(Integer.class, "size", SearchOperation.IN, null));
        products.put("colour", new SearchCriteria(ColourEnum.class, "colour", SearchOperation.IN, null));
        products.put("price", new SearchCriteria(BigDecimal.class, "price", SearchOperation.BETWEEN, null));
        products.put("price_to", new SearchCriteria(BigDecimal.class, "price_to", SearchOperation.LESS_THAN_EQUAL, null));
    }

    public static Map<String, SearchCriteria> getProducts() {
        return products;
    }
}
