package com.task.year.controller;

import com.task.year.payload.LordCreationPayload;
import com.task.year.service.lord.LordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
@RequestMapping(value = "api/lord")
public class LordController {

    private LordService lordService;

    @Autowired
    public LordController(LordService lordService){
        this.lordService = lordService;
    }

    @PostMapping
    public ResponseEntity<?> addLord(@Valid @RequestBody LordCreationPayload lordCreationPayload) {

        lordService.createLord(lordCreationPayload);
        return ResponseEntity.ok("Lord created succesfully");
    }

    @PostMapping(value = "{lordId}/planets/{planetId}/appoint")
    public ResponseEntity<?> appointLordToPlanet(@PathVariable Long lordId,
                                                 @PathVariable Long planetId)  {

        lordService.appointLordToPlanet(lordId, planetId);
        return ResponseEntity.ok("Lord succesfully appointed to the planet");
    }

    @GetMapping(value = "idle-lords")
    public ResponseEntity<?> getIdleLords(){

        return ResponseEntity.ok(lordService.getIdleLords());

    }

    @GetMapping(value = "ten-youngest")
    public ResponseEntity<?> getTenTheYoungestLords(){

        return ResponseEntity.ok(lordService.getTenTheYoungestLords());
    }

}
