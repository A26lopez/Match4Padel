package Controllers;

import Controllers.exceptions.NonexistentEntityException;
import Models.ContactInfo;
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

public class ContactInfoJpaController implements Serializable {

    public ContactInfoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ContactInfo contactInfo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Workers workers = contactInfo.getWorkers();
            if (workers != null) {
                workers = em.getReference(workers.getClass(), workers.getId());
                contactInfo.setWorkers(workers);
            }
            Users users = contactInfo.getUsers();
            if (users != null) {
                users = em.getReference(users.getClass(), users.getId());
                contactInfo.setUsers(users);
            }
            em.persist(contactInfo);
            if (workers != null) {
                ContactInfo oldContactInfoIdOfWorkers = workers.getContactInfoId();
                if (oldContactInfoIdOfWorkers != null) {
                    oldContactInfoIdOfWorkers.setWorkers(null);
                    oldContactInfoIdOfWorkers = em.merge(oldContactInfoIdOfWorkers);
                }
                workers.setContactInfoId(contactInfo);
                workers = em.merge(workers);
            }
            if (users != null) {
                ContactInfo oldContactInfoIdOfUsers = users.getContactInfoId();
                if (oldContactInfoIdOfUsers != null) {
                    oldContactInfoIdOfUsers.setUsers(null);
                    oldContactInfoIdOfUsers = em.merge(oldContactInfoIdOfUsers);
                }
                users.setContactInfoId(contactInfo);
                users = em.merge(users);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ContactInfo contactInfo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ContactInfo persistentContactInfo = em.find(ContactInfo.class, contactInfo.getId());
            Workers workersOld = persistentContactInfo.getWorkers();
            Workers workersNew = contactInfo.getWorkers();
            Users usersOld = persistentContactInfo.getUsers();
            Users usersNew = contactInfo.getUsers();
            if (workersNew != null) {
                workersNew = em.getReference(workersNew.getClass(), workersNew.getId());
                contactInfo.setWorkers(workersNew);
            }
            if (usersNew != null) {
                usersNew = em.getReference(usersNew.getClass(), usersNew.getId());
                contactInfo.setUsers(usersNew);
            }
            contactInfo = em.merge(contactInfo);
            if (workersOld != null && !workersOld.equals(workersNew)) {
                workersOld.setContactInfoId(null);
                workersOld = em.merge(workersOld);
            }
            if (workersNew != null && !workersNew.equals(workersOld)) {
                ContactInfo oldContactInfoIdOfWorkers = workersNew.getContactInfoId();
                if (oldContactInfoIdOfWorkers != null) {
                    oldContactInfoIdOfWorkers.setWorkers(null);
                    oldContactInfoIdOfWorkers = em.merge(oldContactInfoIdOfWorkers);
                }
                workersNew.setContactInfoId(contactInfo);
                workersNew = em.merge(workersNew);
            }
            if (usersOld != null && !usersOld.equals(usersNew)) {
                usersOld.setContactInfoId(null);
                usersOld = em.merge(usersOld);
            }
            if (usersNew != null && !usersNew.equals(usersOld)) {
                ContactInfo oldContactInfoIdOfUsers = usersNew.getContactInfoId();
                if (oldContactInfoIdOfUsers != null) {
                    oldContactInfoIdOfUsers.setUsers(null);
                    oldContactInfoIdOfUsers = em.merge(oldContactInfoIdOfUsers);
                }
                usersNew.setContactInfoId(contactInfo);
                usersNew = em.merge(usersNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contactInfo.getId();
                if (findContactInfo(id) == null) {
                    throw new NonexistentEntityException("The contactInfo with id " + id + " no longer exists.");
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
            ContactInfo contactInfo;
            try {
                contactInfo = em.getReference(ContactInfo.class, id);
                contactInfo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contactInfo with id " + id + " no longer exists.", enfe);
            }
            Workers workers = contactInfo.getWorkers();
            if (workers != null) {
                workers.setContactInfoId(null);
                workers = em.merge(workers);
            }
            Users users = contactInfo.getUsers();
            if (users != null) {
                users.setContactInfoId(null);
                users = em.merge(users);
            }
            em.remove(contactInfo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ContactInfo> findContactInfoEntities() {
        return findContactInfoEntities(true, -1, -1);
    }

    public List<ContactInfo> findContactInfoEntities(int maxResults, int firstResult) {
        return findContactInfoEntities(false, maxResults, firstResult);
    }

    private List<ContactInfo> findContactInfoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ContactInfo.class));
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

    public ContactInfo findContactInfo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ContactInfo.class, id);
        } finally {
            em.close();
        }
    }

    public int getContactInfoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ContactInfo> rt = cq.from(ContactInfo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
