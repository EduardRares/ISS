package ro.iss2025.medicineorderingsystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ro.iss2025.medicineorderingsystem.domain.HospitalEmployee;
import ro.iss2025.medicineorderingsystem.domain.Medicine;
import ro.iss2025.medicineorderingsystem.service.HospitalEmployeeService;
import ro.iss2025.medicineorderingsystem.service.MedicineService;
import ro.iss2025.medicineorderingsystem.utils.events.HospitalEventType;
import ro.iss2025.medicineorderingsystem.utils.events.MedicineEvent;
import ro.iss2025.medicineorderingsystem.utils.events.MedicineEventType;
import ro.iss2025.medicineorderingsystem.utils.observer.Observer;

import java.util.Optional;

public class AdminController {
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button addButtonEmployee;
    @FXML
    private Button updateButtonEmployee;
    @FXML
    private Button deleteButtonEmployee;

    @FXML
    private Label labelToFocus;
    @FXML
    private TableView<HospitalEmployee> tableViewEmployee;
    @FXML
    private TableColumn<HospitalEmployee, Integer> tableColumnId1;
    @FXML
    private TableColumn<HospitalEmployee, String> tableColumnEmail;
    @FXML
    private TableColumn<HospitalEmployee, String> tableColumnPrivilege;
    @FXML
    private Button usersButton;
    @FXML
    private Button medicinesButton;
    @FXML
    private TableColumn<Medicine, Integer> tableColumnId;
    @FXML
    private TableColumn<Medicine, String> tableColumnName;
    @FXML
    private TableColumn<Medicine, String> tableColumnType;
    @FXML
    private TableColumn<Medicine, String> tableColumnDescription;
    @FXML
    private TableView<Medicine> tableViewMedicine;
    private HospitalEmployeeService hospitalEmployeeService;
    private MedicineService medicineService;
    private Medicine medicine;
    private HospitalEmployee hospitalEmployee;
    private HospitalEmployee user;
    ObservableList<Medicine> model = FXCollections.observableArrayList();
    ObservableList<HospitalEmployee> modelEmployee = FXCollections.observableArrayList();

    public void setService(HospitalEmployee user, HospitalEmployeeService hospitalEmployeeService, MedicineService medicineService) {
        this.user = user;
        this.hospitalEmployeeService = hospitalEmployeeService;
        this.medicineService = medicineService;
    }

    @FXML
    private void initialize() {
        labelToFocus.setFocusTraversable(true);
        tableViewMedicine.setVisible(false);
        tableViewEmployee.setVisible(false);
        addButton.setVisible(false);
        updateButton.setVisible(false);
        deleteButton.setVisible(false);
        addButtonEmployee.setVisible(false);
        updateButtonEmployee.setVisible(false);
        deleteButtonEmployee.setVisible(false);

        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableViewMedicine.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            medicine = tableViewMedicine.getSelectionModel().getSelectedItem();
        });

        tableColumnId1.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableColumnPrivilege.setCellValueFactory(new PropertyValueFactory<>("privilege"));
        tableViewEmployee.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            hospitalEmployee = tableViewEmployee.getSelectionModel().getSelectedItem();
        });

    }

    @FXML
    public void handleMedicines() {
        tableViewMedicine.setVisible(true);
        tableViewEmployee.setVisible(false);
        addButton.setVisible(true);
        updateButton.setVisible(true);
        deleteButton.setVisible(true);
        addButtonEmployee.setVisible(false);
        updateButtonEmployee.setVisible(false);
        deleteButtonEmployee.setVisible(false);
        model.clear();
        model.addAll(medicineService.getAllMedicines());
        tableViewMedicine.setItems(model);
    }

    public void handleAdd(ActionEvent actionEvent) {
        Optional<Medicine> m = TakeInfoAlert.takeInfoMedicine(new Medicine(), MedicineEventType.ADD);
        m.ifPresent(value -> medicineService.add(value));
        handleMedicines();
    }

    public void handleUpdate(ActionEvent actionEvent) {
        Optional<Medicine> m = TakeInfoAlert.takeInfoMedicine(medicine, MedicineEventType.UPDATE);
        m.ifPresent(value -> medicineService.update(value));
        handleMedicines();
    }

    public void handleDelete(ActionEvent actionEvent) {
        medicineService.delete(medicine);
        handleMedicines();
    }
    @FXML
    private void handleUsers() {
        tableViewEmployee.setVisible(true);
        tableViewMedicine.setVisible(false);
        addButtonEmployee.setVisible(true);
        updateButtonEmployee.setVisible(true);
        deleteButtonEmployee.setVisible(true);
        addButton.setVisible(false);
        updateButton.setVisible(false);
        deleteButton.setVisible(false);
        modelEmployee.clear();
        modelEmployee.addAll(hospitalEmployeeService.getAllEmployees());
        tableViewEmployee.setItems(modelEmployee);
    }

    public void handleAddEmployee(ActionEvent actionEvent) {
        Optional<HospitalEmployee> e = TakeInfoAlert.takeInfoEmployee(new HospitalEmployee(), HospitalEventType.ADD);
        e.ifPresent(value -> hospitalEmployeeService.add(value));
        handleUsers();
    }

    public void handleUpdateEmployee(ActionEvent actionEvent) {
        Optional<HospitalEmployee> e = TakeInfoAlert.takeInfoEmployee(hospitalEmployee, HospitalEventType.UPDATE);
        e.ifPresent(value -> hospitalEmployeeService.update(value));
        handleUsers();
    }

    public void handleDeleteEmployee(ActionEvent actionEvent) {
        hospitalEmployeeService.delete(hospitalEmployee);
        handleUsers();
    }
}
