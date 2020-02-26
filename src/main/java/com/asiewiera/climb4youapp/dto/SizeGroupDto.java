package com.asiewiera.climb4youapp.dto;

import com.asiewiera.climb4youapp.entities.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Component
public class SizeGroupDto {

    private String name;
    private List<Size> availableSizes;


    public SizeGroupDto(String name) {
        this.name = name;
        availableSizes = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "SizeGroupDto{" +
                "name='" + name + '\'' +
                ", availableSizes=" + availableSizes +
                '}';
    }
}
