package ro.iss2025.medicineorderingsystem.service;

import ro.iss2025.medicineorderingsystem.domain.Medicine;
import ro.iss2025.medicineorderingsystem.domain.Order;
import ro.iss2025.medicineorderingsystem.repository.OrderRepoInterface;

import java.util.List;

public class OrderService {
    OrderRepoInterface orderRepo;
    public OrderService(OrderRepoInterface orderRepo) {
        this.orderRepo = orderRepo;
    }

    public List<Order> findAll() {
        return orderRepo.findAll();
    }

    public void add(Order order) {
        orderRepo.add(order);
    }

    public void update(Order order) {
        orderRepo.update(order);
    }

    public void delete(Order order) {
        orderRepo.delete(order);
    }

    public List<Order> findPendingOrders() {
        return orderRepo.findPendingOrders();
    }

    public List<Order> findProcessedOrders() {
        return orderRepo.findProcessedOrders();
    }
}