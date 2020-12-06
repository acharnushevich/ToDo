package com.academy.comparators;

import com.academy.persistence.entity.WorkLog;

import java.util.Comparator;

public class WorkLogsDateComparator implements Comparator<WorkLog> {
    @Override
    public int compare(WorkLog o1, WorkLog o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}
