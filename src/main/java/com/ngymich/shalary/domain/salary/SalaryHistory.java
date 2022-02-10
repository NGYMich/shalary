package com.ngymich.shalary.domain.salary;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ngymich.shalary.domain.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "salary_histories")
public class SalaryHistory {

    public SalaryHistory() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Column(name = "salary_currency")
    private String salaryCurrency;

    @Column(name = "total_years_of_experience")
    private float totalYearsOfExperience;

    @JsonIgnore
    @OneToMany(mappedBy="salaryHistory", targetEntity = SalaryInfo.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SalaryInfo> salaryInfos = new ArrayList<SalaryInfo>();
}
