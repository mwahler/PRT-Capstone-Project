package com.mwahler.PRTServer.datatransferobjects;

import com.mwahler.PRTServer.models.UserEntity;
import org.bson.types.ObjectId;

import java.util.Date;

public record UserDTO(
        String id,
        String firstName,
        String lastName,
        String username,
        String password,
        Date createdAt) {

    public UserDTO(UserEntity p) {
        this(p.getId() == null ? new ObjectId().toHexString() : p.getId().toHexString(), p.getFirstName(),
                p.getLastName(), p.getUsername(), p.getPassword(), p.getCreatedAt());
    }

    public UserEntity toUserEntity() {
        ObjectId _id = id == null ? new ObjectId() : new ObjectId(id);
        return new UserEntity(_id, firstName, lastName, username, password, createdAt);
    }
}