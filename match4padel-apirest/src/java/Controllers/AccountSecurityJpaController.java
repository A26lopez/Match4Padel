package Controllers;

import Controllers.exceptions.NonexistentEntityException;
import Models.AccountSecurity;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import Models.Workers;
import Models.Users;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class AccountSecurityJpaController implements Serializable {

    public AccountSecurityJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AccountSecurity accountSecurity) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Workers workers = accountSecurity.getWorkers();
            if (workers != null) {
                workers = em.getReference(workers.getClass(), workers.getId());
                accountSecurity.setWorkers(workers);
            }
            Users users = accountSecurity.getUsers();
            if (users != null) {
                users = em.getReference(users.getClass(), users.getId());
                accountSecurity.setUsers(users);
            }
            em.persist(accountSecurity);
            if (workers != null) {
                AccountSecurity oldAccountSecurityIdOfWorkers = workers.getAccountSecurityId();
                if (oldAccountSecurityIdOfWorkers != null) {
                    oldAccountSecurityIdOfWorkers.setWorkers(null);
                    oldAccountSecurityIdOfWorkers = em.merge(oldAccountSecurityIdOfWorkers);
                }
                workers.setAccountSecurityId(accountSecurity);
                workers = em.merge(workers);
            }
            if (users != null) {
                AccountSecurity oldAccountSecurityIdOfUsers = users.getAccountSecurityId();
                if (oldAccountSecurityIdOfUsers != null) {
                    oldAccountSecurityIdOfUsers.setUsers(null);
                    oldAccountSecurityIdOfUsers = em.merge(oldAccountSecurityIdOfUsers);
                }
                users.setAccountSecurityId(accountSecurity);
                users = em.merge(users);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AccountSecurity accountSecurity) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AccountSecurity persistentAccountSecurity = em.find(AccountSecurity.class, accountSecurity.getId());
            Workers workersOld = persistentAccountSecurity.getWorkers();
            Workers workersNew = accountSecurity.getWorkers();
            Users usersOld = persistentAccountSecurity.getUsers();
            Users usersNew = accountSecurity.getUsers();
            if (workersNew != null) {
                workersNew = em.getReference(workersNew.getClass(), workersNew.getId());
                accountSecurity.setWorkers(workersNew);
            }
            if (usersNew != null) {
                usersNew = em.getReference(usersNew.getClass(), usersNew.getId());
                accountSecurity.setUsers(usersNew);
            }
            accountSecurity = em.merge(accountSecurity);
            if (workersOld != null && !workersOld.equals(workersNew)) {
                workersOld.setAccountSecurityId(null);
                workersOld = em.merge(workersOld);
            }
            if (workersNew != null && !workersNew.equals(workersOld)) {
                AccountSecurity oldAccountSecurityIdOfWorkers = workersNew.getAccountSecurityId();
                if (oldAccountSecurityIdOfWorkers != null) {
                    oldAccountSecurityIdOfWorkers.setWorkers(null);
                    oldAccountSecurityIdOfWorkers = em.merge(oldAccountSecurityIdOfWorkers);
                }
                workersNew.setAccountSecurityId(accountSecurity);
                workersNew = em.merge(workersNew);
            }
            if (usersOld != null && !usersOld.equals(usersNew)) {
                usersOld.setAccountSecurityId(null);
                usersOld = em.merge(usersOld);
            }
            if (usersNew != null && !usersNew.equals(usersOld)) {
                AccountSecurity oldAccountSecurityIdOfUsers = usersNew.getAccountSecurityId();
                if (oldAccountSecurityIdOfUsers != null) {
                    oldAccountSecurityIdOfUsers.setUsers(null);
                    oldAccountSecurityIdOfUsers = em.merge(oldAccountSecurityIdOfUsers);
                }
                usersNew.setAccountSecurityId(accountSecurity);
                usersNew = em.merge(usersNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = accountSecurity.getId();
                if (findAccountSecurity(id) == null) {
                    throw new NonexistentEntityException("The accountSecurity with id " + id + " no longer exists.");
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
            AccountSecurity accountSecurity;
            try {
                accountSecurity = em.getReference(AccountSecurity.class, id);
                accountSecurity.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The accountSecurity with id " + id + " no longer exists.", enfe);
            }
            Workers workers = accountSecurity.getWorkers();
            if (workers != null) {
                workers.setAccountSecurityId(null);
                workers = em.merge(workers);
            }
            Users users = accountSecurity.getUsers();
            if (users != null) {
                users.setAccountSecurityId(null);
                users = em.merge(users);
            }
            em.remove(accountSecurity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AccountSecurity> findAccountSecurityEntities() {
        return findAccountSecurityEntities(true, -1, -1);
    }

    public List<AccountSecurity> findAccountSecurityEntities(int maxResults, int firstResult) {
        return findAccountSecurityEntities(false, maxResults, firstResult);
    }

    private List<AccountSecurity> findAccountSecurityEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AccountSecurity.class));
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

    public AccountSecurity findAccountSecurity(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AccountSecurity.class, id);
        } finally {
            em.close();
        }
    }

    public int getAccountSecurityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AccountSecurity> rt = cq.from(AccountSecurity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
