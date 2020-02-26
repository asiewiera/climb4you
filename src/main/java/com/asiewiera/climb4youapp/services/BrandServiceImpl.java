package com.asiewiera.climb4youapp.services;

import com.asiewiera.climb4youapp.entities.Brand;
import com.asiewiera.climb4youapp.exceptions.EntityNotFoundException;
import com.asiewiera.climb4youapp.repositories.BrandRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@NoArgsConstructor
@Data
@Service
public class BrandServiceImpl implements BrandService {
    private BrandRepository brandRepository;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public Brand findById(Integer id) {
        Optional<Brand> brandOptional = brandRepository.findById(id);
        if (brandOptional.isPresent()) {
            return brandOptional.get();
        } else {
            String message = String.format("Brand with selected id: %s doesn't exist", id);
            throw new EntityNotFoundException(message);
        }
    }

    @Override
    public Brand findByName(String name) {
        return brandRepository.findBrandByName(name);
    }


    @Override
    public Brand findBrand(Brand brand) {
        return brandRepository.findBrandByIdAndName(brand.getId(), brand.getName());
    }

    @Override
    public List<Brand> findAllBrands() {
        Iterable<Brand> brandIterable = brandRepository.findAll();
        return StreamSupport.stream(brandIterable.spliterator(), true)
                .collect(Collectors.toList());
    }
}
