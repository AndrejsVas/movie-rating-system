package com.avas.movieratingsystem.test.data;

import com.avas.movieratingsystem.model.UserDTO;

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

    public static List<UserDTO> createUserDtoList() {
        return Arrays.asList(createUserDto(), createUserDto(), createUserDto(), createUserDto(), createUserDto());
    }
}
