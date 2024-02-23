package com.mwahler.PRTServer.services;

import com.mwahler.PRTServer.datatransferobjects.DataPointDTO;
import com.mwahler.PRTServer.datatransferobjects.UserDTO;
import com.mwahler.PRTServer.models.DataPointEntity;
import com.mwahler.PRTServer.models.UserEntity;
import com.mwahler.PRTServer.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO save(UserDTO UserDTO) {
        return new UserDTO(userRepository.save(UserDTO.toUserEntity()));
    }

    @Override
    public List<UserDTO> saveAll(List<UserDTO> userEntities) {
        return userEntities.stream()
                .map(UserDTO::toUserEntity)
                .peek(userRepository::save)
                .map(UserDTO::new)
                .toList();
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(UserDTO::new).toList();
    }

    @Override
    public List<UserDTO> findAll(List<String> ids) {
        return userRepository.findAll(ids).stream().map(UserDTO::new).toList();
    }

    @Override
    public UserDTO findOne(String id) {
        UserEntity entity = userRepository.findOne(id);
        if(entity == null) {
            return null;
        }
        return new UserDTO(entity);
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public long delete(String id) {
        return userRepository.delete(id);
    }

    @Override
    public long delete(List<String> ids) {
        return userRepository.delete(ids);
    }

    @Override
    public long deleteAll() {
        return userRepository.deleteAll();
    }

    @Override
    public UserDTO update(UserDTO UserDTO) {
        return new UserDTO(userRepository.update(UserDTO.toUserEntity()));
    }

    @Override
    public long update(List<UserDTO> userEntities) {
        return userRepository.update(userEntities.stream().map(UserDTO::toUserEntity).toList());
    }
}