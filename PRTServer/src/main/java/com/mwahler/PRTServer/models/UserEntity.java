package com.mwahler.PRTServer.models;

import org.bson.types.ObjectId;

import java.util.Date;
import java.util.Objects;

public class UserEntity {

    private ObjectId id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Date createdAt = new Date();

    public UserEntity() {
    }

    public UserEntity(ObjectId id,
                      String firstName,
                      String lastName,
                      String username,
                      String password,
                      Date createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
    }

    public ObjectId getId() {
        return id;
    }

    public UserEntity setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public UserEntity setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + "username:" + username + "password: ****" + ", createdAt=" + createdAt + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity userEntity = (UserEntity) o;
        return Objects.equals(id, userEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, username, password, createdAt);
    }

}