package Controllers;

import Controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import Models.AccountInfo;
import Models.AccountSecurity;
import Models.ContactInfo;
import Models.WorkInfo;
import Models.Workers;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class WorkersJpaController implements Serializable {

    public WorkersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Workers workers) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AccountInfo accountInfoId = workers.getAccountInfoId();
            if (accountInfoId != null) {
                accountInfoId = em.getReference(accountInfoId.getClass(), accountInfoId.getId());
                workers.setAccountInfoId(accountInfoId);
            }
            AccountSecurity accountSecurityId = workers.getAccountSecurityId();
            if (accountSecurityId != null) {
                accountSecurityId = em.getReference(accountSecurityId.getClass(), accountSecurityId.getId());
                workers.setAccountSecurityId(accountSecurityId);
            }
            ContactInfo contactInfoId = workers.getContactInfoId();
            if (contactInfoId != null) {
                contactInfoId = em.getReference(contactInfoId.getClass(), contactInfoId.getId());
                workers.setContactInfoId(contactInfoId);
            }
            WorkInfo workInfoId = workers.getWorkInfoId();
            if (workInfoId != null) {
                workInfoId = em.getReference(workInfoId.getClass(), workInfoId.getId());
                workers.setWorkInfoId(workInfoId);
            }
            em.persist(workers);
            if (accountInfoId != null) {
                Workers oldWorkersOfAccountInfoId = accountInfoId.getWorkers();
                if (oldWorkersOfAccountInfoId != null) {
                    oldWorkersOfAccountInfoId.setAccountInfoId(null);
                    oldWorkersOfAccountInfoId = em.merge(oldWorkersOfAccountInfoId);
                }
                accountInfoId.setWorkers(workers);
                accountInfoId = em.merge(accountInfoId);
            }
            if (accountSecurityId != null) {
                Workers oldWorkersOfAccountSecurityId = accountSecurityId.getWorkers();
                if (oldWorkersOfAccountSecurityId != null) {
                    oldWorkersOfAccountSecurityId.setAccountSecurityId(null);
                    oldWorkersOfAccountSecurityId = em.merge(oldWorkersOfAccountSecurityId);
                }
                accountSecurityId.setWorkers(workers);
                accountSecurityId = em.merge(accountSecurityId);
            }
            if (contactInfoId != null) {
                Workers oldWorkersOfContactInfoId = contactInfoId.getWorkers();
                if (oldWorkersOfContactInfoId != null) {
                    oldWorkersOfContactInfoId.setContactInfoId(null);
                    oldWorkersOfContactInfoId = em.merge(oldWorkersOfContactInfoId);
                }
                contactInfoId.setWorkers(workers);
                contactInfoId = em.merge(contactInfoId);
            }
            if (workInfoId != null) {
                Workers oldWorkersOfWorkInfoId = workInfoId.getWorkers();
                if (oldWorkersOfWorkInfoId != null) {
                    oldWorkersOfWorkInfoId.setWorkInfoId(null);
                    oldWorkersOfWorkInfoId = em.merge(oldWorkersOfWorkInfoId);
                }
                workInfoId.setWorkers(workers);
                workInfoId = em.merge(workInfoId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Workers workers) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Workers persistentWorkers = em.find(Workers.class, workers.getId());
            AccountInfo accountInfoIdOld = persistentWorkers.getAccountInfoId();
            AccountInfo accountInfoIdNew = workers.getAccountInfoId();
            AccountSecurity accountSecurityIdOld = persistentWorkers.getAccountSecurityId();
            AccountSecurity accountSecurityIdNew = workers.getAccountSecurityId();
            ContactInfo contactInfoIdOld = persistentWorkers.getContactInfoId();
            ContactInfo contactInfoIdNew = workers.getContactInfoId();
            WorkInfo workInfoIdOld = persistentWorkers.getWorkInfoId();
            WorkInfo workInfoIdNew = workers.getWorkInfoId();
            if (accountInfoIdNew != null) {
                accountInfoIdNew = em.getReference(accountInfoIdNew.getClass(), accountInfoIdNew.getId());
                workers.setAccountInfoId(accountInfoIdNew);
            }
            if (accountSecurityIdNew != null) {
                accountSecurityIdNew = em.getReference(accountSecurityIdNew.getClass(), accountSecurityIdNew.getId());
                workers.setAccountSecurityId(accountSecurityIdNew);
            }
            if (contactInfoIdNew != null) {
                contactInfoIdNew = em.getReference(contactInfoIdNew.getClass(), contactInfoIdNew.getId());
                workers.setContactInfoId(contactInfoIdNew);
            }
            if (workInfoIdNew != null) {
                workInfoIdNew = em.getReference(workInfoIdNew.getClass(), workInfoIdNew.getId());
                workers.setWorkInfoId(workInfoIdNew);
            }
            workers = em.merge(workers);
            if (accountInfoIdOld != null && !accountInfoIdOld.equals(accountInfoIdNew)) {
                accountInfoIdOld.setWorkers(null);
                accountInfoIdOld = em.merge(accountInfoIdOld);
            }
            if (accountInfoIdNew != null && !accountInfoIdNew.equals(accountInfoIdOld)) {
                Workers oldWorkersOfAccountInfoId = accountInfoIdNew.getWorkers();
                if (oldWorkersOfAccountInfoId != null) {
                    oldWorkersOfAccountInfoId.setAccountInfoId(null);
                    oldWorkersOfAccountInfoId = em.merge(oldWorkersOfAccountInfoId);
                }
                accountInfoIdNew.setWorkers(workers);
                accountInfoIdNew = em.merge(accountInfoIdNew);
            }
            if (accountSecurityIdOld != null && !accountSecurityIdOld.equals(accountSecurityIdNew)) {
                accountSecurityIdOld.setWorkers(null);
                accountSecurityIdOld = em.merge(accountSecurityIdOld);
            }
            if (accountSecurityIdNew != null && !accountSecurityIdNew.equals(accountSecurityIdOld)) {
                Workers oldWorkersOfAccountSecurityId = accountSecurityIdNew.getWorkers();
                if (oldWorkersOfAccountSecurityId != null) {
                    oldWorkersOfAccountSecurityId.setAccountSecurityId(null);
                    oldWorkersOfAccountSecurityId = em.merge(oldWorkersOfAccountSecurityId);
                }
                accountSecurityIdNew.setWorkers(workers);
                accountSecurityIdNew = em.merge(accountSecurityIdNew);
            }
            if (contactInfoIdOld != null && !contactInfoIdOld.equals(contactInfoIdNew)) {
                contactInfoIdOld.setWorkers(null);
                contactInfoIdOld = em.merge(contactInfoIdOld);
            }
            if (contactInfoIdNew != null && !contactInfoIdNew.equals(contactInfoIdOld)) {
                Workers oldWorkersOfContactInfoId = contactInfoIdNew.getWorkers();
                if (oldWorkersOfContactInfoId != null) {
                    oldWorkersOfContactInfoId.setContactInfoId(null);
                    oldWorkersOfContactInfoId = em.merge(oldWorkersOfContactInfoId);
                }
                contactInfoIdNew.setWorkers(workers);
                contactInfoIdNew = em.merge(contactInfoIdNew);
            }
            if (workInfoIdOld != null && !workInfoIdOld.equals(workInfoIdNew)) {
                workInfoIdOld.setWorkers(null);
                workInfoIdOld = em.merge(workInfoIdOld);
            }
            if (workInfoIdNew != null && !workInfoIdNew.equals(workInfoIdOld)) {
                Workers oldWorkersOfWorkInfoId = workInfoIdNew.getWorkers();
                if (oldWorkersOfWorkInfoId != null) {
                    oldWorkersOfWorkInfoId.setWorkInfoId(null);
                    oldWorkersOfWorkInfoId = em.merge(oldWorkersOfWorkInfoId);
                }
                workInfoIdNew.setWorkers(workers);
                workInfoIdNew = em.merge(workInfoIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = workers.getId();
                if (findWorkers(id) == null) {
                    throw new NonexistentEntityException("The workers with id " + id + " no longer exists.");
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
            Workers workers;
            try {
                workers = em.getReference(Workers.class, id);
                workers.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The workers with id " + id + " no longer exists.", enfe);
            }
            AccountInfo accountInfoId = workers.getAccountInfoId();
            if (accountInfoId != null) {
                accountInfoId.setWorkers(null);
                accountInfoId = em.merge(accountInfoId);
            }
            AccountSecurity accountSecurityId = workers.getAccountSecurityId();
            if (accountSecurityId != null) {
                accountSecurityId.setWorkers(null);
                accountSecurityId = em.merge(accountSecurityId);
            }
            ContactInfo contactInfoId = workers.getContactInfoId();
            if (contactInfoId != null) {
                contactInfoId.setWorkers(null);
                contactInfoId = em.merge(contactInfoId);
            }
            WorkInfo workInfoId = workers.getWorkInfoId();
            if (workInfoId != null) {
                workInfoId.setWorkers(null);
                workInfoId = em.merge(workInfoId);
            }
            em.remove(workers);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Workers> findWorkersEntities() {
        return findWorkersEntities(true, -1, -1);
    }

    public List<Workers> findWorkersEntities(int maxResults, int firstResult) {
        return findWorkersEntities(false, maxResults, firstResult);
    }

    private List<Workers> findWorkersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Workers.class));
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

    public Workers findWorkers(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Workers.class, id);
        } finally {
            em.close();
        }
    }

    public int getWorkersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Workers> rt = cq.from(Workers.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
