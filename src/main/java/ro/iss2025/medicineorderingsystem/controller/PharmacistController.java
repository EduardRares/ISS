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
import ro.iss2025.medicineorderingsystem.utils.events.Event;
import ro.iss2025.medicineorderingsystem.utils.events.MedicineEvent;
import ro.iss2025.medicineorderingsystem.utils.events.OrderEvent;
import ro.iss2025.medicineorderingsystem.utils.observer.Observer;

import java.nio.Buffer;
import java.util.List;

public class PharmacistController implements Observer<Event> {
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
    private TableColumn<Order, String> tableColumnDate;
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
        orderService.addObserver(this);
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
        tableColumnDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
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
        List<Order> processedOrders = orderService.findPendingOrders();
        processedOrders.sort((o1, o2) -> o1.getOrderDate().compareTo(o2.getOrderDate()));
        model.addAll(processedOrders);
        tableViewOrders.setItems(model);
    }

    @FXML
    private void handleProcessedOrders() {
        deliverButton.setVisible(false);
        rejectButton.setVisible(false);
        tableViewOrders.setVisible(true);
        tableViewOrders.setDisable(false);
        model.clear();
        List<Order> processedOrders = orderService.findProcessedOrders();
        processedOrders.sort((o1, o2) -> o1.getOrderDate().compareTo(o2.getOrderDate()));
        model.addAll(processedOrders);
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

    @Override
    public void update(Event event) {
        if(event instanceof OrderEvent) {
            if(deliverButton.isVisible()) {
                model.clear();
                List<Order> processedOrders = orderService.findPendingOrders();
                processedOrders.sort((o1, o2) -> o1.getOrderDate().compareTo(o2.getOrderDate()));
                model.addAll(processedOrders);
                tableViewOrders.setItems(model);
            }
        }
    }
}
