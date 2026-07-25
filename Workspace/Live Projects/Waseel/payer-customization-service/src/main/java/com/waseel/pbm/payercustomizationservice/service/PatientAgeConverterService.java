package com.waseel.pbm.payercustomizationservice.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

@Service
public class PatientAgeConverterService {

	
	/**
	 * Used to convert birthDate to the days
	 * @param dateOfBirth
	 * @return Integer
	 */
	public Long patientAgeConverter(String dateOfBirth) {
		Date birthDate = convertStringToDate(dateOfBirth);
		if (birthDate != null) {
			long diffInMillies = Math.abs(new Date().getTime() - birthDate.getTime());
			long days = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
			return (long) Math.toIntExact(days);
		}
		return null;
	}

	public Date convertStringToDate(String dateOfBirth) {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return formatter.parse(dateOfBirth);
		} catch (ParseException e) {
			return null;
		}
	}
}
