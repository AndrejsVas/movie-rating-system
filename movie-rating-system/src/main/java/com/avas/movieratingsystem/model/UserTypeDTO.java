package com.avas.movieratingsystem.model;


import com.avas.movieratingsystem.business.repository.model.User;
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
    private List<Long> userIds;

}
