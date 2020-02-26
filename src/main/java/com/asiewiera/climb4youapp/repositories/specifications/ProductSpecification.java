package com.asiewiera.climb4youapp.repositories.specifications;

import com.asiewiera.climb4youapp.entities.Product;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductSpecification implements Specification<Product> {

    private List<SearchCriteria> list;

    public ProductSpecification() {
        this.list = new ArrayList<>();
    }

    public void add(SearchCriteria criteria) {
        list.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        //create a new predicate list
        List<Predicate> predicates = new ArrayList<>();
        for (SearchCriteria criteria : list) {
            if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                predicates.add(criteriaBuilder.equal(
                        root.get(criteria.getKey()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
                predicates.add(criteriaBuilder.notEqual(
                        root.get(criteria.getKey()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get(criteria.getKey()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get(criteria.getKey()), criteria.getValue().toString()));
            } else if (criteria.getOperation().equals(SearchOperation.IN)) {
                Predicate predicate = null;
                CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get(criteria.getKey()));
                for (Object val : criteria.getValueList()
                ) {
                    predicate = in.value(val);
                }
                predicates.add(predicate);

            } else if (criteria.getOperation().equals(SearchOperation.BETWEEN)) {
                BigDecimal min_val = (BigDecimal) criteria.getValueList().get(0);
                BigDecimal max_val = (BigDecimal) criteria.getValueList().get(1);
                predicates.add(criteriaBuilder.between(
                        root.get(criteria.getKey()), min_val, max_val));
            }

        }


        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
