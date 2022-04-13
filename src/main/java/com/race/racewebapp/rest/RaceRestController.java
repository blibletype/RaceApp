package com.race.racewebapp.rest;

import com.race.racewebapp.model.CurrentRace;
import com.race.racewebapp.model.Race;
import com.race.racewebapp.model.Response;
import com.race.racewebapp.model.TrafficLight;
import com.race.racewebapp.service.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.race.racewebapp.model.TrafficLightColor.*;
import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/races")
public class RaceRestController {
    @Autowired
    private RaceService raceService;

    private final Long counter = 0L;
    private String token;

    @GetMapping("/list")
    public ResponseEntity<Response> getRaces() {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(raceService.findAll())
                        .message("Races retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

//    @GetMapping("/list")
//    public List<Race> getRaces() {
//        return raceService.findAll();
//    }

    @GetMapping("/search/id/{id}")
    @PreAuthorize("hasAnyAuthority('races:read')")
    public List<Race> getById(@PathVariable String id) {
        return raceService.findRaceById(id);
    }

    @GetMapping("/search/carOwner/{carOwner}")
    @PreAuthorize("hasAnyAuthority('races:read')")
    public List<Race> getByCarOwner(@PathVariable String carOwner) {
        return raceService.findByCarOwner(carOwner);
    }

    @GetMapping("/sortedByTime")
    public ResponseEntity<Response> getSortedList() {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(raceService.sortedList())
                        .message("Sorted races retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('races:write')")
    public Race create(@RequestBody Race race) {
        raceService.save(race);
        return race;
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('races:write')")
    public void deleteById(@PathVariable String id) {
        raceService.deleteById(id);
    }

    @GetMapping("/start")
    @PreAuthorize("hasAnyAuthority('races:write')")
    public void startRace() {
        CurrentRace.raceStatus = "raceStarted";
    }

    @GetMapping("/stop")
    @PreAuthorize("hasAnyAuthority('races:write')")
    public void stopRace() {
        CurrentRace.raceStatus = "raceEnded";
    }

    @GetMapping("/trafficlights/green")
    @PreAuthorize("hasAnyAuthority('races:write')")
    public void toggleTrafficlightsToGreen() {
        CurrentRace.trafficLightStatus = GREEN.name();
    }

    @GetMapping("/trafficlights/yellow")
    @PreAuthorize("hasAnyAuthority('races:write')")
    public void toggleTrafficlightsToYellow() {
        CurrentRace.trafficLightStatus = YELLOW.name();
    }

    @GetMapping("/trafficlights/red")
    @PreAuthorize("hasAnyAuthority('races:write')")
    public void toggleTrafficlightsToRed() {
        CurrentRace.trafficLightStatus = RED.name();
    }

    @GetMapping("/trafficlightstatus")
    public TrafficLight getTrafficLightStatus() {
        TrafficLight trafficLight = new TrafficLight(CurrentRace.trafficLightStatus);
        return trafficLight;
    }

    @GetMapping("/car")
    @PreAuthorize("hasAnyAuthority('races:write')")
    public ResponseEntity<Response> carToken(@RequestParam String carToken) {
        token = carToken;
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .message("Car Token retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/status")
    @PreAuthorize("hasAnyAuthority('races:read')")
    public ResponseEntity<Response> getRaceStatus() {
        return ResponseEntity.ok(
                Response.builder()
                        .raceStatus(CurrentRace.raceStatus)
                        .message("Car Status retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(now())
                        .build()
        );
    }

    @GetMapping("/getToken")
    @PreAuthorize("hasAnyAuthority('races:write')")
    public ResponseEntity<Response> getToken() {
        return ResponseEntity.ok(
                Response.builder()
                        .token(token)
                        .status(OK)
                        .statusCode(OK.value())
                        .timeStamp(now())
                        .build()
        );
    }
}
