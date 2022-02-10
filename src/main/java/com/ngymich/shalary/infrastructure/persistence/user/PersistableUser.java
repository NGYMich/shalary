package com.ngymich.shalary.infrastructure.persistence.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ngymich.shalary.infrastructure.persistence.salary.PersistableSalaryHistory;
import lombok.*;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class PersistableUser {
    public enum Gender {MALE, FEMALE}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.PERSIST, mappedBy = "user")
    private PersistableSalaryHistory salaryHistory;

    @Column(name = "username")
    @NonNull
    private String username;

    @Column(name = "password")
    @NonNull
    private String password;

    @Column(name = "mail")
    @NonNull
    private String mail;


    @Column(name = "main_sector")
    private String mainSector;

    @Column(name = "location")
    private String location;

    @Column(name = "education")
    private String education;


    @Column(name = "age")
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

}
