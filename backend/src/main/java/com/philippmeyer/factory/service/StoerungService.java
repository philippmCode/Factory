package com.philippmeyer.factory.service;

import com.philippmeyer.factory.entity.Stoerung;
import com.philippmeyer.factory.repository.StoerungRepository;
import com.philippmeyer.factory.repository.StoerungSumToday;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoerungService {

    private final StoerungRepository repository;

    public StoerungService(StoerungRepository repository) {
        this.repository = repository;
    }

    public List<Stoerung> findAll() {
        return repository.findAll();
    }

    public List<StoerungSumToday> getAggregatedStoerungSums() {
        return repository.findAggregatedStoerungSums();
    }

    public List<StoerungSumToday> getAggregatedStoerungSumsByFilter(Long filterId) {
        return repository.findAggregatedStoerungSumsByFilter(filterId);
    }

    /**
    public Optional<Stoerung> findById(int id) {
        return repository.findById(id);
    }
    **/

    public Stoerung save(Stoerung stoerung) {
        return repository.save(stoerung);
    }

    /**
    public void deleteById(int id) {
        repository.deleteById(id);
    }
     **/
}
