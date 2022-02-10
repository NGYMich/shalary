package com.ngymich.shalary.domain.salary;

import com.ngymich.shalary.domain.company.Company;
import lombok.Data;
import lombok.NonNull;

@Data
public class SalaryInfo {
    public enum JobLevel { Junior, Intermediate, Senior }

    private long salaryInfoId;
    private JobLevel jobLevel;
    private String jobName;
    private Company company;
    private Double baseSalary;
    private Double stockSalary;
    private Double bonusSalary;
}
