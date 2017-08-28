package com.jaffsoft.swaggerstylevalidator;

public class ValidatorParameters {

    public boolean isValidateNaming() {
        return validateNaming;
    }

    public void setValidateNaming(boolean validateNaming) {
        this.validateNaming = validateNaming;
    }

    enum NamingStrategy {
        UnderscoreCase,
        CamelCase,
        HyphenCase
    }

    private boolean validateInfoLicense = true;
    private boolean validateInfoDescription = true;
    private boolean validateInfoContact = true;

    private boolean validateOperationOperationId = true;
    private boolean validateOperationDescription = true;
    private boolean validateOperationTag = true;
    private boolean validateOperationSummary = true;

    private boolean validateModelPropertiesExample = true;
    private boolean validateModelNoLocalDef = true;

    private boolean validateNaming = true;
    private NamingStrategy pathNamingStrategy = NamingStrategy.HyphenCase;
    private NamingStrategy parameterNamingStrategy = NamingStrategy.HyphenCase;
    private NamingStrategy propertyNamingStrategy = NamingStrategy.HyphenCase;

    public ValidatorParameters() {
        //For Gson
    }

    public boolean isValidateInfoLicense() {
        return validateInfoLicense;
    }

    public boolean isValidateInfoDescription() {
        return validateInfoDescription;
    }

    public boolean isValidateInfoContact() {
        return validateInfoContact;
    }

    public boolean isValidateOperationOperationId() {
        return validateOperationOperationId;
    }

    public boolean isValidateOperationDescription() {
        return validateOperationDescription;
    }

    public boolean isValidateOperationTag() {
        return validateOperationTag;
    }

    public boolean isValidateOperationSummary() {
        return validateOperationSummary;
    }

    public boolean isValidateModelPropertiesExample() {
        return validateModelPropertiesExample;
    }

    public boolean isValidateModelNoLocalDef() {
        return validateModelNoLocalDef;
    }

    public NamingStrategy getPathNamingStrategy() {
        return pathNamingStrategy;
    }

    public NamingStrategy getParameterNamingStrategy() {
        return parameterNamingStrategy;
    }

    public NamingStrategy getPropertyNamingStrategy() {
        return propertyNamingStrategy;
    }

    public void setValidateInfoLicense(boolean validateInfoLicense) {
        this.validateInfoLicense = validateInfoLicense;
    }

    public void setValidateInfoDescription(boolean validateInfoDescription) {
        this.validateInfoDescription = validateInfoDescription;
    }

    public void setValidateInfoContact(boolean validateInfoContact) {
        this.validateInfoContact = validateInfoContact;
    }

    public void setValidateOperationOperationId(boolean validateOperationOperationId) {
        this.validateOperationOperationId = validateOperationOperationId;
    }

    public void setValidateOperationDescription(boolean validateOperationDescription) {
        this.validateOperationDescription = validateOperationDescription;
    }

    public void setValidateOperationTag(boolean validateOperationTag) {
        this.validateOperationTag = validateOperationTag;
    }

    public void setValidateOperationSummary(boolean validateOperationSummary) {
        this.validateOperationSummary = validateOperationSummary;
    }

    public void setValidateModelPropertiesExample(boolean validateModelPropertiesExample) {
        this.validateModelPropertiesExample = validateModelPropertiesExample;
    }

    public void setValidateModelNoLocalDef(boolean validateModelNoLocalDef) {
        this.validateModelNoLocalDef = validateModelNoLocalDef;
    }

    public void setPathNamingStrategy(NamingStrategy pathNamingStrategy) {
        this.pathNamingStrategy = pathNamingStrategy;
    }

    public void setParameterNamingStrategy(NamingStrategy parameterNamingStrategy) {
        this.parameterNamingStrategy = parameterNamingStrategy;
    }

    public void setPropertyNamingStrategy(NamingStrategy propertyNamingStrategy) {
        this.propertyNamingStrategy = propertyNamingStrategy;
    }
}
