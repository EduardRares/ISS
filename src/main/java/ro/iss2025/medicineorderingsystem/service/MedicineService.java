package ro.iss2025.medicineorderingsystem.service;

import ro.iss2025.medicineorderingsystem.repository.MedicineRepoInterface;

public class MedicineService {
    MedicineRepoInterface medicineRepo;
    public MedicineService(MedicineRepoInterface medicineRepo) {
        this.medicineRepo = medicineRepo;
    }
}
