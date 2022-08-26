package com.avas.movieratingsystem.business.service.impl;

import com.avas.movieratingsystem.business.exceptions.ResourceAlreadyExists;
import com.avas.movieratingsystem.business.exceptions.ResourceNotFoundException;
import com.avas.movieratingsystem.business.mappers.MovieMapping;
import com.avas.movieratingsystem.business.repository.MovieRepository;
import com.avas.movieratingsystem.business.repository.model.Movie;
import com.avas.movieratingsystem.business.service.MovieService;
import com.avas.movieratingsystem.model.MovieDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieMapping movieMapper;
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
