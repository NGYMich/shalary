package com.ngymich.shalary.domain.salary;

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


    public List<SalaryHistory> getSalaryHistories() {
        return this.salaryHistoryRepository.findAll();
    }
    public List<SalaryInfo> getSalaryInfos() {
        return this.salaryInfosRepository.findAll();
    }




}
