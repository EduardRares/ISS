package ro.iss2025.medicineorderingsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.iss2025.medicineorderingsystem.controller.AdminController;
import ro.iss2025.medicineorderingsystem.controller.LoginController;
import ro.iss2025.medicineorderingsystem.repository.HospitalEmployeeRepo;
import ro.iss2025.medicineorderingsystem.repository.MedicineRepo;
import ro.iss2025.medicineorderingsystem.repository.MedicineRepoInterface;
import ro.iss2025.medicineorderingsystem.repository.OrderRepo;
import ro.iss2025.medicineorderingsystem.service.HospitalEmployeeService;
import ro.iss2025.medicineorderingsystem.service.MedicineService;
import ro.iss2025.medicineorderingsystem.service.OrderService;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main extends Application {

    private HospitalEmployeeService hospitalEmployeeService;
    private MedicineService medicineService;
    private OrderService orderService;
    @Override
    public void start(Stage stage) throws IOException {
        Properties props=new Properties();
        try {
            props.load(new FileReader("src/main/resources/app.properties"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }
        hospitalEmployeeService = new HospitalEmployeeService(new HospitalEmployeeRepo(props));
        medicineService = new MedicineService(new MedicineRepo(props));
        orderService = new OrderService(new OrderRepo(props));

        initLoginView(stage);

    }

    private void initLoginView(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LoginInterface.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
        LoginController loginController = fxmlLoader.getController();
        loginController.setService(hospitalEmployeeService, medicineService, orderService);
    }

    public static void main(String[] args) {
        launch();
    }
}