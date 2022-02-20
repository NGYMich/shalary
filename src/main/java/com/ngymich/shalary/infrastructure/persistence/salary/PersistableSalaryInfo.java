package com.ngymich.shalary.infrastructure.persistence.salary;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ngymich.shalary.infrastructure.persistence.company.PersistableCompany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Data
@Entity
@Table(name = "salary_info")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersistableSalaryInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    private PersistableSalaryHistory salaryHistory;

    @Column(name = "years_of_experience")
    private Float yearsOfExperience = 0F;

    @Column(name = "job_level")
    private String jobLevel;

    @Column(name = "job_name")
    private String jobName;

    @Column(name = "base_salary")
    private Double baseSalary = 0.0;

    @Column(name = "stock_salary")
    private Double stockSalary = 0.0;

    @Column(name = "bonus_salary")
    private Double bonusSalary = 0.0;

    @Column(name = "total_salary")
    private Double totalSalary;

    @Column(name = "net_total_salary")
    private Double netTotalSalary;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private PersistableCompany company;
}
