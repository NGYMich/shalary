package com.ngymich.shalary.application.User;

import com.ngymich.shalary.domain.salary.SalaryHistory;
import com.ngymich.shalary.domain.user.User;
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
    private User.Gender gender;

    private SalaryHistory salaryHistory;
}
