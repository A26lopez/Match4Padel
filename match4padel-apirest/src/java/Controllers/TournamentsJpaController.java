package Controllers;

import Controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import Models.Teams;
import java.util.ArrayList;
import java.util.Collection;
import Models.Matches;
import Models.Tournaments;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class TournamentsJpaController implements Serializable {

    public TournamentsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tournaments tournaments) {
        if (tournaments.getTeamsCollection() == null) {
            tournaments.setTeamsCollection(new ArrayList<Teams>());
        }
        if (tournaments.getMatchesCollection() == null) {
            tournaments.setMatchesCollection(new ArrayList<Matches>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Teams> attachedTeamsCollection = new ArrayList<Teams>();
            for (Teams teamsCollectionTeamsToAttach : tournaments.getTeamsCollection()) {
                teamsCollectionTeamsToAttach = em.getReference(teamsCollectionTeamsToAttach.getClass(), teamsCollectionTeamsToAttach.getId());
                attachedTeamsCollection.add(teamsCollectionTeamsToAttach);
            }
            tournaments.setTeamsCollection(attachedTeamsCollection);
            Collection<Matches> attachedMatchesCollection = new ArrayList<Matches>();
            for (Matches matchesCollectionMatchesToAttach : tournaments.getMatchesCollection()) {
                matchesCollectionMatchesToAttach = em.getReference(matchesCollectionMatchesToAttach.getClass(), matchesCollectionMatchesToAttach.getId());
                attachedMatchesCollection.add(matchesCollectionMatchesToAttach);
            }
            tournaments.setMatchesCollection(attachedMatchesCollection);
            em.persist(tournaments);
            for (Teams teamsCollectionTeams : tournaments.getTeamsCollection()) {
                teamsCollectionTeams.getTournamentsCollection().add(tournaments);
                teamsCollectionTeams = em.merge(teamsCollectionTeams);
            }
            for (Matches matchesCollectionMatches : tournaments.getMatchesCollection()) {
                Tournaments oldTournamentIdOfMatchesCollectionMatches = matchesCollectionMatches.getTournamentId();
                matchesCollectionMatches.setTournamentId(tournaments);
                matchesCollectionMatches = em.merge(matchesCollectionMatches);
                if (oldTournamentIdOfMatchesCollectionMatches != null) {
                    oldTournamentIdOfMatchesCollectionMatches.getMatchesCollection().remove(matchesCollectionMatches);
                    oldTournamentIdOfMatchesCollectionMatches = em.merge(oldTournamentIdOfMatchesCollectionMatches);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tournaments tournaments) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tournaments persistentTournaments = em.find(Tournaments.class, tournaments.getId());
            Collection<Teams> teamsCollectionOld = persistentTournaments.getTeamsCollection();
            Collection<Teams> teamsCollectionNew = tournaments.getTeamsCollection();
            Collection<Matches> matchesCollectionOld = persistentTournaments.getMatchesCollection();
            Collection<Matches> matchesCollectionNew = tournaments.getMatchesCollection();
            Collection<Teams> attachedTeamsCollectionNew = new ArrayList<Teams>();
            for (Teams teamsCollectionNewTeamsToAttach : teamsCollectionNew) {
                teamsCollectionNewTeamsToAttach = em.getReference(teamsCollectionNewTeamsToAttach.getClass(), teamsCollectionNewTeamsToAttach.getId());
                attachedTeamsCollectionNew.add(teamsCollectionNewTeamsToAttach);
            }
            teamsCollectionNew = attachedTeamsCollectionNew;
            tournaments.setTeamsCollection(teamsCollectionNew);
            Collection<Matches> attachedMatchesCollectionNew = new ArrayList<Matches>();
            for (Matches matchesCollectionNewMatchesToAttach : matchesCollectionNew) {
                matchesCollectionNewMatchesToAttach = em.getReference(matchesCollectionNewMatchesToAttach.getClass(), matchesCollectionNewMatchesToAttach.getId());
                attachedMatchesCollectionNew.add(matchesCollectionNewMatchesToAttach);
            }
            matchesCollectionNew = attachedMatchesCollectionNew;
            tournaments.setMatchesCollection(matchesCollectionNew);
            tournaments = em.merge(tournaments);
            for (Teams teamsCollectionOldTeams : teamsCollectionOld) {
                if (!teamsCollectionNew.contains(teamsCollectionOldTeams)) {
                    teamsCollectionOldTeams.getTournamentsCollection().remove(tournaments);
                    teamsCollectionOldTeams = em.merge(teamsCollectionOldTeams);
                }
            }
            for (Teams teamsCollectionNewTeams : teamsCollectionNew) {
                if (!teamsCollectionOld.contains(teamsCollectionNewTeams)) {
                    teamsCollectionNewTeams.getTournamentsCollection().add(tournaments);
                    teamsCollectionNewTeams = em.merge(teamsCollectionNewTeams);
                }
            }
            for (Matches matchesCollectionOldMatches : matchesCollectionOld) {
                if (!matchesCollectionNew.contains(matchesCollectionOldMatches)) {
                    matchesCollectionOldMatches.setTournamentId(null);
                    matchesCollectionOldMatches = em.merge(matchesCollectionOldMatches);
                }
            }
            for (Matches matchesCollectionNewMatches : matchesCollectionNew) {
                if (!matchesCollectionOld.contains(matchesCollectionNewMatches)) {
                    Tournaments oldTournamentIdOfMatchesCollectionNewMatches = matchesCollectionNewMatches.getTournamentId();
                    matchesCollectionNewMatches.setTournamentId(tournaments);
                    matchesCollectionNewMatches = em.merge(matchesCollectionNewMatches);
                    if (oldTournamentIdOfMatchesCollectionNewMatches != null && !oldTournamentIdOfMatchesCollectionNewMatches.equals(tournaments)) {
                        oldTournamentIdOfMatchesCollectionNewMatches.getMatchesCollection().remove(matchesCollectionNewMatches);
                        oldTournamentIdOfMatchesCollectionNewMatches = em.merge(oldTournamentIdOfMatchesCollectionNewMatches);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tournaments.getId();
                if (findTournaments(id) == null) {
                    throw new NonexistentEntityException("The tournaments with id " + id + " no longer exists.");
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
            Tournaments tournaments;
            try {
                tournaments = em.getReference(Tournaments.class, id);
                tournaments.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tournaments with id " + id + " no longer exists.", enfe);
            }
            Collection<Teams> teamsCollection = tournaments.getTeamsCollection();
            for (Teams teamsCollectionTeams : teamsCollection) {
                teamsCollectionTeams.getTournamentsCollection().remove(tournaments);
                teamsCollectionTeams = em.merge(teamsCollectionTeams);
            }
            Collection<Matches> matchesCollection = tournaments.getMatchesCollection();
            for (Matches matchesCollectionMatches : matchesCollection) {
                matchesCollectionMatches.setTournamentId(null);
                matchesCollectionMatches = em.merge(matchesCollectionMatches);
            }
            em.remove(tournaments);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tournaments> findTournamentsEntities() {
        return findTournamentsEntities(true, -1, -1);
    }

    public List<Tournaments> findTournamentsEntities(int maxResults, int firstResult) {
        return findTournamentsEntities(false, maxResults, firstResult);
    }

    private List<Tournaments> findTournamentsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tournaments.class));
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

    public Tournaments findTournaments(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tournaments.class, id);
        } finally {
            em.close();
        }
    }

    public int getTournamentsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tournaments> rt = cq.from(Tournaments.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
