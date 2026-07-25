package com.waseel.pbm.fdbvalidationservice.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fdb.mkfi.core.AppSettings;

@Component
public class FdbFrameworkDataSourceConfiguration {

	@Value("${medk-framwork.url}")
	private String datsourceUrl;

	@Value("${medk-framwork.username}")
	private String datsourceUsername;

	@Value("${medk-framwork.password}")
	private String datsourcePassword;

	@Value("${medk-framwork.driver}")
	private String datsourceDriver;

	@Value("${medk-framwork.UseJNDI}")
	private String datsourceJndi;

	@Value("${medk-framwork.poolPreparedStatements}")
	private String datsourcePoolPreparedStatements;


	
	@Value("${medk-framwork.initialPoolSize}")
	private String datsourceInitialPoolSize;

	@Value("${medk-framwork.maxPoolSize}")
	private String datsourceMaxPoolSize;

	@Value("${medk-framwork.maxIdlePoolSize}")
	private String datsourceMaxIdlePoolSize;

	@Value("${medk-framwork.minIdlePoolSize}")
	private String datsourceMinIdlePoolSize;

	private AppSettings medkFrameworkFdbSetting;

	@SuppressWarnings("static-access")
	@PostConstruct
	public void setDBConnection() {
		medkFrameworkFdbSetting = new AppSettings();
		medkFrameworkFdbSetting.setProperty("UseJNDI", this.datsourceJndi);
		medkFrameworkFdbSetting.setProperty("jdbc.url", this.datsourceUrl);
		medkFrameworkFdbSetting.setProperty("jdbc.driver", this.datsourceDriver);
		medkFrameworkFdbSetting.setProperty("jdbc.username", this.datsourceUsername);
		medkFrameworkFdbSetting.setProperty("jdbc.password", this.datsourcePassword);
		medkFrameworkFdbSetting.setProperty("poolPreparedStatements", this.datsourcePoolPreparedStatements);
		medkFrameworkFdbSetting.setProperty("initialPoolSize", this.datsourceInitialPoolSize);
		medkFrameworkFdbSetting.setProperty("maxPoolSize", this.datsourceMaxPoolSize);
		medkFrameworkFdbSetting.setProperty("maxIdlePoolSize", this.datsourceMaxIdlePoolSize);
		medkFrameworkFdbSetting.setProperty("minIdlePoolSize", this.datsourceMinIdlePoolSize);

		initializeAllClasses();
	}

	private void initializeAllClasses() {
		List<String> classesToLoad = new ArrayList<>(Arrays.asList("com.fdb.mkfi.core.AllergenGroupXSensitivity",
				"com.fdb.mkfi.core.ClinicalRoute", "com.fdb.mkfi.core.DosageForm",
				"com.fdb.mkfi.core.ExtendedPropertyName", "com.fdb.mkfi.core.FDBCode", "com.fdb.mkfi.core.FDBCodeType",
				"com.fdb.mkfi.core.IngredientStrengthTypeCode", "com.fdb.mkfi.core.Labeler",
				"com.fdb.mkfi.core.MonographDrugDrug", "com.fdb.mkfi.core.MonographDrugFood",
				"com.fdb.mkfi.core.MonographPatientEducation", "com.fdb.mkfi.core.UnitOfMeasure",
				"com.fdb.mkfi.core.UnitOfMeasureTypeCode", "com.fdb.mkfi.core.UOMConversionTableItem",
				"com.fdb.mkfi.core.dosing.DosingSeverityLevel", "com.fdb.mkfi.core.dosing.FrequencyInterval",
				"com.fdb.mkfi.core.dosing.MonographDosing",
				"com.fdb.mkfi.core.interoperability.FDBHL7ObjectIdentifierType",
				"com.fdb.mkfi.screening.DrugDrugRecord", "com.fdb.mkfi.screening.DrugFoodRecord",
				"com.fdb.mkfi.screening.DuplicateTherapyClass", "com.fdb.mkfi.screening.GeriatricRecord",
				"com.fdb.mkfi.screening.LactationRecord", "com.fdb.mkfi.screening.PediatricRecord",
				"com.fdb.mkfi.screening.PregnancyRecord", "com.fdb.mkfi.core.AHFSMonograph",
				"com.fdb.mkfi.core.AllergenGroup", "com.fdb.mkfi.core.AllergenPicklist",
				"com.fdb.mkfi.core.CustomAllergenPicklist", "com.fdb.mkfi.core.CustomAttributeName",
				"com.fdb.mkfi.core.DispensableDrug", "com.fdb.mkfi.core.DispensableGeneric",
				"com.fdb.mkfi.core.DrugName", "com.fdb.mkfi.core.GenericDrug", "com.fdb.mkfi.core.Ingredient",
				"com.fdb.mkfi.core.LabelWarning", "com.fdb.mkfi.core.MedicalCondition",
				"com.fdb.mkfi.core.MedicalConditionSynonym", "com.fdb.mkfi.core.PatientCounselingMessage",
				"com.fdb.mkfi.core.RoutedDoseFormDrug", "com.fdb.mkfi.core.RoutedDoseFormGeneric",
				"com.fdb.mkfi.core.RoutedDrug", "com.fdb.mkfi.core.RoutedGeneric",
				"com.fdb.mkfi.core.UnivIDTypeSearchable"));

		for (String currentClass : classesToLoad) {
			try {
				Class.forName(currentClass);
			} catch (ClassNotFoundException e) {
				System.out.println("This Exception throws from the class initializer ");
				e.printStackTrace();
			}
		}
	}

