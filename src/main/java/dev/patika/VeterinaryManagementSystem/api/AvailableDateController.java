package dev.patika.VeterinaryManagementSystem.api;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IAvailableDateService;
import dev.patika.VeterinaryManagementSystem.business.abstracts.IDoctorService;
import dev.patika.VeterinaryManagementSystem.core.config.modelMapper.IModelMapperService;
import dev.patika.VeterinaryManagementSystem.core.result.Result;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.core.utiles.ResultHelper;
import dev.patika.VeterinaryManagementSystem.dto.Request.AvailableDateSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.AvailableDateResponse;
import dev.patika.VeterinaryManagementSystem.entities.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/available-dates")
public class AvailableDateController {

    // AvailableDateService, DoctorService ve ModelMapper için interface referansları
    private  final IAvailableDateService availableDateService;
    private final IDoctorService doctorService;
    private final IModelMapperService modelMapper;

    // Constructor ile bağımlılıkların enjekte edilmesi
    public AvailableDateController(IAvailableDateService availableDateService,
                                   IDoctorService doctorService,
                                   IModelMapperService modelMapper) {
        this.availableDateService = availableDateService;
        this.doctorService = doctorService;
        this.modelMapper = modelMapper;
    }

    // Belirli bir ID'ye sahip AvailableDate'i getiren endpoint
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDateResponse> get (@PathVariable("id") Long id) {
        AvailableDate availableDate = this.availableDateService.get(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(availableDate,AvailableDateResponse.class));
    }

    // Tüm AvailableDate'leri getiren endpoint
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AvailableDateResponse>> findAll() {
        List<AvailableDate> availableDates = this.availableDateService.findAll();
        List<AvailableDateResponse> availableDateResponses = new ArrayList<>();
        for (AvailableDate availableDate : availableDates) {
            AvailableDateResponse response = new AvailableDateResponse();

            response.setAvailableDate(availableDate.getAvailableDate());
            response.setDoctorId(availableDate.getDoctor().getId());
            availableDateResponses.add(response);
        }

        return ResultHelper.success(availableDateResponses);
    }

    // Yeni bir AvailableDate oluşturan endpoint
    @PostMapping("/createNew")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AvailableDateResponse> save(@Valid @RequestBody AvailableDateSaveRequest availableDateSaveRequest) {
        Doctor doctor = this.doctorService.get(availableDateSaveRequest.getDoctorId());
        AvailableDate availableDate = this.modelMapper.forRequest().map(availableDateSaveRequest, AvailableDate.class);

        // Yeni bir AvailableDate nesnesi oluşturma
        AvailableDate newAvailableDate = new AvailableDate();
        newAvailableDate.setAvailableDate(availableDate.getAvailableDate()); // Tarih bilgisini aktarılır
        newAvailableDate.setDoctor(doctor); // Doktor bilgisini aktarır

        AvailableDate savedAvailableDate = this.availableDateService.save(newAvailableDate, availableDateSaveRequest.getDoctorId());
        return ResultHelper.created(this.modelMapper.forResponse().map(savedAvailableDate, AvailableDateResponse.class));
    }

    // Belirli bir AvailableDate'i silen endpoint
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        this.availableDateService.delete(id);
        return ResultHelper.Ok();
    }

}
