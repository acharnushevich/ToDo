package com.academy.persistence.dao;

import com.academy.persistence.entity.Project;
import com.academy.persistence.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectDaoImpl extends AbstractCrudDao<Project> implements ProjectDeleteDao<Project> {

    public ProjectDaoImpl(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Override
    public void deleteById(long id) {
        EntityManager em = getEntityManager();
        Project project;

        List<User> userUpdateList = new ArrayList<>();
        List<User> userList;

        try {
            project = em.find(Project.class, id);

            userList = em.createQuery("select u from User u", User.class).getResultList();
            for (User user : userList) {
                String projectsId = "";
                for (String itemId : user.getProjectsId().split(",")) {
                    if (id == Long.valueOf(itemId)) {
                        userUpdateList.add(user);
                    } else {
                        projectsId += itemId + ",";
                    }
                }
                user.setProjectsId(projectsId);
            }

            em.getTransaction().begin();
            em.remove(project);

            for (User user : userUpdateList) {
                em.merge(user);
            }
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
}
