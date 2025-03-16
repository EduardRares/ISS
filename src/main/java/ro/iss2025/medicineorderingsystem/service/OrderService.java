package ro.iss2025.medicineorderingsystem.service;

import ro.iss2025.medicineorderingsystem.repository.OrderRepoInterface;

public class OrderService {
    OrderRepoInterface orderRepo;
    public OrderService(OrderRepoInterface orderRepo) {
        this.orderRepo = orderRepo;
    }
}
