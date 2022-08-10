package com.avas.movieratingsystem.business.repository;

import com.avas.movieratingsystem.business.repository.model.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLikeRepository extends JpaRepository<UserLike, Long> {
}
