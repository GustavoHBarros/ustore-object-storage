package com.example.OS.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.OS.model.Payload;

@Repository
public interface MetadataRepository extends JpaRepository<Payload, Long> {

    List<Payload> findAll();
    Optional<Payload> findById(Long id);
    Payload save(Payload payload);
}
