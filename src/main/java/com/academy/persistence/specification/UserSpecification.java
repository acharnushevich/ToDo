package com.academy.persistence.specification;

import com.academy.model.SearchUserDTO;
import com.academy.persistence.entity.User;
import com.academy.persistence.entity.enums.Roles;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserSpecification {
    public Specification<User> findAllSearchIdNotSpecification(Long id, SearchUserDTO searchUserDTO) {
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                Predicate predicate = criteriaBuilder.notEqual(root.get("id"), id);
                if (!"".equals(searchUserDTO.getEmail())) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("email"), "%" + searchUserDTO.getEmail() + "%"));
                }
                if (!"".equals(searchUserDTO.getName())) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("name"), "%" + searchUserDTO.getName() + "%"));
                }
                if (!"".equals(searchUserDTO.getSurname())) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("surname"), "%" + searchUserDTO.getSurname() + "%"));
                }
                if (!"".equals(searchUserDTO.getRole())) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("role"), Roles.valueOf(searchUserDTO.getRole())));
                }

                if (searchUserDTO.getProfileEnable() != null && !"".equals(searchUserDTO.getProfileEnable())) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("profileEnable"), true));
                } else {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("profileEnable"), false));
                }

                return predicate;
            }
        };
    }
}