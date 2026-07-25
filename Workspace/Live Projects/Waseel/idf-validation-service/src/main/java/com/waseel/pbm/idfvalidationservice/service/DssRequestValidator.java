package com.waseel.pbm.idfvalidationservice.service;

import java.util.List;

import com.waseel.pbm.idfvalidationservice.model.DrugList;
import com.waseel.pbm.idfvalidationservice.model.DssRequest;
import com.waseel.pbm.idfvalidationservice.model.Error;
import com.waseel.pbm.idfvalidationservice.model.Result;

public interface DssRequestValidator {
	public abstract void validate(DssRequest request, List<Result> results);

	public abstract List<Error> populateServiceErrorsList(String serviceCode);

	public abstract Result populateServiceValidationResult(DrugList reqService);
}
