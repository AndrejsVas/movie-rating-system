package com.avas.user.microservice.model;


import com.avas.user.microservice.business.repository.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTypeDTO {

    private Long id;
    private String type;
    private List<User> users;

}
