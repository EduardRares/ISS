package ro.iss2025.medicineorderingsystem.repository;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ro.iss2025.medicineorderingsystem.domain.HospitalEmployee;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@Repository
public class HospitalEmployeeHibernateRepo implements HospitalEmployeeRepoInterface{
    @Override
    public Optional<HospitalEmployee> findByCredentials(HospitalEmployee hospitalEmployee) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.createSelectionQuery("from HospitalEmployee where email=:emailM and password=:passwordM ", HospitalEmployee.class)
                    .setParameter("emailM", hospitalEmployee.getEmail())
                    .setParameter("passwordM", hospitalEmployee.getPassword())
                    .getSingleResultOrNull());
        }
    }

    @Override
    public void add(HospitalEmployee entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(entity));
    }

    @Override
    public void update(HospitalEmployee entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            if (!Objects.isNull(session.find(HospitalEmployee.class, entity.getId()))) {
                System.out.println("In update, am gasit userul cu id-ul "+entity.getId());
                session.merge(entity);
                session.flush();
            }
        });
    }

    @Override
    public void delete(HospitalEmployee entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            HospitalEmployee employee=session.createQuery("from HospitalEmployee where id=?1",HospitalEmployee.class).
                    setParameter(1,entity.getId()).uniqueResult();
            System.out.println("In delete am gasit angajatul "+employee);
            if (employee!=null) {
                session.remove(employee);
                session.flush();
            }
        });
    }

    @Override
    public List<HospitalEmployee> findAll() {
        try( Session session=HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from HospitalEmployee ", HospitalEmployee.class).getResultList();
        }
    }

    @Override
    public HospitalEmployee findById(int id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery("from HospitalEmployee where id=:idM ", HospitalEmployee.class)
                    .setParameter("idM", id)
                    .getSingleResultOrNull();
        }
    }
}
