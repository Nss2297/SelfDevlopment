package com.waseel.prescription.service.prescriptions;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.prescription.model.common.PhysicianConfigModel;
import com.waseel.prescription.specification.PhysicianConfigSpecification;

@Service
public class PhysicianConfigService {

	private final Logger log = LoggerFactory.getLogger(PhysicianConfigService.class);

	@Autowired
	private PhysicianConfigSpecification physicianConfigSpecification;

	public List<PhysicianConfigModel> getAllPhysicianDetails(String physician) {
		log.info("physician is : {}", physician);
		return physicianConfigSpecification.findByPhysicianDetail(physician);
	}
}
