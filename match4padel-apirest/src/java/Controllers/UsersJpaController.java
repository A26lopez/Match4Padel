package Controllers;

import Controllers.exceptions.IllegalOrphanException;
import Controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import Models.AccountInfo;
import Models.AccountSecurity;
import Models.ContactInfo;
import Models.Teams;
import java.util.ArrayList;
import java.util.Collection;
import Models.Reservations;
import Models.Matches;
import Models.Users;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class UsersJpaController implements Serializable {

    public UsersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Users users) {
        if (users.getTeamsCollection() == null) {
            users.setTeamsCollection(new ArrayList<Teams>());
        }
        if (users.getTeamsCollection1() == null) {
            users.setTeamsCollection1(new ArrayList<Teams>());
        }
        if (users.getReservationsCollection() == null) {
            users.setReservationsCollection(new ArrayList<Reservations>());
        }
        if (users.getMatchesCollection() == null) {
            users.setMatchesCollection(new ArrayList<Matches>());
        }
        if (users.getMatchesCollection1() == null) {
            users.setMatchesCollection1(new ArrayList<Matches>());
        }
        if (users.getMatchesCollection2() == null) {
            users.setMatchesCollection2(new ArrayList<Matches>());
        }
        if (users.getMatchesCollection3() == null) {
            users.setMatchesCollection3(new ArrayList<Matches>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AccountInfo accountInfoId = users.getAccountInfoId();
            if (accountInfoId != null) {
                accountInfoId = em.getReference(accountInfoId.getClass(), accountInfoId.getId());
                users.setAccountInfoId(accountInfoId);
            }
            AccountSecurity accountSecurityId = users.getAccountSecurityId();
            if (accountSecurityId != null) {
                accountSecurityId = em.getReference(accountSecurityId.getClass(), accountSecurityId.getId());
                users.setAccountSecurityId(accountSecurityId);
            }
            ContactInfo contactInfoId = users.getContactInfoId();
            if (contactInfoId != null) {
                contactInfoId = em.getReference(contactInfoId.getClass(), contactInfoId.getId());
                users.setContactInfoId(contactInfoId);
            }
            Collection<Teams> attachedTeamsCollection = new ArrayList<Teams>();
            for (Teams teamsCollectionTeamsToAttach : users.getTeamsCollection()) {
                teamsCollectionTeamsToAttach = em.getReference(teamsCollectionTeamsToAttach.getClass(), teamsCollectionTeamsToAttach.getId());
                attachedTeamsCollection.add(teamsCollectionTeamsToAttach);
            }
            users.setTeamsCollection(attachedTeamsCollection);
            Collection<Teams> attachedTeamsCollection1 = new ArrayList<Teams>();
            for (Teams teamsCollection1TeamsToAttach : users.getTeamsCollection1()) {
                teamsCollection1TeamsToAttach = em.getReference(teamsCollection1TeamsToAttach.getClass(), teamsCollection1TeamsToAttach.getId());
                attachedTeamsCollection1.add(teamsCollection1TeamsToAttach);
            }
            users.setTeamsCollection1(attachedTeamsCollection1);
            Collection<Reservations> attachedReservationsCollection = new ArrayList<Reservations>();
            for (Reservations reservationsCollectionReservationsToAttach : users.getReservationsCollection()) {
                reservationsCollectionReservationsToAttach = em.getReference(reservationsCollectionReservationsToAttach.getClass(), reservationsCollectionReservationsToAttach.getId());
                attachedReservationsCollection.add(reservationsCollectionReservationsToAttach);
            }
            users.setReservationsCollection(attachedReservationsCollection);
            Collection<Matches> attachedMatchesCollection = new ArrayList<Matches>();
            for (Matches matchesCollectionMatchesToAttach : users.getMatchesCollection()) {
                matchesCollectionMatchesToAttach = em.getReference(matchesCollectionMatchesToAttach.getClass(), matchesCollectionMatchesToAttach.getId());
                attachedMatchesCollection.add(matchesCollectionMatchesToAttach);
            }
            users.setMatchesCollection(attachedMatchesCollection);
            Collection<Matches> attachedMatchesCollection1 = new ArrayList<Matches>();
            for (Matches matchesCollection1MatchesToAttach : users.getMatchesCollection1()) {
                matchesCollection1MatchesToAttach = em.getReference(matchesCollection1MatchesToAttach.getClass(), matchesCollection1MatchesToAttach.getId());
                attachedMatchesCollection1.add(matchesCollection1MatchesToAttach);
            }
            users.setMatchesCollection1(attachedMatchesCollection1);
            Collection<Matches> attachedMatchesCollection2 = new ArrayList<Matches>();
            for (Matches matchesCollection2MatchesToAttach : users.getMatchesCollection2()) {
                matchesCollection2MatchesToAttach = em.getReference(matchesCollection2MatchesToAttach.getClass(), matchesCollection2MatchesToAttach.getId());
                attachedMatchesCollection2.add(matchesCollection2MatchesToAttach);
            }
            users.setMatchesCollection2(attachedMatchesCollection2);
            Collection<Matches> attachedMatchesCollection3 = new ArrayList<Matches>();
            for (Matches matchesCollection3MatchesToAttach : users.getMatchesCollection3()) {
                matchesCollection3MatchesToAttach = em.getReference(matchesCollection3MatchesToAttach.getClass(), matchesCollection3MatchesToAttach.getId());
                attachedMatchesCollection3.add(matchesCollection3MatchesToAttach);
            }
            users.setMatchesCollection3(attachedMatchesCollection3);
            em.persist(users);
            if (accountInfoId != null) {
                Users oldUsersOfAccountInfoId = accountInfoId.getUsers();
                if (oldUsersOfAccountInfoId != null) {
                    oldUsersOfAccountInfoId.setAccountInfoId(null);
                    oldUsersOfAccountInfoId = em.merge(oldUsersOfAccountInfoId);
                }
                accountInfoId.setUsers(users);
                accountInfoId = em.merge(accountInfoId);
            }
            if (accountSecurityId != null) {
                Users oldUsersOfAccountSecurityId = accountSecurityId.getUsers();
                if (oldUsersOfAccountSecurityId != null) {
                    oldUsersOfAccountSecurityId.setAccountSecurityId(null);
                    oldUsersOfAccountSecurityId = em.merge(oldUsersOfAccountSecurityId);
                }
                accountSecurityId.setUsers(users);
                accountSecurityId = em.merge(accountSecurityId);
            }
            if (contactInfoId != null) {
                Users oldUsersOfContactInfoId = contactInfoId.getUsers();
                if (oldUsersOfContactInfoId != null) {
                    oldUsersOfContactInfoId.setContactInfoId(null);
                    oldUsersOfContactInfoId = em.merge(oldUsersOfContactInfoId);
                }
                contactInfoId.setUsers(users);
                contactInfoId = em.merge(contactInfoId);
            }
            for (Teams teamsCollectionTeams : users.getTeamsCollection()) {
                Users oldPlayer1IdOfTeamsCollectionTeams = teamsCollectionTeams.getPlayer1Id();
                teamsCollectionTeams.setPlayer1Id(users);
                teamsCollectionTeams = em.merge(teamsCollectionTeams);
                if (oldPlayer1IdOfTeamsCollectionTeams != null) {
                    oldPlayer1IdOfTeamsCollectionTeams.getTeamsCollection().remove(teamsCollectionTeams);
                    oldPlayer1IdOfTeamsCollectionTeams = em.merge(oldPlayer1IdOfTeamsCollectionTeams);
                }
            }
            for (Teams teamsCollection1Teams : users.getTeamsCollection1()) {
                Users oldPlayer2IdOfTeamsCollection1Teams = teamsCollection1Teams.getPlayer2Id();
                teamsCollection1Teams.setPlayer2Id(users);
                teamsCollection1Teams = em.merge(teamsCollection1Teams);
                if (oldPlayer2IdOfTeamsCollection1Teams != null) {
                    oldPlayer2IdOfTeamsCollection1Teams.getTeamsCollection1().remove(teamsCollection1Teams);
                    oldPlayer2IdOfTeamsCollection1Teams = em.merge(oldPlayer2IdOfTeamsCollection1Teams);
                }
            }
            for (Reservations reservationsCollectionReservations : users.getReservationsCollection()) {
                Users oldUserIdOfReservationsCollectionReservations = reservationsCollectionReservations.getUserId();
                reservationsCollectionReservations.setUserId(users);
                reservationsCollectionReservations = em.merge(reservationsCollectionReservations);
                if (oldUserIdOfReservationsCollectionReservations != null) {
                    oldUserIdOfReservationsCollectionReservations.getReservationsCollection().remove(reservationsCollectionReservations);
                    oldUserIdOfReservationsCollectionReservations = em.merge(oldUserIdOfReservationsCollectionReservations);
                }
            }
            for (Matches matchesCollectionMatches : users.getMatchesCollection()) {
                Users oldOwnerIdOfMatchesCollectionMatches = matchesCollectionMatches.getOwnerId();
                matchesCollectionMatches.setOwnerId(users);
                matchesCollectionMatches = em.merge(matchesCollectionMatches);
                if (oldOwnerIdOfMatchesCollectionMatches != null) {
                    oldOwnerIdOfMatchesCollectionMatches.getMatchesCollection().remove(matchesCollectionMatches);
                    oldOwnerIdOfMatchesCollectionMatches = em.merge(oldOwnerIdOfMatchesCollectionMatches);
                }
            }
            for (Matches matchesCollection1Matches : users.getMatchesCollection1()) {
                Users oldPlayer1IdOfMatchesCollection1Matches = matchesCollection1Matches.getPlayer1Id();
                matchesCollection1Matches.setPlayer1Id(users);
                matchesCollection1Matches = em.merge(matchesCollection1Matches);
                if (oldPlayer1IdOfMatchesCollection1Matches != null) {
                    oldPlayer1IdOfMatchesCollection1Matches.getMatchesCollection1().remove(matchesCollection1Matches);
                    oldPlayer1IdOfMatchesCollection1Matches = em.merge(oldPlayer1IdOfMatchesCollection1Matches);
                }
            }
            for (Matches matchesCollection2Matches : users.getMatchesCollection2()) {
                Users oldPlayer2IdOfMatchesCollection2Matches = matchesCollection2Matches.getPlayer2Id();
                matchesCollection2Matches.setPlayer2Id(users);
                matchesCollection2Matches = em.merge(matchesCollection2Matches);
                if (oldPlayer2IdOfMatchesCollection2Matches != null) {
                    oldPlayer2IdOfMatchesCollection2Matches.getMatchesCollection2().remove(matchesCollection2Matches);
                    oldPlayer2IdOfMatchesCollection2Matches = em.merge(oldPlayer2IdOfMatchesCollection2Matches);
                }
            }
            for (Matches matchesCollection3Matches : users.getMatchesCollection3()) {
                Users oldPlayer3IdOfMatchesCollection3Matches = matchesCollection3Matches.getPlayer3Id();
                matchesCollection3Matches.setPlayer3Id(users);
                matchesCollection3Matches = em.merge(matchesCollection3Matches);
                if (oldPlayer3IdOfMatchesCollection3Matches != null) {
                    oldPlayer3IdOfMatchesCollection3Matches.getMatchesCollection3().remove(matchesCollection3Matches);
                    oldPlayer3IdOfMatchesCollection3Matches = em.merge(oldPlayer3IdOfMatchesCollection3Matches);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Users users) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Users persistentUsers = em.find(Users.class, users.getId());
            AccountInfo accountInfoIdOld = persistentUsers.getAccountInfoId();
            AccountInfo accountInfoIdNew = users.getAccountInfoId();
            AccountSecurity accountSecurityIdOld = persistentUsers.getAccountSecurityId();
            AccountSecurity accountSecurityIdNew = users.getAccountSecurityId();
            ContactInfo contactInfoIdOld = persistentUsers.getContactInfoId();
            ContactInfo contactInfoIdNew = users.getContactInfoId();
            Collection<Teams> teamsCollectionOld = persistentUsers.getTeamsCollection();
            Collection<Teams> teamsCollectionNew = users.getTeamsCollection();
            Collection<Teams> teamsCollection1Old = persistentUsers.getTeamsCollection1();
            Collection<Teams> teamsCollection1New = users.getTeamsCollection1();
            Collection<Reservations> reservationsCollectionOld = persistentUsers.getReservationsCollection();
            Collection<Reservations> reservationsCollectionNew = users.getReservationsCollection();
            Collection<Matches> matchesCollectionOld = persistentUsers.getMatchesCollection();
            Collection<Matches> matchesCollectionNew = users.getMatchesCollection();
            Collection<Matches> matchesCollection1Old = persistentUsers.getMatchesCollection1();
            Collection<Matches> matchesCollection1New = users.getMatchesCollection1();
            Collection<Matches> matchesCollection2Old = persistentUsers.getMatchesCollection2();
            Collection<Matches> matchesCollection2New = users.getMatchesCollection2();
            Collection<Matches> matchesCollection3Old = persistentUsers.getMatchesCollection3();
            Collection<Matches> matchesCollection3New = users.getMatchesCollection3();
            List<String> illegalOrphanMessages = null;
            for (Teams teamsCollectionOldTeams : teamsCollectionOld) {
                if (!teamsCollectionNew.contains(teamsCollectionOldTeams)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Teams " + teamsCollectionOldTeams + " since its player1Id field is not nullable.");
                }
            }
            for (Teams teamsCollection1OldTeams : teamsCollection1Old) {
                if (!teamsCollection1New.contains(teamsCollection1OldTeams)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Teams " + teamsCollection1OldTeams + " since its player2Id field is not nullable.");
                }
            }
            for (Reservations reservationsCollectionOldReservations : reservationsCollectionOld) {
                if (!reservationsCollectionNew.contains(reservationsCollectionOldReservations)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reservations " + reservationsCollectionOldReservations + " since its userId field is not nullable.");
                }
            }
            for (Matches matchesCollectionOldMatches : matchesCollectionOld) {
                if (!matchesCollectionNew.contains(matchesCollectionOldMatches)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Matches " + matchesCollectionOldMatches + " since its ownerId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (accountInfoIdNew != null) {
                accountInfoIdNew = em.getReference(accountInfoIdNew.getClass(), accountInfoIdNew.getId());
                users.setAccountInfoId(accountInfoIdNew);
            }
            if (accountSecurityIdNew != null) {
                accountSecurityIdNew = em.getReference(accountSecurityIdNew.getClass(), accountSecurityIdNew.getId());
                users.setAccountSecurityId(accountSecurityIdNew);
            }
            if (contactInfoIdNew != null) {
                contactInfoIdNew = em.getReference(contactInfoIdNew.getClass(), contactInfoIdNew.getId());
                users.setContactInfoId(contactInfoIdNew);
            }
            Collection<Teams> attachedTeamsCollectionNew = new ArrayList<Teams>();
            for (Teams teamsCollectionNewTeamsToAttach : teamsCollectionNew) {
                teamsCollectionNewTeamsToAttach = em.getReference(teamsCollectionNewTeamsToAttach.getClass(), teamsCollectionNewTeamsToAttach.getId());
                attachedTeamsCollectionNew.add(teamsCollectionNewTeamsToAttach);
            }
            teamsCollectionNew = attachedTeamsCollectionNew;
            users.setTeamsCollection(teamsCollectionNew);
            Collection<Teams> attachedTeamsCollection1New = new ArrayList<Teams>();
            for (Teams teamsCollection1NewTeamsToAttach : teamsCollection1New) {
                teamsCollection1NewTeamsToAttach = em.getReference(teamsCollection1NewTeamsToAttach.getClass(), teamsCollection1NewTeamsToAttach.getId());
                attachedTeamsCollection1New.add(teamsCollection1NewTeamsToAttach);
            }
            teamsCollection1New = attachedTeamsCollection1New;
            users.setTeamsCollection1(teamsCollection1New);
            Collection<Reservations> attachedReservationsCollectionNew = new ArrayList<Reservations>();
            for (Reservations reservationsCollectionNewReservationsToAttach : reservationsCollectionNew) {
                reservationsCollectionNewReservationsToAttach = em.getReference(reservationsCollectionNewReservationsToAttach.getClass(), reservationsCollectionNewReservationsToAttach.getId());
                attachedReservationsCollectionNew.add(reservationsCollectionNewReservationsToAttach);
            }
            reservationsCollectionNew = attachedReservationsCollectionNew;
            users.setReservationsCollection(reservationsCollectionNew);
            Collection<Matches> attachedMatchesCollectionNew = new ArrayList<Matches>();
            for (Matches matchesCollectionNewMatchesToAttach : matchesCollectionNew) {
                matchesCollectionNewMatchesToAttach = em.getReference(matchesCollectionNewMatchesToAttach.getClass(), matchesCollectionNewMatchesToAttach.getId());
                attachedMatchesCollectionNew.add(matchesCollectionNewMatchesToAttach);
            }
            matchesCollectionNew = attachedMatchesCollectionNew;
            users.setMatchesCollection(matchesCollectionNew);
            Collection<Matches> attachedMatchesCollection1New = new ArrayList<Matches>();
            for (Matches matchesCollection1NewMatchesToAttach : matchesCollection1New) {
                matchesCollection1NewMatchesToAttach = em.getReference(matchesCollection1NewMatchesToAttach.getClass(), matchesCollection1NewMatchesToAttach.getId());
                attachedMatchesCollection1New.add(matchesCollection1NewMatchesToAttach);
            }
            matchesCollection1New = attachedMatchesCollection1New;
            users.setMatchesCollection1(matchesCollection1New);
            Collection<Matches> attachedMatchesCollection2New = new ArrayList<Matches>();
            for (Matches matchesCollection2NewMatchesToAttach : matchesCollection2New) {
                matchesCollection2NewMatchesToAttach = em.getReference(matchesCollection2NewMatchesToAttach.getClass(), matchesCollection2NewMatchesToAttach.getId());
                attachedMatchesCollection2New.add(matchesCollection2NewMatchesToAttach);
            }
            matchesCollection2New = attachedMatchesCollection2New;
            users.setMatchesCollection2(matchesCollection2New);
            Collection<Matches> attachedMatchesCollection3New = new ArrayList<Matches>();
            for (Matches matchesCollection3NewMatchesToAttach : matchesCollection3New) {
                matchesCollection3NewMatchesToAttach = em.getReference(matchesCollection3NewMatchesToAttach.getClass(), matchesCollection3NewMatchesToAttach.getId());
                attachedMatchesCollection3New.add(matchesCollection3NewMatchesToAttach);
            }
            matchesCollection3New = attachedMatchesCollection3New;
            users.setMatchesCollection3(matchesCollection3New);
            users = em.merge(users);
            if (accountInfoIdOld != null && !accountInfoIdOld.equals(accountInfoIdNew)) {
                accountInfoIdOld.setUsers(null);
                accountInfoIdOld = em.merge(accountInfoIdOld);
            }
            if (accountInfoIdNew != null && !accountInfoIdNew.equals(accountInfoIdOld)) {
                Users oldUsersOfAccountInfoId = accountInfoIdNew.getUsers();
                if (oldUsersOfAccountInfoId != null) {
                    oldUsersOfAccountInfoId.setAccountInfoId(null);
                    oldUsersOfAccountInfoId = em.merge(oldUsersOfAccountInfoId);
                }
                accountInfoIdNew.setUsers(users);
                accountInfoIdNew = em.merge(accountInfoIdNew);
            }
            if (accountSecurityIdOld != null && !accountSecurityIdOld.equals(accountSecurityIdNew)) {
                accountSecurityIdOld.setUsers(null);
                accountSecurityIdOld = em.merge(accountSecurityIdOld);
            }
            if (accountSecurityIdNew != null && !accountSecurityIdNew.equals(accountSecurityIdOld)) {
                Users oldUsersOfAccountSecurityId = accountSecurityIdNew.getUsers();
                if (oldUsersOfAccountSecurityId != null) {
                    oldUsersOfAccountSecurityId.setAccountSecurityId(null);
                    oldUsersOfAccountSecurityId = em.merge(oldUsersOfAccountSecurityId);
                }
                accountSecurityIdNew.setUsers(users);
                accountSecurityIdNew = em.merge(accountSecurityIdNew);
            }
            if (contactInfoIdOld != null && !contactInfoIdOld.equals(contactInfoIdNew)) {
                contactInfoIdOld.setUsers(null);
                contactInfoIdOld = em.merge(contactInfoIdOld);
            }
            if (contactInfoIdNew != null && !contactInfoIdNew.equals(contactInfoIdOld)) {
                Users oldUsersOfContactInfoId = contactInfoIdNew.getUsers();
                if (oldUsersOfContactInfoId != null) {
                    oldUsersOfContactInfoId.setContactInfoId(null);
                    oldUsersOfContactInfoId = em.merge(oldUsersOfContactInfoId);
                }
                contactInfoIdNew.setUsers(users);
                contactInfoIdNew = em.merge(contactInfoIdNew);
            }
            for (Teams teamsCollectionNewTeams : teamsCollectionNew) {
                if (!teamsCollectionOld.contains(teamsCollectionNewTeams)) {
                    Users oldPlayer1IdOfTeamsCollectionNewTeams = teamsCollectionNewTeams.getPlayer1Id();
                    teamsCollectionNewTeams.setPlayer1Id(users);
                    teamsCollectionNewTeams = em.merge(teamsCollectionNewTeams);
                    if (oldPlayer1IdOfTeamsCollectionNewTeams != null && !oldPlayer1IdOfTeamsCollectionNewTeams.equals(users)) {
                        oldPlayer1IdOfTeamsCollectionNewTeams.getTeamsCollection().remove(teamsCollectionNewTeams);
                        oldPlayer1IdOfTeamsCollectionNewTeams = em.merge(oldPlayer1IdOfTeamsCollectionNewTeams);
                    }
                }
            }
            for (Teams teamsCollection1NewTeams : teamsCollection1New) {
                if (!teamsCollection1Old.contains(teamsCollection1NewTeams)) {
                    Users oldPlayer2IdOfTeamsCollection1NewTeams = teamsCollection1NewTeams.getPlayer2Id();
                    teamsCollection1NewTeams.setPlayer2Id(users);
                    teamsCollection1NewTeams = em.merge(teamsCollection1NewTeams);
                    if (oldPlayer2IdOfTeamsCollection1NewTeams != null && !oldPlayer2IdOfTeamsCollection1NewTeams.equals(users)) {
                        oldPlayer2IdOfTeamsCollection1NewTeams.getTeamsCollection1().remove(teamsCollection1NewTeams);
                        oldPlayer2IdOfTeamsCollection1NewTeams = em.merge(oldPlayer2IdOfTeamsCollection1NewTeams);
                    }
                }
            }
            for (Reservations reservationsCollectionNewReservations : reservationsCollectionNew) {
                if (!reservationsCollectionOld.contains(reservationsCollectionNewReservations)) {
                    Users oldUserIdOfReservationsCollectionNewReservations = reservationsCollectionNewReservations.getUserId();
                    reservationsCollectionNewReservations.setUserId(users);
                    reservationsCollectionNewReservations = em.merge(reservationsCollectionNewReservations);
                    if (oldUserIdOfReservationsCollectionNewReservations != null && !oldUserIdOfReservationsCollectionNewReservations.equals(users)) {
                        oldUserIdOfReservationsCollectionNewReservations.getReservationsCollection().remove(reservationsCollectionNewReservations);
                        oldUserIdOfReservationsCollectionNewReservations = em.merge(oldUserIdOfReservationsCollectionNewReservations);
                    }
                }
            }
            for (Matches matchesCollectionNewMatches : matchesCollectionNew) {
                if (!matchesCollectionOld.contains(matchesCollectionNewMatches)) {
                    Users oldOwnerIdOfMatchesCollectionNewMatches = matchesCollectionNewMatches.getOwnerId();
                    matchesCollectionNewMatches.setOwnerId(users);
                    matchesCollectionNewMatches = em.merge(matchesCollectionNewMatches);
                    if (oldOwnerIdOfMatchesCollectionNewMatches != null && !oldOwnerIdOfMatchesCollectionNewMatches.equals(users)) {
                        oldOwnerIdOfMatchesCollectionNewMatches.getMatchesCollection().remove(matchesCollectionNewMatches);
                        oldOwnerIdOfMatchesCollectionNewMatches = em.merge(oldOwnerIdOfMatchesCollectionNewMatches);
                    }
                }
            }
            for (Matches matchesCollection1OldMatches : matchesCollection1Old) {
                if (!matchesCollection1New.contains(matchesCollection1OldMatches)) {
                    matchesCollection1OldMatches.setPlayer1Id(null);
                    matchesCollection1OldMatches = em.merge(matchesCollection1OldMatches);
                }
            }
            for (Matches matchesCollection1NewMatches : matchesCollection1New) {
                if (!matchesCollection1Old.contains(matchesCollection1NewMatches)) {
                    Users oldPlayer1IdOfMatchesCollection1NewMatches = matchesCollection1NewMatches.getPlayer1Id();
                    matchesCollection1NewMatches.setPlayer1Id(users);
                    matchesCollection1NewMatches = em.merge(matchesCollection1NewMatches);
                    if (oldPlayer1IdOfMatchesCollection1NewMatches != null && !oldPlayer1IdOfMatchesCollection1NewMatches.equals(users)) {
                        oldPlayer1IdOfMatchesCollection1NewMatches.getMatchesCollection1().remove(matchesCollection1NewMatches);
                        oldPlayer1IdOfMatchesCollection1NewMatches = em.merge(oldPlayer1IdOfMatchesCollection1NewMatches);
                    }
                }
            }
            for (Matches matchesCollection2OldMatches : matchesCollection2Old) {
                if (!matchesCollection2New.contains(matchesCollection2OldMatches)) {
                    matchesCollection2OldMatches.setPlayer2Id(null);
                    matchesCollection2OldMatches = em.merge(matchesCollection2OldMatches);
                }
            }
            for (Matches matchesCollection2NewMatches : matchesCollection2New) {
                if (!matchesCollection2Old.contains(matchesCollection2NewMatches)) {
                    Users oldPlayer2IdOfMatchesCollection2NewMatches = matchesCollection2NewMatches.getPlayer2Id();
                    matchesCollection2NewMatches.setPlayer2Id(users);
                    matchesCollection2NewMatches = em.merge(matchesCollection2NewMatches);
                    if (oldPlayer2IdOfMatchesCollection2NewMatches != null && !oldPlayer2IdOfMatchesCollection2NewMatches.equals(users)) {
                        oldPlayer2IdOfMatchesCollection2NewMatches.getMatchesCollection2().remove(matchesCollection2NewMatches);
                        oldPlayer2IdOfMatchesCollection2NewMatches = em.merge(oldPlayer2IdOfMatchesCollection2NewMatches);
                    }
                }
            }
            for (Matches matchesCollection3OldMatches : matchesCollection3Old) {
                if (!matchesCollection3New.contains(matchesCollection3OldMatches)) {
                    matchesCollection3OldMatches.setPlayer3Id(null);
                    matchesCollection3OldMatches = em.merge(matchesCollection3OldMatches);
                }
            }
            for (Matches matchesCollection3NewMatches : matchesCollection3New) {
                if (!matchesCollection3Old.contains(matchesCollection3NewMatches)) {
                    Users oldPlayer3IdOfMatchesCollection3NewMatches = matchesCollection3NewMatches.getPlayer3Id();
                    matchesCollection3NewMatches.setPlayer3Id(users);
                    matchesCollection3NewMatches = em.merge(matchesCollection3NewMatches);
                    if (oldPlayer3IdOfMatchesCollection3NewMatches != null && !oldPlayer3IdOfMatchesCollection3NewMatches.equals(users)) {
                        oldPlayer3IdOfMatchesCollection3NewMatches.getMatchesCollection3().remove(matchesCollection3NewMatches);
                        oldPlayer3IdOfMatchesCollection3NewMatches = em.merge(oldPlayer3IdOfMatchesCollection3NewMatches);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = users.getId();
                if (findUsers(id) == null) {
                    throw new NonexistentEntityException("The users with id " + id + " no longer exists.");
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
            Users users;
            try {
                users = em.getReference(Users.class, id);
                users.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The users with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Teams> teamsCollectionOrphanCheck = users.getTeamsCollection();
            for (Teams teamsCollectionOrphanCheckTeams : teamsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Users (" + users + ") cannot be destroyed since the Teams " + teamsCollectionOrphanCheckTeams + " in its teamsCollection field has a non-nullable player1Id field.");
            }
            Collection<Teams> teamsCollection1OrphanCheck = users.getTeamsCollection1();
            for (Teams teamsCollection1OrphanCheckTeams : teamsCollection1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Users (" + users + ") cannot be destroyed since the Teams " + teamsCollection1OrphanCheckTeams + " in its teamsCollection1 field has a non-nullable player2Id field.");
            }
            Collection<Reservations> reservationsCollectionOrphanCheck = users.getReservationsCollection();
            for (Reservations reservationsCollectionOrphanCheckReservations : reservationsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Users (" + users + ") cannot be destroyed since the Reservations " + reservationsCollectionOrphanCheckReservations + " in its reservationsCollection field has a non-nullable userId field.");
            }
            Collection<Matches> matchesCollectionOrphanCheck = users.getMatchesCollection();
            for (Matches matchesCollectionOrphanCheckMatches : matchesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Users (" + users + ") cannot be destroyed since the Matches " + matchesCollectionOrphanCheckMatches + " in its matchesCollection field has a non-nullable ownerId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            AccountInfo accountInfoId = users.getAccountInfoId();
            if (accountInfoId != null) {
                accountInfoId.setUsers(null);
                accountInfoId = em.merge(accountInfoId);
            }
            AccountSecurity accountSecurityId = users.getAccountSecurityId();
            if (accountSecurityId != null) {
                accountSecurityId.setUsers(null);
                accountSecurityId = em.merge(accountSecurityId);
            }
            ContactInfo contactInfoId = users.getContactInfoId();
            if (contactInfoId != null) {
                contactInfoId.setUsers(null);
                contactInfoId = em.merge(contactInfoId);
            }
            Collection<Matches> matchesCollection1 = users.getMatchesCollection1();
            for (Matches matchesCollection1Matches : matchesCollection1) {
                matchesCollection1Matches.setPlayer1Id(null);
                matchesCollection1Matches = em.merge(matchesCollection1Matches);
            }
            Collection<Matches> matchesCollection2 = users.getMatchesCollection2();
            for (Matches matchesCollection2Matches : matchesCollection2) {
                matchesCollection2Matches.setPlayer2Id(null);
                matchesCollection2Matches = em.merge(matchesCollection2Matches);
            }
            Collection<Matches> matchesCollection3 = users.getMatchesCollection3();
            for (Matches matchesCollection3Matches : matchesCollection3) {
                matchesCollection3Matches.setPlayer3Id(null);
                matchesCollection3Matches = em.merge(matchesCollection3Matches);
            }
            em.remove(users);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Users> findUsersEntities() {
        return findUsersEntities(true, -1, -1);
    }

    public List<Users> findUsersEntities(int maxResults, int firstResult) {
        return findUsersEntities(false, maxResults, firstResult);
    }

    private List<Users> findUsersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Users.class));
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

    public Users findUsers(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Users.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Users> rt = cq.from(Users.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
