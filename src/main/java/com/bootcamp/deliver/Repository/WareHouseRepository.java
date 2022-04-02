package com.bootcamp.deliver.Repository;

import com.bootcamp.deliver.Model.warehouse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WareHouseRepository extends JpaRepository<warehouse, Long> {

}
