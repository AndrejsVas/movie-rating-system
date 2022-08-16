package com.avas.movieratingsystem.business.service.impl;

import com.avas.movieratingsystem.business.exceptions.ResourceAlreadyExists;
import com.avas.movieratingsystem.business.exceptions.ResourceConflict;
import com.avas.movieratingsystem.business.mappers.ReviewMapping;
import com.avas.movieratingsystem.business.repository.ReviewRepository;
import com.avas.movieratingsystem.business.repository.model.Review;
import com.avas.movieratingsystem.model.ReviewDTO;
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

import static com.avas.movieratingsystem.test.data.ReviewTestData.createReviewDto;
import static com.avas.movieratingsystem.test.data.ReviewTestData.createReviewDtoList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewMapping reviewMapping;
    @Mock
    private ReviewMapping mockReviewMapping;

    @Spy
    @InjectMocks
    private ReviewServiceImpl reviewService;

    private ReviewDTO reviewDTO;
    private Review review;

    @BeforeEach
    public void createTestData(){
        this.reviewDTO = createReviewDto();
        this.review = reviewMapping.mapReviewDtoToReview(reviewDTO);
    }
    @Test
    @DisplayName("Retrieval of all Reviews")
    public void testMoviesSuccessfully() {
        List<ReviewDTO> reviewDtoList =createReviewDtoList();
        List<Review> reviewList = reviewMapping.mapReviewListDtoToReviewList(reviewDtoList);
        when(reviewRepository.findAll()).thenReturn(reviewList);
        when(mockReviewMapping.mapReviewListToReviewListDto(reviewList)).thenReturn(reviewDtoList);
        List<ReviewDTO> reviewDTOListReturned = reviewService.getAllReviews();
        Assertions.assertTrue(reviewDtoList.equals(reviewDTOListReturned));
        verify(reviewRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Retrieval of empty Review list")
    public void testGetAllReviewsEmpty(){
        List<ReviewDTO> emptyDtoList = new ArrayList<>();
        List<Review> emptyReviewList = reviewMapping.mapReviewListDtoToReviewList(emptyDtoList);
        when(reviewRepository.findAll()).thenReturn(emptyReviewList);
        when(mockReviewMapping.mapReviewListToReviewListDto(emptyReviewList)).thenReturn(emptyDtoList);
        Assertions.assertThrows(ResourceAlreadyExists.class, () -> reviewService.getAllReviews());
        verify(reviewRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deleting review by id")
    public void testDeletingReviewById(){
        doReturn(Optional.of(reviewDTO)).when(reviewService).findReviewById(1L);
        reviewService.deleteReviewById(1L);
        verify(reviewRepository, times(1)).deleteById(anyLong());
    }

    @Test
    @DisplayName("Delete non existing review")
    public void testDeleteNotFound(){
        doReturn(Optional.empty()).when(reviewService).findReviewById(1L);
        Assertions.assertThrows(ResourceAlreadyExists.class,() -> reviewService.deleteReviewById(1L));
    }

    @Test
    @DisplayName("Testing finding Review by id")
    public void testSuccessfullyFindingReviewById(){
        when(mockReviewMapping.mapReviewToReviewDto(review)).thenReturn(reviewDTO);
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(review));
        reviewService.findReviewById(anyLong());
        verify(reviewRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Find non existing review by id")
    public void testFailingFindMovieById(){
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceAlreadyExists.class , () -> reviewService.findReviewById(anyLong()));
    }

    @Test
    @DisplayName("Create a review")
    public void testSuccessfullyCreatingAReview(){
        when(reviewRepository.existsByMovieIdAndUserId(review.getMovieId(), review.getUserId())).thenReturn(false);
        when(mockReviewMapping.mapReviewDtoToReview(reviewDTO)).thenReturn(review);
        when(reviewRepository.save(review)).thenReturn(review);
        reviewService.createReview(reviewDTO);
        verify(reviewRepository, times(1)).save(review);
    }

    @Test
    @DisplayName("Create a duplicate review")
    public void testFailingCreatingAReview(){
        when(mockReviewMapping.mapReviewDtoToReview(reviewDTO)).thenReturn(review);
        when(reviewRepository.existsByMovieIdAndUserId(review.getMovieId(), review.getUserId())).thenReturn(true);
        Assertions.assertThrows(ResourceAlreadyExists.class, ()-> reviewService.createReview(reviewDTO));
    }

    @Test
    @DisplayName("Update a review by Id")
    public void testSuccessfullyUpdatingAReviewById(){
        when(reviewRepository.existsById(anyLong())).thenReturn(true);
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(review));
        when(mockReviewMapping.mapReviewToReviewDto(review)).thenReturn(reviewDTO);
        when(mockReviewMapping.mapReviewDtoToReview(reviewDTO)).thenReturn(review);
        when(reviewRepository.save(review)).thenReturn(review);
        ReviewDTO returnedReviewDto = reviewService.updateReviewById(reviewDTO, 1L);
        Assertions.assertEquals(reviewDTO,returnedReviewDto);
        verify(reviewRepository, times(1)).save(review);
    }

    @Test
    @DisplayName("Update non existing review")
    public void testFailingToUpdateNonExistingReview(){
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceAlreadyExists.class, () -> reviewService.updateReviewById(reviewDTO, 1L));
    }

    @Test
    @DisplayName(("Update a review with invalid movie id or user id"))
    public void testFailUpdatingAMovieWhichDoesNotExist(){
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(review));
        when(mockReviewMapping.mapReviewToReviewDto(review)).thenReturn(reviewDTO);
        when(reviewRepository.save(review)).thenReturn(review);
        Assertions.assertThrows(ResourceConflict.class, ()-> reviewService.updateReviewById(createReviewDto(), 1L));
    }

    @Test
    @DisplayName("Delete a review")
    public void testSuccessfullyDeleteAMovie(){
        doReturn(Optional.of(reviewDTO)).when(reviewService).findReviewById(anyLong());
        reviewService.deleteReviewById(1L);
        verify(reviewRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Delete a non existing review")
    public void testFailingDeleteAMovie(){
        doReturn(Optional.empty()).when(reviewService).findReviewById(anyLong());
        Assertions.assertThrows(ResourceAlreadyExists.class , ()-> reviewService.deleteReviewById(1L));
    }
}
