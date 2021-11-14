package com.task.year.service;

import com.task.year.component.GivenLordExistsChecker;
import com.task.year.component.GivenPlanetExistsChecker;
import com.task.year.component.LordNameAvailabilityChecker;
import com.task.year.dto.LordDTO;
import com.task.year.entity.Lord;
import com.task.year.entity.Planet;
import com.task.year.exception.BadRequestException;
import com.task.year.exception.ResourceNotFoundException;
import com.task.year.mapper.LordMapper;
import com.task.year.payload.LordCreationPayload;
import com.task.year.repository.LordRepository;
import com.task.year.repository.PlanetRepository;
import com.task.year.service.lord.LordServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class LordServiceImplTest {

    @Mock
    private LordRepository lordRepository;

    @Mock
    private LordNameAvailabilityChecker lordNameAvailabilityChecker;

    @Mock
    private GivenLordExistsChecker givenLordExistsChecker;

    @Mock
    private GivenPlanetExistsChecker givenPlanetExistsChecker;

    @Mock
    private PlanetRepository planetRepository;

    @Autowired
    @InjectMocks
    private LordServiceImpl underTest;

    private LordCreationPayload lordCreationPayload = new LordCreationPayload();

    private List<Lord> lords = new ArrayList<>();

    private Lord lord = new Lord();

    private Planet planet = new Planet();


    @BeforeEach
    void setUp() {
        lordCreationPayload.setName("Rumata");
        lordCreationPayload.setAge(35);

        Lord lord1 = new Lord();
        lord1.setId(1);
        lord1.setName("Lord1 Test");
        lord1.setAge(20);

        Lord lord2 = new Lord();
        lord2.setId(2);
        lord2.setName("Lord2 Test");
        lord2.setAge(25);

        Lord lord3 = new Lord();
        lord3.setId(2);
        lord3.setName("Lord3 Test");
        lord3.setAge(30);

        lords.add(lord1);
        lords.add(lord2);
        lords.add(lord3);

        Planet planet1 = new Planet();
        planet1.setName("Planet Test");
        lord1.addPlanets(planet1);

    }

    void tearDown(){
        lords = null;
        lord = null;
        lordCreationPayload = null;
        planet = null;
    }


    @Test
    void lordCreation_success(){

        Mockito.doNothing().when(lordNameAvailabilityChecker).check(lordCreationPayload.getName());
        Mockito.doReturn(LordMapper.mapLordFromLordCreationPayload(lordCreationPayload)).
                when(lordRepository).save(ArgumentMatchers.any());

        Lord lord = underTest.createLord(lordCreationPayload);

        Assertions.assertNotNull(lord);
        assertEquals(lordCreationPayload.getName(), lord.getName());
        assertEquals(lordCreationPayload.getAge(), lord.getAge());

    }

    @Test
    void creationFail_beacuseOfNameIsAlreadyTaken(){
        Mockito.doThrow(BadRequestException.class).when(lordNameAvailabilityChecker).check(ArgumentMatchers.anyString());
        Assertions.assertThrows(BadRequestException.class, () -> underTest.createLord(lordCreationPayload));
    }


    @Test
    void appointLordToPlanet_success() {

        Mockito.doNothing().when(givenLordExistsChecker).check(lord.getId());
        Mockito.doNothing().when(givenPlanetExistsChecker).check(planet.getId());
        Mockito.doReturn(lord).when(lordRepository).getById(ArgumentMatchers.anyLong());
        Mockito.doReturn(planet).when(planetRepository).getById(ArgumentMatchers.anyLong());

        underTest.appointLordToPlanet(lord.getId(), planet.getId());

        ArgumentCaptor<Planet> planetArgumentCaptor = ArgumentCaptor.forClass(Planet.class);

        verify(planetRepository).save(planetArgumentCaptor.capture());
        Planet capturedPlanet = planetArgumentCaptor.getValue();

        assertThat(capturedPlanet).isEqualTo(planet);

    }

    @Test
    void appointFail_beacuseOfResourcesNotFound(){
        Mockito.doThrow(ResourceNotFoundException.class).when(givenLordExistsChecker).check(ArgumentMatchers.anyLong());
//        Mockito.doThrow(ResourceNotFoundException.class).when(givenPlanetExistsChecker).check(ArgumentMatchers.anyLong());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> underTest.appointLordToPlanet(lord.getId(), planet.getId()));
    }

    @Test
    void getIdleLords() {

        Mockito.doReturn(lords.stream().filter(l -> l.getPlanets().isEmpty()||l.getPlanets()==null).
                collect(Collectors.toList())).when(lordRepository).findAll();


        List<LordDTO> idleLords = underTest.getIdleLords();

        Assertions.assertEquals(2, idleLords.size());
        Assertions.assertEquals(lords.get(1).getId(), idleLords.get(0).getId());
        Assertions.assertEquals(lords.get(2).getId(), idleLords.get(1).getId());


    }

}