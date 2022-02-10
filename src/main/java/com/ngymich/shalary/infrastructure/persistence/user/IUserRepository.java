package com.ngymich.shalary.infrastructure.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface IUserRepository extends JpaRepository<PersistableUser, Long> {


}
