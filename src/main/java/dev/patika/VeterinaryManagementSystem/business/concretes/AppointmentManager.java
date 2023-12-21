package dev.patika.VeterinaryManagementSystem.business.concretes;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IAppointmentService;
import dev.patika.VeterinaryManagementSystem.core.config.exception.NotFoundException;
import dev.patika.VeterinaryManagementSystem.core.utiles.Msg;
import dev.patika.VeterinaryManagementSystem.entities.Appointment;
import dev.patika.VeterinaryManagementSystem.entities.Doctor;
import dev.patika.VeterinaryManagementSystem.repository.AppointmentRepo;
import dev.patika.VeterinaryManagementSystem.repository.DoctorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentManager implements IAppointmentService {
    private final AppointmentRepo appointmentRepo;
    private final DoctorRepo doctorRepo;

    @Override
    public Appointment save(Appointment appointment) {
        Doctor doctor = doctorRepo.findById(appointment.getDoctor().getId())
                .orElseThrow(() -> new NotFoundException("Doktor bulunamadı."));
        // Doktorun müsait olduğu günlerden birinde mi kontrol et
        LocalDate appointmentDate = appointment.getAppointmentDateTime().toLocalDate();
        boolean isDoctorAvailable = this.isDoctorAvailable(doctor.getId(), appointmentDate);
        if (!isDoctorAvailable) {
            throw new IllegalArgumentException("Doktor bu tarihte müsait değil.");
        }
        // Aynı saatte başka bir randevusu var mı kontrol et
        boolean hasAppointmentAtSameTime = this.existsByAppointmentDateTime(appointment.getAppointmentDateTime());
        if (hasAppointmentAtSameTime) {
            throw new IllegalArgumentException("Doktorun aynı saatte başka bir randevusu bulunuyor.");
        }
        // Doktorun AvailableDate listesini kontrol et
        if (!doctor.isAvailableOnDate(appointmentDate)) {
            throw new IllegalArgumentException("Doktorun bu tarihte müsaitlik bilgisi bulunmamaktadır.");
        }

        return appointmentRepo.save(appointment);
    }
    public boolean isDoctorAvailable(Long doctorId, LocalDate date) {
        Doctor doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new NotFoundException("Doktor bulunamadı."));
        // Doktorun müsait olduğu günleri içeren bir metod düşünülerek aşağıdaki kontrol sağlanabilir
        return doctor.isAvailableOnDate(date);
    }

    public boolean existsByAppointmentDateTime(LocalDateTime appointmentDateTime) {
        return appointmentRepo.existsByAppointmentDateTime(appointmentDateTime);
    }

    @Override
    public List<Appointment> findAll() {
        return this.appointmentRepo.findAll();
    }


    // Belirli bir randevuyu ID'ye göre getirir
    @Override
    public Appointment get(Long id) {
        return this.appointmentRepo.findById(id).orElseThrow(()-> new NotFoundException(Msg.NOT_FOUND));
    }

    // Belirli bir randevuyu siler
    @Override
    public boolean delete(long id) {
        Appointment appointment = this.get(id);
        this.appointmentRepo.delete(appointment);
        return true;
    }

    // Bir doktorun belirli bir tarih aralığındaki randevularını getirir
    @Override
    public List<Appointment> getByDoctorIdAndDateTimeBetween(Long doctorId, LocalDateTime startDate, LocalDateTime endDate) {
        if (doctorId == null || doctorId <= 0 || startDate == null || endDate == null) {
            throw new IllegalArgumentException("Geçersiz parametreler");
        }
        // Doktorun randevularını belirtilen tarih aralığında getir
        List<Appointment> doctorAppointments = appointmentRepo.findByDoctorIdAndAppointmentDateTimeBetween(doctorId, startDate, endDate);
        return doctorAppointments;
    }



    // Bir hayvanın belirli bir tarih aralığındaki randevularını getirir
    @Override
    public List<Appointment> getByAnimalIdAndDateTimeBetween(Long animalId, LocalDateTime startDate, LocalDateTime endDate) {
        if (animalId == null || animalId <= 0 || startDate == null || endDate == null) {
            throw new IllegalArgumentException("Geçersiz parametreler");
        }
        endDate = endDate.plusDays(1);
        return appointmentRepo.findByAnimalIdAndAppointmentDateTimeBetween(animalId, startDate, endDate);
    }

    // Belirli bir tarih aralığındaki tüm randevuları getirir
    @Override
    public List<Appointment> getAppointmentsByDateRange(String startDate, String endDate) {
        LocalDateTime startDateTime = LocalDateTime.parse(startDate);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate);
        List<Appointment> appointments = appointmentRepo.findByAppointmentDateTimeBetween(startDateTime, endDateTime);
        return appointments;
    }

}
