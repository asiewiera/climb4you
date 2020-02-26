package com.asiewiera.climb4youapp.repositories.specifications;

import com.asiewiera.climb4youapp.entities.Product;
import com.asiewiera.climb4youapp.enums.ColourEnum;
import com.asiewiera.climb4youapp.enums.GenderEnum;
import com.asiewiera.climb4youapp.exceptions.UnknownParameterException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class SearchCriteria {
    private Class specificClass;
    private String key;
    private SearchOperation operation;
    private String originalValue;
    private Object value;
    private List<Object> valueList;
    private static List<SearchOperation> pluralOperation = Arrays.asList(SearchOperation.IN);


    public SearchCriteria(Class specificClass, String key, SearchOperation operation, String originalValue) {
        this.specificClass = specificClass;
        this.key = key;
        this.operation = operation;
        this.originalValue = originalValue;

        if (originalValue != null) {
            if (operation.equals(SearchOperation.IN) || operation.equals(SearchOperation.BETWEEN)) {
                String[] arr = originalValue.split(",");
                valueList = Arrays.stream(arr).map(e -> casting(specificClass, e)).collect(Collectors.toList());
                if (operation.equals(SearchOperation.BETWEEN)) {

                    if (valueList.size() != 2) {
                        throw new UnknownParameterException(operation.toString() + " is used only with 2 parameters, but found: + " + valueList.size());
                    }
                }
            } else {
                this.value = casting(specificClass, originalValue);
            }
        }
    }


    public SearchCriteria(String key, SearchOperation operation, Object value, List<Object> valueList) {
        this.key = key;
        this.operation = operation;
        this.value = value;
        this.valueList = valueList;
    }


    private Object casting(Class specificClass, String stringValue) {
        Object outputValue;
        switch (specificClass.getSimpleName()) {
            case "Integer":
                outputValue = Integer.parseInt(stringValue);
                break;
            case "Long":
                outputValue = Long.parseLong(stringValue);
                break;
            case "Double":
                outputValue = Double.parseDouble(stringValue);
                break;
            case "BigDecimal":
                outputValue = new BigDecimal(stringValue);
                break;
            case "ColourEnum":
                outputValue = ColourEnum.valueOf(stringValue);
                break;
            case "GenderEnum":
                outputValue = GenderEnum.valueOf(stringValue);
                break;
            default:
                outputValue = value;
        }
        return outputValue;

    }
}
