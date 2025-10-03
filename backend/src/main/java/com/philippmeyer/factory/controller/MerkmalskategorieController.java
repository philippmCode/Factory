package com.philippmeyer.factory.controller;

import com.philippmeyer.factory.entity.Merkmalskategorie;
import com.philippmeyer.factory.service.MerkmalskategorieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/merkmalskategorie")
public class MerkmalskategorieController {

    private final MerkmalskategorieService service;

    public MerkmalskategorieController(MerkmalskategorieService service) {
        this.service = service;
    }

    @GetMapping
    public List<Merkmalskategorie> getAll() {
        return service.findAll();
    }

    /**
     @GetMapping("/{id}")
     public Optional<Stoerung> getById(@PathVariable int id) {
     return service.findById(id);
     }


    @PostMapping
    public Merkmalskategorie create(@RequestBody Merkmalskategorie stoerung) {
        return service.save(stoerung);
    }


     @DeleteMapping("/{id}")
     public void delete(@PathVariable int id) {
     service.deleteById(id);
     }
     **/
}

