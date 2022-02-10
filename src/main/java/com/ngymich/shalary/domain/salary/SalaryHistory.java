package com.ngymich.shalary.domain.salary;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ngymich.shalary.domain.user.User;
import lombok.Data;

import javax.persistence.*;

@JsonIgnoreProperties
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

    @OneToOne(mappedBy = "salaryHistory")
    private User user;


    @Column(name = "salary_currency")
    private String salaryCurrency;

    @Column(name = "total_years_of_experience")
    private float totalYearsOfExperience;

//    @Column
//    private HashMap<Float, SalaryInfo> salaries;
}
