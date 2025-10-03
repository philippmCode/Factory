package com.philippmeyer.factory.repository;

import com.philippmeyer.factory.entity.Merkmalskategorie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MerkmalskategorieRepository extends JpaRepository<Merkmalskategorie, Long> {

    List<Merkmalskategorie> findAll();

    List<Merkmalskategorie> findAllById(Iterable<Long> ids);
}