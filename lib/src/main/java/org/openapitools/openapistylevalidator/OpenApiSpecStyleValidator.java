package org.openapitools.openapistylevalidator;

import org.openapitools.openapistylevalidator.styleerror.StyleError;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.Operation;
import org.eclipse.microprofile.openapi.models.PathItem;
import org.eclipse.microprofile.openapi.models.info.Contact;
import org.eclipse.microprofile.openapi.models.info.Info;
import org.eclipse.microprofile.openapi.models.info.License;
import org.eclipse.microprofile.openapi.models.media.Schema;
import org.eclipse.microprofile.openapi.models.parameters.Parameter;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OpenApiSpecStyleValidator {

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
        validateInfo();
        validateOperations();
        validateModels();
        validateNaming();

        return errorAggregator.getErrorList();
    }

    private void validateInfo() {
        Info info = openAPI.getInfo();
        License license = info.getLicense();
        if (parameters.isValidateInfoLicense()) {
            if (license != null) {
                List<Boolean> infoPresence = new ArrayList<>();
                infoPresence.add(license.getName() != null && !license.getName().isEmpty());
                infoPresence.add(license.getUrl() != null && !license.getUrl().isEmpty());
                errorAggregator.validateMinimumInfo(infoPresence, StyleError.StyleCheckSection.APIInfo, "license", "name|url");
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
                infoPresence.add(contact.getEmail() != null && !contact.getEmail().isEmpty());
                errorAggregator.validateMinimumInfo(infoPresence, StyleError.StyleCheckSection.APIInfo, "contact", "name|url|email");
            } else {
                errorAggregator.logMissingOrEmptyAttribute(StyleError.StyleCheckSection.APIInfo, "contact");
            }
        }
    }

    private void validateOperations() {
        for (String key : openAPI.getPaths().getPathItems().keySet()) {
            PathItem path = openAPI.getPaths().getPathItems().get(key);
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

    private void validateModels() {
        if (openAPI.getComponents() != null && openAPI.getComponents().getSchemas() != null) {
            for (String definition : openAPI.getComponents().getSchemas().keySet()) {
                Schema model = openAPI.getComponents().getSchemas().get(definition);

                if (model.getProperties() != null) {
                    for (Map.Entry<String, Schema> entry : model.getProperties().entrySet()) {
                        Schema property = entry.getValue();

                        if (parameters.isValidateModelPropertiesExample()) {
                            if (property.getExample() == null) {
                                errorAggregator.logMissingOrEmptyModelAttribute(definition, entry.getKey(), "example");
                            }
                        }
                        
                        /*
                    if (parameters.isValidateModelNoLocalDef()) {
                        //TODO:
                    }*/
                    }
                }
            }
        }
    }

    private void validateNaming() {
        if (parameters.isValidateNaming()) {
            if (openAPI.getComponents() != null && openAPI.getComponents().getSchemas() != null) {
                for (String definition : openAPI.getComponents().getSchemas().keySet()) {
                    Schema model = openAPI.getComponents().getSchemas().get(definition);

                    if (model.getProperties() != null) {
                        for (Map.Entry<String, Schema> entry : model.getProperties().entrySet()) {
                            boolean isValid = namingValidator.isNamingValid(entry.getKey(), parameters.getPropertyNamingStrategy());
                            if (!isValid) {
                                errorAggregator.logModelBadNaming(entry.getKey(),
                                        "property",
                                        parameters.getPropertyNamingStrategy().getAppelation(),
                                        definition);
                            }
                        }
                    }
                }
            }

            if (openAPI.getPaths() != null && openAPI.getPaths().getPathItems() != null) {
                for (String key : openAPI.getPaths().getPathItems().keySet()) {
                    PathItem path = openAPI.getPaths().getPathItems().get(key);
                    for (PathItem.HttpMethod method : path.getOperations().keySet()) {
                        Operation op = path.getOperations().get(method);
                        if (op != null && op.getParameters() != null) {
                            for (Parameter opParam : op.getParameters()) {
                                boolean shouldValidate;
                                if (opParam.getIn() == Parameter.In.HEADER && opParam.getName().startsWith("X-")) {
                                    shouldValidate = !parameters.isIgnoreHeaderXNaming();
                                } else {
                                    shouldValidate = true;
                                }

                                if (shouldValidate && opParam.getRef() == null) {
                                    boolean isValid = namingValidator.isNamingValid(opParam.getName(), parameters.getParameterNamingStrategy());
                                    if (!isValid) {
                                        errorAggregator.logOperationBadNaming(opParam.getName(),
                                                "parameter",
                                                parameters.getParameterNamingStrategy().getAppelation(),
                                                key,
                                                method);
                                    }
                                }
                            }
                        }
                    }

                    String[] pathParts = key.split("/");
                    for (String part : pathParts) {
                        if (!(part.startsWith("{") && part.endsWith("}"))) {
                            boolean isValid = namingValidator.isNamingValid("part", parameters.getPathNamingStrategy());
                            if (!isValid) {
                                errorAggregator.logOperationBadNaming(part,
                                        "path",
                                        parameters.getPathNamingStrategy().getAppelation(),
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