package ro.iss2025.medicineorderingsystem.service;

import ro.iss2025.medicineorderingsystem.domain.HospitalEmployee;
import ro.iss2025.medicineorderingsystem.repository.HospitalEmployeeRepoInterface;

import java.util.Optional;

public class HospitalEmployeeService {
    HospitalEmployeeRepoInterface hospitalEmployeeRepo;
    public HospitalEmployeeService(HospitalEmployeeRepoInterface hospitalEmployeeRepo) {
        this.hospitalEmployeeRepo = hospitalEmployeeRepo;
    }

    public Optional<HospitalEmployee> findEmployeeByCredentials(HospitalEmployee hospitalEmployee) {
        return hospitalEmployeeRepo.findByCredentials(hospitalEmployee);
    }


}
