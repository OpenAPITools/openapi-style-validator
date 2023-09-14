package org.openapitools.openapistylevalidator;

import java.util.*;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.Operation;
import org.eclipse.microprofile.openapi.models.PathItem;
import org.eclipse.microprofile.openapi.models.info.Contact;
import org.eclipse.microprofile.openapi.models.info.Info;
import org.eclipse.microprofile.openapi.models.info.License;
import org.eclipse.microprofile.openapi.models.media.Schema;
import org.eclipse.microprofile.openapi.models.parameters.Parameter;
import org.openapitools.openapistylevalidator.ValidatorParameters.NamingConvention;
import org.openapitools.openapistylevalidator.styleerror.StyleError;

public class OpenApiSpecStyleValidator {
    public static final String INPUT_FILE = "inputFile";
    public static final String X_STYLE_VALIDATOR_IGNORED = "x-style-validator-ignored";

    private final OpenAPI openAPI;
    private final ErrorAggregator errorAggregator;
    private ValidatorParameters parameters;
    private final NamingValidator namingValidator;

    public OpenApiSpecStyleValidator(OpenAPI openApi) {
        this.openAPI = openApi;
        errorAggregator = new ErrorAggregator();
        namingValidator = new NamingValidator();
    }

    public List<StyleError> validate(ValidatorParameters parameters) {
        this.parameters = parameters;
        validateStructure();
        validateInfo();
        validateOperations();
        validateModels();
        validateNaming();

        return errorAggregator.getErrorList();
    }

    private void validateStructure() {
        boolean hasNoPaths = openAPI.getPaths() == null;
        boolean hasNoComponents = openAPI.getComponents() == null;
        if (hasNoPaths && hasNoComponents) {
            errorAggregator.logMissingPathsAndComponents();
        }
    }

    private void validateInfo() {
        Info info = openAPI.getInfo();
        License license = info.getLicense();
        if (parameters.isValidateInfoLicense()) {
            if (license != null) {
                List<Boolean> infoPresence = new ArrayList<>();
                infoPresence.add(license.getName() != null && !license.getName().isEmpty());
                infoPresence.add(license.getUrl() != null && !license.getUrl().isEmpty());
                errorAggregator.validateMinimumInfo(
                        infoPresence, StyleError.StyleCheckSection.APIInfo, "license", "name|url");
            } else {
                errorAggregator.logMissingOrEmptyAttribute(StyleError.StyleCheckSection.APIInfo, "license");
            }
        }

        if (parameters.isValidateInfoDescription()) {
            String description = info.getDescription();
            if (description == null || description.isEmpty()) {
                errorAggregator.logMissingOrEmptyAttribute(StyleError.StyleCheckSection.APIInfo, "description");
            }
        }

        if (parameters.isValidateInfoContact()) {
            Contact contact = info.getContact();
            if (contact != null) {
                List<Boolean> infoPresence = new ArrayList<>();
                infoPresence.add(contact.getName() != null && !contact.getName().isEmpty());
                infoPresence.add(contact.getUrl() != null && !contact.getUrl().isEmpty());
                infoPresence.add(
                        contact.getEmail() != null && !contact.getEmail().isEmpty());
                errorAggregator.validateMinimumInfo(
                        infoPresence, StyleError.StyleCheckSection.APIInfo, "contact", "name|url|email");
            } else {
                errorAggregator.logMissingOrEmptyAttribute(StyleError.StyleCheckSection.APIInfo, "contact");
            }
        }
    }

    private void validateOperations() {
        /* An OpenAPI specification may be valid without any `paths`. The
         * parser returns null in that case.
         * See https://github.com/OpenAPITools/openapi-style-validator/issues/190
         */
        if (openAPI.getPaths() == null) return;

        for (String key : openAPI.getPaths().getPathItems().keySet()) {
            PathItem path = openAPI.getPaths().getPathItems().get(key);
            boolean ignoreValidation = isPathIgnored(path);

            if (!ignoreValidation) {
                for (PathItem.HttpMethod method : path.getOperations().keySet()) {
                    Operation op = path.getOperations().get(method);
                    if (parameters.isValidateOperationOperationId()) {
                        if (op.getOperationId() == null || op.getOperationId().isEmpty()) {
                            errorAggregator.logMissingOrEmptyOperationAttribute(key, method, "operationId");
                        }
                    }

                    if (parameters.isValidateOperationDescription()) {
                        if (op.getDescription() == null || op.getDescription().isEmpty()) {
                            errorAggregator.logMissingOrEmptyOperationAttribute(key, method, "description");
                        }
                    }

                    if (parameters.isValidateOperationSummary()) {
                        if (op.getSummary() == null || op.getSummary().isEmpty()) {
                            errorAggregator.logMissingOrEmptyOperationAttribute(key, method, "summary");
                        }
                    }

                    if (parameters.isValidateOperationTag()) {
                        if (op.getTags() == null || op.getTags().isEmpty()) {
                            errorAggregator.logMissingOrEmptyOperationCollection(key, method, "tags");
                        }
                    }
                }
            }
        }
    }

    private boolean isPathIgnored(PathItem path) {
        Object ignoreValidationObj =
                path.getExtensions() != null ? path.getExtensions().get(X_STYLE_VALIDATOR_IGNORED) : null;
        boolean ignoreValidation = false;
        if (ignoreValidationObj != null) {
            ignoreValidation = (boolean) ignoreValidationObj;
        }
        return ignoreValidation;
    }

    private void validateModels() {
        if (openAPI.getComponents() != null && openAPI.getComponents().getSchemas() != null) {
            openAPI.getComponents().getSchemas().forEach((modelName, model) -> {
                validateModelProperties(modelName, model);
                validateModelRequiredProperties(modelName, model);
            });
        }
    }

