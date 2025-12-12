package com.hospital.medicine_cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.medicine_cart.model.MedicineCartItem;

public interface MedicineCartRepository extends JpaRepository<MedicineCartItem, Long>{
    
}
