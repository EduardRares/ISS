package ro.iss2025.medicineorderingsystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ro.iss2025.medicineorderingsystem.Main;
import ro.iss2025.medicineorderingsystem.domain.HospitalEmployee;
import ro.iss2025.medicineorderingsystem.service.HospitalEmployeeService;
import ro.iss2025.medicineorderingsystem.service.MedicineService;
import ro.iss2025.medicineorderingsystem.service.OrderService;

import java.io.IOException;
import java.util.Optional;

public class LoginController {
    public TextField emailText;
    public PasswordField passwordText;
    @FXML
    private Label welcomeText;

    private HospitalEmployeeService hospitalEmployeeService;
    private MedicineService medicineService;
    private OrderService orderService;

    public void setService(HospitalEmployeeService hospitalEmployeeService, MedicineService medicineService, OrderService orderService) {
        this.hospitalEmployeeService = hospitalEmployeeService;
        this.medicineService = medicineService;
        this.orderService = orderService;
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void login(ActionEvent actionEvent) throws IOException {
        HospitalEmployee hospitalEmployee = new HospitalEmployee(emailText.getText(), passwordText.getText());
        Optional<HospitalEmployee> hospitalEmployee1 = hospitalEmployeeService.findEmployeeByCredentials(hospitalEmployee);
        if(hospitalEmployee1.isPresent()) {
            HospitalEmployee user = hospitalEmployee1.get();
            if (user.getPrivilege().toString().equals("ADMIN")) {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AdminInterface.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("Admin Interface");
                stage.setScene(scene);
                stage.show();
                AdminController adminController = fxmlLoader.getController();
                adminController.setService(user, hospitalEmployeeService, medicineService);
            }
            else if(user.getPrivilege().toString().equals("PHARMACIST")) {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PharmacistInterface.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("Pharmacist Interface");
                stage.setScene(scene);
                stage.show();
                PharmacistController pharmacistController = fxmlLoader.getController();
                pharmacistController.setService(user, orderService, hospitalEmployeeService, medicineService);
            }
            else {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StaffInterface.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("Hospital Employee Interface");
                stage.setScene(scene);
                stage.show();
                HospitalEmployeeController pharmacistController = fxmlLoader.getController();
                pharmacistController.setService(user, orderService, hospitalEmployeeService, medicineService);
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No such hospital employee");
            alert.showAndWait();
        }
    }
}