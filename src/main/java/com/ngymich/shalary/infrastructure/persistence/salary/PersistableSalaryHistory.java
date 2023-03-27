package com.ngymich.shalary.infrastructure.persistence.salary;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ngymich.shalary.infrastructure.persistence.user.PersistableUser;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "salary_history")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersistableSalaryHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonBackReference
    @OneToOne(mappedBy = "salaryHistory", fetch = FetchType.EAGER)
    private PersistableUser user;


    @Column(name = "salary_currency")
    private String salaryCurrency = "";

    @Column(name = "total_years_of_experience")
    private float totalYearsOfExperience = 0F;

    @OneToMany(targetEntity = PersistableSalaryInfo.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "salary_history_id", referencedColumnName = "id")
    private List<PersistableSalaryInfo> salaryInfos = new ArrayList<>();
}
