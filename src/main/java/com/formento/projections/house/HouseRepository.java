package com.formento.projections.house;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasRole('ROLE_USER')")
@RepositoryRestResource
public interface HouseRepository extends CrudRepository<House, String> {

    @Query("{ user: ?#{principal.username} }")
    Iterable<House> findAll();

    <S extends House> S save(S entity);

    void delete(String id);

    void delete(House house);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void delete(Iterable<? extends House> iterable);

    @RestResource(exported = false)
    void deleteAll();

}
