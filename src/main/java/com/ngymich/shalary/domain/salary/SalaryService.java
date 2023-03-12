package com.ngymich.shalary.domain.salary;

import com.ngymich.shalary.infrastructure.persistence.salary.SalaryHistoryJpaRepository;
import com.ngymich.shalary.infrastructure.persistence.salary.SalaryInfosJpaRepository;
import com.ngymich.shalary.infrastructure.persistence.salary.PersistableSalaryHistory;
import com.ngymich.shalary.infrastructure.persistence.salary.PersistableSalaryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SalaryService {

    @Autowired private Environment env;
    private final SalaryHistoryJpaRepository salaryHistoryRepository;
    private final SalaryInfosJpaRepository salaryInfosRepository;

    public SalaryService(SalaryHistoryJpaRepository salaryHistoryRepository, SalaryInfosJpaRepository salaryInfosRepository, Environment env) {
        this.salaryHistoryRepository = salaryHistoryRepository;
        this.salaryInfosRepository = salaryInfosRepository;
        this.env = env;
        System.out.println("Current active profiles : " + Arrays.toString(env.getActiveProfiles()));
    }


    public List<PersistableSalaryHistory> getSalaryHistories() {
        return this.salaryHistoryRepository.findAll();
    }

    public List<PersistableSalaryInfo> getSalaryInfos() {
        return this.salaryInfosRepository.findAll();
    }


    public PersistableSalaryInfo addSalaryInfo(PersistableSalaryInfo salaryInfo) {
        return this.salaryInfosRepository.save(salaryInfo);
    }
}
