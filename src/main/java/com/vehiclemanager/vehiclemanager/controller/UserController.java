package com.vehiclemanager.vehiclemanager.controller;

import com.vehiclemanager.vehiclemanager.dto.CreateUserDto;
import com.vehiclemanager.vehiclemanager.dto.UserDto;
import com.vehiclemanager.vehiclemanager.dto.UserListDto;
import com.vehiclemanager.vehiclemanager.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    @Operation(summary = "Create User", description = "Creates a new user", tags = "Users Endpoints")
    public ResponseEntity<Void> newUser(@RequestBody CreateUserDto dto) {
        userService.createUser(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    @Operation(summary = "List Users", description = "List all users", tags = "Users Endpoints")
    public ResponseEntity<UserListDto> listUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        UserListDto users = userService.getAllUsers(page, pageSize);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "Get User", description = "Get User by Id", tags = "Users Endpoints")
    public ResponseEntity<UserDto> getUserById(@PathVariable UUID userId) {
        UserDto user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/users/{userId}")
    @Operation(summary = "Delete User", description = "Delete User by Id", tags = "Users Endpoints")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/{userId}")
    @Operation(summary = "Update User", description = "Update an existent User", tags = "Users Endpoints")
    public ResponseEntity<Void> updateUser(@PathVariable UUID userId, @RequestBody CreateUserDto dto) {
        userService.updateUser(userId, dto);
        return ResponseEntity.ok().build();
    }
}
