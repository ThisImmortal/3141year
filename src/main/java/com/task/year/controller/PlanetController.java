package com.task.year.controller;

import com.task.year.entity.Planet;
import com.task.year.payload.PlanetCreationPayload;
import com.task.year.service.planet.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/planet")
public class PlanetController {

    private PlanetService planetService;

    @Autowired
    public PlanetController(PlanetService planetService){
        this.planetService = planetService;
    }

    @PostMapping
    public ResponseEntity<?> addPlanet(@Valid @RequestBody PlanetCreationPayload planetCreationPayload){
        Planet planet = planetService.createPlanet(planetCreationPayload);

        return ResponseEntity.ok("Planet created succesfully: ");
    }

    @DeleteMapping(value = "{planetId}")
    public ResponseEntity<?> deletePlanet(@PathVariable("planetId") long id)  {

        planetService.deletePlanet(id);
        return ResponseEntity.ok("Planet was destroyed succesfully!");
    }



}
