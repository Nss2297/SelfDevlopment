package com.waseel.pbm.pbmadminservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.waseel.pbm.pbmadminservice.model.DrugToDiagnosisApprovalCategoryModel;
import com.waseel.pbm.pbmadminservice.specification.DrugToDiagnosisApprovalCategorySpecification;
import com.waseel.pbm.pbmadminservice.util.UserInfoUtil;

@Service
public class DrugToDiagnosisApprovalCategoryService {

	private final Logger log = LoggerFactory.getLogger(DrugToDiagnosisApprovalCategoryService.class);

	@Autowired
	private DrugToDiagnosisApprovalCategorySpecification approvalCategorySpecification;

	public Page<DrugToDiagnosisApprovalCategoryModel> getAllCategoryOfApproval(int pageNumber, int recordSize,
			String name) {
		log.info("Page Number :- {}, Record Size :- {}, name :- {} ", pageNumber, recordSize, name);
		String category = UserInfoUtil.getAccCategory(SecurityContextHolder.getContext().getAuthentication());
		if (category.equalsIgnoreCase("payer"))
			name = UserInfoUtil.getAccName(SecurityContextHolder.getContext().getAuthentication());
		return approvalCategorySpecification.findByApprovalCategoryWithPagination(pageNumber, recordSize, name,
				category);
	}
}
