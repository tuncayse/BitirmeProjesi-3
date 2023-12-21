package dev.patika.VeterinaryManagementSystem.repository;

import dev.patika.VeterinaryManagementSystem.entities.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepo extends JpaRepository<Animal,Long> {

    // Bu metot, ismi belirtilen hayvanların listesini büyük-küçük harf duyarlı olmadan döndürür.
    List<Animal> findByNameIgnoreCaseContaining(String name);

    // Bu metot, verilen müşteri ID'sine sahip hayvanların listesini döndürür.
    List<Animal> findByCustomerId(Long customerId);
}
