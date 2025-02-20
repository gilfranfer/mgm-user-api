/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.67).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package org.gil.mgm.users.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import org.gil.mgm.users.api.model.ErrorResponse;
import org.gil.mgm.users.api.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-02-11T22:44:28.452335707Z[GMT]")
@Validated
public interface UsersApi {

    @Operation(summary = "Get user by ID", description = "Retrieve User data for the given ID.", tags = {"Users"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details retrieved.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Resource not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal service error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @RequestMapping(value = "/users/{userId}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<User> getUserById(@Parameter(in = ParameterIn.PATH, description = "User's unique identifier.", required = true, schema = @Schema(allowableValues = {"0"}
    )) @PathVariable("userId") Long userId
            , @Parameter(in = ParameterIn.HEADER, description = "Unique identifier used to trace and track requests across multiple services or components.", schema = @Schema()) @RequestHeader(value = "correlationId", required = false) UUID correlationId
    );


    @Operation(summary = "Get users", description = "Retrieve User data by profession or date range. - Optional query parameter `profession` can be used to retrieve only users with the given profession. - Optional query parameter `starDate` and `endDate` can be used to retrieve only users created withing the given date range. ", tags={ "Users" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of user details retrieved.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class)))),
            @ApiResponse(responseCode = "400", description = "Bad Request.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal service error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
    @RequestMapping(value = "/users",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<List<User>> getUsers(@Parameter(in = ParameterIn.HEADER, description = "Unique identifier used to trace and track requests across multiple services or components." ,schema=@Schema()) @RequestHeader(value="correlationId", required=false) UUID correlationId
            , @Parameter(in = ParameterIn.QUERY, description = "User's profession." ,schema=@Schema()) @Valid @RequestParam(value = "profession", required = false) String profession
            , @Parameter(in = ParameterIn.QUERY, description = "Start Date (inclusive)." ,schema=@Schema()) @Valid @RequestParam(value = "startDate", required = false) LocalDate startDate
            , @Parameter(in = ParameterIn.QUERY, description = "End Date (inclusive)." ,schema=@Schema()) @Valid @RequestParam(value = "endDate", required = false) LocalDate endDate
    );


    @Operation(summary = "Create user", description = "Create a new user.", tags={ "Users" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal service error.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
    @RequestMapping(value = "/users",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<User> postUser(
            @Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody User body,
            @Parameter(in = ParameterIn.HEADER, description = "Unique identifier used to trace and track requests across multiple services or components." ,schema=@Schema()) @RequestHeader(value="correlationId", required=false) UUID correlationId
    );

}

