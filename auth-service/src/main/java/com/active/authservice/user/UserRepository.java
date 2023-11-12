package com.active.authservice.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserModel, String> {
    boolean existsByEmail(String email);
    Optional<UserModel> getByGid(String gid);
    Optional<UserModel> findByEmail(String email);


}
