package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.entities.AvailableDate;

import java.util.List;

public interface IAvailableDateService {
    AvailableDate save(AvailableDate availableDate,Long doctorId);
    AvailableDate get(Long id);
    boolean delete (long id);

    List<AvailableDate> getAllByDoctorId(Long id);

    List<AvailableDate> findAll();

}
