package ro.iss2025.medicineorderingsystem.controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ro.iss2025.medicineorderingsystem.domain.HospitalEmployee;
import ro.iss2025.medicineorderingsystem.service.HospitalEmployeeService;
import ro.iss2025.medicineorderingsystem.service.MedicineService;

public class AdminController {
    public Label labelToFocus;
    public Button usersButton;
    public Button medicinesButton;
    private HospitalEmployeeService hospitalEmployeeService;
    private MedicineService medicineService;

    public void setService(HospitalEmployee user, HospitalEmployeeService hospitalEmployeeService, MedicineService medicineService) {
        this.hospitalEmployeeService = hospitalEmployeeService;
        this.medicineService = medicineService;
        init();
    }

    private void init() {
        labelToFocus.setFocusTraversable(true);
    }
}
