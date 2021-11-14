package com.task.year.service.planet;

import com.task.year.entity.Planet;
import com.task.year.payload.PlanetCreationPayload;

public interface PlanetService {

    Planet createPlanet(PlanetCreationPayload planetCreationPayload);
    void deletePlanet(long id);
}
