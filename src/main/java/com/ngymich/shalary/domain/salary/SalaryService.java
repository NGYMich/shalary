package com.ngymich.shalary.domain.salary;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryService {

    private final ISalaryHistoryRepository salaryHistoryRepository;

    public SalaryService(ISalaryHistoryRepository salaryHistoryRepository) {
        this.salaryHistoryRepository = salaryHistoryRepository;
    }


    public List<SalaryHistory> getSalaryHistories() {
        return this.salaryHistoryRepository.findAll();
    }


}
