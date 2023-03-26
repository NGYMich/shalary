package com.ngymich.shalary.application.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ngymich.shalary.infrastructure.persistence.salary.PersistableSalaryHistory;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@Builder
public class UserDTO {

    private Long id;
    @NonNull private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) private String password;
    private String username;

    private String mainSector;
    private String location;
    private String locationImage;
    private String city;
    private String education;
    private Integer age;
    private String gender;
    private String comment;
    private PersistableSalaryHistory salaryHistory;
    private LocalDate createdDate;
    private LocalDate modifiedDate;
    private String provider;
    private Integer thumbsUp = 0;
    private Integer thumbsDown = 0;
    private boolean validated = false;


}
