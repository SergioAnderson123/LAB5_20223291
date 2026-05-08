package org.example.lab5_20223291.repository;


import org.example.lab5_20223291.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Customer,Integer> {
}
