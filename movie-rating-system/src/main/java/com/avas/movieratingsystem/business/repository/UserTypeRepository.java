package com.avas.movieratingsystem.business.repository;

import com.avas.movieratingsystem.business.repository.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {
}
