package com.waseel.dssadminservice.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waseel.dssadminservice.model.memberchronic.MemberChronicDiseaseResponseModel;
import com.waseel.dssadminservice.repository.mdss.MemberChronicDiseaseRepository;

@Service
public class MemberChronicDiseaseService {
	private final Logger log = LoggerFactory.getLogger(MemberChronicDiseaseService.class);

	@Autowired
	private MemberChronicDiseaseRepository memberChronicDiseaseRepository;

	@Transactional(readOnly = true)
	public List<MemberChronicDiseaseResponseModel> getChronicDiseaseDetailsByMemberId(String memberId) {
		log.info("Fetch member chronic disease for idNumber or memberId: [{}]", memberId);
		return memberChronicDiseaseRepository.findChronicDiseaseDetailsByMemberId(memberId);
	}
}