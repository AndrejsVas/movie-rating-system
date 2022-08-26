package com.avas.review.microservice.business.repository;

import com.avas.review.microservice.business.repository.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {
    boolean existsByType(String type);
}
