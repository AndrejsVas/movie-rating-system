package com.avas.usertype.microservice.web.controller;

import com.avas.usertype.microservice.business.service.UserTypeService;
import com.avas.usertype.microservice.controller.UserTypeController;
import com.avas.usertype.microservice.test.data.UserTypeTestData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.avas.library.business.exceptions.ResourceAlreadyExists;
import com.avas.library.business.exceptions.ResourceConflict;
import com.avas.library.business.exceptions.ResourceNotFoundException;
import com.avas.library.model.UserTypeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserTypeController.class)
public class UserTypeControllerTest {
    public static String URL = "/api/v1/user_type";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserTypeController userTypeController;

    @MockBean
    private UserTypeService userTypeService;

    private List<UserTypeDTO> userTypeDTOSPredefined;
    private UserTypeDTO userTypeDto;

    @BeforeEach
    public void beforeEach() {
        this.userTypeDTOSPredefined = UserTypeTestData.createUserTypeDtoListPredefined();
        this.userTypeDto = UserTypeTestData.createUserTypeDtoPredefined();
    }

    @Test
    @DisplayName("Test endpoint to find all UserTypes successfully")
    public void findAllUserTypes() throws Exception {
        when(userTypeService.getAllUserTypes()).thenReturn(userTypeDTOSPredefined);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].type").value("admin"))
                .andExpect(status().isOk());
        verify(userTypeService, times(1)).getAllUserTypes();
    }

    @Test
    @DisplayName("Test endpoint to successfully finding a UserType by id")
    public void findUserTypeById() throws Exception {
        when(userTypeService.findUserTypeById(anyLong())).thenReturn(Optional.of(userTypeDto));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("admin"))
                .andExpect(status().isOk());
        verify(userTypeService, times(1)).findUserTypeById(1L);
    }

    @Test
    @DisplayName("Test endpoint to find a non existing UserType by id")
    public void findNonExistingUserTypeById() throws Exception {
        when(userTypeService.findUserTypeById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/1"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(status().isNotFound());
        verify(userTypeService, times(1)).findUserTypeById(1L);
    }

    @Test
    @DisplayName("Test endpoint to find empty UserTypes list")
    public void findAllUserTypesEmpty() throws Exception {
        when(userTypeService.getAllUserTypes()).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(status().isNotFound());
        verify(userTypeService, times(1)).getAllUserTypes();

    }

    @Test
    @DisplayName("Test endpoint to create UserType")
    public void createAUserType() throws Exception {
        userTypeDto.setId(null);

        when(userTypeService.createUserType(userTypeDto)).thenReturn(userTypeDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .content(new ObjectMapper().writeValueAsString(userTypeDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("admin"));

        verify(userTypeService, times(1)).createUserType(userTypeDto);


    }

    @Test
    @DisplayName("Test endpoint to create a duplicate  UserType")
    public void createAUserTypeDuplicate() throws Exception {
        userTypeDto.setId(null);

        when(userTypeService.createUserType(userTypeDto)).thenThrow(ResourceAlreadyExists.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .content(new ObjectMapper().writeValueAsString(userTypeDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());

        verify(userTypeService, times(1)).createUserType(userTypeDto);
    }

    @Test
    @DisplayName("Test endpoint to update a UserType")
    public void updateAUserType() throws Exception {
        when(userTypeService.updateUserTypeById(userTypeDto, 1L)).thenReturn(userTypeDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL + "/1")
                        .content(new ObjectMapper().writeValueAsString(userTypeDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("admin"));
        verify(userTypeService, times(1)).updateUserTypeById(userTypeDto, 1L);
    }

    @Test
    @DisplayName("Test endpoint to update a UserType which does not exist")
    public void updateANonExistingUserType() throws Exception {
        when(userTypeService.updateUserTypeById(userTypeDto, 1L)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL + "/1")
                        .content(new ObjectMapper().writeValueAsString(userTypeDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(status().isNotFound());
        verify(userTypeService, times(1)).updateUserTypeById(userTypeDto, 1L);
    }

    @Test
    @DisplayName("Test endpoint to update a UserType with taken email")
    public void updateANonUserTypeWithTakenType() throws Exception {
        when(userTypeService.updateUserTypeById(userTypeDto, 1L)).thenThrow(ResourceConflict.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL + "/1")
                        .content(new ObjectMapper().writeValueAsString(userTypeDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceConflict))
                .andExpect(status().isConflict());
        verify(userTypeService, times(1)).updateUserTypeById(userTypeDto, 1L);
    }

    @Test
    @DisplayName("Test endpoint to successfully deleting a UserType by id")
    public void deleteUserTypeById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(userTypeService, times(1)).deleteUserTypeById(1L);
    }


}
