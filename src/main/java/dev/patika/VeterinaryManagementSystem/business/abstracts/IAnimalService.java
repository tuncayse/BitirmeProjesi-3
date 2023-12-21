package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.entities.Animal;
import dev.patika.VeterinaryManagementSystem.entities.AvailableDate;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IAnimalService {
    Animal save(Animal animal,Long customerId);
    Animal get(Long id);
    Page<Animal> cursor(int page, int pageSize);
    Animal update (Animal animal);
    boolean delete (long id);
    List<Animal> getAnimalsByName(String name);
    List<Animal> getAnimalsByCustomerId(Long customerId);
    List<Animal> findAll();
}


