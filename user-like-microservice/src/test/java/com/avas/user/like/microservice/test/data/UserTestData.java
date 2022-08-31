package com.avas.user.like.microservice.test.data;

import main.java.com.avas.library.model.UserDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class UserTestData {

    private static final String[] randomEmailList = {"good", "bad", "decent", "better call soul", "outsanding"};
    private static final String[] randomNameList = {"good", "bad", "decent", "better call soul", "outsanding"};
    private static final String[] randomSurnameList = {"good", "bad", "decent", "better call soul", "outsanding"};

    private static final Random userId = new Random();
    private static final Random emailRandom = new Random();
    private static final Random nameRandom = new Random();
    private static final Random surnameRandom = new Random();
    private static final Random score = new Random();

    public static UserDTO createUserDto() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId.nextLong());
        userDTO.setEmail(randomEmailList[emailRandom.nextInt(randomEmailList.length)]);
        userDTO.setName(randomNameList[nameRandom.nextInt(randomNameList.length)]);
        userDTO.setSurname(randomSurnameList[surnameRandom.nextInt(randomSurnameList.length)]);
        return userDTO;
    }
    public static UserDTO createUserDtoPredefined() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setEmail("testEmail@mail.com");
        userDTO.setName("tester");
        userDTO.setSurname("mcTester");
        return userDTO;
    }

    public static List<UserDTO> createUserDtoList() {
        return Arrays.asList(createUserDto(), createUserDto(), createUserDto(), createUserDto(), createUserDto());
    }
    public static List<UserDTO> createUserDtoListPredefined() {
        return Arrays.asList(createUserDtoPredefined(), createUserDtoPredefined(), createUserDtoPredefined());
    }
}
