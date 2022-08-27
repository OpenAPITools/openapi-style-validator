package org.openapitools.openapistylevalidator.gradle;

import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.parser.core.models.ParseOptions;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.options.Option;
import org.openapitools.empoa.swagger.core.internal.SwAdapter;
import org.openapitools.openapistylevalidator.OpenApiSpecStyleValidator;
import org.openapitools.openapistylevalidator.ValidatorParameters;
import org.openapitools.openapistylevalidator.ValidatorParameters.NamingConvention;
import org.openapitools.openapistylevalidator.error.StyleError;

import java.util.List;

public class OpenAPIStyleValidatorTask extends DefaultTask {

    private String inputFile;

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
    private NamingConvention propertyNamingConvention = NamingConvention.CamelCase;

    public OpenAPIStyleValidatorTask() {
        this.setGroup("Verification");
        this.setDescription("Validate that OpenAPI files against style rules");
    }

    @TaskAction
    public void execute() {
        if (inputFile == null) {
            throw new GradleException(String.format("The input file is not defined, set the '%s' option", OpenApiSpecStyleValidator.INPUT_FILE));
        }
        getLogger().quiet(String.format("Validating spec: %s", inputFile));

        OpenAPIParser openApiParser = new OpenAPIParser();
        ParseOptions parseOptions = new ParseOptions();
        parseOptions.setResolve(true);

        SwaggerParseResult parserResult = openApiParser.readLocation(inputFile, null, parseOptions);
        io.swagger.v3.oas.models.OpenAPI swaggerOpenAPI = parserResult.getOpenAPI();

        org.eclipse.microprofile.openapi.models.OpenAPI openAPI = SwAdapter.toOpenAPI(swaggerOpenAPI);
        OpenApiSpecStyleValidator openApiSpecStyleValidator = new OpenApiSpecStyleValidator(openAPI);

        ValidatorParameters parameters = createValidatorParameters();
        getLogger().quiet(String.format("Validating with options: %s", parameters));
        List<StyleError> result = openApiSpecStyleValidator.validate(parameters);
        if (!result.isEmpty()) {
            getLogger().error("OpenAPI Specification does not meet the requirements. Issues:\n");
            result.stream().map(StyleError::toString).forEach(m -> getLogger().error(String.format("\t%s", m)));
            throw new GradleException("OpenAPI Style validation failed");
        }
    }

    @Option(option = OpenApiSpecStyleValidator.INPUT_FILE, description = "OpenAPI specification being validated")
    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    @Option(option = ValidatorParameters.VALIDATE_INFO_LICENSE, description = "Ensures that there is a license section in the info section")
    public void setValidateInfoLicense(boolean validateInfoLicense) {
        this.validateInfoLicense = validateInfoLicense;
    }

    @Option(option = ValidatorParameters.VALIDATE_INFO_DESCRIPTION, description = "Ensures that there is a description attribute in the info section")
    public void setValidateInfoDescription(boolean validateInfoDescription) {
        this.validateInfoDescription = validateInfoDescription;
    }

    @Option(option = ValidatorParameters.VALIDATE_INFO_CONTACT, description = "Ensures that there is a contact section in the info section")
    public void setValidateInfoContact(boolean validateInfoContact) {
        this.validateInfoContact = validateInfoContact;
    }

    @Option(option = ValidatorParameters.VALIDATE_OPERATION_OPERATION_ID, description = "Ensures that there is an operation id for each operation")
    public void setValidateOperationOperationId(boolean validateOperationOperationId) {
        this.validateOperationOperationId = validateOperationOperationId;
    }

    @Option(option = ValidatorParameters.VALIDATE_OPERATION_DESCRIPTION, description = "Ensures that there is a description for each operation")
    public void setValidateOperationDescription(boolean validateOperationDescription) {
        this.validateOperationDescription = validateOperationDescription;
    }

    @Option(option = ValidatorParameters.VALIDATE_OPERATION_TAG, description = "Ensures that there is a tag for each operation")
    public void setValidateOperationTag(boolean validateOperationTag) {
        this.validateOperationTag = validateOperationTag;
    }

    @Option(option = ValidatorParameters.VALIDATE_OPERATION_SUMMARY, description = "Ensures that there is a summary for each operation")
    public void setValidateOperationSummary(boolean validateOperationSummary) {
        this.validateOperationSummary = validateOperationSummary;
    }

