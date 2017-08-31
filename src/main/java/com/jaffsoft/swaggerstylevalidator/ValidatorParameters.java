package com.jaffsoft.swaggerstylevalidator;

class ValidatorParameters {

    enum NamingStrategy {
        UnderscoreCase("underscore_case"),
        CamelCase("camelCase"),
        HyphenCase("hyphen-case");

        private final String appelation;

        NamingStrategy(String appelation) {
            this.appelation = appelation;
        }

        public String getAppelation() {
            return appelation;
        }
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

    ValidatorParameters() {
        //For Gson
    }

    boolean isValidateInfoLicense() {
        return validateInfoLicense;
    }

    boolean isValidateInfoDescription() {
        return validateInfoDescription;
    }

    boolean isValidateInfoContact() {
        return validateInfoContact;
    }

    boolean isValidateOperationOperationId() {
        return validateOperationOperationId;
    }

    boolean isValidateOperationDescription() {
        return validateOperationDescription;
    }

    boolean isValidateOperationTag() {
        return validateOperationTag;
    }

    boolean isValidateOperationSummary() {
        return validateOperationSummary;
    }

    boolean isValidateModelPropertiesExample() {
        return validateModelPropertiesExample;
    }

    boolean isValidateModelNoLocalDef() {
        return validateModelNoLocalDef;
    }

    NamingStrategy getPathNamingStrategy() {
        return pathNamingStrategy;
    }

    NamingStrategy getParameterNamingStrategy() {
        return parameterNamingStrategy;
    }

    void setValidateInfoLicense(boolean validateInfoLicense) {
        this.validateInfoLicense = validateInfoLicense;
    }

    void setValidateInfoDescription(boolean validateInfoDescription) {
        this.validateInfoDescription = validateInfoDescription;
    }

    void setValidateInfoContact(boolean validateInfoContact) {
        this.validateInfoContact = validateInfoContact;
    }

    void setValidateOperationOperationId(boolean validateOperationOperationId) {
        this.validateOperationOperationId = validateOperationOperationId;
    }

    void setValidateOperationDescription(boolean validateOperationDescription) {
        this.validateOperationDescription = validateOperationDescription;
    }

    void setValidateOperationTag(boolean validateOperationTag) {
        this.validateOperationTag = validateOperationTag;
    }

    void setValidateOperationSummary(boolean validateOperationSummary) {
        this.validateOperationSummary = validateOperationSummary;
    }

    void setValidateModelPropertiesExample(boolean validateModelPropertiesExample) {
        this.validateModelPropertiesExample = validateModelPropertiesExample;
    }

    void setValidateModelNoLocalDef(boolean validateModelNoLocalDef) {
        this.validateModelNoLocalDef = validateModelNoLocalDef;
    }

    void setPathNamingStrategy(NamingStrategy pathNamingStrategy) {
        this.pathNamingStrategy = pathNamingStrategy;
    }

    void setParameterNamingStrategy(NamingStrategy parameterNamingStrategy) {
        this.parameterNamingStrategy = parameterNamingStrategy;
    }

    boolean isValidateNaming() {
        return validateNaming;
    }

    void setValidateNaming(boolean validateNaming) {
        this.validateNaming = validateNaming;
    }
}
