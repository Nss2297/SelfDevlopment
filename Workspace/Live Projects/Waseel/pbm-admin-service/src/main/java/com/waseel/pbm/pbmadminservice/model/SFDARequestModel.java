package com.waseel.pbm.pbmadminservice.model;

import com.waseel.pbm.pbmadminservice.validator.customannotation.*;

import javax.validation.constraints.NotEmpty;

public class SFDARequestModel {

    @NotEmpty(message = "SFDA Code should not be null or empty")
    @NoMoreThan150Length(message = "SFDA Code {noMoreThan150LengthValidation}")
    @NotContainsWhiteSpace(message = "SFDA Code {notContainsWhiteSpace}")
    @AlphaNumericWithDashValidation(message = "SFDA Code {alphaNumericWithDash}")
    private String sfdaCode;
    @NotEmpty(message = "Trade Name should not be null or empty")
    @NoMoreThan100Length(message = "Trade Name {noMoreThan100LengthValidation}")
    @SpecialCharacterValidation(message = "Trade Name {specialCharacterAllowed}")
    private String tradeName;
    @NotEmpty(message = "Price should not be null or empty")
    @NoMoreThan100Length(message = "Price {noMoreThan100LengthValidation}")
    @NumericValue(message = "Price should be numeric value with 2 decimal place")
    private String price;
    @NotEmpty(message = "GranularUnit should not be null or empty")
    @NoMoreThan100Length(message = "GranularUnit {noMoreThan100LengthValidation}")
    @NumericValue(message = "GranularUnit should be numeric value")
    private String granularUnit;
    @NoMoreThan64Length(message = "Scientific Code {noMoreThan64LengthValidation}")
    @SpecialCharacterValidation(message = "Scientific Code {specialCharacterAllowed}")
    private String scientificCode;
    @NoMoreThan256Length(message = "Scientific Name {noMoreThan256LengthValidation}")
    @SpecialCharacterValidation(message = "Scientific Name {specialCharacterAllowed}")
    private String scientificName;
    @NoMoreThan100Length(message = "GTIN Code {noMoreThan100LengthValidation}")
    @AlphaNumericWithDashValidation(message = "GTIN Code {alphaNumericWithDash}")
    private String gtinCode;

    public String getSfdaCode() {
        return sfdaCode;
    }

    public void setSfdaCode(String sfdaCode) {
        this.sfdaCode = sfdaCode;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGranularUnit() {
        return granularUnit;
    }

    public void setGranularUnit(String granularUnit) {
        this.granularUnit = granularUnit;
    }

    public String getScientificCode() {
        return scientificCode;
    }

    public void setScientificCode(String scientificCode) {
        this.scientificCode = scientificCode;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getGtinCode() {
        return gtinCode;
    }

    public void setGtinCode(String gtinCode) {
        this.gtinCode = gtinCode;
    }

    public SFDARequestModel(String sfdaCode, String tradeName, String price, String granularUnit, String scientificCode,
                            String scientificName, String gtinCode) {
        super();
        this.sfdaCode = sfdaCode;
        this.tradeName = tradeName;
        this.price = price;
        this.granularUnit = granularUnit;
        this.scientificCode = scientificCode;
        this.scientificName = scientificName;
        this.gtinCode = gtinCode;
    }

    public SFDARequestModel() {

    }
}
