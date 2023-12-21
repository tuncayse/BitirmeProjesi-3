package dev.patika.VeterinaryManagementSystem.repository;

import dev.patika.VeterinaryManagementSystem.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface VaccineRepo extends JpaRepository<Vaccine,Long> {

    // Bu metot, verilen hayvan ID'sine sahip aşıların listesini döndürür.
    List<Vaccine> findByAnimalId(Long animalId);

    // Bu metot, belirli bir hayvan ID'sine ve belirli bir koruma başlangıç tarihi aralığında olan aşıların listesini döndürür.
    List<Vaccine> findByAnimalIdAndProtectionStartDateBetween(Long animalId, LocalDate startDate, LocalDate endDate);

    // Bu metot, belirli bir koruma başlangıç tarihi aralığında olan aşıların listesini döndürür.
    List<Vaccine> findByProtectionStartDateBetween(LocalDate startDate, LocalDate endDate);


}
