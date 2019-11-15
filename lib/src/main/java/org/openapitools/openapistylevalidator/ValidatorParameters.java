package org.openapitools.openapistylevalidator;

public class ValidatorParameters {

    public static enum NamingStrategy {
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
    private boolean ignoreHeaderXNaming = true;
    private NamingStrategy pathNamingStrategy = NamingStrategy.HyphenCase;
    private NamingStrategy parameterNamingStrategy = NamingStrategy.CamelCase;
    private NamingStrategy propertyNamingStrategy = NamingStrategy.CamelCase;

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

    public void setPropertyNamingStrategy(NamingStrategy propertyNamingStrategy) {
        this.propertyNamingStrategy = propertyNamingStrategy;
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

    public boolean isValidateNaming() {
        return validateNaming;
    }

    public void setValidateNaming(boolean validateNaming) {
        this.validateNaming = validateNaming;
    }

    public boolean isIgnoreHeaderXNaming() {
        return ignoreHeaderXNaming;
    }

    public void setIgnoreHeaderXNaming(boolean ignoreHeaderXNaming) {
        this.ignoreHeaderXNaming = ignoreHeaderXNaming;
    }
}
