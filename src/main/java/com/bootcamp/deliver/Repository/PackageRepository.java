package com.bootcamp.deliver.Repository;

import java.util.List;

import com.bootcamp.deliver.Model.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long>{
    
    public List<Package> findPackageById(Long id);

}
