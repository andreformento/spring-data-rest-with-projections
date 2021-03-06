package com.formento.projections.example;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * This repository shows interface and method-level security. The entire repository requires ROLE_USER, while certain
 * operations require ROLE_ADMIN.
 *
 * @author Greg Turnquist
 * @author Oliver Gierke
 */
@PreAuthorize("hasRole('ROLE_USER')")
public interface ItemRepository extends CrudRepository<Item, String> {

    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#save(S)
     */
    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    <S extends Item> S save(S s);

    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#delete(java.io.Serializable)
     */
    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void delete(String aLong);
}
