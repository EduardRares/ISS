package ro.iss2025.medicineorderingsystem.repository;

import ro.iss2025.medicineorderingsystem.domain.HospitalEmployee;

import java.util.Optional;

public interface HospitalEmployeeRepoInterface extends Repository<Integer, HospitalEmployee>{
    public Optional<HospitalEmployee> findByCredentials(HospitalEmployee hospitalEmployee);
}
