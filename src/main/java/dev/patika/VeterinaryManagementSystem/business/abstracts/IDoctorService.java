package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.entities.Animal;
import dev.patika.VeterinaryManagementSystem.entities.Doctor;
import org.springframework.data.domain.Page;

import javax.print.Doc;
import java.time.LocalDate;
import java.util.List;

public interface IDoctorService {
    Doctor save(Doctor doctor);
    Doctor get(Long id);
    Page<Doctor> cursor(int page, int pageSize);
    Doctor update (Doctor doctor);
    boolean delete (long id);
    List<Doctor> findAll();

    boolean isDoctorAvailable(Long id, LocalDate appointmentDate);

    boolean isAvailableOnDate(Long doctorId, LocalDate date);
}
