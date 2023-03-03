package com.ngymich.shalary.application.User;

import com.ngymich.shalary.infrastructure.persistence.salary.PersistableSalaryHistory;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class UserDTO {

    private Long id;
    @NonNull private String username;
    @NonNull private String password;
    @NonNull private String mail;
    private String mainSector;
    private String location;
    private String education;
    private boolean validated = false;

    private int age;
    private String gender;
    private String comment;

    private PersistableSalaryHistory salaryHistory;
}
