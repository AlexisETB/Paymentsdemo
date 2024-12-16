package ec.edu.uce.paymentsdemo.jpa.services;
import ec.edu.uce.paymentsdemo.jpa.Entities.PaymentMethod;
import ec.edu.uce.paymentsdemo.jpa.Manager.EntityManagerProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;


@ApplicationScoped
public class PaymentMethodService implements CrudService<PaymentMethod> {

    public PaymentMethodService() {}

    @Inject
    private EntityManagerProvider emp;

    @Transactional
    public void createPaymentMethod(PaymentMethod paymentMethod) {
        EntityManager em = emp.getEntityManager();
        em.persist(paymentMethod);
    }

    @Override
    @Transactional
    public List<PaymentMethod> list() {
        EntityManager em = emp.getEntityManager();
        try {
            return em.createQuery("FROM PaymentMethod", PaymentMethod.class).getResultList();
        }catch (Exception e) { throw new ServiceJdbcException(e.getMessage(), e.getCause());}
    }

    @Override
    @Transactional
    public PaymentMethod findById(Long id) {
        EntityManager em = emp.getEntityManager();
        try {
            return em.find(PaymentMethod.class, id);
        }catch (Exception e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Transactional
    public PaymentMethod findByIdS(String id) {
        EntityManager em = emp.getEntityManager();
        try {
            return em.find(PaymentMethod.class, id);
        }catch (Exception e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    @Transactional
    public void save(PaymentMethod paymentMethod) {
        EntityManager em = emp.getEntityManager();
        try {
            if (paymentMethod.getId() != null) {
                em.merge(paymentMethod);
            } else {
                em.persist(paymentMethod);
            }
        }catch (Exception e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        EntityManager em = emp.getEntityManager();
        try {
            PaymentMethod paymentMethod = em.find(PaymentMethod.class, id);
            if (paymentMethod != null) {
                em.remove(paymentMethod);
            }
        }catch  (Exception e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    @Transactional
    public void update(PaymentMethod paymentMethod) {
        EntityManager em = emp.getEntityManager();
        try {
            if (paymentMethod.getId() == null) {
                throw new IllegalArgumentException("El ID debe ser válido para actualizar");
            }
            em.merge(paymentMethod);
        }catch (Exception e) {
            throw new ServiceJdbcException("Error updating user: " + paymentMethod.getId(), e);
        }
    }
}
