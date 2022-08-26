package com.avas.movieratingsystem.business.service.impl;


        import com.avas.movieratingsystem.business.exceptions.ResourceAlreadyExists;
        import com.avas.movieratingsystem.business.exceptions.ResourceNotFoundException;
        import com.avas.movieratingsystem.business.mappers.UserMapping;
        import com.avas.movieratingsystem.business.repository.UserRepository;
        import com.avas.movieratingsystem.business.repository.model.User;
        import com.avas.movieratingsystem.model.UserDTO;
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

        import static com.avas.movieratingsystem.test.data.UserTestData.createUserDto;
        import static com.avas.movieratingsystem.test.data.UserTestData.createUserDtoList;
        import static org.mockito.ArgumentMatchers.any;
        import static org.mockito.ArgumentMatchers.anyLong;
        import static org.mockito.ArgumentMatchers.anyString;
        import static org.mockito.Mockito.doReturn;
        import static org.mockito.Mockito.times;
        import static org.mockito.Mockito.verify;
        import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Autowired
    private UserMapping userMapping;
    @Mock
    private UserMapping mockUserMapping;

    @Spy
    @InjectMocks
    private UserServiceImpl userService;

    private UserDTO userDTO;
    private User user;

    @BeforeEach
    public void createTestData(){
        this.userDTO = createUserDto();
        this.user = userMapping.mapUserDtoToUser(userDTO);
    }
    @Test
    @DisplayName("Retrieval of all Users")
    public void testMoviesSuccessfully() {
        List<UserDTO> userDtoList =createUserDtoList();
        List<User> userList = userMapping.mapUserListDtoToUserList(userDtoList);
        when(userRepository.findAll()).thenReturn(userList);
        when(mockUserMapping.mapUserListToUserDto(userList)).thenReturn(userDtoList);
        List<UserDTO> userDtoListReturned = userService.getAllUsers();
        Assertions.assertTrue(userDtoList.equals(userDtoListReturned));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Retrieval of empty User list")
    public void testGetAllUserEmpty(){
        List<UserDTO> emptyUserDtoList = new ArrayList<UserDTO>();
        List<User> emptyUserList = userMapping.mapUserListDtoToUserList(emptyUserDtoList);
        when(userRepository.findAll()).thenReturn(emptyUserList);
        when(mockUserMapping.mapUserListToUserDto(emptyUserList)).thenReturn(emptyUserDtoList);
        Assertions.assertThrows(ResourceNotFoundException.class, ()-> userService.getAllUsers());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deleting user by id")
    public void testDeletingUserById(){
        doReturn(Optional.of(userDTO)).when(userService).findUserById(1L);
        userService.deleteUserById(1L);
        verify(userRepository, times(1)).deleteById(anyLong());
    }

    @Test
    @DisplayName("Deleting when user does not exist")
    public void testDeleteByIdNull(){
        doReturn(Optional.empty()).when(userService).findUserById(1L);
        Assertions.assertThrows(ResourceNotFoundException.class,() -> userService.deleteUserById(1L));
    }

    @Test
    @DisplayName("Finding user by id")
    public void testSuccessfullyFindingUserById(){

        when(mockUserMapping.mapUserToUserDto(user)).thenReturn(userDTO);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        userService.findUserById(anyLong());
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Find non existing user by id")
    public void testFailingFindUserById(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class , () -> userService.findUserById(anyLong()));
    }

    @Test
    @DisplayName("Create a user")
    public void testSuccessfullyCreatingAUser(){
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(false);
        when(mockUserMapping.mapUserDtoToUser(userDTO)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        userService.createUser(userDTO);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    @DisplayName("Create a duplicate user")
    public void testFailingCreatingAUser(){
        when(userRepository.existsByEmail(anyString())).thenReturn(true);
        Assertions.assertThrows(ResourceAlreadyExists.class, ()-> userService.createUser(userDTO));
    }

    @Test
    @DisplayName("Update a user by Id")
    public void testSuccessfullyUpdatingAUserById(){

        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(mockUserMapping.mapUserDtoToUser(any(UserDTO.class))).thenReturn(user);
        when(mockUserMapping.mapUserToUserDto(any(User.class))).thenReturn(userDTO);
        when(userRepository.save(user)).thenReturn(user);
        UserDTO returnedUserDto = userService.updateUser(userDTO, 1L);
        Assertions.assertEquals(userDTO,returnedUserDto);
        verify(userRepository, times(1)).save(user);

    }

    @Test
    @DisplayName("Update user which does not exist")
    public void testFailUpdatingNonExistingUser(){
        when(userRepository.existsById(anyLong())).thenReturn(false);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(userDTO, 1L));
    }

    @Test
    @DisplayName(("Update a user with already taken email"))
    public void testFailUpdatingAMovieWhichDoesNotExist(){
        when(userRepository.existsById(anyLong())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(true);
        Assertions.assertThrows(ResourceNotFoundException.class, ()-> userService.updateUser(userDTO, 1L));
    }

    @Test
    @DisplayName("Delete a User")
    public void testSuccessfullyDeleteAUser(){
        doReturn(Optional.of(userDTO)).when(userService).findUserById(anyLong());
        userService.deleteUserById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Delete a non existing user")
    public void testFailingDeleteAUser(){
        doReturn(Optional.empty()).when(userService).findUserById(anyLong());
        Assertions.assertThrows(ResourceNotFoundException.class , ()-> userService.deleteUserById(1L));
    }
}
