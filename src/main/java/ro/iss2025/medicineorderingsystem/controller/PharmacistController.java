package ro.iss2025.medicineorderingsystem.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ro.iss2025.medicineorderingsystem.domain.HospitalEmployee;
import ro.iss2025.medicineorderingsystem.domain.Medicine;
import ro.iss2025.medicineorderingsystem.domain.Order;
import ro.iss2025.medicineorderingsystem.domain.Status;
import ro.iss2025.medicineorderingsystem.service.HospitalEmployeeService;
import ro.iss2025.medicineorderingsystem.service.MedicineService;
import ro.iss2025.medicineorderingsystem.service.OrderService;

import java.nio.Buffer;

public class PharmacistController {
    @FXML
    private Button deliverButton;
    @FXML
    private Button rejectButton;
    @FXML
    private TableColumn<Order, Integer> tableColumnId;
    @FXML
    private TableColumn<Order, String> tableColumnMedicineName;
    @FXML
    private TableColumn<Order, Integer> tableColumnQuantity;
    @FXML
    private TableView<Order> tableViewOrders;
    private HospitalEmployee user;
    private MedicineService medicineService;
    private HospitalEmployeeService hospitalEmployeeService;
    private OrderService orderService;
    private Order order;
    ObservableList<Order> model = FXCollections.observableArrayList();

    public void setService(HospitalEmployee user, OrderService orderService, HospitalEmployeeService hospitalEmployeeService, MedicineService medicineService) {
        this.user = user;
        this.orderService = orderService;
        this.hospitalEmployeeService = hospitalEmployeeService;
        this.medicineService = medicineService;
    }

    @FXML
    private void initialize() {
        deliverButton.setVisible(false);
        rejectButton.setVisible(false);
        tableViewOrders.setVisible(false);

        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnMedicineName.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMedicine().getName())
        );
        tableColumnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tableViewOrders.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            order = tableViewOrders.getSelectionModel().getSelectedItem();
        });
    }

    @FXML
    private void handlePendingOrders() {
        deliverButton.setVisible(true);
        rejectButton.setVisible(true);
        tableViewOrders.setVisible(true);
        tableViewOrders.setEditable(true);
        model.clear();
        model.addAll(orderService.findPendingOrders());
        tableViewOrders.setItems(model);
    }

    @FXML
    private void handleProcessedOrders() {
        deliverButton.setVisible(false);
        rejectButton.setVisible(false);
        tableViewOrders.setVisible(true);
        tableViewOrders.setDisable(false);
        model.clear();
        model.addAll(orderService.findProcessedOrders());
        tableViewOrders.setItems(model);
    }

    public void handleDeliverOrder(ActionEvent actionEvent) {
        order.setStatus(Status.DELIVERED);
        orderService.update(order);
        handlePendingOrders();
    }

    public void handleRejectOrder(ActionEvent actionEvent) {
        order.setStatus(Status.REJECTED);
        orderService.update(order);
        handlePendingOrders();
    }
}
