package com.bootcamp.deliver.Repository;

import com.bootcamp.deliver.Model.ProductOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {

}
