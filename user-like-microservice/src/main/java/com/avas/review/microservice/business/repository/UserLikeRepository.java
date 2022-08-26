package com.avas.review.microservice.business.repository;

import com.avas.review.microservice.business.repository.model.Review;
import com.avas.review.microservice.business.repository.model.User;
import com.avas.review.microservice.business.repository.model.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserLikeRepository extends JpaRepository<UserLike, Long> {
    boolean existsByUserIdAndReviewId(User user, Review review);
    Optional<UserLike> findByUserIdAndReviewId(User user, Review review);
    List<UserLike> findAllByReviewId(Review review);
    List<UserLike> findAllByUserId(User user);
}
