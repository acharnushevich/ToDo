package com.academy.comparators;

import com.academy.persistence.entity.Project;

import java.util.Comparator;

public class ProjectsNameComparator implements Comparator<Project> {
    @Override
    public int compare(Project o1, Project o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
