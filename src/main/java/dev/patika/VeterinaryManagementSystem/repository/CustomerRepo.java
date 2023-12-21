package dev.patika.VeterinaryManagementSystem.repository;

import dev.patika.VeterinaryManagementSystem.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Long> {

    // Bu metot, belirtilen ismi içeren müşterilerin listesini büyük-küçük harf duyarlı olmadan döndürür.
    List<Customer> findByNameContainingIgnoreCase(String name);
}
