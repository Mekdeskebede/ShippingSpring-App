package com.bootcamp.deliver.Repository;

import com.bootcamp.deliver.Model.Address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
