package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.entities.Appointment;

import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentService {
    Appointment save(Appointment appointment);
    Appointment get(Long id);
    boolean delete (long id);
    List<Appointment> getByDoctorIdAndDateTimeBetween(Long doctorId, LocalDateTime startDate, LocalDateTime endDate);

    List<Appointment> getByAnimalIdAndDateTimeBetween(Long animalId, LocalDateTime startDate, LocalDateTime endDate);

    List<Appointment> getAppointmentsByDateRange(String startDate, String endDate);

    boolean existsByAppointmentDateTime(LocalDateTime appointmentDateTime);

    List<Appointment> findAll();
}