	public String getDatsourceUrl() {
		return datsourceUrl;
	}

	public void setDatsourceUrl(String datsourceUrl) {
		this.datsourceUrl = datsourceUrl;
	}

	public String getDatsourceUsername() {
		return datsourceUsername;
	}

	public void setDatsourceUsername(String datsourceUsername) {
		this.datsourceUsername = datsourceUsername;
	}

	public String getDatsourcePassword() {
		return datsourcePassword;
	}

	public void setDatsourcePassword(String datsourcePassword) {
		this.datsourcePassword = datsourcePassword;
	}

	public String getDatsourceDriver() {
		return datsourceDriver;
	}

	public void setDatsourceDriver(String datsourceDriver) {
		this.datsourceDriver = datsourceDriver;
	}

	public String getDatsourceJndi() {
		return datsourceJndi;
	}

	public void setDatsourceJndi(String datsourceJndi) {
		this.datsourceJndi = datsourceJndi;
	}

	public AppSettings getMedkFrameworkFdbSetting() {
		return medkFrameworkFdbSetting;
	}

	public void setMedkFrameworkFdbSetting(AppSettings medkFrameworkFdbSetting) {
		this.medkFrameworkFdbSetting = medkFrameworkFdbSetting;
	}

	public String getDatsourcePoolPreparedStatements() {
		return datsourcePoolPreparedStatements;
	}

	public void setDatsourcePoolPreparedStatements(String datsourcePoolPreparedStatements) {
		this.datsourcePoolPreparedStatements = datsourcePoolPreparedStatements;
	}

	public String getDatsourceInitialPoolSize() {
		return datsourceInitialPoolSize;
	}

	public void setDatsourceInitialPoolSize(String datsourceInitialPoolSize) {
		this.datsourceInitialPoolSize = datsourceInitialPoolSize;
	}

	public String getDatsourceMaxPoolSize() {
		return datsourceMaxPoolSize;
	}

	public void setDatsourceMaxPoolSize(String datsourceMaxPoolSize) {
		this.datsourceMaxPoolSize = datsourceMaxPoolSize;
	}

	public String getDatsourceMaxIdlePoolSize() {
		return datsourceMaxIdlePoolSize;
	}

	public void setDatsourceMaxIdlePoolSize(String datsourceMaxIdlePoolSize) {
		this.datsourceMaxIdlePoolSize = datsourceMaxIdlePoolSize;
	}

	public String getDatsourceMinIdlePoolSize() {
		return datsourceMinIdlePoolSize;
	}

	public void setDatsourceMinIdlePoolSize(String datsourceMinIdlePoolSize) {
		this.datsourceMinIdlePoolSize = datsourceMinIdlePoolSize;
	}

	
	
}
