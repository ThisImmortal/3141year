package com.task.year.component;

import com.task.year.exception.BadRequestException;
import com.task.year.repository.LordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LordNameAvailabilityChecker {

    @Autowired
    private LordRepository lordRepository;

    public void check(String lordName){
        log.info("Checking if lord is already registered with such lord name: {}", lordName);

        if(lordRepository.existsByName(lordName)){
            throw new BadRequestException("Lord is already created with such name");
        }

    }
}
