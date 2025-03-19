package Controllers;

import Controllers.exceptions.NonexistentEntityException;
import Models.Payments;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import Models.Reservations;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class PaymentsJpaController implements Serializable {

    public PaymentsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Payments payments) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reservations reservationId = payments.getReservationId();
            if (reservationId != null) {
                reservationId = em.getReference(reservationId.getClass(), reservationId.getId());
                payments.setReservationId(reservationId);
            }
            em.persist(payments);
            if (reservationId != null) {
                reservationId.getPaymentsCollection().add(payments);
                reservationId = em.merge(reservationId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Payments payments) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Payments persistentPayments = em.find(Payments.class, payments.getId());
            Reservations reservationIdOld = persistentPayments.getReservationId();
            Reservations reservationIdNew = payments.getReservationId();
            if (reservationIdNew != null) {
                reservationIdNew = em.getReference(reservationIdNew.getClass(), reservationIdNew.getId());
                payments.setReservationId(reservationIdNew);
            }
            payments = em.merge(payments);
            if (reservationIdOld != null && !reservationIdOld.equals(reservationIdNew)) {
                reservationIdOld.getPaymentsCollection().remove(payments);
                reservationIdOld = em.merge(reservationIdOld);
            }
            if (reservationIdNew != null && !reservationIdNew.equals(reservationIdOld)) {
                reservationIdNew.getPaymentsCollection().add(payments);
                reservationIdNew = em.merge(reservationIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = payments.getId();
                if (findPayments(id) == null) {
                    throw new NonexistentEntityException("The payments with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Payments payments;
            try {
                payments = em.getReference(Payments.class, id);
                payments.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The payments with id " + id + " no longer exists.", enfe);
            }
            Reservations reservationId = payments.getReservationId();
            if (reservationId != null) {
                reservationId.getPaymentsCollection().remove(payments);
                reservationId = em.merge(reservationId);
            }
            em.remove(payments);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Payments> findPaymentsEntities() {
        return findPaymentsEntities(true, -1, -1);
    }

    public List<Payments> findPaymentsEntities(int maxResults, int firstResult) {
        return findPaymentsEntities(false, maxResults, firstResult);
    }

    private List<Payments> findPaymentsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Payments.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Payments findPayments(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Payments.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaymentsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Payments> rt = cq.from(Payments.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
