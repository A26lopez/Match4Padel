package Controllers;

import Controllers.exceptions.NonexistentEntityException;
import Models.Teams;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import Models.Users;
import Models.Tournaments;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class TeamsJpaController implements Serializable {

    public TeamsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Teams teams) {
        if (teams.getTournamentsCollection() == null) {
            teams.setTournamentsCollection(new ArrayList<Tournaments>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Users player1Id = teams.getPlayer1Id();
            if (player1Id != null) {
                player1Id = em.getReference(player1Id.getClass(), player1Id.getId());
                teams.setPlayer1Id(player1Id);
            }
            Users player2Id = teams.getPlayer2Id();
            if (player2Id != null) {
                player2Id = em.getReference(player2Id.getClass(), player2Id.getId());
                teams.setPlayer2Id(player2Id);
            }
            Collection<Tournaments> attachedTournamentsCollection = new ArrayList<Tournaments>();
            for (Tournaments tournamentsCollectionTournamentsToAttach : teams.getTournamentsCollection()) {
                tournamentsCollectionTournamentsToAttach = em.getReference(tournamentsCollectionTournamentsToAttach.getClass(), tournamentsCollectionTournamentsToAttach.getId());
                attachedTournamentsCollection.add(tournamentsCollectionTournamentsToAttach);
            }
            teams.setTournamentsCollection(attachedTournamentsCollection);
            em.persist(teams);
            if (player1Id != null) {
                player1Id.getTeamsCollection().add(teams);
                player1Id = em.merge(player1Id);
            }
            if (player2Id != null) {
                player2Id.getTeamsCollection().add(teams);
                player2Id = em.merge(player2Id);
            }
            for (Tournaments tournamentsCollectionTournaments : teams.getTournamentsCollection()) {
                tournamentsCollectionTournaments.getTeamsCollection().add(teams);
                tournamentsCollectionTournaments = em.merge(tournamentsCollectionTournaments);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Teams teams) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Teams persistentTeams = em.find(Teams.class, teams.getId());
            Users player1IdOld = persistentTeams.getPlayer1Id();
            Users player1IdNew = teams.getPlayer1Id();
            Users player2IdOld = persistentTeams.getPlayer2Id();
            Users player2IdNew = teams.getPlayer2Id();
            Collection<Tournaments> tournamentsCollectionOld = persistentTeams.getTournamentsCollection();
            Collection<Tournaments> tournamentsCollectionNew = teams.getTournamentsCollection();
            if (player1IdNew != null) {
                player1IdNew = em.getReference(player1IdNew.getClass(), player1IdNew.getId());
                teams.setPlayer1Id(player1IdNew);
            }
            if (player2IdNew != null) {
                player2IdNew = em.getReference(player2IdNew.getClass(), player2IdNew.getId());
                teams.setPlayer2Id(player2IdNew);
            }
            Collection<Tournaments> attachedTournamentsCollectionNew = new ArrayList<Tournaments>();
            for (Tournaments tournamentsCollectionNewTournamentsToAttach : tournamentsCollectionNew) {
                tournamentsCollectionNewTournamentsToAttach = em.getReference(tournamentsCollectionNewTournamentsToAttach.getClass(), tournamentsCollectionNewTournamentsToAttach.getId());
                attachedTournamentsCollectionNew.add(tournamentsCollectionNewTournamentsToAttach);
            }
            tournamentsCollectionNew = attachedTournamentsCollectionNew;
            teams.setTournamentsCollection(tournamentsCollectionNew);
            teams = em.merge(teams);
            if (player1IdOld != null && !player1IdOld.equals(player1IdNew)) {
                player1IdOld.getTeamsCollection().remove(teams);
                player1IdOld = em.merge(player1IdOld);
            }
            if (player1IdNew != null && !player1IdNew.equals(player1IdOld)) {
                player1IdNew.getTeamsCollection().add(teams);
                player1IdNew = em.merge(player1IdNew);
            }
            if (player2IdOld != null && !player2IdOld.equals(player2IdNew)) {
                player2IdOld.getTeamsCollection().remove(teams);
                player2IdOld = em.merge(player2IdOld);
            }
            if (player2IdNew != null && !player2IdNew.equals(player2IdOld)) {
                player2IdNew.getTeamsCollection().add(teams);
                player2IdNew = em.merge(player2IdNew);
            }
            for (Tournaments tournamentsCollectionOldTournaments : tournamentsCollectionOld) {
                if (!tournamentsCollectionNew.contains(tournamentsCollectionOldTournaments)) {
                    tournamentsCollectionOldTournaments.getTeamsCollection().remove(teams);
                    tournamentsCollectionOldTournaments = em.merge(tournamentsCollectionOldTournaments);
                }
            }
            for (Tournaments tournamentsCollectionNewTournaments : tournamentsCollectionNew) {
                if (!tournamentsCollectionOld.contains(tournamentsCollectionNewTournaments)) {
                    tournamentsCollectionNewTournaments.getTeamsCollection().add(teams);
                    tournamentsCollectionNewTournaments = em.merge(tournamentsCollectionNewTournaments);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = teams.getId();
                if (findTeams(id) == null) {
                    throw new NonexistentEntityException("The teams with id " + id + " no longer exists.");
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
            Teams teams;
            try {
                teams = em.getReference(Teams.class, id);
                teams.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The teams with id " + id + " no longer exists.", enfe);
            }
            Users player1Id = teams.getPlayer1Id();
            if (player1Id != null) {
                player1Id.getTeamsCollection().remove(teams);
                player1Id = em.merge(player1Id);
            }
            Users player2Id = teams.getPlayer2Id();
            if (player2Id != null) {
                player2Id.getTeamsCollection().remove(teams);
                player2Id = em.merge(player2Id);
            }
            Collection<Tournaments> tournamentsCollection = teams.getTournamentsCollection();
            for (Tournaments tournamentsCollectionTournaments : tournamentsCollection) {
                tournamentsCollectionTournaments.getTeamsCollection().remove(teams);
                tournamentsCollectionTournaments = em.merge(tournamentsCollectionTournaments);
            }
            em.remove(teams);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Teams> findTeamsEntities() {
        return findTeamsEntities(true, -1, -1);
    }

    public List<Teams> findTeamsEntities(int maxResults, int firstResult) {
        return findTeamsEntities(false, maxResults, firstResult);
    }

    private List<Teams> findTeamsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Teams.class));
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

    public Teams findTeams(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Teams.class, id);
        } finally {
            em.close();
        }
    }

    public int getTeamsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Teams> rt = cq.from(Teams.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
