package com.ngymich.shalary.infrastructure.persistence.salary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Data
@Entity
@Table(name = "salary_infos")
public class PersistableSalaryInfo {

    enum JobLevel { Junior, Intermediate, Senior }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long salaryInfoId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "salaryHistory_id", referencedColumnName = "id", insertable = false, updatable = false)
    private PersistableSalaryHistory salaryHistory;

    @Enumerated(EnumType.STRING)
    @Column(name = "job_level") private JobLevel jobLevel;
    @Column(name = "job_name") private String jobName;
    @Column(name = "base_salary") private Double baseSalary;
    @Column(name = "stock_salary") private Double stockSalary;
    @Column(name = "bonus_salary") private Double bonusSalary;
}
