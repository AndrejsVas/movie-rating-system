package com.avas.movieratingsystem.business.service.impl;

import com.avas.movieratingsystem.business.exceptions.ResourceAlreadyExists;
import com.avas.movieratingsystem.business.exceptions.ResourceNotFoundException;
import com.avas.movieratingsystem.business.mappers.MovieTypeMapping;
import com.avas.movieratingsystem.business.repository.MovieTypeRepository;
import com.avas.movieratingsystem.business.repository.model.MovieType;
import com.avas.movieratingsystem.business.service.MovieTypeService;
import com.avas.movieratingsystem.model.MovieTypeDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class MovieTypeServiceImpl implements MovieTypeService {
    @Autowired
    MovieTypeRepository movieTypeRepository;

    @Autowired
    MovieTypeMapping movieTypeMapping;
    public List<MovieTypeDTO> getAllMovieTypes() {
        return movieTypeRepository.findAll().stream().map(movieTypeMapping::mapMovieTypeToMovieTypeDto).collect(Collectors.toList());

    }

    public Optional<MovieTypeDTO> findMovieTypeById(Long id) {
        Optional<MovieTypeDTO> movieTypeDTO = movieTypeRepository.findById(id)
                .map(movieType -> movieTypeMapping.mapMovieTypeToMovieTypeDto(movieType));
        if (!movieTypeDTO.isPresent()) {
            log.warn("movie type with id:{} Not found", id);
            throw new ResourceNotFoundException("movie type with id:" + id + " does not exist");
        }
        log.info("Found movie type :{}", movieTypeDTO);
        return movieTypeDTO;
    }

    public void deleteMovieTypeById(Long id) {
        movieTypeRepository.deleteById(id);
        log.info("movie type with id: {} is deleted", id);
    }

    public MovieTypeDTO createMovieType(MovieTypeDTO newMovieType) {
        boolean movieTypeAlreadyExists = movieTypeRepository.existsByType(newMovieType.getType());
        if(movieTypeAlreadyExists){
            log.warn("Can not create movie type ,  movie type  already exists");
            throw new ResourceAlreadyExists("Can not create movie type , movie type  already exists");
        }
        MovieType savedMovieType = movieTypeRepository.save(movieTypeMapping.mapMovieTypeDtoToMovieType(newMovieType));
        log.info("movie type  is created : {}", savedMovieType);
        return movieTypeMapping.mapMovieTypeToMovieTypeDto(savedMovieType);
    }

    public MovieTypeDTO updateMovieTypeById(MovieTypeDTO modifyExistingMovieType, Long id) {
        boolean movieTypeAlreadyExists = movieTypeRepository.existsByType(modifyExistingMovieType.getType());
        if(movieTypeAlreadyExists){
            log.warn("Can not update movie type. This movie type is already taken :{}", modifyExistingMovieType.getType());
            throw new ResourceAlreadyExists("Can not update movie type. This movie type is already taken");
        }
        modifyExistingMovieType.setId(id);
        MovieType modifiedMovieType = movieTypeRepository.save(movieTypeMapping.mapMovieTypeDtoToMovieType(modifyExistingMovieType));
        log.info("movie type is updated id :{}, movie type is now :{}", modifiedMovieType.getId(), modifiedMovieType);
        return movieTypeMapping.mapMovieTypeToMovieTypeDto(modifiedMovieType);
    }

}
