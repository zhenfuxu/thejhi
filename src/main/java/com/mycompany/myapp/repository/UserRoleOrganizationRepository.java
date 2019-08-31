package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UserRoleOrganization;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UserRoleOrganization entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserRoleOrganizationRepository extends JpaRepository<UserRoleOrganization, Long>, JpaSpecificationExecutor<UserRoleOrganization> {

}
