package com.philippmeyer.factory.service;

import com.philippmeyer.factory.entity.Merkmalskategorie;
import com.philippmeyer.factory.repository.MerkmalskategorieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerkmalskategorieService {

    private final MerkmalskategorieRepository repository;

    public MerkmalskategorieService(MerkmalskategorieRepository repository) {
        this.repository = repository;
    }

    public List<Merkmalskategorie> findAll() {
        return repository.findAll();
    }
}
