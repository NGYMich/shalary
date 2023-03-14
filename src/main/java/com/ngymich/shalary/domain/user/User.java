package com.ngymich.shalary.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ngymich.shalary.infrastructure.persistence.salary.PersistableSalaryHistory;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class User {
    private Long id;
    private boolean validated = false;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String mail;
    private String mainSector;
    private String location;
    private String locationImage;
    private String education;
    private int age;
    private String gender;
    private String comment;
    private PersistableSalaryHistory salaryHistory;
    private LocalDate lastUpdate;
}
