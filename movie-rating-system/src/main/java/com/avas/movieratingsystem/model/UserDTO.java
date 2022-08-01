package com.avas.movieratingsystem.model;

import com.avas.movieratingsystem.business.repository.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private UserType userType;
}
