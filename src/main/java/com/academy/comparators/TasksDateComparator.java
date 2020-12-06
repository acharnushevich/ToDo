package com.academy.comparators;

import com.academy.persistence.entity.Task;

import java.util.Comparator;

public class TasksDateComparator implements Comparator<Task> {
    @Override
    public int compare(Task o1, Task o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}
