package com.task.year.component;

import com.task.year.exception.ResourceNotFoundException;
import com.task.year.repository.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GivenPlanetExistsChecker {

    @Autowired
    private PlanetRepository planetRepository;

    public void check(long planetId){
        if (!planetRepository.existsById(planetId)){
            throw new ResourceNotFoundException("Planet not found with such id: " + planetId);
        }
    }
}
