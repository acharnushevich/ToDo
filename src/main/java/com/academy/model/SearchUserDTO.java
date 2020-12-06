package com.academy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchUserDTO {
    String email;
    String name;
    String surname;
    String role;
    String profileEnable;
}
