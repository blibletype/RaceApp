package com.race.racewebapp.repository;

import com.race.racewebapp.model.Race;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RaceRepository extends MongoRepository<Race, String> {
    List<Race> findByCarOwner(String carOwner);
    List<Race> findRaceById(String id);
    List<Race> findRacesByOrderByTime();
}
