package com.avas.movieratingsystem.test.data;

import com.avas.movieratingsystem.model.UserTypeDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class UserTypeTestData {

    private static final String[] randomUserTypeList = {"user", "admin", "moderator"};

    private static final Random userTypeId = new Random();
    private static final Random randomUserType = new Random();


    public static UserTypeDTO createUserTypeDto() {
        UserTypeDTO userTypeDTO = new UserTypeDTO();
        userTypeDTO.setId(userTypeId.nextLong());
        userTypeDTO.setType(randomUserTypeList[randomUserType.nextInt(randomUserTypeList.length)]);

        return userTypeDTO;
    }
    public static UserTypeDTO createUserTypeDtoPredefined() {
        UserTypeDTO userTypeDTO = new UserTypeDTO();
        userTypeDTO.setId(1L);
        userTypeDTO.setType("admin");

        return userTypeDTO;
    }

    public static List<UserTypeDTO> createUserTypeDtoList() {
        return Arrays.asList(createUserTypeDto(), createUserTypeDto(), createUserTypeDto(), createUserTypeDto(), createUserTypeDto());
    }
    public static List<UserTypeDTO> createUserTypeDtoListPredefined() {
        return Arrays.asList(createUserTypeDtoPredefined(), createUserTypeDtoPredefined(), createUserTypeDtoPredefined());
    }
}
