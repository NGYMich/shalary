package com.ngymich.shalary.infrastructure.persistence.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface UserJpaRepository extends JpaRepository<PersistableUser, Long> {

    PersistableUser findByEmail(String email);

    boolean existsByEmail(String email);

    Page<PersistableUser> findAll(Pageable topTwenty);

    List<PersistableUser> findAllByLocation(String location);
}
