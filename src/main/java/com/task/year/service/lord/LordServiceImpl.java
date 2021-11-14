package com.task.year.service.lord;

import com.task.year.component.GivenLordExistsChecker;
import com.task.year.component.GivenPlanetExistsChecker;
import com.task.year.component.LordNameAvailabilityChecker;
import com.task.year.dto.LordDTO;
import com.task.year.entity.Lord;
import com.task.year.entity.Planet;
import com.task.year.mapper.LordDTOMapper;
import com.task.year.mapper.LordMapper;
import com.task.year.payload.LordCreationPayload;
import com.task.year.repository.LordRepository;
import com.task.year.repository.PlanetRepository;
import com.task.year.service.planet.PlanetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LordServiceImpl implements LordService{

    private LordRepository lordRepository;
    private PlanetRepository planetRepository;
    private LordNameAvailabilityChecker lordNameAvailabilityChecker;
    private GivenLordExistsChecker givenLordExistsChecker;
    private GivenPlanetExistsChecker givenPlanetExistsChecker;

    @Autowired
    public LordServiceImpl(LordRepository lordRepository,
                           PlanetRepository planetRepository,
                           LordNameAvailabilityChecker lordNameAvailabilityChecker,
                           GivenLordExistsChecker givenLordExistsChecker,
                           GivenPlanetExistsChecker givenPlanetExistsChecker){

        this.lordRepository = lordRepository;
        this.planetRepository = planetRepository;
        this.lordNameAvailabilityChecker = lordNameAvailabilityChecker;
        this.givenLordExistsChecker = givenLordExistsChecker;
        this.givenPlanetExistsChecker = givenPlanetExistsChecker;
    }

    @Override
    public Lord createLord(LordCreationPayload lordCreationPayload){
        log.info("Creating a user: {}", lordCreationPayload);
        lordNameAvailabilityChecker.check(lordCreationPayload.getName());

        Lord lord = LordMapper.mapLordFromLordCreationPayload(lordCreationPayload);

        return lordRepository.save(lord);
    }



    @Override
    public void appointLordToPlanet(long lordId, long planetId) {
        log.info("Appoint Lord to planet: {}", planetId);

        givenLordExistsChecker.check(lordId);
        givenPlanetExistsChecker.check(planetId);

        Lord lord = lordRepository.getById(lordId);
        Planet planet = planetRepository.getById(planetId);

        planet.setLord(lord);
        lord.addPlanets(planet);
        planetRepository.save(planet);

    }

    @Override
    public List<LordDTO> getIdleLords() {
        log.info("Getting the idle lords");

       return lordRepository.findAll().stream().filter(x-> x.getPlanets().isEmpty()||x.getPlanets()==null).
                map(LordDTOMapper :: mapFromLord).collect(Collectors.toList());

    }

    @Override
    public List<LordDTO> getTenTheYoungestLords() {

        List<Lord> youngestLords = lordRepository.getTenTheYoungestLords();

        return youngestLords.stream().map(LordDTOMapper :: mapFromLord).
                collect(Collectors.toList());
    }
}
