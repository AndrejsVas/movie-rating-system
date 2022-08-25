package com.avas.movieratingsystem.web.controller;

import com.avas.movieratingsystem.business.service.UserTypeService;
import com.avas.movieratingsystem.model.UserTypeDTO;
import com.avas.movieratingsystem.swagger.HTMLResponseMessages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

@Log4j2
@Controller
@Tag(name = "User type controller", description = "A controller for operations with the user type database")
@RequestMapping("api/v1/user_type")
public class UserTypeController {

    @Autowired
    UserTypeService userTypeService;

    @GetMapping
    @Operation(summary = "Retrieve a list of all user types")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = HTMLResponseMessages.HTTP_200, content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserTypeDTO.class))
                    )
            }),
            @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<UserTypeDTO>> getAllUserTypes() {
        List<UserTypeDTO> userTypeDTOS = userTypeService.getAllUserTypes();
        return ResponseEntity.ok(userTypeDTOS);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a specific user type by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = HTMLResponseMessages.HTTP_200, content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserTypeDTO.class)
                    )
            }),
            @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<UserTypeDTO> getUserTypeById(@PathVariable Long id) {
        Optional<UserTypeDTO> foundUserTypeDTO = userTypeService.findUserTypeById(id);
        log.info("User type found : {}", foundUserTypeDTO.get());
        return new ResponseEntity<>(foundUserTypeDTO.get(), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create a user type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = HTMLResponseMessages.HTTP_201, content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserTypeDTO.class)
                    )
            }),
            @ApiResponse(responseCode = "400", description = HTMLResponseMessages.HTTP_400),
            @ApiResponse(responseCode = "409", description = HTMLResponseMessages.HTTP_409),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<UserTypeDTO> createUserType(@RequestBody UserTypeDTO userTypeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.warn("Binding result error");
            return ResponseEntity.badRequest().build();
        }
        UserTypeDTO savedUserType = userTypeService.createUserType(userTypeDTO);
        log.debug("New user type is created : {}", userTypeDTO);
        return new ResponseEntity<>(savedUserType, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = HTMLResponseMessages.HTTP_200, content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserTypeDTO.class)
                    )
            }),
            @ApiResponse(responseCode = "400", description = HTMLResponseMessages.HTTP_400),
            @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
            @ApiResponse(responseCode = "409", description = HTMLResponseMessages.HTTP_409),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<UserTypeDTO> updateUserTypeById(@PathVariable Long id,
                                                          @RequestBody UserTypeDTO modifiedUserTypeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.warn("Binding result error");
            return ResponseEntity.badRequest().build();
        }
        UserTypeDTO returnedUserTypeDTO = userTypeService.updateUserTypeById(modifiedUserTypeDTO, id);
        log.debug("User type with id: {} is now :{}", id, returnedUserTypeDTO);
        return new ResponseEntity<>(returnedUserTypeDTO, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = HTMLResponseMessages.HTTP_204_WITHOUT_DATA),
            @ApiResponse(responseCode = "400", description = HTMLResponseMessages.HTTP_400),
            @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)
    })
    public ResponseEntity<UserTypeDTO> deleteMovieTypeById(@PathVariable Long id) {
        log.info("Delete User Type by passing ID, where ID is:{}", id);
        userTypeService.deleteUserTypeById(id);
        log.debug("User type with id {} is deleted", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
