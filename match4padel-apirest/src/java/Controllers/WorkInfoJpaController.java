package Controllers;

import Controllers.exceptions.NonexistentEntityException;
import Models.WorkInfo;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import Models.Workers;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class WorkInfoJpaController implements Serializable {

    public WorkInfoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(WorkInfo workInfo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Workers workers = workInfo.getWorkers();
            if (workers != null) {
                workers = em.getReference(workers.getClass(), workers.getId());
                workInfo.setWorkers(workers);
            }
            em.persist(workInfo);
            if (workers != null) {
                WorkInfo oldWorkInfoIdOfWorkers = workers.getWorkInfoId();
                if (oldWorkInfoIdOfWorkers != null) {
                    oldWorkInfoIdOfWorkers.setWorkers(null);
                    oldWorkInfoIdOfWorkers = em.merge(oldWorkInfoIdOfWorkers);
                }
                workers.setWorkInfoId(workInfo);
                workers = em.merge(workers);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(WorkInfo workInfo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            WorkInfo persistentWorkInfo = em.find(WorkInfo.class, workInfo.getId());
            Workers workersOld = persistentWorkInfo.getWorkers();
            Workers workersNew = workInfo.getWorkers();
            if (workersNew != null) {
                workersNew = em.getReference(workersNew.getClass(), workersNew.getId());
                workInfo.setWorkers(workersNew);
            }
            workInfo = em.merge(workInfo);
            if (workersOld != null && !workersOld.equals(workersNew)) {
                workersOld.setWorkInfoId(null);
                workersOld = em.merge(workersOld);
            }
            if (workersNew != null && !workersNew.equals(workersOld)) {
                WorkInfo oldWorkInfoIdOfWorkers = workersNew.getWorkInfoId();
                if (oldWorkInfoIdOfWorkers != null) {
                    oldWorkInfoIdOfWorkers.setWorkers(null);
                    oldWorkInfoIdOfWorkers = em.merge(oldWorkInfoIdOfWorkers);
                }
                workersNew.setWorkInfoId(workInfo);
                workersNew = em.merge(workersNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = workInfo.getId();
                if (findWorkInfo(id) == null) {
                    throw new NonexistentEntityException("The workInfo with id " + id + " no longer exists.");
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
            WorkInfo workInfo;
            try {
                workInfo = em.getReference(WorkInfo.class, id);
                workInfo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The workInfo with id " + id + " no longer exists.", enfe);
            }
            Workers workers = workInfo.getWorkers();
            if (workers != null) {
                workers.setWorkInfoId(null);
                workers = em.merge(workers);
            }
            em.remove(workInfo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<WorkInfo> findWorkInfoEntities() {
        return findWorkInfoEntities(true, -1, -1);
    }

    public List<WorkInfo> findWorkInfoEntities(int maxResults, int firstResult) {
        return findWorkInfoEntities(false, maxResults, firstResult);
    }

    private List<WorkInfo> findWorkInfoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(WorkInfo.class));
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

    public WorkInfo findWorkInfo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(WorkInfo.class, id);
        } finally {
            em.close();
        }
    }

    public int getWorkInfoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<WorkInfo> rt = cq.from(WorkInfo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
