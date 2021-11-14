package com.task.year.mapper;

import com.task.year.dto.LordDTO;
import com.task.year.entity.Lord;

public class LordDTOMapper {

    public static LordDTO mapFromLord(Lord lord){

        return new LordDTO(lord.getId(),
                           lord.getName(),
                           lord.getAge());
    }
}
