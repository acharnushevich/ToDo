package com.academy.persistence.specification;

import com.academy.model.SearchProjectDTO;
import com.academy.persistence.entity.Project;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ProjectSpecification {
    public Specification<Project> findAllSearchSpecification(SearchProjectDTO searchProjectDTO) {
        return new Specification<Project>() {
            @Override
            public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                Predicate predicate = criteriaBuilder.like(root.get("name"), "%" + searchProjectDTO.getName() + "%");

                return predicate;
            }
        };
    }
}
