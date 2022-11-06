package com.dvmena.inventory.repository;

import com.dvmena.inventory.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory,Long> {
    List<SubCategory> findByCategoryId(long id);

    void deleteByCategoryId(long id);
}
