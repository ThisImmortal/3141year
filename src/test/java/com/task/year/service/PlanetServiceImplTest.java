package com.task.year.service;

import com.task.year.component.GivenPlanetExistsChecker;
import com.task.year.component.PlanetNameAvailabilityChecker;
import com.task.year.entity.Planet;
import com.task.year.exception.BadRequestException;
import com.task.year.exception.ResourceNotFoundException;
import com.task.year.mapper.PlanetMapper;
import com.task.year.payload.PlanetCreationPayload;
import com.task.year.repository.PlanetRepository;
import com.task.year.service.planet.PlanetServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PlanetServiceImplTest {

    @Mock
    private PlanetRepository planetRepository;

    @Mock
    private PlanetNameAvailabilityChecker planetNameAvailabilityChecker;

    @Mock
    private GivenPlanetExistsChecker givenPlanetExistsChecker;

    @Autowired
    @InjectMocks
    private PlanetServiceImpl underTest;

    private PlanetCreationPayload planetCreationPayload = new PlanetCreationPayload();

    Planet planet = new Planet();

    @BeforeEach
    void setUp(){
        planetCreationPayload.setName("Arkanar");

        planet.setId(1);
        planet.setName("Planet 1 Test");

    }

    @AfterEach
    void tearDown(){
        planetCreationPayload = null;
        planet = null;
    }


    @Test
    void planetCreation_success() {

        Mockito.doNothing().when(planetNameAvailabilityChecker).check(planetCreationPayload.getName());
        Mockito.doReturn(PlanetMapper.mapPlanetFromPlanetCreationPayload(planetCreationPayload)).
                when(planetRepository).save(ArgumentMatchers.any());

        Planet planet = underTest.createPlanet(planetCreationPayload);
        Assertions.assertNotNull(planet);
        Assertions.assertEquals(planetCreationPayload.getName(), planet.getName());

    }

    @Test
    void creationFail_beacuseOfNameIsAlreadyTaken(){

        Mockito.doThrow(BadRequestException.class).when(planetNameAvailabilityChecker).check(ArgumentMatchers.anyString());
        Assertions.assertThrows(BadRequestException.class, () -> underTest.createPlanet(planetCreationPayload));

    }


    @Test
    void planetDelete_success() {

        Mockito.doNothing().when(givenPlanetExistsChecker).check(planet.getId());

        //when
        underTest.deletePlanet(planet.getId());

        //then
        verify(planetRepository).deleteById(planet.getId());

    }

    @Test
    void deleteFail_beacuseOfPlanetNotFound(){

        Mockito.doThrow(ResourceNotFoundException.class).when(givenPlanetExistsChecker).check(ArgumentMatchers.anyLong());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> underTest.deletePlanet(planet.getId()));
    }
}