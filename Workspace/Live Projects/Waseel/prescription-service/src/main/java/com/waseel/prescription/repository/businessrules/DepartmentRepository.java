package com.waseel.prescription.repository.businessrules;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waseel.prescription.persist.businessrules.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
