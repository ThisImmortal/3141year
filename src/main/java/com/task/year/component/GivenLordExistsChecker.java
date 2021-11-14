package com.task.year.component;


import com.task.year.exception.ResourceNotFoundException;
import com.task.year.repository.LordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GivenLordExistsChecker {

    @Autowired
    private LordRepository lordRepository;

    public void check(long lordId){
        if (!lordRepository.existsById(lordId)){
            throw new ResourceNotFoundException("Lord not found with such id: " + lordId);
        }
    }
}
