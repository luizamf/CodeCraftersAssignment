package com.challenge.clientEnroller.repository;


import com.challenge.clientEnroller.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface ClientEnrollRepository extends JpaRepository<Client, UUID> {

}