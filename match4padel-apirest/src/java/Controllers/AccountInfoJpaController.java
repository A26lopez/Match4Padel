package Controllers;

import Controllers.exceptions.NonexistentEntityException;
import Models.AccountInfo;
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

public class AccountInfoJpaController implements Serializable {

    public AccountInfoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AccountInfo accountInfo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Workers workers = accountInfo.getWorkers();
            if (workers != null) {
                workers = em.getReference(workers.getClass(), workers.getId());
                accountInfo.setWorkers(workers);
            }
            Users users = accountInfo.getUsers();
            if (users != null) {
                users = em.getReference(users.getClass(), users.getId());
                accountInfo.setUsers(users);
            }
            em.persist(accountInfo);
            if (workers != null) {
                AccountInfo oldAccountInfoIdOfWorkers = workers.getAccountInfoId();
                if (oldAccountInfoIdOfWorkers != null) {
                    oldAccountInfoIdOfWorkers.setWorkers(null);
                    oldAccountInfoIdOfWorkers = em.merge(oldAccountInfoIdOfWorkers);
                }
                workers.setAccountInfoId(accountInfo);
                workers = em.merge(workers);
            }
            if (users != null) {
                AccountInfo oldAccountInfoIdOfUsers = users.getAccountInfoId();
                if (oldAccountInfoIdOfUsers != null) {
                    oldAccountInfoIdOfUsers.setUsers(null);
                    oldAccountInfoIdOfUsers = em.merge(oldAccountInfoIdOfUsers);
                }
                users.setAccountInfoId(accountInfo);
                users = em.merge(users);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AccountInfo accountInfo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AccountInfo persistentAccountInfo = em.find(AccountInfo.class, accountInfo.getId());
            Workers workersOld = persistentAccountInfo.getWorkers();
            Workers workersNew = accountInfo.getWorkers();
            Users usersOld = persistentAccountInfo.getUsers();
            Users usersNew = accountInfo.getUsers();
            if (workersNew != null) {
                workersNew = em.getReference(workersNew.getClass(), workersNew.getId());
                accountInfo.setWorkers(workersNew);
            }
            if (usersNew != null) {
                usersNew = em.getReference(usersNew.getClass(), usersNew.getId());
                accountInfo.setUsers(usersNew);
            }
            accountInfo = em.merge(accountInfo);
            if (workersOld != null && !workersOld.equals(workersNew)) {
                workersOld.setAccountInfoId(null);
                workersOld = em.merge(workersOld);
            }
            if (workersNew != null && !workersNew.equals(workersOld)) {
                AccountInfo oldAccountInfoIdOfWorkers = workersNew.getAccountInfoId();
                if (oldAccountInfoIdOfWorkers != null) {
                    oldAccountInfoIdOfWorkers.setWorkers(null);
                    oldAccountInfoIdOfWorkers = em.merge(oldAccountInfoIdOfWorkers);
                }
                workersNew.setAccountInfoId(accountInfo);
                workersNew = em.merge(workersNew);
            }
            if (usersOld != null && !usersOld.equals(usersNew)) {
                usersOld.setAccountInfoId(null);
                usersOld = em.merge(usersOld);
            }
            if (usersNew != null && !usersNew.equals(usersOld)) {
                AccountInfo oldAccountInfoIdOfUsers = usersNew.getAccountInfoId();
                if (oldAccountInfoIdOfUsers != null) {
                    oldAccountInfoIdOfUsers.setUsers(null);
                    oldAccountInfoIdOfUsers = em.merge(oldAccountInfoIdOfUsers);
                }
                usersNew.setAccountInfoId(accountInfo);
                usersNew = em.merge(usersNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = accountInfo.getId();
                if (findAccountInfo(id) == null) {
                    throw new NonexistentEntityException("The accountInfo with id " + id + " no longer exists.");
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
            AccountInfo accountInfo;
            try {
                accountInfo = em.getReference(AccountInfo.class, id);
                accountInfo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The accountInfo with id " + id + " no longer exists.", enfe);
            }
            Workers workers = accountInfo.getWorkers();
            if (workers != null) {
                workers.setAccountInfoId(null);
                workers = em.merge(workers);
            }
            Users users = accountInfo.getUsers();
            if (users != null) {
                users.setAccountInfoId(null);
                users = em.merge(users);
            }
            em.remove(accountInfo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AccountInfo> findAccountInfoEntities() {
        return findAccountInfoEntities(true, -1, -1);
    }

    public List<AccountInfo> findAccountInfoEntities(int maxResults, int firstResult) {
        return findAccountInfoEntities(false, maxResults, firstResult);
    }

    private List<AccountInfo> findAccountInfoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AccountInfo.class));
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

    public AccountInfo findAccountInfo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AccountInfo.class, id);
        } finally {
            em.close();
        }
    }

    public int getAccountInfoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AccountInfo> rt = cq.from(AccountInfo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
