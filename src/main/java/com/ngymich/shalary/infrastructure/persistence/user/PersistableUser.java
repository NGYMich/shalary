package com.ngymich.shalary.infrastructure.persistence.user;

import com.ngymich.shalary.infrastructure.persistence.salary.PersistableSalaryHistory;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_entity")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersistableUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "validated")
    private boolean validated = false;

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

    @Column(name = "gender")
    private String gender;

    @Column(name = "comment")
    private String comment;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private PersistableSalaryHistory salaryHistory;
}
