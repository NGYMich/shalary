package com.ngymich.shalary.domain.salary;

import com.ngymich.shalary.infrastructure.persistence.salary.ISalaryHistoryRepository;
import com.ngymich.shalary.infrastructure.persistence.salary.ISalaryInfosRepository;
import com.ngymich.shalary.infrastructure.persistence.salary.PersistableSalaryHistory;
import com.ngymich.shalary.infrastructure.persistence.salary.PersistableSalaryInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryService {

    private final ISalaryHistoryRepository salaryHistoryRepository;
    private final ISalaryInfosRepository salaryInfosRepository;

    public SalaryService(ISalaryHistoryRepository salaryHistoryRepository, ISalaryInfosRepository salaryInfosRepository) {
        this.salaryHistoryRepository = salaryHistoryRepository;
        this.salaryInfosRepository = salaryInfosRepository;
    }


    public List<PersistableSalaryHistory> getSalaryHistories() {
        return this.salaryHistoryRepository.findAll();
    }
    public List<PersistableSalaryInfo> getSalaryInfos() {
        return this.salaryInfosRepository.findAll();
    }




}
