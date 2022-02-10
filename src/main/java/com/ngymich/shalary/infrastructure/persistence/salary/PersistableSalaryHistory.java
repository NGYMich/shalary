package com.ngymich.shalary.infrastructure.persistence.salary;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ngymich.shalary.infrastructure.persistence.user.PersistableUser;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "salary_histories")
public class PersistableSalaryHistory {

    public PersistableSalaryHistory() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private PersistableUser user;


    @Column(name = "salary_currency")
    private String salaryCurrency;

    @Column(name = "total_years_of_experience")
    private float totalYearsOfExperience;

    @JsonIgnore
    @OneToMany(mappedBy="salaryHistory", targetEntity = PersistableSalaryInfo.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PersistableSalaryInfo> salaryInfos = new ArrayList<PersistableSalaryInfo>();
}
