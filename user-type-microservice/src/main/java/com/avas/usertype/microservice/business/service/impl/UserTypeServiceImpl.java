package com.avas.usertype.microservice.business.service.impl;

import com.avas.usertype.microservice.business.exceptions.ResourceAlreadyExists;
import com.avas.usertype.microservice.business.exceptions.ResourceNotFoundException;
import com.avas.usertype.microservice.business.mappers.UserTypeMapper;
import com.avas.usertype.microservice.business.repository.UserTypeRepository;
import com.avas.usertype.microservice.business.repository.model.UserType;
import com.avas.usertype.microservice.business.service.UserTypeService;
import com.avas.usertype.microservice.model.UserTypeDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class UserTypeServiceImpl implements UserTypeService {
    @Autowired
    UserTypeRepository userTypeRepository;

    @Autowired
    UserTypeMapper userTypeMapper;

    public List<UserTypeDTO> getAllUserTypes() {
        List<UserType> returnedUserTypeList = userTypeRepository.findAll();
        if (returnedUserTypeList.isEmpty())
            throw new ResourceNotFoundException("No user types found");
        log.info("user type list size is :{}", returnedUserTypeList.size());
        return userTypeMapper.mapUserTypeListToUserTypeListDto(returnedUserTypeList);
    }

    public Optional<UserTypeDTO> findUserTypeById(Long id) {
        Optional<UserTypeDTO> userTypeDTO = userTypeRepository.findById(id)
                .map(userType -> userTypeMapper.mapUserTypeToUserTypeDto(userType));
        userTypeDTO.orElseThrow(() -> new ResourceNotFoundException("user type with id:{0} does not exist", id));
        log.info("Found UserType :{}", userTypeDTO);
        return userTypeDTO;
    }

    public void deleteUserTypeById(Long id) {
        findUserTypeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserType for delete with id {0} is not found.", id));
        userTypeRepository.deleteById(id);
        log.info("UserType with id: {} is deleted", id);
    }

    public UserTypeDTO createUserType(UserTypeDTO newUserType) {
        boolean userTypeAlreadyExists = userTypeRepository.existsByType(newUserType.getType());
        if (userTypeAlreadyExists) {
            throw new ResourceAlreadyExists("Can not create UserType, UserType already exists");
        }
        UserType savedUserType = userTypeRepository.save(userTypeMapper.mapUserTypeDtoToUserType(newUserType));
        log.info("UserType is created : {}", savedUserType);
        return userTypeMapper.mapUserTypeToUserTypeDto(savedUserType);
    }

    public UserTypeDTO updateUserTypeById(UserTypeDTO modifyExistingUserType, Long id) {
        if (!userTypeRepository.existsById(id))
            throw new ResourceNotFoundException("User type with id:{0} is not found", id);
        if (userTypeRepository.existsByType(modifyExistingUserType.getType())) {
            throw new ResourceAlreadyExists("Can not update user type. This user type is already taken");
        }
        modifyExistingUserType.setId(id);
        UserType modifiedUserType = userTypeRepository.save(userTypeMapper.mapUserTypeDtoToUserType(modifyExistingUserType));
        log.info("UserType is updated id :{}, UserType is now :{}", modifiedUserType.getId(), modifiedUserType);
        return userTypeMapper.mapUserTypeToUserTypeDto(modifiedUserType);
    }

}
