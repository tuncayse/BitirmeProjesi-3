package dev.patika.VeterinaryManagementSystem.business.concretes;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IAvailableDateService;
import dev.patika.VeterinaryManagementSystem.core.config.exception.NotFoundException;
import dev.patika.VeterinaryManagementSystem.core.utiles.Msg;
import dev.patika.VeterinaryManagementSystem.entities.AvailableDate;
import dev.patika.VeterinaryManagementSystem.entities.Doctor;
import dev.patika.VeterinaryManagementSystem.repository.AvailableDateRepo;
import dev.patika.VeterinaryManagementSystem.repository.DoctorRepo;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AvailableDateManager implements IAvailableDateService {

    private final AvailableDateRepo availableDateRepo;
    private final DoctorRepo doctorRepo;

    public AvailableDateManager(AvailableDateRepo availableDateRepo, DoctorRepo doctorRepo) {
        this.availableDateRepo = availableDateRepo;
        this.doctorRepo = doctorRepo;
    }
/*
    @Override
    public AvailableDate save(AvailableDate availableDate, Long doctorId) {
        Doctor doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new NotFoundException("Doktor bulunamadı."));

        // Doktoru belirt ve AvailableDate'i kaydet
        availableDate.setDoctor(doctor);
        return availableDateRepo.save(availableDate);
    }

 */

    @Override
    public AvailableDate save(AvailableDate availableDate, Long doctorId) {
        Doctor doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new NotFoundException("Doktor bulunamadı."));

        LocalDate dateToCheck = availableDate.getAvailableDate();
        // Belirli bir tarih için aynı doktora ait availableDate'in var olup olmadığını kontrol et
        Optional<AvailableDate> existingDate = availableDateRepo.findByAvailableDateAndDoctorId(dateToCheck, doctorId);
        if (existingDate.isPresent()) {
            // Eğer varsa hata fırlat veya mevcut olanı geri döndür
            throw new RuntimeException("Bu tarih için zaten bir kayıt mevcut.");
            // Alternatif olarak:
            // return existingDate.get();
        }

        availableDate.setDoctor(doctor);
        return availableDateRepo.save(availableDate);
    }


    public List<AvailableDate> getAllByDoctorId(Long doctorId) {
        return availableDateRepo.findAllByDoctorId(doctorId);
    }

    @Override
    public List<AvailableDate> findAll() {
        return this.availableDateRepo.findAll();
    }

    @Override
    public AvailableDate get(Long id) {
        return this.availableDateRepo.findById(id).orElseThrow(()-> new NotFoundException(Msg.NOT_FOUND));
    }

    //Belirli bir AvailableDate' i siler.
    @Override
    public boolean delete(long id) {
        AvailableDate availableDate = this.get(id);
        this.availableDateRepo.delete(availableDate);
        return true;
    }

}
