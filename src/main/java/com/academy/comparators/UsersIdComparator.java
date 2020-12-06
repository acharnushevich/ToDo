package com.academy.comparators;

import com.academy.persistence.entity.User;

import java.util.Comparator;

public class UsersIdComparator implements Comparator<User> {
    @Override
    public int compare(User o1, User o2) {
        int out = 0;

        if (o1.getId() > o2.getId()) {
            out = 1;
        } else if (o1.getId() < o2.getId()) {
            out = -1;
        }

        return out;
    }
}
