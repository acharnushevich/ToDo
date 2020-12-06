package com.academy.comparators;

import com.academy.persistence.entity.Activity;

import java.util.Comparator;

public class ActivitiesDateComparator implements Comparator<Activity> {
    @Override
    public int compare(Activity o1, Activity o2) {
        return o2.getDate().compareTo(o1.getDate());
    }
}

