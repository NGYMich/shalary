package com.ngymich.shalary.domain.salary;

import com.ngymich.shalary.infrastructure.persistence.salary.SalaryHistoryJpaRepository;
import com.ngymich.shalary.infrastructure.persistence.salary.SalaryInfosJpaRepository;
import com.ngymich.shalary.infrastructure.persistence.salary.PersistableSalaryHistory;
import com.ngymich.shalary.infrastructure.persistence.salary.PersistableSalaryInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryService {

    private final SalaryHistoryJpaRepository salaryHistoryRepository;
    private final SalaryInfosJpaRepository salaryInfosRepository;

    public SalaryService(SalaryHistoryJpaRepository salaryHistoryRepository, SalaryInfosJpaRepository salaryInfosRepository) {
        this.salaryHistoryRepository = salaryHistoryRepository;
        this.salaryInfosRepository = salaryInfosRepository;
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
