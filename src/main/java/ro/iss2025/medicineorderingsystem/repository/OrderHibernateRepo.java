package ro.iss2025.medicineorderingsystem.repository;

import org.hibernate.Session;
import ro.iss2025.medicineorderingsystem.domain.HospitalEmployee;
import ro.iss2025.medicineorderingsystem.domain.Medicine;
import ro.iss2025.medicineorderingsystem.domain.Order;
import ro.iss2025.medicineorderingsystem.domain.Status;

import java.util.List;

public class OrderHibernateRepo implements OrderRepoInterface{
    @Override
    public Medicine findMedicineById(Integer medicineId) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery("from Medicine where id=:idM ", Medicine.class)
                    .setParameter("idM", medicineId)
                    .getSingleResultOrNull();
        }
    }

    @Override
    public HospitalEmployee findEmployeeById(Integer employeeId) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery("from HospitalEmployee where id=:idM ", HospitalEmployee.class)
                    .setParameter("idM", employeeId)
                    .getSingleResultOrNull();
        }
    }

    @Override
    public List<Order> findPendingOrders() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from Order where status = :st", Order.class)
                    .setParameter("st", Status.PENDING)
                    .getResultList();
        }
    }

    @Override
    public List<Order> findProcessedOrders() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from Order where status = :st", Order.class)
                    .setParameter("st", Status.PENDING)
                    .getResultList();
        }
    }

    @Override
    public List<Order> findOrdersByEmployeeId(Integer employeeId) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from Order where employee.id = :eid", Order.class)
                    .setParameter("eid", employeeId)
                    .getResultList();
        }
    }

    @Override
    public void add(Order entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(entity));
    }

    @Override
    public void update(Order entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Order existing = session.find(Order.class, entity.getId());
            if (existing != null) {
                session.merge(entity);
                session.flush();
            }
        });
    }

    @Override
    public void delete(Order entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Order existing = session.createSelectionQuery(
                            "from Order where id = :oid", Order.class)
                    .setParameter("oid", entity.getId())
                    .getSingleResultOrNull();
            if (existing != null) {
                session.remove(existing);
                session.flush();
            }
        });
    }

    @Override
    public List<Order> findAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Order", Order.class)
                    .getResultList();
        }
    }

    @Override
    public Order findById(int id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery(
                            "from Order where id = :oid", Order.class)
                    .setParameter("oid", id)
                    .getSingleResultOrNull();
        }
    }
}
