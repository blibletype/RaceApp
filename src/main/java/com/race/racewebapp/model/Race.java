package com.race.racewebapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Race {
    @Id
    private String id;
    private String carOwner;
    private String time;
    private String date;
    private String carToken;
}
