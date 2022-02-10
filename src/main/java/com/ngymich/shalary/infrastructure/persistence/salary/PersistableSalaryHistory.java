package com.ngymich.shalary.infrastructure.persistence.salary;


import com.fasterxml.jackson.annotation.*;
import com.ngymich.shalary.infrastructure.persistence.user.PersistableUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "salary_history")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersistableSalaryHistory implements Serializable {
    private static final long serialVersionUID = 101L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonBackReference
    @OneToOne(mappedBy = "salaryHistory", fetch = FetchType.EAGER)
    private PersistableUser user;


    @Column(name = "salary_currency")
    private String salaryCurrency;

    @Column(name = "total_years_of_experience")
    private float totalYearsOfExperience;

    @JsonIgnore
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToMany(targetEntity = PersistableSalaryInfo.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PersistableSalaryInfo> salaryInfos = new ArrayList<PersistableSalaryInfo>();
}
