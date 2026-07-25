package com.waseel.prescription.repository.businessrules;

import org.springframework.data.repository.CrudRepository;

import com.waseel.prescription.persist.businessrules.PhysicianCategory;

public interface PhysicianCategoryRepository extends CrudRepository<PhysicianCategory, String> {

}
