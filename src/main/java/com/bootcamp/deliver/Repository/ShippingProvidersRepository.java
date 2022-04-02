package com.bootcamp.deliver.Repository;

import com.bootcamp.deliver.Model.ShippingProvider;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingProvidersRepository extends JpaRepository<ShippingProvider, Long>{
    
}
