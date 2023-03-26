package com.ngymich.shalary.infrastructure.persistence.user;

import com.ngymich.shalary.infrastructure.persistence.salary.PersistableSalaryHistory;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
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

    @Column(name = "city")
    private String city;

    @Column(name = "education")
    private String education;

    @Column(name = "age")
    private Integer age;

    @Column(name = "gender")
    private String gender;

    @Column(name = "comment")
    private String comment;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private PersistableSalaryHistory salaryHistory;

    @Column(name = "last_update_timestamp")
    private LocalDateTime lastUpdateTimestamp;


    @Column(name = "created_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date modifiedDate;

    private String provider;
    @Column(name = "provider_user_id")
    private String providerUserId;
    @Column(name = "display_name")
    private String displayName;

    private String email;

    @Column(name = "enabled", columnDefinition = "BIT", length = 1)
    private boolean enabled;
}
