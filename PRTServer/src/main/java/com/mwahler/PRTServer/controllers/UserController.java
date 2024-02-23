package com.mwahler.PRTServer.controllers;

import com.mwahler.PRTServer.datatransferobjects.UserDTO;
import com.mwahler.PRTServer.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO postUser(@RequestBody UserDTO UserDTO) {
        return userService.save(UserDTO);
    }

    @PostMapping("users")
    @ResponseStatus(HttpStatus.CREATED)
    public List<UserDTO> postUsers(@RequestBody List<UserDTO> userEntities) {
        return userService.saveAll(userEntities);
    }

    @GetMapping("users")
    public List<UserDTO> getUsers() {
        return userService.findAll();
    }

    @GetMapping("user/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String id) {
        UserDTO UserDTO = userService.findOne(id);
        if (UserDTO == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(UserDTO);
    }

    @GetMapping("users/{ids}")
    public List<UserDTO> getUsers(@PathVariable String ids) {
        List<String> listIds = List.of(ids.split(","));
        return userService.findAll(listIds);
    }

    @GetMapping("users/count")
    public Long getCount() {
        return userService.count();
    }

    @DeleteMapping("user/{id}")
    public Long deleteUser(@PathVariable String id) {
        return userService.delete(id);
    }

    @DeleteMapping("users/{ids}")
    public Long deleteUsers(@PathVariable String ids) {
        List<String> listIds = List.of(ids.split(","));
        return userService.delete(listIds);
    }

    @DeleteMapping("users")
    public Long deleteUsers() {
        return userService.deleteAll();
    }

    @PutMapping("user")
    public UserDTO putUser(@RequestBody UserDTO UserDTO) {
        return userService.update(UserDTO);
    }

    @PutMapping("users")
    public Long putUser(@RequestBody List<UserDTO> userEntities) {
        return userService.update(userEntities);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}