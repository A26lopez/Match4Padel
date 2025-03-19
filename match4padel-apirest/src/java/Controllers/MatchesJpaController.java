package Controllers;

import Controllers.exceptions.IllegalOrphanException;
import Controllers.exceptions.NonexistentEntityException;
import Models.Matches;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import Models.Reservations;
import Models.Tournaments;
import Models.Users;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class MatchesJpaController implements Serializable {

    public MatchesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Matches matches) throws IllegalOrphanException {
        List<String> illegalOrphanMessages = null;
        Reservations reservationIdOrphanCheck = matches.getReservationId();
        if (reservationIdOrphanCheck != null) {
            Matches oldMatchesOfReservationId = reservationIdOrphanCheck.getMatches();
            if (oldMatchesOfReservationId != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Reservations " + reservationIdOrphanCheck + " already has an item of type Matches whose reservationId column cannot be null. Please make another selection for the reservationId field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reservations reservationId = matches.getReservationId();
            if (reservationId != null) {
                reservationId = em.getReference(reservationId.getClass(), reservationId.getId());
                matches.setReservationId(reservationId);
            }
            Tournaments tournamentId = matches.getTournamentId();
            if (tournamentId != null) {
                tournamentId = em.getReference(tournamentId.getClass(), tournamentId.getId());
                matches.setTournamentId(tournamentId);
            }
            Users ownerId = matches.getOwnerId();
            if (ownerId != null) {
                ownerId = em.getReference(ownerId.getClass(), ownerId.getId());
                matches.setOwnerId(ownerId);
            }
            Users player1Id = matches.getPlayer1Id();
            if (player1Id != null) {
                player1Id = em.getReference(player1Id.getClass(), player1Id.getId());
                matches.setPlayer1Id(player1Id);
            }
            Users player2Id = matches.getPlayer2Id();
            if (player2Id != null) {
                player2Id = em.getReference(player2Id.getClass(), player2Id.getId());
                matches.setPlayer2Id(player2Id);
            }
            Users player3Id = matches.getPlayer3Id();
            if (player3Id != null) {
                player3Id = em.getReference(player3Id.getClass(), player3Id.getId());
                matches.setPlayer3Id(player3Id);
            }
            em.persist(matches);
            if (reservationId != null) {
                reservationId.setMatches(matches);
                reservationId = em.merge(reservationId);
            }
            if (tournamentId != null) {
                tournamentId.getMatchesCollection().add(matches);
                tournamentId = em.merge(tournamentId);
            }
            if (ownerId != null) {
                ownerId.getMatchesCollection().add(matches);
                ownerId = em.merge(ownerId);
            }
            if (player1Id != null) {
                player1Id.getMatchesCollection().add(matches);
                player1Id = em.merge(player1Id);
            }
            if (player2Id != null) {
                player2Id.getMatchesCollection().add(matches);
                player2Id = em.merge(player2Id);
            }
            if (player3Id != null) {
                player3Id.getMatchesCollection().add(matches);
                player3Id = em.merge(player3Id);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Matches matches) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Matches persistentMatches = em.find(Matches.class, matches.getId());
            Reservations reservationIdOld = persistentMatches.getReservationId();
            Reservations reservationIdNew = matches.getReservationId();
            Tournaments tournamentIdOld = persistentMatches.getTournamentId();
            Tournaments tournamentIdNew = matches.getTournamentId();
            Users ownerIdOld = persistentMatches.getOwnerId();
            Users ownerIdNew = matches.getOwnerId();
            Users player1IdOld = persistentMatches.getPlayer1Id();
            Users player1IdNew = matches.getPlayer1Id();
            Users player2IdOld = persistentMatches.getPlayer2Id();
            Users player2IdNew = matches.getPlayer2Id();
            Users player3IdOld = persistentMatches.getPlayer3Id();
            Users player3IdNew = matches.getPlayer3Id();
            List<String> illegalOrphanMessages = null;
            if (reservationIdNew != null && !reservationIdNew.equals(reservationIdOld)) {
                Matches oldMatchesOfReservationId = reservationIdNew.getMatches();
                if (oldMatchesOfReservationId != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Reservations " + reservationIdNew + " already has an item of type Matches whose reservationId column cannot be null. Please make another selection for the reservationId field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (reservationIdNew != null) {
                reservationIdNew = em.getReference(reservationIdNew.getClass(), reservationIdNew.getId());
                matches.setReservationId(reservationIdNew);
            }
            if (tournamentIdNew != null) {
                tournamentIdNew = em.getReference(tournamentIdNew.getClass(), tournamentIdNew.getId());
                matches.setTournamentId(tournamentIdNew);
            }
            if (ownerIdNew != null) {
                ownerIdNew = em.getReference(ownerIdNew.getClass(), ownerIdNew.getId());
                matches.setOwnerId(ownerIdNew);
            }
            if (player1IdNew != null) {
                player1IdNew = em.getReference(player1IdNew.getClass(), player1IdNew.getId());
                matches.setPlayer1Id(player1IdNew);
            }
            if (player2IdNew != null) {
                player2IdNew = em.getReference(player2IdNew.getClass(), player2IdNew.getId());
                matches.setPlayer2Id(player2IdNew);
            }
            if (player3IdNew != null) {
                player3IdNew = em.getReference(player3IdNew.getClass(), player3IdNew.getId());
                matches.setPlayer3Id(player3IdNew);
            }
            matches = em.merge(matches);
            if (reservationIdOld != null && !reservationIdOld.equals(reservationIdNew)) {
                reservationIdOld.setMatches(null);
                reservationIdOld = em.merge(reservationIdOld);
            }
            if (reservationIdNew != null && !reservationIdNew.equals(reservationIdOld)) {
                reservationIdNew.setMatches(matches);
                reservationIdNew = em.merge(reservationIdNew);
            }
            if (tournamentIdOld != null && !tournamentIdOld.equals(tournamentIdNew)) {
                tournamentIdOld.getMatchesCollection().remove(matches);
                tournamentIdOld = em.merge(tournamentIdOld);
            }
            if (tournamentIdNew != null && !tournamentIdNew.equals(tournamentIdOld)) {
                tournamentIdNew.getMatchesCollection().add(matches);
                tournamentIdNew = em.merge(tournamentIdNew);
            }
            if (ownerIdOld != null && !ownerIdOld.equals(ownerIdNew)) {
                ownerIdOld.getMatchesCollection().remove(matches);
                ownerIdOld = em.merge(ownerIdOld);
            }
            if (ownerIdNew != null && !ownerIdNew.equals(ownerIdOld)) {
                ownerIdNew.getMatchesCollection().add(matches);
                ownerIdNew = em.merge(ownerIdNew);
            }
            if (player1IdOld != null && !player1IdOld.equals(player1IdNew)) {
                player1IdOld.getMatchesCollection().remove(matches);
                player1IdOld = em.merge(player1IdOld);
            }
            if (player1IdNew != null && !player1IdNew.equals(player1IdOld)) {
                player1IdNew.getMatchesCollection().add(matches);
                player1IdNew = em.merge(player1IdNew);
            }
            if (player2IdOld != null && !player2IdOld.equals(player2IdNew)) {
                player2IdOld.getMatchesCollection().remove(matches);
                player2IdOld = em.merge(player2IdOld);
            }
            if (player2IdNew != null && !player2IdNew.equals(player2IdOld)) {
                player2IdNew.getMatchesCollection().add(matches);
                player2IdNew = em.merge(player2IdNew);
            }
            if (player3IdOld != null && !player3IdOld.equals(player3IdNew)) {
                player3IdOld.getMatchesCollection().remove(matches);
                player3IdOld = em.merge(player3IdOld);
            }
            if (player3IdNew != null && !player3IdNew.equals(player3IdOld)) {
                player3IdNew.getMatchesCollection().add(matches);
                player3IdNew = em.merge(player3IdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = matches.getId();
                if (findMatches(id) == null) {
                    throw new NonexistentEntityException("The matches with id " + id + " no longer exists.");
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
            Matches matches;
            try {
                matches = em.getReference(Matches.class, id);
                matches.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The matches with id " + id + " no longer exists.", enfe);
            }
            Reservations reservationId = matches.getReservationId();
            if (reservationId != null) {
                reservationId.setMatches(null);
                reservationId = em.merge(reservationId);
            }
            Tournaments tournamentId = matches.getTournamentId();
            if (tournamentId != null) {
                tournamentId.getMatchesCollection().remove(matches);
                tournamentId = em.merge(tournamentId);
            }
            Users ownerId = matches.getOwnerId();
            if (ownerId != null) {
                ownerId.getMatchesCollection().remove(matches);
                ownerId = em.merge(ownerId);
            }
            Users player1Id = matches.getPlayer1Id();
            if (player1Id != null) {
                player1Id.getMatchesCollection().remove(matches);
                player1Id = em.merge(player1Id);
            }
            Users player2Id = matches.getPlayer2Id();
            if (player2Id != null) {
                player2Id.getMatchesCollection().remove(matches);
                player2Id = em.merge(player2Id);
            }
            Users player3Id = matches.getPlayer3Id();
            if (player3Id != null) {
                player3Id.getMatchesCollection().remove(matches);
                player3Id = em.merge(player3Id);
            }
            em.remove(matches);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Matches> findMatchesEntities() {
        return findMatchesEntities(true, -1, -1);
    }

    public List<Matches> findMatchesEntities(int maxResults, int firstResult) {
        return findMatchesEntities(false, maxResults, firstResult);
    }

    private List<Matches> findMatchesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Matches.class));
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

    public Matches findMatches(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Matches.class, id);
        } finally {
            em.close();
        }
    }

    public int getMatchesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Matches> rt = cq.from(Matches.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
