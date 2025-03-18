package ro.iss2025.medicineorderingsystem.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

import java.time.LocalDateTime;

public class HospitalEmployeeController implements Observer<Event> {
    @FXML
    private TextField quantityText;
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
    @FXML
    private TableColumn<Medicine, Integer> tableColumnId1;
    @FXML
    private TableColumn<Medicine, String> tableColumnName;
    @FXML
    private TableColumn<Medicine, Integer> tableColumnType;
    @FXML
    private TableColumn<Medicine, String> tableColumnDescription;
    @FXML
    private TableView<Medicine> tableViewMedicine;
    @FXML
    private Button addButton;
    private HospitalEmployeeService hospitalEmployeeService;
    private MedicineService medicineService;
    private OrderService orderService;
    private HospitalEmployee user;
    private Order order;
    private Medicine medicine;
    ObservableList<Medicine> model = FXCollections.observableArrayList();
    ObservableList<Order> modelOrder = FXCollections.observableArrayList();

    public void setService(HospitalEmployee user, OrderService orderService, HospitalEmployeeService hospitalEmployeeService, MedicineService medicineService) {
        this.user = user;
        this.orderService = orderService;
        this.hospitalEmployeeService = hospitalEmployeeService;
        this.medicineService = medicineService;
        orderService.addObserver(this);
        medicineService.addObserver(this);

    }

    @FXML
    private void initialize() {
        tableViewOrders.setVisible(false);
        tableViewMedicine.setVisible(false);
        addButton.setVisible(false);
        quantityText.setVisible(false);

        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnMedicineName.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMedicine().getName())
        );
        tableColumnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tableColumnDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        tableViewOrders.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            order = tableViewOrders.getSelectionModel().getSelectedItem();
        });

        tableColumnId1.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableViewMedicine.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            medicine = tableViewMedicine.getSelectionModel().getSelectedItem();
        });
    }

    @FXML
    private void handleMedicines() {
        addButton.setVisible(true);
        tableViewMedicine.setVisible(true);
        tableViewOrders.setVisible(false);
        quantityText.setVisible(true);
        model.clear();
        model.addAll(medicineService.getAllMedicines());
        tableViewMedicine.setItems(model);
    }

    @FXML
    private void handleOrders() {
        addButton.setVisible(false);
        tableViewOrders.setVisible(true);
        tableViewMedicine.setVisible(false);
        quantityText.setVisible(false);
        modelOrder.clear();
        modelOrder.addAll(orderService.findOrdersByEmployeeId(user.getId()));
        tableViewOrders.setItems(modelOrder);
    }

    @FXML
    private void handleAddOrder() {
        try {
            Integer quantity = Integer.parseInt(quantityText.getText());
            Order o = new Order(medicine, user, quantity, Status.PENDING, LocalDateTime.now());
            orderService.add(o);
            quantityText.clear();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void update(Event event) {
        if(event instanceof MedicineEvent) {
            model.clear();
            model.addAll(medicineService.getAllMedicines());
            tableViewMedicine.setItems(model);
        }
        else if(event instanceof OrderEvent) {
            modelOrder.clear();
            modelOrder.addAll(orderService.findOrdersByEmployeeId(user.getId()));
            tableViewOrders.setItems(modelOrder);
        }
    }

}
