package com.waseel.prescription.repository.businessrules;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.waseel.prescription.persist.businessrules.Department;
import com.waseel.prescription.persist.businessrules.DeptSpecPhyscAssc;

public interface DeptSpecPhyscAsscRepository extends JpaRepository<DeptSpecPhyscAssc, Long> {

	@Query("SELECT new com.waseel.prescription.persist.businessrules.Department(d.departmentId, d.departmentName) FROM DeptSpecPhyscAssc dspa, Department d WHERE dspa.physicianInfoId = :physicianInfoId AND dspa.departmentId=d.departmentId AND dspa.isEnabled='1' and d.isDeleted='0'")
	Optional<Department> findByPhysicianInfoId(@Param("physicianInfoId") Long physicianInfoId);
}
