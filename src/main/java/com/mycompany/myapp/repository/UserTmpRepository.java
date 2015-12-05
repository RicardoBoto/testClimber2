package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UserTmp;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserTmp entity.
 */
public interface UserTmpRepository extends JpaRepository<UserTmp,Long> {

}
