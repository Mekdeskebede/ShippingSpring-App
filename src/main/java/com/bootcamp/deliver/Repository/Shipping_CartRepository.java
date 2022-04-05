package com.bootcamp.deliver.Repository;

import com.bootcamp.deliver.Model.Shipping_Cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Shipping_CartRepository extends JpaRepository<Shipping_Cart, Long> {

}
