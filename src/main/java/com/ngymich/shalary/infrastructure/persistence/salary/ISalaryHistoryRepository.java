package com.ngymich.shalary.infrastructure.persistence.salary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ISalaryHistoryRepository extends JpaRepository<PersistableSalaryHistory, Long> {


}
