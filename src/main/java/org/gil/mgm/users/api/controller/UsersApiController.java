package org.gil.mgm.users.api.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.gil.mgm.users.api.exception.ValidationException;
import org.gil.mgm.users.api.model.User;
import org.gil.mgm.users.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-02-11T22:44:28.452335707Z[GMT]")
@RestController
public class UsersApiController implements UsersApi {

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);
    private final UserService userService;
    private final HttpServletRequest request;

    @Autowired
    public UsersApiController(UserService userService, HttpServletRequest request) {
        this.userService = userService;
        this.request = request;
    }

    public ResponseEntity<User> getUserById(
            @Parameter(in = ParameterIn.PATH, description = "User's unique identifier.", required = true, schema = @Schema(allowableValues = {"0"})) @PathVariable("userId") Long userId,
            @Parameter(in = ParameterIn.HEADER, description = "Unique identifier used to trace and track requests across multiple services or components.", schema = @Schema()) @RequestHeader(value = "correlationId", required = false) UUID correlationId
    ) {
        log.info("Executing getUserById");
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    public ResponseEntity<List<User>> getUsers(
            @Parameter(in = ParameterIn.HEADER, description = "Unique identifier used to trace and track requests across multiple services or components." ,schema=@Schema()) @RequestHeader(value="correlationId", required=false) UUID correlationId,
            @Parameter(in = ParameterIn.QUERY, description = "User's profession." ,schema=@Schema()) @Valid @RequestParam(value = "profession", required = false) String profession,
            @Parameter(in = ParameterIn.QUERY, description = "Start Date (inclusive)." ,schema=@Schema()) @Valid @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @Parameter(in = ParameterIn.QUERY, description = "End Date (inclusive)." ,schema=@Schema()) @Valid @RequestParam(value = "endDate", required = false) LocalDate endDate
    ) {
        log.info("Executing getUsers");
        if(nonNull(profession)){
            return ResponseEntity.ok(userService.getUsersByProfession(profession));
        }else if(nonNull(startDate) && nonNull(endDate)){
            return ResponseEntity.ok(userService.getUsersByDateRange(startDate, endDate));
        }else if (nonNull(startDate)||nonNull(endDate)) {
            throw new ValidationException("Both Start Date and End Date must be provided");
        }else{
            return ResponseEntity.ok(userService.getAllUsers());
        }
    }

    public ResponseEntity<User> postUser(
            @Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody User body,
            @Parameter(in = ParameterIn.HEADER, description = "Unique identifier used to trace and track requests across multiple services or components." ,schema=@Schema()) @RequestHeader(value="correlationId", required=false) UUID correlationId
    ) {
        log.info("Executing postUser");
        validateUserRequestBodyOrThrow(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(body));
    }

    private void validateUserRequestBodyOrThrow(@Valid User body) {
        if(isNull(body) || isNull(body.getFirstname()) || isNull(body.getLastname())
                || isNull(body.getEmail())|| isNull(body.getProfession())
                || isNull(body.getDateCreated())|| isNull(body.getCity())
                || isNull(body.getCountry())
        ) {
            throw new ValidationException("Invalid request body");
        }
    }

}
