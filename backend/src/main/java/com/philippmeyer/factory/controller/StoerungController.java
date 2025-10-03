package com.philippmeyer.factory.controller;

import com.philippmeyer.factory.entity.Stoerung;
import com.philippmeyer.factory.repository.StoerungSumToday;
import com.philippmeyer.factory.service.StoerungService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stoerung")
public class StoerungController {

    private final StoerungService service;

    public StoerungController(StoerungService service) {
        this.service = service;
    }

    // Endpoint für aggregierte Störungsdaten
    @GetMapping("/sum")
    public List<StoerungSumToday> getStoerungSumByFilter(@RequestParam(required = false) Long filterId) {
        if (filterId != null) {
            return service.getAggregatedStoerungSumsByFilter(filterId);
        } else {
            return service.getAggregatedStoerungSums();
        }
    }

    @GetMapping
    public List<Stoerung> getAll() {
        return service.findAll();
    }

    /**
    @GetMapping("/{id}")
    public Optional<Stoerung> getById(@PathVariable int id) {
        return service.findById(id);
    }
     **/

    @PostMapping
    public Stoerung create(@RequestBody Stoerung stoerung) {
        return service.save(stoerung);
    }

    /**
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.deleteById(id);
    }
    **/
}
