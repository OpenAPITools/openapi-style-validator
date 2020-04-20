package org.openapitools.openapistylevalidator;

public class ValidatorParameters {

    public static enum NamingConvention {
        UnderscoreCase("underscore_case"),
        CamelCase("camelCase"),
        HyphenCase("hyphen-case");

        private final String designation;

        NamingConvention(String appelation) {
            this.designation = appelation;
        }

        /**
         * @return the name of the naming convention as it can be used in the reports
         */
        public String getDesignation() {
            return designation;
        }
    }

    /**
     * @deprecated Please use {@link ValidatorParameters.NamingConvention} instead.
     */
    @Deprecated
    public static enum NamingStrategy {
        UnderscoreCase,
        CamelCase,
        HyphenCase;

        /**
         * @deprecated Please use {@link ValidatorParameters.NamingConvention#getDesignation()} instead.
         * @return the name of the strategy as it can be used in the reports
         */
        @Deprecated
        public String getAppelation() {
            return toConvention(this).getDesignation();
        }

        public static NamingStrategy valueOf(NamingConvention pathNamingConvention) {
            if(pathNamingConvention == null) {
                return null;
            }
            return NamingStrategy.valueOf(pathNamingConvention.name());
        }

        public static NamingConvention toConvention(NamingStrategy pathNamingStrategy) {
            if(pathNamingStrategy == null) {
                return null;
            }
            return NamingConvention.valueOf(pathNamingStrategy.name());
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
    private NamingConvention pathNamingConvention = NamingConvention.HyphenCase;
    private NamingConvention parameterNamingConvention = NamingConvention.CamelCase;
    private NamingConvention propertyNamingConvention= NamingConvention.CamelCase;

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

    public NamingConvention getPathNamingConvention() {
        return pathNamingConvention;
    }

    public NamingConvention getParameterNamingConvention() {
        return parameterNamingConvention;
    }

    public NamingConvention getPropertyNamingConvention() {
        return propertyNamingConvention;
    }

    public NamingStrategy getPathNamingStrategy() {
        return NamingStrategy.valueOf(getPathNamingConvention());
    }

    public NamingStrategy getParameterNamingStrategy() {
        return NamingStrategy.valueOf(getParameterNamingConvention());
    }

    public NamingStrategy getPropertyNamingStrategy() {
        return NamingStrategy.valueOf(getPropertyNamingConvention());
    }

    public ValidatorParameters setValidateInfoLicense(boolean validateInfoLicense) {
        this.validateInfoLicense = validateInfoLicense;
        return this;
    }

    public ValidatorParameters setValidateInfoDescription(boolean validateInfoDescription) {
        this.validateInfoDescription = validateInfoDescription;
        return this;
    }

    public ValidatorParameters setValidateInfoContact(boolean validateInfoContact) {
        this.validateInfoContact = validateInfoContact;
        return this;
    }

    public ValidatorParameters setValidateOperationOperationId(boolean validateOperationOperationId) {
        this.validateOperationOperationId = validateOperationOperationId;
        return this;
    }

    public ValidatorParameters setValidateOperationDescription(boolean validateOperationDescription) {
        this.validateOperationDescription = validateOperationDescription;
        return this;
    }

    public ValidatorParameters setValidateOperationTag(boolean validateOperationTag) {
        this.validateOperationTag = validateOperationTag;
        return this;
    }

    public ValidatorParameters setValidateOperationSummary(boolean validateOperationSummary) {
        this.validateOperationSummary = validateOperationSummary;
        return this;
    }

    public ValidatorParameters setValidateModelPropertiesExample(boolean validateModelPropertiesExample) {
        this.validateModelPropertiesExample = validateModelPropertiesExample;
        return this;
    }

    public ValidatorParameters setValidateModelNoLocalDef(boolean validateModelNoLocalDef) {
        this.validateModelNoLocalDef = validateModelNoLocalDef;
        return this;
    }

    public ValidatorParameters setPathNamingConvention(NamingConvention pathNamingConvention) {
        this.pathNamingConvention = pathNamingConvention;
        return this;
    }

    public ValidatorParameters setParameterNamingConvention(NamingConvention parameterNamingConvention) {
        this.parameterNamingConvention = parameterNamingConvention;
        return this;
    }

    public ValidatorParameters setPropertyNamingConvention(NamingConvention propertyNamingConvention) {
        this.propertyNamingConvention = propertyNamingConvention;
        return this;
    }

    public ValidatorParameters setPathNamingStrategy(NamingStrategy pathNamingStrategy) {
        setPathNamingConvention(NamingStrategy.toConvention(pathNamingStrategy));
        return this;
    }

    public ValidatorParameters setParameterNamingStrategy(NamingStrategy parameterNamingStrategy) {
        setParameterNamingConvention(NamingStrategy.toConvention(parameterNamingStrategy));
        return this;
    }

    public ValidatorParameters setPropertyNamingStrategy(NamingStrategy propertyNamingStrategy) {
        setPropertyNamingConvention(NamingStrategy.toConvention(propertyNamingStrategy));
        return this;
    }

    public boolean isValidateNaming() {
        return validateNaming;
    }

    public ValidatorParameters setValidateNaming(boolean validateNaming) {
        this.validateNaming = validateNaming;
        return this;
    }

    public boolean isIgnoreHeaderXNaming() {
        return ignoreHeaderXNaming;
    }

    public ValidatorParameters setIgnoreHeaderXNaming(boolean ignoreHeaderXNaming) {
        this.ignoreHeaderXNaming = ignoreHeaderXNaming;
        return this;
    }
}
