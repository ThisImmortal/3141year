package com.task.year.service.planet;

import com.task.year.component.GivenPlanetExistsChecker;
import com.task.year.component.PlanetNameAvailabilityChecker;
import com.task.year.entity.Planet;
import com.task.year.mapper.PlanetMapper;
import com.task.year.payload.PlanetCreationPayload;
import com.task.year.repository.PlanetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class PlanetServiceImpl implements PlanetService{

    private PlanetRepository planetRepository;
    private PlanetNameAvailabilityChecker planetNameAvailabilityChecker;
    private GivenPlanetExistsChecker givenPlanetExistsChecker;


    @Autowired
    public PlanetServiceImpl(PlanetRepository planetRepository,
                             PlanetNameAvailabilityChecker planetNameAvailabilityChecker,
                             GivenPlanetExistsChecker givenPlanetExistsChecker){

        this.planetRepository = planetRepository;
        this.planetNameAvailabilityChecker = planetNameAvailabilityChecker;
        this.givenPlanetExistsChecker = givenPlanetExistsChecker;
    }

    @Override
    public Planet createPlanet(PlanetCreationPayload planetCreationPayload) {
        log.info("Creating a user: {}", planetCreationPayload.getName());

        planetNameAvailabilityChecker.check(planetCreationPayload.getName());

        Planet planet = PlanetMapper.mapPlanetFromPlanetCreationPayload(planetCreationPayload);

        return planetRepository.save(planet);
    }


    @Override
    public void deletePlanet(long id) {
        log.info("Deleting a planet with id: {}", id);

        givenPlanetExistsChecker.check(id);

        planetRepository.deleteById(id);
    }
}
