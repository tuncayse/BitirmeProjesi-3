package dev.patika.VeterinaryManagementSystem.business.concretes;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IDoctorService;
import dev.patika.VeterinaryManagementSystem.core.config.exception.NotFoundException;
import dev.patika.VeterinaryManagementSystem.core.utiles.Msg;
import dev.patika.VeterinaryManagementSystem.entities.Doctor;
import dev.patika.VeterinaryManagementSystem.repository.DoctorRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DoctorManager implements IDoctorService {
    // DoctorRepo bağımlılığını enjekte etmek için constructor
    private final DoctorRepo doctorRepo;

    // Constructor enjeksiyonu
    public DoctorManager(DoctorRepo doctorRepo) {
        this.doctorRepo = doctorRepo;
    }

    // Doktoru kaydetmek için
    @Override
    public Doctor save(Doctor doctor) {
        return this.doctorRepo.save(doctor); // DoctorRepo'nun save metodu kullanılır
    }


    // Doktoru ID'ye göre getirmek için
    @Override
    public Doctor get(Long id) {
        // DoctorRepo'daki findById kullanılır, eğer bulunamazsa NotFound exception fırlatılır
        return this.doctorRepo.findById(id).orElseThrow(()-> new NotFoundException(Msg.NOT_FOUND));
    }

    // Sayfalı olarak doktorları getirmek için
    @Override
    public Page<Doctor> cursor(int page, int pageSize) {
        // Sayfalama yapmak için PageRequest kullanılır
        Pageable pageable = PageRequest.of(page,pageSize);
        return this.doctorRepo.findAll(pageable);// DoctorRepo'nun findAll metodu kullanılır
    }

    // Doktoru güncellemek için
    @Override
    public Doctor update(Doctor doctor) {
        this.get(doctor.getId());
        return this.doctorRepo.save(doctor);// DoctorRepo'nun save metodu kullanılır
    }

    // Doktoru silmek için
    @Override
    public boolean delete(long id) {
        Doctor doctor=this.get(id);
        this.doctorRepo.delete(doctor);// DoctorRepo'nun delete metodu ile silinir
        return true;// Silme işlemi başarılı olduğu için true döndürülür
    }

    @Override
    public List<Doctor> findAll() {
        return this.doctorRepo.findAll();
    }

    @Override
    public boolean isDoctorAvailable(Long id, LocalDate appointmentDate) {
        return false;
    }


    @Override
    public boolean isAvailableOnDate(Long doctorId, LocalDate date) {
        Doctor doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new NotFoundException("Doktor bulunamadı."));

        // Doktorun müsait olduğu günleri içeren bir metod düşünülerek aşağıdaki kontrol sağlanabilir
        return doctor.isAvailableOnDate(date);
    }



}
