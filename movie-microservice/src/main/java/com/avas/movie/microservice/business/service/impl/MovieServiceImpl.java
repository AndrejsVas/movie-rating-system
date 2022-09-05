package com.avas.movie.microservice.business.service.impl;

import com.avas.movie.microservice.business.repository.MovieRepository;
import com.avas.movie.microservice.business.service.MovieService;
import lombok.extern.log4j.Log4j2;
import com.avas.library.business.exceptions.ResourceAlreadyExists;
import com.avas.library.business.exceptions.ResourceNotFoundException;
import com.avas.library.business.mappers.MovieMapping;
import com.avas.library.business.repository.model.Movie;
import com.avas.library.model.MovieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieMapping movieMapper;

    @Override
    public List<MovieDTO>  getTopTenMovies() {
        Map<MovieDTO, Long> top10 = new HashMap<MovieDTO, Long>();
        List<MovieDTO> movieDTOList = getAllMovies();
        for(MovieDTO movieDTO : movieDTOList){
            top10.put(movieDTO, movieRepository.countChildrenByParent(movieMapper.mapMovieDtoToMovie(movieDTO))
            );
        }
        Map<MovieDTO,Long> topTen = top10.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .limit(10)
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        return topTen.keySet().stream().collect(Collectors.toList());
    }

    public List<MovieDTO> getAllMovies() {
        List<Movie> returnedMovieList = movieRepository.findAll();
        if(returnedMovieList.isEmpty())
            throw new ResourceNotFoundException("No reviews found");
        log.info("movie list size is :{}", returnedMovieList.size());
        return movieMapper.mapMovieListToMovieListDto(returnedMovieList);

    }

    public Optional<MovieDTO> findMovieById(Long id) {
        Optional<MovieDTO> foundMovieDto = movieRepository.findById(id)
                .map(foundUser -> movieMapper.mapMovieToMovieDto(foundUser));
        foundMovieDto.orElseThrow(() -> new ResourceNotFoundException("Movie with id:{0} Not found" , id));
        log.info("Found movie :{}", foundMovieDto);
        return foundMovieDto;
    }

    public void deleteMovieById(Long id) {
        findMovieById(id)
                .orElseThrow(() -> new ResourceAlreadyExists("Movie for delete with id {0} is not found.", id));
        movieRepository.deleteById(id);
        log.info("Movie with id: {} is deleted", id);

    }

    public MovieDTO createMovie(MovieDTO newMovie) {
        boolean movieAlreadyExists = movieRepository.existsByTitle(newMovie.getTitle());
        if(movieAlreadyExists){
            log.warn("Can not create movie, movie with this title already exists");
            throw new ResourceAlreadyExists("Can not create movie, movie with this title already exists");
        }
        Movie savedMovie = movieRepository.save(movieMapper.mapMovieDtoToMovie(newMovie));
        log.info("Movie is created : {}", savedMovie);
        return movieMapper.mapMovieToMovieDto(savedMovie);
    }

    public MovieDTO updateMovieById(MovieDTO modifyExistingMovie, Long id) {
        boolean movieAlreadyExists = movieRepository.existsByTitle(modifyExistingMovie.getTitle());
        if(movieAlreadyExists){
            log.warn("Can not update movie. This movie title is already taken :{}", modifyExistingMovie.getMovieType());
            throw new ResourceAlreadyExists("Can not update movie. This movie title is already taken");
        }
        if(!movieRepository.existsById(id)){
            throw new ResourceAlreadyExists("Movie with this id:{0} does not exist", id);
        }
        modifyExistingMovie.setId(id);
        Movie modifiedMovie = movieRepository.save(movieMapper.mapMovieDtoToMovie(modifyExistingMovie));
        log.info("Movie is updated movie id :{}, movie is now :{}", modifiedMovie.getId(), modifiedMovie);
        return movieMapper.mapMovieToMovieDto(modifiedMovie);
    }

}
