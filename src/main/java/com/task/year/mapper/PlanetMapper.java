package com.task.year.mapper;

import com.task.year.entity.Planet;
import com.task.year.payload.PlanetCreationPayload;

public class PlanetMapper {

    public static Planet mapPlanetFromPlanetCreationPayload(PlanetCreationPayload planetCreationPayload){

        return new Planet(planetCreationPayload.getName());
    }
}
