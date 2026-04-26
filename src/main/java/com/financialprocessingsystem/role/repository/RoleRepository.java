package com.financialprocessingsystem.role.repository;

import com.financialprocessingsystem.role.enums.RoleName;
import com.financialprocessingsystem.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
