package com.task.year.component;

import com.task.year.exception.BadRequestException;
import com.task.year.repository.PlanetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PlanetNameAvailabilityChecker {

    @Autowired
    private PlanetRepository planetRepository;

    public void check(String planetName){
        log.info("Checking if planet is already registered with such planet name: {}", planetName);

        if(planetRepository.existsByName(planetName)){
            throw new BadRequestException("Planet is already created with such name");
        }
    }
}
