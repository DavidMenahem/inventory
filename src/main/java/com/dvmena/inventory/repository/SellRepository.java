package com.dvmena.inventory.repository;

import com.dvmena.inventory.model.Sell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellRepository extends JpaRepository<Sell,Long> {
}
