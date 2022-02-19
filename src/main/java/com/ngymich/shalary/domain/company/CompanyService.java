package com.ngymich.shalary.domain.company;

import com.ngymich.shalary.infrastructure.persistence.company.CompanyJpaRepository;
import com.ngymich.shalary.infrastructure.persistence.company.PersistableCompany;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyJpaRepository companyJpaRepository;

    public CompanyService(CompanyJpaRepository companyJpaRepository) {
        this.companyJpaRepository = companyJpaRepository;
    }


    public List<PersistableCompany> getCompanies() {
        return this.companyJpaRepository.findAll();
    }
}
