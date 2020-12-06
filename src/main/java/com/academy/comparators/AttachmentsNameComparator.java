package com.academy.comparators;

import com.academy.persistence.entity.Attachment;

import java.util.Comparator;

public class AttachmentsNameComparator implements Comparator<Attachment> {
    @Override
    public int compare(Attachment o1, Attachment o2) {
        return o1.getFileName().compareTo(o2.getFileName());
    }
}
