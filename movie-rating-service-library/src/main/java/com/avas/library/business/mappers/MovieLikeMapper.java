package com.avas.library.business.mappers;

import com.avas.library.business.repository.model.Movie;
import com.avas.library.business.repository.model.MovieLike;
import com.avas.library.business.repository.model.User;
import com.avas.library.model.MovieLikeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieLikeMapper {

    @Mappings({
            @Mapping(source = "userId", target = "userId", qualifiedByName = "userIdToUserIdLong"),
            @Mapping(source = "movieId", target = "movieId", qualifiedByName = "movieToMovieIdLong")
    })
    MovieLikeDTO mapMovieLikeToMovieLikeDto(MovieLike userLike);

    @Mappings({
            @Mapping(source = "userId", target = "userId", qualifiedByName = "userIdLongToUserId"),
            @Mapping(source = "movieId", target = "movieId", qualifiedByName = "movieIdLongToMovie")
    })
    MovieLike mapMovieLikeDtoToMovieLike(MovieLikeDTO userLike);

    List<MovieLikeDTO> mapMovieLikeListToMovieLikeDtoList(List<MovieLike> userLike);

    List<MovieLike> mapMovieLikeDtoListToMovieLikeList(List<MovieLikeDTO> userLike);

    @Named("userIdToUserIdLong")
    default Long userIdToUserIdLong(User userId) {
        return userId.getId();
    }

    @Named("userIdLongToUserId")
    default User userIdLongToUserId(Long userId) {
        return new User(userId);
    }

    @Named("movieToMovieIdLong")
    default Long movieToMovieIdLong(Movie movie) {
        return movie.getId();
    }

    @Named("movieIdLongToMovie")
    default Movie movieIdLongToMovie(Long movieId) {
        return new Movie(movieId);
    }
}
