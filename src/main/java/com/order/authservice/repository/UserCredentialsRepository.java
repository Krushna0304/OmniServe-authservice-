package com.order.authservice.repository;


import com.order.authservice.entity.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials,String> {
    Optional<UserCredentials> findByUsername(String username);
}
