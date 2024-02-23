package com.mwahler.PRTServer.services;


import com.mwahler.PRTServer.datatransferobjects.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO save(UserDTO UserDTO);

    List<UserDTO> saveAll(List<UserDTO> userEntities);

    List<UserDTO> findAll();

    List<UserDTO> findAll(List<String> ids);

    UserDTO findOne(String id);

    long count();

    long delete(String id);

    long delete(List<String> ids);

    long deleteAll();

    UserDTO update(UserDTO UserDTO);

    long update(List<UserDTO> userEntities);

}