package com.academy.persistence.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractCrudDao<T> implements ProjectDeleteDao<T> {

    private final EntityManagerFactory entityManagerFactory;

    public AbstractCrudDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private final Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    protected EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public T create(T t) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(t);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return t;
    }

    public T findById(long id) {
        EntityManager em = getEntityManager();
        T t = null;
        try {
            t = em.find(clazz, id);
        } catch (Exception e) {
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return t;
    }

    public T save(T t) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            t = em.merge(t);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return t;
    }

    @Override
    public void deleteById(long id) {

        EntityManager em = getEntityManager();
        T t;
        try {
            t = em.find(clazz, id);

            em.getTransaction().begin();
            em.remove(t);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public List<T> findAll() {
        EntityManager em = getEntityManager();
        List<T> list = null;
        try {
            list = em.createQuery(String.format("select t from %s t", clazz.getName()), clazz)
                    .getResultList();
        } catch (Exception e) {
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return list;
    }

    protected T getSingleResultByQuery(String query) {
        EntityManager em = getEntityManager();
        T t = null;
        try {
            t = em.createQuery(query, clazz).getSingleResult();
        } catch (Exception e) {
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return t;
    }

    protected List<T> getResultListByQuery(String query) {
        EntityManager em = getEntityManager();
        List<T> list = null;
        try {
            list = em.createQuery(query, clazz).getResultList();
        } catch (Exception e) {
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return list;
    }
}
