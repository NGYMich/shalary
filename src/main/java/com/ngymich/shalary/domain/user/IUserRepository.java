package com.ngymich.shalary.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface IUserRepository extends JpaRepository<User, Long> {


}
