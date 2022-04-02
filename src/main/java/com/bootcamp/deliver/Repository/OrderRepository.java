package com.bootcamp.deliver.Repository;

import com.bootcamp.deliver.Model.Order;
import com.bootcamp.deliver.Model.ProductOrder;
import com.bootcamp.deliver.Model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository <Order,Long> {
    @Query(value = "SELECT p FROM ProductOrder p WHERE p.user= ?1")
    public ProductOrder findByUser(User user);
}
