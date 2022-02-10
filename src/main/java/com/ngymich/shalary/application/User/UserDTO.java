package com.ngymich.shalary.application.User;

import com.ngymich.shalary.infrastructure.persistence.salary.PersistableSalaryHistory;
import com.ngymich.shalary.infrastructure.persistence.user.PersistableUser;
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

    private int age;
    private PersistableUser.Gender gender;

    private PersistableSalaryHistory salaryHistory;
}
