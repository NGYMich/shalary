package com.ngymich.shalary.domain.salary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ngymich.shalary.domain.company.Company;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Data
@Entity
@Table(name = "salary_infos")
public class SalaryInfo {

    enum JobLevel { Junior, Intermediate, Senior }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long salaryInfoId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "salaryHistory_id", referencedColumnName = "id", insertable = false, updatable = false)
    private SalaryHistory salaryHistory;

    @Enumerated(EnumType.STRING)
    @Column(name = "job_level") private JobLevel jobLevel;
    @Column(name = "job_name") private String jobName;
    @Column(name = "base_salary") private Double baseSalary;
    @Column(name = "stock_salary") private Double stockSalary;
    @Column(name = "bonus_salary") private Double bonusSalary;
}
