package com.bootcamp.deliver.Repository;

import com.bootcamp.deliver.Model.DeliveryAddress;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryAddressRepository extends JpaRepository <DeliveryAddress, Long> {
    
}
