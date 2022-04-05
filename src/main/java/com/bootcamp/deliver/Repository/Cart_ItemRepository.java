package com.bootcamp.deliver.Repository;

import java.util.List;

import com.bootcamp.deliver.Model.Cart_Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Cart_ItemRepository extends JpaRepository<Cart_Item, Long>{
    
    public List<Cart_Item> findPackageById(Long id);

}
