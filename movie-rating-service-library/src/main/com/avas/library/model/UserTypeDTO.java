package main.com.avas.library.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.com.avas.library.business.repository.model.User;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTypeDTO {

    private Long id;
    private String type;
    private List<User> users;

}
