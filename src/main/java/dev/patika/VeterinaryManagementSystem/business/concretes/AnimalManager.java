package dev.patika.VeterinaryManagementSystem.business.concretes;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IAnimalService;
import dev.patika.VeterinaryManagementSystem.core.config.exception.NotFoundException;
import dev.patika.VeterinaryManagementSystem.core.utiles.Msg;
import dev.patika.VeterinaryManagementSystem.entities.Animal;
import dev.patika.VeterinaryManagementSystem.entities.Customer;
import dev.patika.VeterinaryManagementSystem.repository.AnimalRepo;
import dev.patika.VeterinaryManagementSystem.repository.CustomerRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalManager implements IAnimalService {
    // AnimalRepo bağımlılığını enjekte etmek için constructor
    private final AnimalRepo animalRepo;
    private final CustomerRepo customerRepo;

    public AnimalManager(AnimalRepo animalRepo, CustomerRepo customerRepo) {
        this.animalRepo = animalRepo;
        this.customerRepo = customerRepo;
    }

    // Yeni bir hayvan kaydetmek için

    @Override
    public Animal save(Animal animal, Long customerId) {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Müşteri bulunamadı."));

        animal.setCustomer(customer);
        return animalRepo.save(animal);
    }

    // Hayvanı ID'ye göre getirmek için
    @Override
    public Animal get(Long id) {
        // AnimalRepo'daki findById kullanılır, eğer bulunamazsa NotFound exception fırlatılır
        return this.animalRepo.findById(id).orElseThrow(()-> new NotFoundException(Msg.NOT_FOUND));
    }

    // Sayfalı olarak hayvanları getirmek için
    @Override
    public Page<Animal> cursor(int page, int pageSize) {
        // Sayfalama için PageRequest kullanılır
        Pageable pageable = PageRequest.of(page,pageSize);
        return this.animalRepo.findAll(pageable); // AnimalRepo'nun findAll metodu kullanılır
    }

    // Hayvanı güncellemek için
    @Override
    public Animal update(Animal animal) {
        this.get(animal.getId());
        return this.animalRepo.save(animal); // AnimalRepo'nun save metodu kullanılır
    }


    // Hayvanı silmek için
    @Override
    public boolean delete(long id) {  // ID'ye göre hayvan getirilir
        Animal animal =this.get(id);  // AnimalRepo'nun delete metodu ile silinir
        this.animalRepo.delete(animal); // Silme işlemi başarılı olduğu için true döndürülür
        return true;
    }

    @Override
    public List<Animal> getAnimalsByName(String name) {
        // İsmi belirtilen hayvanları büyük/küçük harf duyarlı olmadan içeren hayvanları getirir.
        return this.animalRepo.findByNameIgnoreCaseContaining(name);
    }
    @Override
    public List<Animal> getAnimalsByCustomerId(Long customerId) {
        // Belirtilen müşteri ID'sine sahip hayvanları getirir.
        return this.animalRepo.findByCustomerId(customerId);
    }

    @Override
    public List<Animal> findAll() {
        return this.animalRepo.findAll();
    }

}
