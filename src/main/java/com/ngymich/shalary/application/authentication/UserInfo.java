package com.ngymich.shalary.application.authentication;

import java.time.LocalDate;
import java.util.List;

import com.ngymich.shalary.infrastructure.persistence.salary.PersistableSalaryHistory;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserInfo {
    String id;
    String email;
    String username;

    boolean hasSalaryHistory;

    String mainSector;
    String location;
    String locationImage;
    String city;
    String education;
    Integer age;
    String gender;
    String comment;
    PersistableSalaryHistory salaryHistory;
    LocalDate createdDate;
    LocalDate modifiedDate;
    String provider;
    Integer thumbsUp = 0;
    Integer thumbsDown = 0;
    boolean validated = false;
}
