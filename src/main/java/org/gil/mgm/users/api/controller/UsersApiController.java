package org.gil.mgm.users.api.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.servlet.http.HttpServletRequest;
import org.gil.mgm.users.api.model.User;
import org.gil.mgm.users.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

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

}
