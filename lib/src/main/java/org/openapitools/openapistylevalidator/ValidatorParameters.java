package org.openapitools.openapistylevalidator;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ValidatorParameters {
    public static final String VALIDATE_INFO_LICENSE = "validateInfoLicense";
    public static final String VALIDATE_INFO_DESCRIPTION = "validateInfoDescription";
    public static final String VALIDATE_INFO_CONTACT = "validateInfoContact";

    public static final String VALIDATE_OPERATION_OPERATION_ID = "validateOperationOperationId";
    public static final String VALIDATE_OPERATION_DESCRIPTION = "validateOperationDescription";
    public static final String VALIDATE_OPERATION_TAG = "validateOperationTag";
    public static final String VALIDATE_OPERATION_SUMMARY = "validateOperationSummary";

    public static final String VALIDATE_MODEL_PROPERTIES_EXAMPLE = "validateModelPropertiesExample";
    public static final String VALIDATE_MODEL_PROPERTIES_DESCRIPTION = "validateModelPropertiesDescription";
    public static final String VALIDATE_MODEL_REQUIRED_PROPERTIES = "validateModelRequiredProperties";
    public static final String VALIDATE_MODEL_NO_LOCAL_DEF = "validateModelNoLocalDef";

    public static final String VALIDATE_NAMING = "validateNaming";
    public static final String IGNORE_HEADER_X_NAMING = "ignoreHeaderXNaming";
    public static final String PATH_NAMING_CONVENTION = "pathNamingConvention";
    public static final String PARAMETER_NAMING_CONVENTION = "parameterNamingConvention";
    public static final String HEADER_NAMING_CONVENTION = "headerNamingConvention";
    public static final String SCHEMA_NAMING_CONVENTION = "schemaNamingConvention";
    public static final String PROPERTY_NAMING_CONVENTION = "propertyNamingConvention";
    public static final String QUERY_PARAM_NAMING_CONVENTION = "queryParamNamingConvention";
    public static final String PATH_PARAM_NAMING_CONVENTION = "pathParamNamingConvention";
    public static final String COOKIE_PARAM_NAMING_CONVENTION = "cookieParamNamingConvention";

    public static enum NamingConvention {
        UnderscoreCase("underscore_case"),
        UnderscoreUpperCase("UNDERSCORE_UPPER_CASE"),
        CamelCase("camelCase"),
        PascalCase("PascalCase"),
        HyphenCase("hyphen-case"),
        AnyCase("AnyCase"),
        HyphenUpperCase("Hyphen-Upper-Case");

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

    private boolean validateInfoLicense = true;
    private boolean validateInfoDescription = true;
    private boolean validateInfoContact = true;

    private boolean validateOperationOperationId = true;
    private boolean validateOperationDescription = true;
    private boolean validateOperationTag = true;
    private boolean validateOperationSummary = true;

    private boolean validateModelPropertiesExample = true;
    private boolean validateModelPropertiesDescription = true;
    private boolean validateModelRequiredProperties = true;
    private boolean validateModelNoLocalDef = true;

    private boolean validateNaming = true;
    private boolean ignoreHeaderXNaming = true;
    private NamingConvention pathNamingConvention = NamingConvention.HyphenCase;
    private NamingConvention parameterNamingConvention = NamingConvention.CamelCase;
    private NamingConvention headerNamingConvention = NamingConvention.UnderscoreUpperCase;
    private NamingConvention schemaNamingConvention = NamingConvention.PascalCase;
    private NamingConvention propertyNamingConvention = NamingConvention.CamelCase;
    private NamingConvention queryParamNamingConvention = NamingConvention.CamelCase;
    private NamingConvention pathParamNamingConvention = NamingConvention.CamelCase;
    private NamingConvention cookieParamNamingConvention = NamingConvention.CamelCase;

    private boolean queryParamNamingConventionWasExplicitlySet = false;
    private boolean pathParamNamingConventionWasExplicitlySet = false;
    private boolean cookieParamNamingConventionWasExplicitlySet = false;

    private Set<String> allowedModelProperties = new HashSet<>();

    public ValidatorParameters() {
        // For Gson
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

    public boolean isValidateModelPropertiesDescription() {
        return validateModelPropertiesDescription;
    }

    public boolean isValidateModelRequiredProperties() {
        return validateModelRequiredProperties;
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

    public NamingConvention getHeaderNamingConvention() {
        return headerNamingConvention;
    }

    public NamingConvention getSchemaNamingConvention() {
        return schemaNamingConvention;
    }

    public NamingConvention getPropertyNamingConvention() {
        return propertyNamingConvention;
    }

    public NamingConvention getQueryParamNamingConvention() {
        return queryParamNamingConvention;
    }

    public NamingConvention getPathParamNamingConvention() {
        return pathParamNamingConvention;
    }

    public NamingConvention getCookieParamNamingConvention() {
        return cookieParamNamingConvention;
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

    public ValidatorParameters setValidateModelPropertiesDescription(boolean validateModelPropertiesDescription) {
        this.validateModelPropertiesDescription = validateModelPropertiesDescription;
        return this;
    }

    public ValidatorParameters setValidateModelRequiredProperties(boolean validateModelRequiredProperties) {
        this.validateModelRequiredProperties = validateModelRequiredProperties;
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

        // Setting the higher level parameter naming convention overrides the sub-conventions
        if (!this.cookieParamNamingConventionWasExplicitlySet) {
            this.cookieParamNamingConvention = parameterNamingConvention;
        }

        if (!this.pathParamNamingConventionWasExplicitlySet) {
            this.pathParamNamingConvention = parameterNamingConvention;
        }

        if (!this.queryParamNamingConventionWasExplicitlySet) {
            this.queryParamNamingConvention = parameterNamingConvention;
        }

        return this;
    }

    public ValidatorParameters setHeaderNamingConvention(NamingConvention headerNamingConvention) {
        this.headerNamingConvention = headerNamingConvention;
        return this;
    }

    public ValidatorParameters setSchemaNamingConvention(NamingConvention schemaNamingConvention) {
        this.schemaNamingConvention = schemaNamingConvention;
        return this;
    }

    public ValidatorParameters setPropertyNamingConvention(NamingConvention propertyNamingConvention) {
        this.propertyNamingConvention = propertyNamingConvention;
        return this;
    }

    public ValidatorParameters setQueryParamNamingConvention(NamingConvention queryParamNamingConvention) {
        this.queryParamNamingConvention = queryParamNamingConvention;
        this.queryParamNamingConventionWasExplicitlySet = true;
        return this;
    }

    public ValidatorParameters setPathParamNamingConvention(NamingConvention pathParamNamingConvention) {
        this.pathParamNamingConvention = pathParamNamingConvention;
        this.pathParamNamingConventionWasExplicitlySet = true;
        return this;
    }

    public ValidatorParameters setCookieParamNamingConvention(NamingConvention cookieParamNamingConvention) {
        this.cookieParamNamingConvention = cookieParamNamingConvention;
        this.cookieParamNamingConventionWasExplicitlySet = true;
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

    public ValidatorParameters setAllowedModelProperties(List<String> allowedModelProperties) {
        this.allowedModelProperties = new HashSet<>(allowedModelProperties);
        return this;
    }

    public Set<String> getAllowedModelProperties() {
        return Collections.unmodifiableSet(this.allowedModelProperties);
    }

    @Override
    public String toString() {
        return String.format(
                "ValidatorParameters [" + "validateInfoLicense=%s, "
                        + "validateInfoDescription=%s, "
                        + "validateInfoContact=%s, "
                        + "validateOperationOperationId=%s, "
                        + "validateOperationDescription=%s, "
                        + "validateOperationTag=%s, validateOperationSummary=%s, "
                        + "validateModelPropertiesExample=%s, "
                        + "validateModelPropertiesDescription=%s, "
                        + "validateModelRequiredProperties=%s, "
                        + "validateModelNoLocalDef=%s, "
                        + "validateNaming=%s, "
                        + "ignoreHeaderXNaming=%s, "
                        + "pathNamingConvention=%s, "
                        + "headerNamingConvention=%s, "
                        + "parameterNamingConvention=%s, "
                        + "schemaNamingConvention=%s, "
                        + "propertyNamingConvention=%s, "
                        + "queryParamNamingConvention=%s, "
                        + "pathParamNamingConvention=%s, "
                        + "cookieParamNamingConvention=%s"
                        + "]",
                validateInfoLicense,
                validateInfoDescription,
                validateInfoContact,
                validateOperationOperationId,
                validateOperationDescription,
                validateOperationTag,
                validateOperationSummary,
                validateModelPropertiesExample,
                validateModelPropertiesDescription,
                validateModelRequiredProperties,
                validateModelNoLocalDef,
                validateNaming,
                ignoreHeaderXNaming,
                pathNamingConvention,
                headerNamingConvention,
                parameterNamingConvention,
                schemaNamingConvention,
                propertyNamingConvention,
                queryParamNamingConvention,
                pathParamNamingConvention,
                cookieParamNamingConvention);
    }
}
