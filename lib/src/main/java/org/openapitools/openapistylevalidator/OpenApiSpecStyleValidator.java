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
                validateModelRequiredProperties(modelName, model, new HashSet<>());
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

    private void validateModelRequiredProperties(String modelName, Schema model, Set<Schema> visited) {
        if (!parameters.isValidateModelRequiredProperties() || model == null || model.getRef() != null) {
            return;
        }
        /*
         * A composition (allOf/anyOf/oneOf) may validly declare a required
         * property in any of its members instead of the composing schema
         * itself, so the required names are checked against every property
         * name declared across the whole composition tree rooted at this
         * schema.
         * See https://github.com/OpenAPITools/openapi-style-validator/pull/809#discussion_r4502925779
         */
        Set<String> knownPropertyNames = collectCompositionPropertyNames(model, new HashSet<>());
        validateModelRequiredProperties(modelName, model, knownPropertyNames, visited);
    }

    private void validateModelRequiredProperties(
            String modelName, Schema model, Set<String> knownPropertyNames, Set<Schema> visited) {
        if (!parameters.isValidateModelRequiredProperties() || model == null) {
            return;
        }
        if (!visited.add(model)) {
            return;
        }
        /*
         * A schema declaring keywords next to its $ref (legal in OpenAPI 3.1)
         * is validated in place: its own required list and composition
         * members are checked against the known names, while the referenced
         * component schema keeps being validated under its own name, so the
         * ref itself is not followed here.
         * See https://github.com/OpenAPITools/openapi-style-validator/pull/809#discussion_r4503877003
         */
        if (model.getRequired() != null) {
            model.getRequired().forEach(nameOfRequiredProperty -> {
                if (!knownPropertyNames.contains(nameOfRequiredProperty)) {
                    errorAggregator.logMissingModelProperty(modelName, nameOfRequiredProperty);
                }
            });
        }
        // array items describe different instances: they get their own composition scope
        validateModelRequiredProperties(modelName, model.getItems(), visited);
        validateModelRequiredProperties(modelName, model.getAllOf(), knownPropertyNames, visited);
        validateModelRequiredProperties(modelName, model.getAnyOf(), knownPropertyNames, visited);
        validateModelRequiredProperties(modelName, model.getOneOf(), knownPropertyNames, visited);
    }

    private void validateModelRequiredProperties(
            String modelName, List<Schema> models, Set<String> knownPropertyNames, Set<Schema> visited) {
        if (models != null) {
            models.forEach(model -> validateModelRequiredProperties(modelName, model, knownPropertyNames, visited));
        }
    }

    /*
     * Collects every property name declared in the composition tree rooted at
     * the given schema: the schema itself and all its allOf/anyOf/oneOf
     * members, transitively. Members that are $refs are followed through the
     * component schemas they point to, because their properties count for the
     * composition too. Sibling keywords declared next to a $ref are legal in
     * OpenAPI 3.1, so the referencing schema's own properties and composition
     * members are collected as well, not only the referenced schema's names.
     * See https://github.com/OpenAPITools/openapi-style-validator/pull/809#discussion_r4503877003
     * Array items are intentionally not traversed: they describe different
     * instances, not a composition of the same one.
     */
    private Set<String> collectCompositionPropertyNames(Schema model, Set<Schema> visitedSchemas) {
        Set<String> propertyNames = new HashSet<>();
        if (model == null || !visitedSchemas.add(model)) {
            return propertyNames;
        }
        if (model.getRef() != null) {
            propertyNames.addAll(
                    collectCompositionPropertyNames(resolveComponentSchema(model.getRef()), visitedSchemas));
        }
        if (model.getProperties() != null) {
            propertyNames.addAll(model.getProperties().keySet());
        }
        for (List<Schema> members : Arrays.asList(model.getAllOf(), model.getAnyOf(), model.getOneOf())) {
            if (members != null) {
                members.forEach(
                        member -> propertyNames.addAll(collectCompositionPropertyNames(member, visitedSchemas)));
            }
        }
        return propertyNames;
    }

    private Schema resolveComponentSchema(String ref) {
        String schemasPrefix = "#/components/schemas/";
        if (ref.startsWith(schemasPrefix)
                && openAPI.getComponents() != null
                && openAPI.getComponents().getSchemas() != null) {
            return openAPI.getComponents().getSchemas().get(ref.substring(schemasPrefix.length()));
        }
        return null;
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

                    validatePropertyNaming(definition, model);

                    validateEnumNaming(definition, model);
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

    private void validatePropertyNaming(String definition, Schema model) {
        if (model != null && model.getProperties() != null) {
            for (Map.Entry<String, Schema> entry : model.getProperties().entrySet()) {
                String propertyName = entry.getKey();
                boolean isValid = namingValidator.isNamingValid(propertyName, parameters.getPropertyNamingConvention())
                        || parameters.getAllowedModelProperties().contains(propertyName);
                if (!isValid) {
                    errorAggregator.logModelBadNaming(
                            propertyName,
                            "property",
                            parameters.getPropertyNamingConvention().getDesignation(),
                            definition);
                }
                validateEnumNaming(definition, entry.getValue());
                validatePropertyNaming(definition, entry.getValue());
            }
        }
    }

    private void validateEnumNaming(String definition, Schema model) {
        if (model != null && model.getEnumeration() != null) {
            for (Object enumValue : model.getEnumeration()) {
                if (enumValue instanceof String) {
                    String enumString = (String) enumValue;
                    boolean isEnumNameValid =
                            namingValidator.isNamingValid(enumString, parameters.getEnumNamingConvention());
                    if (!isEnumNameValid) {
                        errorAggregator.logModelBadNaming(
                                enumString,
                                "enum value",
                                parameters.getEnumNamingConvention().getDesignation(),
                                definition);
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
