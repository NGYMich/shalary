package com.ngymich.shalary.infrastructure.persistence.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CompanyJpaRepository extends JpaRepository<PersistableCompany, Long> {


}
