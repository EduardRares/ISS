package ro.iss2025.medicineorderingsystem.repository;

import org.hibernate.Session;
import ro.iss2025.medicineorderingsystem.domain.HospitalEmployee;
import ro.iss2025.medicineorderingsystem.domain.Medicine;

import java.util.List;
import java.util.Objects;

public class MedicineHibernateRepo implements MedicineRepoInterface{
    @Override
    public void add(Medicine entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(entity));
    }

    @Override
    public void update(Medicine entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            if (!Objects.isNull(session.find(Medicine.class, entity.getId()))) {
                System.out.println("In update, am gasit medicamentul cu id-ul "+entity.getId());
                session.merge(entity);
                session.flush();
            }
        });
    }

    @Override
    public void delete(Medicine entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Medicine employee=session.createQuery("from Medicine where id=?1",Medicine.class).
                    setParameter(1,entity.getId()).uniqueResult();
            System.out.println("In delete am gasit medicamentul "+employee);
            if (employee!=null) {
                session.remove(employee);
                session.flush();
            }
        });
    }

    @Override
    public List<Medicine> findAll() {
        try( Session session=HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Medicine ", Medicine.class).getResultList();
        }
    }

    @Override
    public Medicine findById(int id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery("from Medicine where id=:idM ", Medicine.class)
                    .setParameter("idM", id)
                    .getSingleResultOrNull();
        }
    }
}
