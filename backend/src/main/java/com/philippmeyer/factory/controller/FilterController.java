package com.philippmeyer.factory.controller;

import com.philippmeyer.factory.entity.Filter;
import com.philippmeyer.factory.service.FilterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filter")
public class FilterController {

    private final FilterService service;

    public FilterController(FilterService service) {
        this.service = service;
    }

    @GetMapping
    public List<Filter> getAll() {
        return service.findAll();
    }

    /**
     @GetMapping("/{id}")
     public Optional<Filter> getById(@PathVariable int id) {
     return service.findById(id);
     }
     **/


     @PostMapping
     public Filter create(@RequestBody Filter filter) {
     return service.save(filter);
     }

    /**
     @DeleteMapping("/{id}")
     public void delete(@PathVariable int id) {
     service.deleteById(id);
     }
     **/
}

