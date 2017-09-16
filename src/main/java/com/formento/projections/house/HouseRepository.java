package com.formento.projections.house;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasRole('ROLE_USER')")
@RepositoryRestResource
public interface HouseRepository extends CrudRepository<House, String> {

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    <S extends House> S save(S entity);

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    void delete(String id);

    @Query("{ user: ?#{principal.username} }")
    Iterable<House> findAll();

}
