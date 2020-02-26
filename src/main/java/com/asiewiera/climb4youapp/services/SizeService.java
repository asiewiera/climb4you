package com.asiewiera.climb4youapp.services;

import com.asiewiera.climb4youapp.dto.SizeGroupDto;
import com.asiewiera.climb4youapp.entities.Size;

import java.util.List;

public interface SizeService {

    List<SizeGroupDto> findAllSizeGroups();

    List<Size> findAllSizes();

    List<Size> findAllSizesInGroup(String group);

    Size findSize(Integer id);

    Size findSizeByName(String name);

    Size findSizeByNameAndCategory(String name, String categoryName);
}
