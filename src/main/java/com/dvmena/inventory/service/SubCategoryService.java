package com.dvmena.inventory.service;

import com.dvmena.inventory.model.SubCategory;
import com.dvmena.inventory.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;

    public void add(SubCategory subCategory){
        subCategoryRepository.save(subCategory);
    }

    public List<SubCategory> findByCategoryId(long id){
        return subCategoryRepository.findByCategoryId(id);
    }
}
