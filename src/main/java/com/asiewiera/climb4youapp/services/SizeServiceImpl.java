package com.asiewiera.climb4youapp.services;

import com.asiewiera.climb4youapp.dto.SizeGroupDto;
import com.asiewiera.climb4youapp.entities.Size;
import com.asiewiera.climb4youapp.exceptions.EntityNotFoundException;
import com.asiewiera.climb4youapp.repositories.SizeRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SizeServiceImpl implements SizeService {


    private SizeRepository sizeRepository;

    public SizeServiceImpl(SizeRepository sizeRepository) {
        this.sizeRepository = sizeRepository;
    }

    @Override
    public List<Size> findAllSizes() {
        Iterable<Size> sizes = sizeRepository.findAll();
        return StreamSupport.stream(sizes.spliterator(), true).collect(Collectors.toList());
    }

    @Override
    public List<SizeGroupDto> findAllSizeGroups() {
        List<Size> sizes = findAllSizes();
        Map<String, List<Size>> sizeGroupLists = new HashMap<>();
        List<SizeGroupDto> currentList = new ArrayList<>();
        SizeGroupDto sizeGroupDto;

        sizes.stream().map(e -> e.getSizeCategory()).distinct().forEach(e -> sizeGroupLists.put(e, new ArrayList<>()));
        for (Size sizeSel : sizes
        ) {
            String catName = sizeSel.getSizeCategory();
            sizeGroupLists.get(catName).add(sizeSel);
        }
        for (Map.Entry<String, List<Size>> entry : sizeGroupLists.entrySet()) {
            sizeGroupDto = new SizeGroupDto(entry.getKey(), entry.getValue());
            currentList.add(sizeGroupDto);
        }
        return currentList;
    }

    @Override
    public List<Size> findAllSizesInGroup(String group) {
        Iterable<Size> sizes = sizeRepository.findAllBySizeCategory(group);
        return StreamSupport.stream(sizes.spliterator(), true).collect(Collectors.toList());
    }

    @Override
    public Size findSize(Integer id) {
        Optional<Size> optionalSize = sizeRepository.findById(id);
        if (optionalSize.isPresent()) {
            return optionalSize.get();
        } else {
            String msg = String.format("Size with id = %s does not exists", id);
            throw new EntityNotFoundException(msg);
        }

    }


    @Override
    public Size findSizeByName(String name) {
        return sizeRepository.findByName(name);
    }

    @Override
    public Size findSizeByNameAndCategory(String name, String categoryName) {
        Size size = sizeRepository.findByNameAndSizeCategory(name, categoryName);
        if (size == null) {
            throw new EntityNotFoundException("Size not found");
        } else {
            return size;
        }

    }
}
