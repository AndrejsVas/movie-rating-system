package com.avas.usertype.microservice.business.service.impl;


import com.avas.usertype.microservice.business.exceptions.ResourceAlreadyExists;
import com.avas.usertype.microservice.business.exceptions.ResourceNotFoundException;
import com.avas.usertype.microservice.business.mappers.UserTypeMapper;
import com.avas.usertype.microservice.business.repository.UserTypeRepository;
import com.avas.usertype.microservice.business.repository.model.UserType;
import com.avas.usertype.microservice.model.UserTypeDTO;
import com.avas.usertype.microservice.test.data.UserTypeTestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class UserTypeServiceImplTest {

    @Mock
    private UserTypeRepository userTypeRepository;

    @Autowired
    private UserTypeMapper userTypeMapper;
    @Mock
    private UserTypeMapper mockUserTypeMapper;

    @Spy
    @InjectMocks
    private UserTypeServiceImpl userTypeService;

    private UserTypeDTO userTypeDTO;
    private UserType userType;

    @BeforeEach
    public void createTestData() {
        this.userTypeDTO = UserTypeTestData.createUserTypeDto();
        this.userType = userTypeMapper.mapUserTypeDtoToUserType(userTypeDTO);
    }

    @Test
    @DisplayName("Retrieval of all UserTypes")
    public void testUserTypesSuccessfully() {
        List<UserTypeDTO> userTypeDtoList = UserTypeTestData.createUserTypeDtoList();
        List<UserType> movieTypeList = userTypeMapper.mapUserTypeDtoListToUserTypeList(userTypeDtoList);
        when(userTypeRepository.findAll()).thenReturn(movieTypeList);
        when(mockUserTypeMapper.mapUserTypeListToUserTypeListDto(movieTypeList)).thenReturn(userTypeDtoList);
        List<UserTypeDTO> movieTypeDtoListReturned = userTypeService.getAllUserTypes();
        Assertions.assertTrue(userTypeDtoList.equals(movieTypeDtoListReturned));
        verify(userTypeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Retrieval of empty UserType list")
    public void testGetAllUserTypeEmpty() {
        List<UserTypeDTO> emptyUserTypeDTOList = new ArrayList<UserTypeDTO>();
        List<UserType> emptyUserList = userTypeMapper.mapUserTypeDtoListToUserTypeList(emptyUserTypeDTOList);
        when(userTypeRepository.findAll()).thenReturn(emptyUserList);
        when(mockUserTypeMapper.mapUserTypeListToUserTypeListDto(emptyUserList)).thenReturn(emptyUserTypeDTOList);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> userTypeService.getAllUserTypes());
        verify(userTypeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Finding UserType by id")
    public void testSuccessfullyFindingUserTypeById() {

        when(mockUserTypeMapper.mapUserTypeToUserTypeDto(userType)).thenReturn(userTypeDTO);
        when(userTypeRepository.findById(anyLong())).thenReturn(Optional.of(userType));
        userTypeService.findUserTypeById(anyLong());
        verify(userTypeRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Find non existing UserType by id")
    public void testFailingFindUserTypeById() {
        when(userTypeRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> userTypeService.findUserTypeById(anyLong()));
    }

    @Test
    @DisplayName("Deleting UserType by id")
    public void testDeletingUserTypeById() {
        doReturn(Optional.of(userTypeDTO)).when(userTypeService).findUserTypeById(1L);
        userTypeService.deleteUserTypeById(1L);
        verify(userTypeRepository, times(1)).deleteById(anyLong());
    }

    @Test
    @DisplayName("Deleting when UserType does not exist")
    public void testDeleteUserTypeNotFound() {
        doReturn(Optional.empty()).when(userTypeService).findUserTypeById(1L);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> userTypeService.deleteUserTypeById(1L));
    }


    @Test
    @DisplayName("Create a UserType")
    public void testSuccessfullyCreatingAUserType() {
        when(userTypeRepository.existsByType(userTypeDTO.getType())).thenReturn(false);
        when(mockUserTypeMapper.mapUserTypeDtoToUserType(userTypeDTO)).thenReturn(userType);
        when(userTypeRepository.save(userType)).thenReturn(userType);
        userTypeService.createUserType(userTypeDTO);
        verify(userTypeRepository, times(1)).save(userType);
    }

    @Test
    @DisplayName("Create a duplicate UserType")
    public void testFailingCreatingAUserType() {
        when(userTypeRepository.existsByType(anyString())).thenReturn(true);
        Assertions.assertThrows(ResourceAlreadyExists.class, () -> userTypeService.createUserType(userTypeDTO));
    }

    @Test
    @DisplayName("Update a UserType by Id")
    public void testSuccessfullyUpdatingAUserTypeById() {

        when(userTypeRepository.existsById(anyLong())).thenReturn(true);
        when(userTypeRepository.existsByType(anyString())).thenReturn(false);
        when(mockUserTypeMapper.mapUserTypeDtoToUserType(any(UserTypeDTO.class))).thenReturn(userType);
        when(mockUserTypeMapper.mapUserTypeToUserTypeDto(any(UserType.class))).thenReturn(userTypeDTO);
        when(userTypeRepository.save(userType)).thenReturn(userType);
        UserTypeDTO returnedUserTypeDto = userTypeService.updateUserTypeById(userTypeDTO, 1L);
        Assertions.assertEquals(userTypeDTO, returnedUserTypeDto);
        verify(userTypeRepository, times(1)).save(userType);

    }

    @Test
    @DisplayName("Update UserType which does not exist")
    public void testFailUpdatingNonExistingUserType() {
        when(userTypeRepository.existsById(anyLong())).thenReturn(false);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> userTypeService.updateUserTypeById(userTypeDTO, 1L));
    }

    @Test
    @DisplayName(("Update a UserType with already taken email"))
    public void testFailUpdatingAUserTypeWhichDoesNotExist() {
        when(userTypeRepository.existsById(anyLong())).thenReturn(false);
        when(userTypeRepository.existsByType(anyString())).thenReturn(true);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> userTypeService.updateUserTypeById(userTypeDTO, 1L));
    }

    @Test
    @DisplayName("Delete a UserType")
    public void testSuccessfullyDeleteAUserType() {
        doReturn(Optional.of(userTypeDTO)).when(userTypeService).findUserTypeById(anyLong());
        userTypeService.deleteUserTypeById(1L);
        verify(userTypeRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Delete a non existing UserType")
    public void testFailingDeleteAUserType() {
        doReturn(Optional.empty()).when(userTypeService).findUserTypeById(anyLong());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> userTypeService.deleteUserTypeById(1L));
    }
}
