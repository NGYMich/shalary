package com.ngymich.shalary.infrastructure.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserJpaRepository extends JpaRepository<PersistableUser, Long> {


}
