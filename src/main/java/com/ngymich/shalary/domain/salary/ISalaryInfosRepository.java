package com.ngymich.shalary.domain.salary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ISalaryInfosRepository extends JpaRepository<SalaryInfo, Long> {


}
