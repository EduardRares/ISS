package ro.iss2025.medicineorderingsystem.service;

import ro.iss2025.medicineorderingsystem.domain.Medicine;
import ro.iss2025.medicineorderingsystem.repository.MedicineRepoInterface;

import java.util.List;

public class MedicineService {
    MedicineRepoInterface medicineRepo;
    public MedicineService(MedicineRepoInterface medicineRepo) {
        this.medicineRepo = medicineRepo;
    }

    public List<Medicine> getAllMedicines() {
        return medicineRepo.findAll();
    }

    public void add(Medicine medicine) {
        medicineRepo.add(medicine);
    }

    public void update(Medicine medicine) {
        medicineRepo.update(medicine);
    }

    public void delete(Medicine medicine) {
        medicineRepo.delete(medicine);
    }
}
