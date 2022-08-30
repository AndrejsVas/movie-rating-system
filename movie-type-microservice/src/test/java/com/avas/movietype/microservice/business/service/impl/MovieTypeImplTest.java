package com.avas.movietype.microservice.business.service.impl;


import com.avas.movietype.microservice.business.repository.MovieTypeRepository;
import com.avas.movietype.microservice.test.data.MovieTypeTestData;
import main.com.avas.library.business.exceptions.ResourceAlreadyExists;
import main.com.avas.library.business.exceptions.ResourceNotFoundException;
import main.com.avas.library.business.mappers.MovieTypeMapping;
import main.com.avas.library.business.repository.model.MovieType;
import main.com.avas.library.model.MovieTypeDTO;
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
public class MovieTypeImplTest {

    @Mock
    private MovieTypeRepository movieTypeRepository;

    @Autowired
    private MovieTypeMapping movieTypeMapping;
    @Mock
    private MovieTypeMapping mockMovieTypeMapping;

    @Spy
    @InjectMocks
    private MovieTypeServiceImpl movieTypeService;

    private MovieTypeDTO movieTypeDTO;
    private MovieType movieType;

    @BeforeEach
    public void createTestData() {
        this.movieTypeDTO = MovieTypeTestData.createMovieTypeDto();
        this.movieType = movieTypeMapping.mapMovieTypeDtoToMovieType(movieTypeDTO);
    }

    @Test
    @DisplayName("Retrieval of all MovieTypes")
    public void testMovieTypeSuccessfully() {
        List<MovieTypeDTO> userDtoList = MovieTypeTestData.createMovieTypeDtoList();
        List<MovieType> movieTypeList = movieTypeMapping.mapMovieTypeListDtoToMovieTypeList(userDtoList);
        when(movieTypeRepository.findAll()).thenReturn(movieTypeList);
        when(mockMovieTypeMapping.mapMovieTypeListToMovieTypeListDto(movieTypeList)).thenReturn(userDtoList);
        List<MovieTypeDTO> movieTypeDtoListReturned = movieTypeService.getAllMovieTypes();
        Assertions.assertTrue(userDtoList.equals(movieTypeDtoListReturned));
        verify(movieTypeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Retrieval of empty movie type list")
    public void testGetAllMovieTypeEmpty() {
        List<MovieTypeDTO> emptyMovieTypeDTOList = new ArrayList<MovieTypeDTO>();
        List<MovieType> emptyUserList = movieTypeMapping.mapMovieTypeListDtoToMovieTypeList(emptyMovieTypeDTOList);
        when(movieTypeRepository.findAll()).thenReturn(emptyUserList);
        when(mockMovieTypeMapping.mapMovieTypeListToMovieTypeListDto(emptyUserList)).thenReturn(emptyMovieTypeDTOList);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> movieTypeService.getAllMovieTypes());
        verify(movieTypeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Finding movieType by id")
    public void testSuccessfullyFindingMovieTypeById() {

        when(mockMovieTypeMapping.mapMovieTypeToMovieTypeDto(movieType)).thenReturn(movieTypeDTO);
        when(movieTypeRepository.findById(anyLong())).thenReturn(Optional.of(movieType));
        movieTypeService.findMovieTypeById(anyLong());
        verify(movieTypeRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Find non existing movie type by id")
    public void testFailingFindMovieTypeById() {
        when(movieTypeRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> movieTypeService.findMovieTypeById(anyLong()));
    }

    @Test
    @DisplayName("Deleting movie type by id")
    public void testDeletingUserById() {
        doReturn(Optional.of(movieTypeDTO)).when(movieTypeService).findMovieTypeById(1L);
        movieTypeService.deleteMovieTypeById(1L);
        verify(movieTypeRepository, times(1)).deleteById(anyLong());
    }

    @Test
    @DisplayName("Deleting when movie type does not exist")
    public void testDeleteUserNotFound() {
        doReturn(Optional.empty()).when(movieTypeService).findMovieTypeById(1L);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> movieTypeService.deleteMovieTypeById(1L));
    }


    @Test
    @DisplayName("Create a movie type")
    public void testSuccessfullyCreatingAMovieType() {
        when(movieTypeRepository.existsByType(movieTypeDTO.getType())).thenReturn(false);
        when(mockMovieTypeMapping.mapMovieTypeDtoToMovieType(movieTypeDTO)).thenReturn(movieType);
        when(movieTypeRepository.save(movieType)).thenReturn(movieType);
        movieTypeService.createMovieType(movieTypeDTO);
        verify(movieTypeRepository, times(1)).save(movieType);
    }

    @Test
    @DisplayName("Create a duplicate movie type")
    public void testFailingCreatingAMovieType() {
        when(movieTypeRepository.existsByType(anyString())).thenReturn(true);
        Assertions.assertThrows(ResourceAlreadyExists.class, () -> movieTypeService.createMovieType(movieTypeDTO));
    }

    @Test
    @DisplayName("Update a movie type by Id")
    public void testSuccessfullyUpdatingAMovieTypeById() {

        when(movieTypeRepository.existsById(anyLong())).thenReturn(true);
        when(movieTypeRepository.existsByType(anyString())).thenReturn(false);
        when(mockMovieTypeMapping.mapMovieTypeDtoToMovieType(any(MovieTypeDTO.class))).thenReturn(movieType);
        when(mockMovieTypeMapping.mapMovieTypeToMovieTypeDto(any(MovieType.class))).thenReturn(movieTypeDTO);
        when(movieTypeRepository.save(movieType)).thenReturn(movieType);
        MovieTypeDTO returnedMovieTypeDto = movieTypeService.updateMovieTypeById(movieTypeDTO, 1L);
        Assertions.assertEquals(movieTypeDTO, returnedMovieTypeDto);
        verify(movieTypeRepository, times(1)).save(movieType);

    }

    @Test
    @DisplayName("Update movie type which does not exist")
    public void testFailUpdatingNonExistingMovieType() {
        when(movieTypeRepository.existsById(anyLong())).thenReturn(false);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> movieTypeService.updateMovieTypeById(movieTypeDTO, 1L));
    }

    @Test
    @DisplayName(("Update a movie with already taken email"))
    public void testFailUpdatingAMovieTypeWhichDoesNotExist() {
        when(movieTypeRepository.existsById(anyLong())).thenReturn(false);
        when(movieTypeRepository.existsByType(anyString())).thenReturn(true);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> movieTypeService.updateMovieTypeById(movieTypeDTO, 1L));
    }

    @Test
    @DisplayName("Delete a MovieType")
    public void testSuccessfullyDeleteAMovieType() {
        doReturn(Optional.of(movieTypeDTO)).when(movieTypeService).findMovieTypeById(anyLong());
        movieTypeService.deleteMovieTypeById(1L);
        verify(movieTypeRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Delete a non existing MovieType")
    public void testFailingDeleteAMovieType() {
        doReturn(Optional.empty()).when(movieTypeService).findMovieTypeById(anyLong());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> movieTypeService.deleteMovieTypeById(1L));
    }
}
