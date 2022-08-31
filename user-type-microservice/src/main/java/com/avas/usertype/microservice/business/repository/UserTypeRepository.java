package com.avas.usertype.microservice.business.repository;

import main.java.com.avas.library.business.repository.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {
    boolean existsByType(String type);
}
