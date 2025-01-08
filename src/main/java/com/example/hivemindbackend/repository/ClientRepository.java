package com.example.hivemindbackend.repository;


import com.example.hivemindbackend.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByUser_UserIdAndStatusFalse(Long userId);
    List<Client> findByUser_UserIdAndStatusTrue(Long userId);


    Optional<Client> findByIdAndUserId(Long id, Long userId);
}