    private void validateModelProperties(String modelName, Schema model) {
        if (model.getProperties() != null) {
            model.getProperties().forEach((propertyName, property) -> {
                boolean isNotRefProperty = property.getRef() == null
                        && (property.getItems() == null || property.getItems().getRef() == null);
                if (parameters.isValidateModelPropertiesExample()
                        && property.getExample() == null
                        && ((property.getItems() == null && property.getRef() == null)
                                || (property.getItems() != null
                                        && property.getItems().getRef() == null))
                        && property.getAllOf() == null
                        && isNotRefProperty) {
                    errorAggregator.logMissingOrEmptyModelAttribute(modelName, propertyName, "example");
                }
                if (parameters.isValidateModelPropertiesDescription()
                        && property.getDescription() == null
                        && isNotRefProperty) {
                    errorAggregator.logMissingOrEmptyModelAttribute(modelName, propertyName, "description");
                }
                /*
                if (parameters.isValidateModelNoLocalDef()) {
                    //TODO:
                }*/
            });
        }
    }

    private void validateModelRequiredProperties(String modelName, Schema model) {
        if (parameters.isValidateModelRequiredProperties() && model.getRequired() != null) {
            Set<String> namesOfProperties =
                    Optional.ofNullable(model.getProperties()).map(Map::keySet).orElse(Collections.emptySet());
            model.getRequired().forEach(nameOfRequiredProperty -> {
                if (!namesOfProperties.contains(nameOfRequiredProperty)) {
                    errorAggregator.logMissingModelProperty(modelName, nameOfRequiredProperty);
                }
            });
        }
    }

    private void validateNaming() {
        if (parameters.isValidateNaming()) {
            if (openAPI.getComponents() != null && openAPI.getComponents().getSchemas() != null) {
                for (String definition : openAPI.getComponents().getSchemas().keySet()) {
                    Schema model = openAPI.getComponents().getSchemas().get(definition);
                    boolean isSchemaNameValid =
                            namingValidator.isNamingValid(definition, parameters.getSchemaNamingConvention());
                    if (!isSchemaNameValid) {
                        errorAggregator.logModelBadNaming(
                                definition,
                                "schema",
                                parameters.getSchemaNamingConvention().getDesignation(),
                                definition);
                    }

                    if (model.getProperties() != null) {
                        for (Map.Entry<String, Schema> entry :
                                model.getProperties().entrySet()) {
                            String name = entry.getKey();
                            boolean isValid = namingValidator.isNamingValid(
                                            name, parameters.getPropertyNamingConvention())
                                    || parameters.getAllowedModelProperties().contains(name);
                            if (!isValid) {
                                errorAggregator.logModelBadNaming(
                                        entry.getKey(),
                                        "property",
                                        parameters.getPropertyNamingConvention().getDesignation(),
                                        definition);
                            }
                        }
                    }
                }
            }

            if (openAPI.getPaths() != null && openAPI.getPaths().getPathItems() != null) {
                for (String key : openAPI.getPaths().getPathItems().keySet()) {
                    PathItem path = openAPI.getPaths().getPathItems().get(key);
                    boolean ignoreValidation = isPathIgnored(path);

                    if (!ignoreValidation) {
                        for (PathItem.HttpMethod method : path.getOperations().keySet()) {
                            Operation op = path.getOperations().get(method);
                            if (op != null && op.getParameters() != null) {
                                for (Parameter opParam : op.getParameters()) {
                                    boolean shouldValidate;
                                    if (opParam.getIn() == Parameter.In.HEADER
                                            && opParam.getName().startsWith("X-")) {
                                        shouldValidate = !parameters.isIgnoreHeaderXNaming();
                                    } else {
                                        shouldValidate = true;
                                    }

                                    if (shouldValidate && opParam.getRef() == null) {
                                        if (opParam.getIn() == Parameter.In.HEADER) {
                                            validateParamNaming(
                                                    opParam.getName(),
                                                    "header",
                                                    parameters.getHeaderNamingConvention(),
                                                    key,
                                                    method);

                                        } else {
                                            if (opParam.getIn() == Parameter.In.QUERY) {
                                                validateParamNaming(
                                                        opParam.getName(),
                                                        "query parameter",
                                                        parameters.getQueryParamNamingConvention(),
                                                        key,
                                                        method);

                                            } else if (opParam.getIn() == Parameter.In.PATH) {
                                                validateParamNaming(
                                                        opParam.getName(),
                                                        "path parameter",
                                                        parameters.getPathParamNamingConvention(),
                                                        key,
                                                        method);

                                            } else if (opParam.getIn() == Parameter.In.COOKIE) {
                                                validateParamNaming(
                                                        opParam.getName(),
                                                        "cookie parameter",
                                                        parameters.getCookieParamNamingConvention(),
                                                        key,
                                                        method);
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        String[] pathParts = key.split("/");
                        for (String part : pathParts) {
                            if (!part.isEmpty() && !(part.startsWith("{") && part.endsWith("}"))) {
                                boolean isValid =
                                        namingValidator.isNamingValid(part, parameters.getPathNamingConvention());
                                if (!isValid) {
                                    errorAggregator.logOperationBadNaming(
                                            part,
                                            "path",
                                            parameters.getPathNamingConvention().getDesignation(),
                                            key,
                                            null);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void validateParamNaming(
            String paramName,
            String variableType,
            NamingConvention namingConvention,
            String key,
            PathItem.HttpMethod method) {
        boolean isValid = namingValidator.isNamingValid(paramName, namingConvention);
        if (!isValid) {
            errorAggregator.logOperationBadNaming(
                    paramName, variableType, namingConvention.getDesignation(), key, method);
        }
    }
}
