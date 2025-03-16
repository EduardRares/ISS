package ro.iss2025.medicineorderingsystem.repository;

import ro.iss2025.medicineorderingsystem.domain.Entity;

import java.util.List;

public interface Repository<ID, E extends Entity<ID>> {
    void add(E entity);
    void update(E entity);
    void delete(E entity);
    List<E> findAll();
    E findById(int id);
}
