package com.avas.user.like.microservice.business.service.impl;


import com.avas.library.business.exceptions.ResourceAlreadyExists;
import com.avas.library.business.exceptions.ResourceNotFoundException;
import com.avas.library.business.mappers.ReviewMapping;
import com.avas.library.business.mappers.UserLikeMapper;
import com.avas.library.business.mappers.UserMapping;
import com.avas.library.business.repository.model.Review;
import com.avas.library.business.repository.model.User;
import com.avas.library.business.repository.model.UserLike;
import com.avas.library.model.ReviewDTO;
import com.avas.library.model.UserDTO;
import com.avas.library.model.UserLikeDTO;
import com.avas.user.like.microservice.business.repository.ReviewRepository;
import com.avas.user.like.microservice.business.repository.UserLikeRepository;
import com.avas.user.like.microservice.business.repository.UserRepository;
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

import static com.avas.user.like.microservice.test.data.ReviewTestData.createReviewDto;
import static com.avas.user.like.microservice.test.data.UserLikeTestData.createUserLikeDTO;
import static com.avas.user.like.microservice.test.data.UserLikeTestData.createUserLikeDtoList;
import static com.avas.user.like.microservice.test.data.UserTestData.createUserDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class UserLikeServiceImplTest {

    @Mock
    private UserLikeRepository userLikeRepository;
    @Mock
    private UserRepository userRepository;

    @Autowired
    private UserLikeMapper userLikeMapper;
    @Autowired
    private UserMapping userMapping;
    @Mock
    private UserMapping mockUserMapping;
    @Mock
    private UserLikeMapper mockUserLikeMapper;

    @Mock
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewMapping reviewMapping;
    @Mock
    private ReviewMapping mockReviewMapping;
    @Spy
    @InjectMocks
    private UserLikeServiceImpl userLikeService;

    private UserLikeDTO userLikeDTO;
    private UserLike userLike;

    @BeforeEach
    public void createTestData() {
        this.userLikeDTO = createUserLikeDTO();
        this.userLike = userLikeMapper.mapUserLikeDtoToUserLike(userLikeDTO);
    }

    @Test
    @DisplayName("Retrieval of all UserLikes for a user")
    public void testSuccessfullyGetAllUserLikesForUser() {
        UserDTO userDto = createUserDto();
        List<UserLikeDTO> userLikeDtoList = createUserLikeDtoList();
        User user = userMapping.mapUserDtoToUser(userDto);
        when(mockUserMapping.mapUserToUserDto(user)).thenReturn(userDto);
        when(mockUserMapping.mapUserDtoToUser(userDto)).thenReturn(user);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userLikeRepository.findAllByUserId(user)).thenReturn(userLikeMapper
                .mapUserLikeDtoListToUserLikeList(userLikeDtoList));
        when(mockUserLikeMapper.mapUserLikeToUserLikeDto(userLike)).thenReturn(userLikeDTO);
        List<UserLikeDTO> userLikeDTOList = userLikeService.getAllUserLikes(anyLong());
        Assertions.assertNotEquals(userLikeDTOList.size(), 0);
        verify(userLikeRepository, times(1)).findAllByUserId(user);
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Retrieval of all UserLikes for a non existing user")
    public void testFailGetAllUserLikesForNonExistingUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> userLikeService.getAllUserLikes(1L));
    }

    @Test
    @DisplayName("Retrieval of empty user likes list for a user")
    public void testFailGetAllUserLikesEmpty() {
        UserDTO userDto = createUserDto();
        User user = userMapping.mapUserDtoToUser(userDto);
        List<UserLikeDTO> userLikeDtoList = new ArrayList<UserLikeDTO>();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userLikeRepository.findAllByUserId(user)).thenReturn(userLikeMapper
                .mapUserLikeDtoListToUserLikeList(userLikeDtoList));
        when(mockUserLikeMapper.mapUserLikeToUserLikeDto(userLike)).thenReturn(userLikeDTO);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> userLikeService.getAllUserLikes(1L));
    }


    @Test
    @DisplayName("Retrieval of all UserLikes for a review")
    public void testSuccessfullyGetAllUserLikesForReview() {
        ReviewDTO reviewDTO = createReviewDto();
        List<UserLikeDTO> userLikeDtoList = createUserLikeDtoList();
        Review review = reviewMapping.mapReviewDtoToReview(reviewDTO);
        when(mockReviewMapping.mapReviewToReviewDto(review)).thenReturn(reviewDTO);
        when(mockReviewMapping.mapReviewDtoToReview(reviewDTO)).thenReturn(review);
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(review));
        when(userLikeRepository.findAllByReviewId(review)).thenReturn(userLikeMapper
                .mapUserLikeDtoListToUserLikeList(userLikeDtoList));
        when(mockUserLikeMapper.mapUserLikeToUserLikeDto(userLike)).thenReturn(userLikeDTO);
        List<UserLikeDTO> userLikeDTOList = userLikeService.getAllLikesForAReview(anyLong());
        Assertions.assertNotEquals(userLikeDTOList.size(), 0);
        verify(userLikeRepository, times(1)).findAllByReviewId(review);
        verify(reviewRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Retrieval of all UserLikes for a non existing review")
    public void testFailGetAllUserLikesForNonExistingReview() {
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> userLikeService.getAllLikesForAReview(1L));
    }

    @Test
    @DisplayName("Retrieval of empty user likes list for a review")
    public void testFailGetAllUserLikesEmptyForReview() {
        ReviewDTO reviewDTO = createReviewDto();
        Review review = reviewMapping.mapReviewDtoToReview(reviewDTO);
        List<UserLikeDTO> userLikeDtoList = new ArrayList<UserLikeDTO>();
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(review));
        when(userLikeRepository.findAllByReviewId(review)).thenReturn(userLikeMapper
                .mapUserLikeDtoListToUserLikeList(userLikeDtoList));
        when(mockUserLikeMapper.mapUserLikeToUserLikeDto(userLike)).thenReturn(userLikeDTO);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> userLikeService.getAllLikesForAReview(1L));
    }

    @Test
    @DisplayName("Toggle Review Like from like to dislike")
    public void testToggleReviewFromDislikeToLike() {
        ReviewDTO reviewDTO = createReviewDto();
        Review review = reviewMapping.mapReviewDtoToReview(reviewDTO);
        when(mockReviewMapping.mapReviewToReviewDto(review)).thenReturn(reviewDTO);
        when(mockReviewMapping.mapReviewDtoToReview(reviewDTO)).thenReturn(review);
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(review));
        UserDTO userDto = createUserDto();
        User user = userMapping.mapUserDtoToUser(userDto);
        when(mockUserMapping.mapUserToUserDto(user)).thenReturn(userDto);
        when(mockUserMapping.mapUserDtoToUser(userDto)).thenReturn(user);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        when(userLikeRepository.existsByUserIdAndReviewId(user, review)).thenReturn(true);
        when(userLikeRepository.findByUserIdAndReviewId(user, review)).thenReturn(Optional.of(userLike));
        userLikeService.toggleReviewLike(1L, 1L);
        verify(userLikeRepository, times(1)).delete(userLike);
    }

    @Test
    @DisplayName("Toggle Review Like from dislike to like")
    public void testToggleReviewLikeFromLikeToDislike() {
        ReviewDTO reviewDTO = createReviewDto();
        Review review = reviewMapping.mapReviewDtoToReview(reviewDTO);
        when(mockReviewMapping.mapReviewToReviewDto(review)).thenReturn(reviewDTO);
        when(mockReviewMapping.mapReviewDtoToReview(reviewDTO)).thenReturn(review);
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(review));
        UserDTO userDto = createUserDto();
        User user = userMapping.mapUserDtoToUser(userDto);
        when(mockUserMapping.mapUserToUserDto(user)).thenReturn(userDto);
        when(mockUserMapping.mapUserDtoToUser(userDto)).thenReturn(user);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userLikeRepository.existsByUserIdAndReviewId(user, review)).thenReturn(false);
        when(userLikeRepository.save(any(UserLike.class))).thenReturn(userLike);
        when(mockUserLikeMapper.mapUserLikeToUserLikeDto(any(UserLike.class))).thenReturn(userLikeDTO);
        userLikeService.toggleReviewLike(1L, 1L);
        verify(userLikeRepository, times(1)).save(any(UserLike.class));

    }

    @Test
    @DisplayName("Toggle Review Like for a review or reviewer which dont exist")
    public void testToggleReviewLikeForNonExistingReviewerOrUser() {
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceAlreadyExists.class,
                () -> userLikeService.toggleReviewLike(1L, 1L));
    }

}
