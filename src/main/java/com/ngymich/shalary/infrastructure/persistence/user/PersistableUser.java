package com.ngymich.shalary.infrastructure.persistence.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ngymich.shalary.application.user.UserDTO;
import com.ngymich.shalary.infrastructure.persistence.salary.PersistableSalaryHistory;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "user", schema = "public")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersistableUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    @NonNull
    private String email;

    @Column(name = "password")
    @NonNull
    private String password;

    @Column(name = "username")
    @NonNull
    private String username;

    @Column(name = "main_sector")
    private String mainSector;

    @Column(name = "location")
    private String location;

    @Column(name = "education")
    private String education;

    @Column(name = "city")
    private String city;

    @Column(name = "age")
    private Integer age;

    @Column(name = "gender")
    private String gender;

    @Column(name = "comment", length = 5000)
    private String comment;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private PersistableSalaryHistory salaryHistory;

    @Column(name = "created_date", nullable = false, updatable = false)
    protected LocalDateTime createdDate;

    @Column(name = "modified_date")
    protected LocalDateTime modifiedDate;

    @Column(name = "provider")
    private String provider;

    @Column(name = "thumbs_up")
    private Integer thumbsUp;

    @Column(name = "thumbs_down")
    private Integer thumbsDown;


    @Column(name = "validated")
    private boolean validated = false;

    @PrePersist
    private void prePersist() {
        if (salaryHistory != null) {
            this.salaryHistory.setUser(this);
        }
    }

}
