package com.klack.klack.auth.repository;

import com.klack.klack.auth.entity.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<Users, String> {
    Optional<Users> findByEmail(String email);
    boolean existsByEmail(String email);
}
