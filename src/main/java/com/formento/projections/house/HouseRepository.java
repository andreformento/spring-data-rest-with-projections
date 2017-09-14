package com.formento.projections.house;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * This repository shows interface and method-level security. The entire repository requires ROLE_USER, while certain operations require ROLE_ADMIN.
 *
 * @author Greg Turnquist
 * @author Oliver Gierke
 */
@PreAuthorize("hasRole('ROLE_USER')")
public interface HouseRepository extends CrudRepository<House, String> {

    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#save(S)
     */
    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    <S extends House> S save(S entity);

    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#delete(java.io.Serializable)
     */
    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    void delete(String id);

}
