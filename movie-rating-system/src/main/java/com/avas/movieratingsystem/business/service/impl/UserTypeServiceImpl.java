package com.avas.movieratingsystem.business.service.impl;

import com.avas.movieratingsystem.business.exceptions.ResourceAlreadyExists;
import com.avas.movieratingsystem.business.exceptions.ResourceNotFoundException;
import com.avas.movieratingsystem.business.mappers.UserTypeMapper;
import com.avas.movieratingsystem.business.repository.UserTypeRepository;
import com.avas.movieratingsystem.business.repository.model.UserType;
import com.avas.movieratingsystem.business.service.UserTypeService;
import com.avas.movieratingsystem.model.UserTypeDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class UserTypeServiceImpl implements UserTypeService {
    @Autowired
    UserTypeRepository userTypeRepository;

    @Autowired
    UserTypeMapper userTypeMapper;
    public List<UserTypeDTO> getAllUserTypes() {
        return userTypeRepository.findAll().stream().map(userTypeMapper::mapUserTypeToUserTypeDto).collect(Collectors.toList());

    }

    public Optional<UserTypeDTO> findUserTypeById(Long id) {
        Optional<UserTypeDTO> userTypeDTO = userTypeRepository.findById(id)
                .map(userType -> userTypeMapper.mapUserTypeToUserTypeDto(userType));
        if (!userTypeDTO.isPresent()) {
            log.warn("UserType with id:{} Not found", id);
            throw new ResourceNotFoundException("UserType with id:" + id + " does not exist");
        }
        log.info("Found UserType :{}", userTypeDTO);
        return userTypeDTO;
    }

    public void deleteUserTypeById(Long id) {
        userTypeRepository.deleteById(id);
        log.info("UserType with id: {} is deleted", id);
    }

    public UserTypeDTO createUserType(UserTypeDTO newUserType) {
        boolean userTypeAlreadyExists = userTypeRepository.existsByType(newUserType.getType());
        if(userTypeAlreadyExists){
            log.warn("Can not create UserType,  UserType already exists");
            throw new ResourceAlreadyExists("Can not create UserType, UserType already exists");
        }
        UserType savedUserType = userTypeRepository.save(userTypeMapper.mapUserTypeDtoToUserType(newUserType));
        log.info("UserType is created : {}", savedUserType);
        return userTypeMapper.mapUserTypeToUserTypeDto(savedUserType);
    }

    public UserTypeDTO updateUserTypeById(UserTypeDTO modifyExistingUserType, Long id) {
        boolean userTypeAlreadyExists = userTypeRepository.existsByType(modifyExistingUserType.getType());
        if(userTypeAlreadyExists){
            log.warn("Can not update UserType. This UserType is already taken :{}", modifyExistingUserType.getType());
            throw new ResourceAlreadyExists("Can not update UserType. This UserType is already taken");
        }
        modifyExistingUserType.setId(id);
        UserType modifiedUserType = userTypeRepository.save(userTypeMapper.mapUserTypeDtoToUserType(modifyExistingUserType));
        log.info("UserType is updated id :{}, UserType is now :{}", modifiedUserType.getId(), modifiedUserType);
        return userTypeMapper.mapUserTypeToUserTypeDto(modifiedUserType);
    }

    public boolean checkIfUserTypeExistsById(Long id){
        return userTypeRepository.existsById(id);
    }
}
