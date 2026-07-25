package com.waseel.pbm.payercustomizationservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waseel.pbm.payercustomizationservice.service.CustomizationRequestService;

@RestController
@RequestMapping("/lovs")
public class CustomizationLovController {
	@Autowired
	CustomizationRequestService customizationRequestService;

	@GetMapping("/customization-modules")
	public ResponseEntity<List<String>> getAllModuleName() {
		return ResponseEntity.ok(customizationRequestService.getAllModuleName());
	}

}
