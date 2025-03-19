package Controllers;

import Controllers.exceptions.IllegalOrphanException;
import Controllers.exceptions.NonexistentEntityException;
import Models.Courts;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import Models.Reservations;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class CourtsJpaController implements Serializable {

    public CourtsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Courts courts) {
        if (courts.getReservationsCollection() == null) {
            courts.setReservationsCollection(new ArrayList<Reservations>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Reservations> attachedReservationsCollection = new ArrayList<Reservations>();
            for (Reservations reservationsCollectionReservationsToAttach : courts.getReservationsCollection()) {
                reservationsCollectionReservationsToAttach = em.getReference(reservationsCollectionReservationsToAttach.getClass(), reservationsCollectionReservationsToAttach.getId());
                attachedReservationsCollection.add(reservationsCollectionReservationsToAttach);
            }
            courts.setReservationsCollection(attachedReservationsCollection);
            em.persist(courts);
            for (Reservations reservationsCollectionReservations : courts.getReservationsCollection()) {
                Courts oldCourtIdOfReservationsCollectionReservations = reservationsCollectionReservations.getCourtId();
                reservationsCollectionReservations.setCourtId(courts);
                reservationsCollectionReservations = em.merge(reservationsCollectionReservations);
                if (oldCourtIdOfReservationsCollectionReservations != null) {
                    oldCourtIdOfReservationsCollectionReservations.getReservationsCollection().remove(reservationsCollectionReservations);
                    oldCourtIdOfReservationsCollectionReservations = em.merge(oldCourtIdOfReservationsCollectionReservations);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Courts courts) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Courts persistentCourts = em.find(Courts.class, courts.getId());
            Collection<Reservations> reservationsCollectionOld = persistentCourts.getReservationsCollection();
            Collection<Reservations> reservationsCollectionNew = courts.getReservationsCollection();
            List<String> illegalOrphanMessages = null;
            for (Reservations reservationsCollectionOldReservations : reservationsCollectionOld) {
                if (!reservationsCollectionNew.contains(reservationsCollectionOldReservations)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reservations " + reservationsCollectionOldReservations + " since its courtId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Reservations> attachedReservationsCollectionNew = new ArrayList<Reservations>();
            for (Reservations reservationsCollectionNewReservationsToAttach : reservationsCollectionNew) {
                reservationsCollectionNewReservationsToAttach = em.getReference(reservationsCollectionNewReservationsToAttach.getClass(), reservationsCollectionNewReservationsToAttach.getId());
                attachedReservationsCollectionNew.add(reservationsCollectionNewReservationsToAttach);
            }
            reservationsCollectionNew = attachedReservationsCollectionNew;
            courts.setReservationsCollection(reservationsCollectionNew);
            courts = em.merge(courts);
            for (Reservations reservationsCollectionNewReservations : reservationsCollectionNew) {
                if (!reservationsCollectionOld.contains(reservationsCollectionNewReservations)) {
                    Courts oldCourtIdOfReservationsCollectionNewReservations = reservationsCollectionNewReservations.getCourtId();
                    reservationsCollectionNewReservations.setCourtId(courts);
                    reservationsCollectionNewReservations = em.merge(reservationsCollectionNewReservations);
                    if (oldCourtIdOfReservationsCollectionNewReservations != null && !oldCourtIdOfReservationsCollectionNewReservations.equals(courts)) {
                        oldCourtIdOfReservationsCollectionNewReservations.getReservationsCollection().remove(reservationsCollectionNewReservations);
                        oldCourtIdOfReservationsCollectionNewReservations = em.merge(oldCourtIdOfReservationsCollectionNewReservations);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = courts.getId();
                if (findCourts(id) == null) {
                    throw new NonexistentEntityException("The courts with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Courts courts;
            try {
                courts = em.getReference(Courts.class, id);
                courts.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The courts with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Reservations> reservationsCollectionOrphanCheck = courts.getReservationsCollection();
            for (Reservations reservationsCollectionOrphanCheckReservations : reservationsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Courts (" + courts + ") cannot be destroyed since the Reservations " + reservationsCollectionOrphanCheckReservations + " in its reservationsCollection field has a non-nullable courtId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(courts);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Courts> findCourtsEntities() {
        return findCourtsEntities(true, -1, -1);
    }

    public List<Courts> findCourtsEntities(int maxResults, int firstResult) {
        return findCourtsEntities(false, maxResults, firstResult);
    }

    private List<Courts> findCourtsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Courts.class));
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

    public Courts findCourts(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Courts.class, id);
        } finally {
            em.close();
        }
    }

    public int getCourtsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Courts> rt = cq.from(Courts.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
