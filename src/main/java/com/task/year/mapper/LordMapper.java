package com.task.year.mapper;

import com.task.year.entity.Lord;
import com.task.year.payload.LordCreationPayload;

public class LordMapper {

    public static Lord mapLordFromLordCreationPayload(LordCreationPayload lordCreationPayload){

        return new Lord(lordCreationPayload.getName(),
                        lordCreationPayload.getAge());
    }
}
