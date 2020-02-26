package com.asiewiera.climb4youapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryDto {

    private Integer id;
    private String name;
    private String parentName;
    private List<String> parentPath = new ArrayList<>();
    private List<CategoryDto> children = new ArrayList<>();

    public CategoryDto(Integer id, String name, String parentName) {
        this.id = id;
        this.name = name;
        this.parentName = parentName;
    }

    public void addToParentPath(String name) {
        parentPath.add(0, name);
    }

    public void addToParentPath(List<String> names) {
        parentPath.addAll(0, names);
    }

}
