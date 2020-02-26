package com.asiewiera.climb4youapp.services;

import com.asiewiera.climb4youapp.dto.SizeGroupDto;
import com.asiewiera.climb4youapp.entities.Size;
import com.asiewiera.climb4youapp.repositories.SizeRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SizeServiceImplTest {

    @Autowired
    SizeRepository sizeRepository;

    @Autowired
    SizeService sizeService;


    @Test
    public void shouldFindAllSizes() {
        List<Size> sizeGroups;
        sizeGroups = sizeService.findAllSizes();
        Assert.assertFalse(sizeGroups.isEmpty());
        //sizeGroups.stream().forEach(System.out::println);
    }

    @Test
    public void shouldFindAllSizeGroups() {

        List<SizeGroupDto> sizeGroups = sizeService.findAllSizeGroups();
        Assert.assertFalse(sizeGroups.isEmpty());
        //sizeGroups.stream().forEach(System.out::println);

    }


    @Test
    public void ShouldFindAllSizesInGroup() {
        String sizeCategory = "meters";
        List<Size> sizeGroups = sizeService.findAllSizesInGroup(sizeCategory);
        sizeGroups.stream().forEach(System.out::println);
    }

    @Test
    public void shouldFindSize() {
        Integer id = 4;
        Size size = sizeService.findSize(id);
        System.out.println(size);
    }

    @Test
    public void shouldFindSizeByNameAndCatName() {
        String name = "NA";
        String catName = "NA";
        Size size = sizeRepository.findByNameAndSizeCategory(name, catName);
        Assert.assertEquals(name, size.getName());
    }
}