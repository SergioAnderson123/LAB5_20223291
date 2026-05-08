package org.example.lab5_20223291.repository;

import org.example.lab5_20223291.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product , Integer> {

}
