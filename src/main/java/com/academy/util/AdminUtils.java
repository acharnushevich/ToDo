package com.academy.util;

import com.academy.model.*;
import com.academy.persistence.entity.enums.Roles;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdminUtils {
    public List<RoleDTO> getDictionaryRoles() {
        RoleDTO roleItem;
        List<RoleDTO> roleList = new ArrayList<>();

        for (Roles itemDao : Roles.values()) {
            roleItem = new RoleDTO();
            roleItem.setId(itemDao.name());
            roleItem.setName(itemDao.name());
            roleList.add(roleItem);
        }
        return roleList;
    }
}
