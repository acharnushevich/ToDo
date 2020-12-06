package com.academy.persistence.repositories;

import com.academy.model.SearchUserDTO;
import com.academy.persistence.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    @Query("from User u where u.id <> :id")
    List<User> findAllWithOutId(@Param("id") Long id);

    @Query("from User u where u.email = :email and u.password = :password")
    User findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    boolean existsByEmail(String email);

    boolean existsByIdNotAndEmail(Long id, String email);

    @EntityGraph(attributePaths = {"tasks", "workLogs", "activities"})
    @Query("from User u where u.id = :id")
    User findFullById(@Param("id") Long id);
}
