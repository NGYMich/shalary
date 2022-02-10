package com.ngymich.shalary.infrastructure.persistence.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ngymich.shalary.infrastructure.persistence.salary.PersistableSalaryHistory;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersistableUser implements Serializable {
    private static final long serialVersionUID = 102L;

    public enum Gender {MALE, FEMALE}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
//    @JsonIgnoreProperties("salaryHistory")
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
