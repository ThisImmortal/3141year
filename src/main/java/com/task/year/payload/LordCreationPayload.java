package com.task.year.payload;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LordCreationPayload {

    @NotBlank(message = "Name may not be blank")
    private String name;

    private long age;
}
