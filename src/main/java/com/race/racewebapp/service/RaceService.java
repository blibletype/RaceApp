package com.race.racewebapp.service;

import com.race.racewebapp.model.Race;
import com.race.racewebapp.repository.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RaceService {
    @Autowired
    private RaceRepository raceRepository;

    public List<Race> findAll() {
        return raceRepository.findAll();
    }

   public List<Race> findRaceById(String id) {
        return raceRepository.findRaceById(id);
   }

    public List<Race> findByCarOwner(String carOwner) {
        return raceRepository.findByCarOwner(carOwner);
    }

    public List<Race> sortedList() {
        return raceRepository.findRacesByOrderByTime();
    }

    public void deleteById(String id) {
        raceRepository.deleteById(id);
    }

    public void save(Race race) {
        raceRepository.save(race);
    }

}
