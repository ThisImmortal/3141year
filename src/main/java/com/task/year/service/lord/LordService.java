package com.task.year.service.lord;

import com.task.year.dto.LordDTO;
import com.task.year.entity.Lord;
import com.task.year.payload.LordCreationPayload;

import java.util.List;

public interface LordService {
    Lord createLord(LordCreationPayload lordCreationPayload);

    void appointLordToPlanet(long lordId, long planetId);

    List<LordDTO> getIdleLords();

    List<LordDTO> getTenTheYoungestLords();
}
