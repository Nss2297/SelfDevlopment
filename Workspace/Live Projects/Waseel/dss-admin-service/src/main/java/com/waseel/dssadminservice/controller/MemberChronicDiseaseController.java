package com.waseel.dssadminservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waseel.dssadminservice.model.memberchronic.MemberChronicDiseaseResponseModel;
import com.waseel.dssadminservice.service.MemberChronicDiseaseService;

@RestController
@CrossOrigin("*")
@RequestMapping("/member-chronic-disease")
public class MemberChronicDiseaseController {

	@Autowired
	MemberChronicDiseaseService memberChronicDiseaseService;

	@GetMapping("/{memberId}")
	public ResponseEntity<List<MemberChronicDiseaseResponseModel>> getChronicDiseasesByMember(
			@PathVariable String memberId) {
		return ResponseEntity.ok(memberChronicDiseaseService.getChronicDiseaseDetailsByMemberId(memberId));
	}
}
