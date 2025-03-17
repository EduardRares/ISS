package ro.iss2025.medicineorderingsystem.service;

import ro.iss2025.medicineorderingsystem.domain.HospitalEmployee;
import ro.iss2025.medicineorderingsystem.repository.HospitalEmployeeRepoInterface;

import java.util.List;
import java.util.Optional;

public class HospitalEmployeeService {
    HospitalEmployeeRepoInterface hospitalEmployeeRepo;
    public HospitalEmployeeService(HospitalEmployeeRepoInterface hospitalEmployeeRepo) {
        this.hospitalEmployeeRepo = hospitalEmployeeRepo;
    }

    public Optional<HospitalEmployee> findEmployeeByCredentials(HospitalEmployee hospitalEmployee) {
        return hospitalEmployeeRepo.findByCredentials(hospitalEmployee);
    }

    public void add(HospitalEmployee hospitalEmployee) {
        hospitalEmployeeRepo.add(hospitalEmployee);
    }

    public void update(HospitalEmployee hospitalEmployee) {
        hospitalEmployeeRepo.update(hospitalEmployee);
    }

    public void delete(HospitalEmployee hospitalEmployee) {
        hospitalEmployeeRepo.delete(hospitalEmployee);
    }

    public List<HospitalEmployee> getAllEmployees() {
        return hospitalEmployeeRepo.findAll();
    }
}
