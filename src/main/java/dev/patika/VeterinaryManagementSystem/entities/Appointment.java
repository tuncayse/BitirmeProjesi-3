package dev.patika.VeterinaryManagementSystem.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long id;

    @Column(name = "appointment_date")
    private LocalDateTime appointmentDateTime;


    @ManyToOne
    @JoinColumn(name = "appointment_doctor_id",referencedColumnName = "doctor_id")
    private Doctor doctor;

    @ManyToOne()
    @JoinColumn(name ="appointment_animal_id",referencedColumnName = "animal_id")
    private Animal animal;


    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", appointmentDateTime=" + appointmentDateTime  +
                '}';
    }
}
