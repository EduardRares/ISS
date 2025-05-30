package ro.iss2025.medicineorderingsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.iss2025.medicineorderingsystem.controller.AdminController;
import ro.iss2025.medicineorderingsystem.controller.LoginController;
import ro.iss2025.medicineorderingsystem.repository.*;
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
    private HospitalEmployeeService hospitalEmployeeService1;
    private MedicineService medicineService1;
    private OrderService orderService1;
    @Override
    public void start(Stage stage) throws IOException {
        Properties props=new Properties();
        try {
            props.load(new FileReader("src/main/resources/app.properties"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }
        hospitalEmployeeService1 = new HospitalEmployeeService(new HospitalEmployeeRepo(props));
        medicineService1 = new MedicineService(new MedicineRepo(props));
        orderService1 = new OrderService(new OrderRepo(props));

        initLoginView(stage);

    }

    private void initLoginView(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LoginInterface.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
        LoginController loginController = fxmlLoader.getController();
        hospitalEmployeeService = new HospitalEmployeeService(new HospitalEmployeeHibernateRepo());
        medicineService = new MedicineService(new MedicineHibernateRepo());
        orderService = new OrderService(new OrderHibernateRepo());
        loginController.setService(hospitalEmployeeService1, medicineService1, orderService1);
    }

    public static void main(String[] args) {
        launch();
    }
}