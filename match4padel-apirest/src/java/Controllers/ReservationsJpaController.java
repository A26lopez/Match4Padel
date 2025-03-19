package Controllers;

import Controllers.exceptions.IllegalOrphanException;
import Controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import Models.Courts;
import Models.Users;
import Models.Matches;
import Models.Payments;
import Models.Reservations;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class ReservationsJpaController implements Serializable {

    public ReservationsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reservations reservations) {
        if (reservations.getPaymentsCollection() == null) {
            reservations.setPaymentsCollection(new ArrayList<Payments>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Courts courtId = reservations.getCourtId();
            if (courtId != null) {
                courtId = em.getReference(courtId.getClass(), courtId.getId());
                reservations.setCourtId(courtId);
            }
            Users userId = reservations.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getId());
                reservations.setUserId(userId);
            }
            Matches matches = reservations.getMatches();
            if (matches != null) {
                matches = em.getReference(matches.getClass(), matches.getId());
                reservations.setMatches(matches);
            }
            Collection<Payments> attachedPaymentsCollection = new ArrayList<Payments>();
            for (Payments paymentsCollectionPaymentsToAttach : reservations.getPaymentsCollection()) {
                paymentsCollectionPaymentsToAttach = em.getReference(paymentsCollectionPaymentsToAttach.getClass(), paymentsCollectionPaymentsToAttach.getId());
                attachedPaymentsCollection.add(paymentsCollectionPaymentsToAttach);
            }
            reservations.setPaymentsCollection(attachedPaymentsCollection);
            em.persist(reservations);
            if (courtId != null) {
                courtId.getReservationsCollection().add(reservations);
                courtId = em.merge(courtId);
            }
            if (userId != null) {
                userId.getReservationsCollection().add(reservations);
                userId = em.merge(userId);
            }
            if (matches != null) {
                Reservations oldReservationIdOfMatches = matches.getReservationId();
                if (oldReservationIdOfMatches != null) {
                    oldReservationIdOfMatches.setMatches(null);
                    oldReservationIdOfMatches = em.merge(oldReservationIdOfMatches);
                }
                matches.setReservationId(reservations);
                matches = em.merge(matches);
            }
            for (Payments paymentsCollectionPayments : reservations.getPaymentsCollection()) {
                Reservations oldReservationIdOfPaymentsCollectionPayments = paymentsCollectionPayments.getReservationId();
                paymentsCollectionPayments.setReservationId(reservations);
                paymentsCollectionPayments = em.merge(paymentsCollectionPayments);
                if (oldReservationIdOfPaymentsCollectionPayments != null) {
                    oldReservationIdOfPaymentsCollectionPayments.getPaymentsCollection().remove(paymentsCollectionPayments);
                    oldReservationIdOfPaymentsCollectionPayments = em.merge(oldReservationIdOfPaymentsCollectionPayments);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Reservations reservations) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reservations persistentReservations = em.find(Reservations.class, reservations.getId());
            Courts courtIdOld = persistentReservations.getCourtId();
            Courts courtIdNew = reservations.getCourtId();
            Users userIdOld = persistentReservations.getUserId();
            Users userIdNew = reservations.getUserId();
            Matches matchesOld = persistentReservations.getMatches();
            Matches matchesNew = reservations.getMatches();
            Collection<Payments> paymentsCollectionOld = persistentReservations.getPaymentsCollection();
            Collection<Payments> paymentsCollectionNew = reservations.getPaymentsCollection();
            List<String> illegalOrphanMessages = null;
            if (matchesOld != null && !matchesOld.equals(matchesNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Matches " + matchesOld + " since its reservationId field is not nullable.");
            }
            for (Payments paymentsCollectionOldPayments : paymentsCollectionOld) {
                if (!paymentsCollectionNew.contains(paymentsCollectionOldPayments)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Payments " + paymentsCollectionOldPayments + " since its reservationId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (courtIdNew != null) {
                courtIdNew = em.getReference(courtIdNew.getClass(), courtIdNew.getId());
                reservations.setCourtId(courtIdNew);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getId());
                reservations.setUserId(userIdNew);
            }
            if (matchesNew != null) {
                matchesNew = em.getReference(matchesNew.getClass(), matchesNew.getId());
                reservations.setMatches(matchesNew);
            }
            Collection<Payments> attachedPaymentsCollectionNew = new ArrayList<Payments>();
            for (Payments paymentsCollectionNewPaymentsToAttach : paymentsCollectionNew) {
                paymentsCollectionNewPaymentsToAttach = em.getReference(paymentsCollectionNewPaymentsToAttach.getClass(), paymentsCollectionNewPaymentsToAttach.getId());
                attachedPaymentsCollectionNew.add(paymentsCollectionNewPaymentsToAttach);
            }
            paymentsCollectionNew = attachedPaymentsCollectionNew;
            reservations.setPaymentsCollection(paymentsCollectionNew);
            reservations = em.merge(reservations);
            if (courtIdOld != null && !courtIdOld.equals(courtIdNew)) {
                courtIdOld.getReservationsCollection().remove(reservations);
                courtIdOld = em.merge(courtIdOld);
            }
            if (courtIdNew != null && !courtIdNew.equals(courtIdOld)) {
                courtIdNew.getReservationsCollection().add(reservations);
                courtIdNew = em.merge(courtIdNew);
            }
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getReservationsCollection().remove(reservations);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getReservationsCollection().add(reservations);
                userIdNew = em.merge(userIdNew);
            }
            if (matchesNew != null && !matchesNew.equals(matchesOld)) {
                Reservations oldReservationIdOfMatches = matchesNew.getReservationId();
                if (oldReservationIdOfMatches != null) {
                    oldReservationIdOfMatches.setMatches(null);
                    oldReservationIdOfMatches = em.merge(oldReservationIdOfMatches);
                }
                matchesNew.setReservationId(reservations);
                matchesNew = em.merge(matchesNew);
            }
            for (Payments paymentsCollectionNewPayments : paymentsCollectionNew) {
                if (!paymentsCollectionOld.contains(paymentsCollectionNewPayments)) {
                    Reservations oldReservationIdOfPaymentsCollectionNewPayments = paymentsCollectionNewPayments.getReservationId();
                    paymentsCollectionNewPayments.setReservationId(reservations);
                    paymentsCollectionNewPayments = em.merge(paymentsCollectionNewPayments);
                    if (oldReservationIdOfPaymentsCollectionNewPayments != null && !oldReservationIdOfPaymentsCollectionNewPayments.equals(reservations)) {
                        oldReservationIdOfPaymentsCollectionNewPayments.getPaymentsCollection().remove(paymentsCollectionNewPayments);
                        oldReservationIdOfPaymentsCollectionNewPayments = em.merge(oldReservationIdOfPaymentsCollectionNewPayments);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = reservations.getId();
                if (findReservations(id) == null) {
                    throw new NonexistentEntityException("The reservations with id " + id + " no longer exists.");
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
            Reservations reservations;
            try {
                reservations = em.getReference(Reservations.class, id);
                reservations.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reservations with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Matches matchesOrphanCheck = reservations.getMatches();
            if (matchesOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Reservations (" + reservations + ") cannot be destroyed since the Matches " + matchesOrphanCheck + " in its matches field has a non-nullable reservationId field.");
            }
            Collection<Payments> paymentsCollectionOrphanCheck = reservations.getPaymentsCollection();
            for (Payments paymentsCollectionOrphanCheckPayments : paymentsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Reservations (" + reservations + ") cannot be destroyed since the Payments " + paymentsCollectionOrphanCheckPayments + " in its paymentsCollection field has a non-nullable reservationId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Courts courtId = reservations.getCourtId();
            if (courtId != null) {
                courtId.getReservationsCollection().remove(reservations);
                courtId = em.merge(courtId);
            }
            Users userId = reservations.getUserId();
            if (userId != null) {
                userId.getReservationsCollection().remove(reservations);
                userId = em.merge(userId);
            }
            em.remove(reservations);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Reservations> findReservationsEntities() {
        return findReservationsEntities(true, -1, -1);
    }

    public List<Reservations> findReservationsEntities(int maxResults, int firstResult) {
        return findReservationsEntities(false, maxResults, firstResult);
    }

    private List<Reservations> findReservationsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reservations.class));
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

    public Reservations findReservations(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reservations.class, id);
        } finally {
            em.close();
        }
    }

    public int getReservationsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reservations> rt = cq.from(Reservations.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
