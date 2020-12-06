package com.academy.persistence.specification;

import com.academy.model.SearchTaskDTO;
import com.academy.persistence.entity.Task;
import com.academy.persistence.entity.enums.Priority;
import com.academy.persistence.entity.enums.TaskStatus;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskSpecification {
    public Specification<Task> findAllSearchSpecification(SearchTaskDTO searchTaskDTO, String userProjectsId) {
        return new Specification<Task>() {
            @Override
            public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                root.fetch("user");
                root.fetch("project");

                Predicate predicate = null;
                if (!"".equals(searchTaskDTO.getProjectId())) {
                    predicate = criteriaBuilder.equal(root.get("project").get("id"), Long.valueOf(searchTaskDTO.getProjectId()));
                } else {
                    for (String itemId : userProjectsId.split(",")) {
                        if (predicate != null) {
                            predicate = criteriaBuilder.or(predicate, criteriaBuilder.equal(root.get("project").get("id"), Long.valueOf(itemId)));
                        } else {
                            predicate = criteriaBuilder.equal(root.get("project").get("id"), Long.valueOf(itemId));
                        }
                    }
                }
                if (!"".equals(searchTaskDTO.getName())) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("name"), searchTaskDTO.getName()));
                }
                if (!"".equals(searchTaskDTO.getPriority())) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("priority"), Priority.valueOf(searchTaskDTO.getPriority())));
                }
                if (!"".equals(searchTaskDTO.getTaskStatus())) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("taskStatus"), TaskStatus.valueOf(searchTaskDTO.getTaskStatus())));
                }
                if (!"".equals(searchTaskDTO.getFromDate())) {
                    try {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.<Date>get("date"), new SimpleDateFormat("yyyy-MM-dd").parse(searchTaskDTO.getFromDate())));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (!"".equals(searchTaskDTO.getToDate())) {
                    try {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.<Date>get("date"), new SimpleDateFormat("yyyy-MM-dd").parse(searchTaskDTO.getToDate())));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                return predicate;
            }
        };
    }
}
