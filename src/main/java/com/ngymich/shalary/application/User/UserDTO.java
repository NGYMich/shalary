package com.ngymich.shalary.application.User;

import com.ngymich.shalary.infrastructure.persistence.salary.PersistableSalaryHistory;
import lombok.Data;
import lombok.NonNull;

@Data
public class UserDTO {

    @NonNull private String username;
    @NonNull private String password;
    @NonNull private String mail;
    private String mainSector;
    private String location;
    private String education;
    private boolean validated = false;

    private int age;
    private String gender;

    private PersistableSalaryHistory salaryHistory;
}