    @Option(option = ValidatorParameters.VALIDATE_MODEL_PROPERTIES_EXAMPLE, description = "Ensures that the properties of the Schemas have an example value defined")
    public void setValidateModelPropertiesExample(boolean validateModelPropertiesExample) {
        this.validateModelPropertiesExample = validateModelPropertiesExample;
    }

    @Option(option = ValidatorParameters.VALIDATE_MODEL_PROPERTIES_DESCRIPTION, description = "Ensures that the properties of the Schemas have a description value defined")
    public void setValidateModelPropertiesDescription(boolean validateModelPropertiesDescription) {
        this.validateModelPropertiesDescription = validateModelPropertiesDescription;
    }

    @Option(option = ValidatorParameters.VALIDATE_MODEL_REQUIRED_PROPERTIES, description = "Ensures that all required properties of the Schemas are listed among their properties")
    public void setValidateModelRequiredProperties(boolean validateModelRequiredProperties) {
        this.validateModelRequiredProperties = validateModelRequiredProperties;
    }

    @Option(option = ValidatorParameters.VALIDATE_MODEL_NO_LOCAL_DEF, description = "Not implemented yet")
    public void setValidateModelNoLocalDef(boolean validateModelNoLocalDef) {
        this.validateModelNoLocalDef = validateModelNoLocalDef;
    }

    @Option(option = ValidatorParameters.VALIDATE_NAMING, description = "Ensures the names follow a given naming convention")
    public void setValidateNaming(boolean validateNaming) {
        this.validateNaming = validateNaming;
    }

    @Option(option = ValidatorParameters.IGNORE_HEADER_X_NAMING, description = "Exclude from validation header parameters starting with 'x-'")
    public void setIgnoreHeaderXNaming(boolean ignoreHeaderXNaming) {
        this.ignoreHeaderXNaming = ignoreHeaderXNaming;
    }

    @Option(option = ValidatorParameters.PATH_NAMING_CONVENTION, description = "Naming convention for paths")
    public void setPathNamingConvention(NamingConvention pathNamingConvention) {
        this.pathNamingConvention = pathNamingConvention;
    }

    @Option(option = ValidatorParameters.PARAMETER_NAMING_CONVENTION, description = "Naming convention for parameters")
    public void setParameterNamingConvention(NamingConvention parameterNamingConvention) {
        this.parameterNamingConvention = parameterNamingConvention;
    }

    @Option(option = ValidatorParameters.HEADER_NAMING_CONVENTION, description = "Naming convention for headers")
    public void setHeaderNamingConvention(NamingConvention headerNamingConvention) {
        this.headerNamingConvention = headerNamingConvention;
    }

    @Option(option = ValidatorParameters.PROPERTY_NAMING_CONVENTION, description = "Naming convention for properties")
    public void setPropertyNamingConvention(NamingConvention propertyNamingConvention) {
        this.propertyNamingConvention = propertyNamingConvention;
    }

    public ValidatorParameters createValidatorParameters() {
        ValidatorParameters parameters = new ValidatorParameters();
        parameters.setValidateInfoLicense(validateInfoLicense);
        parameters.setValidateInfoDescription(validateInfoDescription);
        parameters.setValidateInfoContact(validateInfoContact);
        parameters.setValidateOperationOperationId(validateOperationOperationId);
        parameters.setValidateOperationDescription(validateOperationDescription);
        parameters.setValidateOperationTag(validateOperationTag);
        parameters.setValidateOperationSummary(validateOperationSummary);
        parameters.setValidateModelPropertiesExample(validateModelPropertiesExample);
        parameters.setValidateModelPropertiesDescription(validateModelPropertiesDescription);
        parameters.setValidateModelRequiredProperties(validateModelRequiredProperties);
        parameters.setValidateModelNoLocalDef(validateModelNoLocalDef);
        parameters.setValidateNaming(validateNaming);
        parameters.setIgnoreHeaderXNaming(ignoreHeaderXNaming);
        parameters.setPathNamingConvention(pathNamingConvention);
        parameters.setParameterNamingConvention(parameterNamingConvention);
        parameters.setHeaderNamingConvention(headerNamingConvention);
        parameters.setPropertyNamingConvention(propertyNamingConvention);
        return parameters;
    }

}
