package com.rbs.retail.billing.controllers;

import com.rbs.retail.billing.dto.UserDto;
import com.rbs.retail.billing.response.UserResponse;
import com.rbs.retail.billing.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserDto request){

        try{
             UserResponse userResponse = userService.createUser(request);

             return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
        }catch(Exception e){

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create the user");
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> readUsers(){

        List<UserResponse> userResponses = userService.readUsers();

        return ResponseEntity.status(HttpStatus.OK).body(userResponses);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id){

        try{
            userService.deleteUser(id);

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }
}
