package com.ngymich.shalary.infrastructure.persistence.salary;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "salary_info")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersistableSalaryInfo implements Serializable {
    private static final long serialVersionUID = 100L;

    enum JobLevel { Junior, Intermediate, Senior }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long salaryInfoId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @NotFound(action = NotFoundAction.IGNORE)
    private PersistableSalaryHistory salaryHistory;

    @Enumerated(EnumType.STRING)
    @Column(name = "job_level") private JobLevel jobLevel;
    @Column(name = "job_name") private String jobName;
    @Column(name = "base_salary") private Double baseSalary;
    @Column(name = "stock_salary") private Double stockSalary;
    @Column(name = "bonus_salary") private Double bonusSalary;
    @Column(name = "total_salary") private Double totalSalary;
    @Column(name = "net_total_salary") private Double netTotalSalary;
}
