package com.academy.util;

import com.academy.model.*;
import com.academy.persistence.entity.enums.Priority;
import com.academy.persistence.entity.enums.Roles;
import com.academy.persistence.entity.enums.TaskStatus;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class ContentUtils {
    public String getParam(String parameter) {
        String out = "";
        if (parameter != null) {
            out = parameter;
        }
        return out;
    }
}
