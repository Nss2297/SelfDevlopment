package com.waseel.drugexclusionvalidationservice.repository.businessrules;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waseel.drugexclusionvalidationservice.persist.businessrules.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
