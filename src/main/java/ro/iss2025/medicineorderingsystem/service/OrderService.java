package ro.iss2025.medicineorderingsystem.service;

import ro.iss2025.medicineorderingsystem.controller.HospitalEmployeeController;
import ro.iss2025.medicineorderingsystem.domain.Medicine;
import ro.iss2025.medicineorderingsystem.domain.Order;
import ro.iss2025.medicineorderingsystem.repository.OrderRepoInterface;
import ro.iss2025.medicineorderingsystem.utils.events.Event;
import ro.iss2025.medicineorderingsystem.utils.events.OrderEvent;
import ro.iss2025.medicineorderingsystem.utils.events.OrderEventType;
import ro.iss2025.medicineorderingsystem.utils.observer.Observable;
import ro.iss2025.medicineorderingsystem.utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class OrderService implements Observable<Event> {
    OrderRepoInterface orderRepo;
    private List<Observer<Event>> observersOrderEvent = new ArrayList<Observer<Event>>();

    public OrderService(OrderRepoInterface orderRepo) {
        this.orderRepo = orderRepo;
    }

    public List<Order> findAll() {
        return orderRepo.findAll();
    }

    public void add(Order order) {
        orderRepo.add(order);
        notifyObservers(new OrderEvent(OrderEventType.PENDING, order));
    }

    public void update(Order order) {
        orderRepo.update(order);
        if(order.getStatus().equals("REJECTED")) notifyObservers(new OrderEvent(OrderEventType.REJECTED, order));
        else notifyObservers(new OrderEvent(OrderEventType.DELIVERED, order));
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

    public List<Order> findOrdersByEmployeeId(Integer employeeId) {
        return orderRepo.findOrdersByEmployeeId(employeeId);
    }

    @Override
    public void addObserver(Observer<Event> observer) {
        observersOrderEvent.add(observer);
    }

    @Override
    public void removeObserver(Observer<Event> observer) {
        observersOrderEvent.remove(observer);
    }

    @Override
    public void notifyObservers(Event t) {
        observersOrderEvent.stream().forEach(observer -> observer.update(t));
    }

}