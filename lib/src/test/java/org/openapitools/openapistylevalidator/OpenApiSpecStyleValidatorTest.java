package org.openapitools.openapistylevalidator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.microprofile.openapi.OASFactory;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.media.Schema;
import org.eclipse.microprofile.openapi.models.media.Schema.SchemaType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openapitools.openapistylevalidator.error.StyleError;

class OpenApiSpecStyleValidatorTest {

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void testModelPropertiesExampleValidation(boolean isValidateModelPropertiesExample) {
        OpenAPI openAPI = createValidOpenAPI()
                .components(OASFactory.createComponents()
                        .addSchema(
                                "MyObject",
                                OASFactory.createSchema()
                                        .type(SchemaType.OBJECT)
                                        .addProperty(
                                                "propertyWithExample",
                                                OASFactory.createSchema()
                                                        .type(SchemaType.STRING)
                                                        .example("example"))
                                        .addProperty(
                                                "propertyWithoutExample",
                                                OASFactory.createSchema().type(SchemaType.STRING))));

        ValidatorParameters parameters = TestDataProvider.createParametersDisablingAllValidations();
        parameters.setValidateModelPropertiesExample(isValidateModelPropertiesExample);

        List<StyleError> errors = new OpenApiSpecStyleValidator(openAPI).validate(parameters);
        if (isValidateModelPropertiesExample) {
            assertEquals(1, errors.size());
            assertEquals(
                    "*ERROR* in Model 'MyObject', property 'propertyWithoutExample', field 'example' -> This field should be present and not empty",
                    errors.get(0).toString());
        } else {
            assertEquals(0, errors.size());
        }
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void testModelPropertiesDescriptionValidation(boolean isValidateModelPropertiesDescription) {
        OpenAPI openAPI = createValidOpenAPI()
                .components(OASFactory.createComponents()
                        .addSchema(
                                "MyObject",
                                OASFactory.createSchema()
                                        .type(SchemaType.OBJECT)
                                        .addProperty(
                                                "propertyWithDescription",
                                                OASFactory.createSchema()
                                                        .type(SchemaType.STRING)
                                                        .description("description"))
                                        .addProperty(
                                                "propertyWithoutDescription",
                                                OASFactory.createSchema().type(SchemaType.STRING))));

        ValidatorParameters parameters = TestDataProvider.createParametersDisablingAllValidations();
        parameters.setValidateModelPropertiesDescription(isValidateModelPropertiesDescription);

        List<StyleError> errors = new OpenApiSpecStyleValidator(openAPI).validate(parameters);
        if (isValidateModelPropertiesDescription) {
            assertEquals(1, errors.size());
            assertEquals(
                    "*ERROR* in Model 'MyObject', property 'propertyWithoutDescription', field 'description' -> This field should be present and not empty",
                    errors.get(0).toString());
        } else {
            assertEquals(0, errors.size());
        }
    }

    /**
     * Test for the bug #88
     */
    @Test
    void testModelPropertiesExampleValidationIgnoresRefProperty() {
        OpenAPI openAPI = createValidOpenAPI()
                .components(OASFactory.createComponents()
                        .addSchema(
                                "MyObject",
                                OASFactory.createSchema()
                                        .addProperty(
                                                "refProperty",
                                                OASFactory.createSchema().ref("#/components/schemas/RefProperty"))));

        ValidatorParameters parameters =
                TestDataProvider.createParametersDisablingAllValidations().setValidateModelPropertiesExample(true);

        List<StyleError> errors = new OpenApiSpecStyleValidator(openAPI).validate(parameters);
        assertEquals(0, errors.size());
    }

    // Test for bug 169
    @Test
    void testModelPropertiesExampleValidationIgnoresItemsRefProperty() {
        OpenAPI openAPI = createValidOpenAPI()
                .components(OASFactory.createComponents()
                        .addSchema(
                                "MyObject",
                                OASFactory.createSchema()
                                        .addProperty(
                                                "refProperty",
                                                OASFactory.createSchema()
                                                        .items(OASFactory.createSchema()
                                                                .ref("#/components/schemas/RefProperty")))));

        ValidatorParameters parameters =
                TestDataProvider.createParametersDisablingAllValidations().setValidateModelPropertiesExample(true);

        List<StyleError> errors = new OpenApiSpecStyleValidator(openAPI).validate(parameters);
        assertEquals(0, errors.size());
    }

    // https://github.com/OpenAPITools/openapi-style-validator/issues/178
    @Test
    void testModelPropertiesExampleValidationIgnoresAllOffComposition() {
        Schema component = OASFactory.createSchema()
                .type(SchemaType.OBJECT)
                .addProperty(
                        "property1",
                        OASFactory.createSchema().type(SchemaType.STRING).example("Just a string property"));
        OpenAPI openAPI = createValidOpenAPI()
                .components(OASFactory.createComponents()
                        .addSchema("Component", component)
                        .addSchema(
                                "Composition",
                                OASFactory.createSchema()
                                        .type(SchemaType.OBJECT)
                                        .addProperty(
                                                "component",
                                                OASFactory.createSchema()
                                                        .type(SchemaType.OBJECT)
                                                        .addAllOf(component))
                                        .addProperty(
                                                "other",
                                                OASFactory.createSchema()
                                                        .type(SchemaType.STRING)
                                                        .example("Example"))));
        ValidatorParameters parameters =
                TestDataProvider.createParametersDisablingAllValidations().setValidateModelPropertiesExample(true);

        List<StyleError> errors = new OpenApiSpecStyleValidator(openAPI).validate(parameters);
        assertEquals(0, errors.size());
    }

    @Test
    void validationSkipsIgnoredOperations() {
        Map<String, Object> extensions = new HashMap<>();
        extensions.put("x-style-validator-ignored", Boolean.TRUE);

        OpenAPI openAPI = createValidOpenAPI()
                .paths(OASFactory.createPaths()
                        .addPathItem(
                                "/my-pathWithWeird_NAMING",
                                OASFactory.createPathItem()
                                        .extensions(extensions)
                                        .GET(OASFactory.createOperation()
                                                .responses(OASFactory.createAPIResponses()
                                                        .addAPIResponse(
                                                                "200",
                                                                OASFactory.createAPIResponse()
                                                                        .description("OK"))))));

        ValidatorParameters parameters = TestDataProvider.createParametersDisablingAllValidations()
                .setValidateNaming(true)
                .setValidateOperationOperationId(true)
                .setValidateOperationDescription(true)
                .setValidateOperationTag(true)
                .setValidateOperationSummary(true);

        List<StyleError> errors = new OpenApiSpecStyleValidator(openAPI).validate(parameters);
        assertEquals(0, errors.size());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void validateModelRequiredProperties(boolean isValidateModelRequiredProperties) {
        OpenAPI openAPI = createValidOpenAPI()
                .components(OASFactory.createComponents()
                        .addSchema(
                                "MyObject",
                                OASFactory.createSchema()
                                        .type(SchemaType.OBJECT)
                                        .addRequired("id")
                                        .addProperty(
                                                "name",
                                                OASFactory.createSchema().type(SchemaType.STRING))));

        ValidatorParameters parameters = TestDataProvider.createParametersDisablingAllValidations()
                .setValidateModelRequiredProperties(isValidateModelRequiredProperties);

        List<StyleError> errors = new OpenApiSpecStyleValidator(openAPI).validate(parameters);
        if (isValidateModelRequiredProperties) {
            assertEquals(1, errors.size());
            assertEquals(
                    "*ERROR* in Model 'MyObject', property 'id' -> This property should be present or removed from the list of required",
                    errors.get(0).toString());
        } else {
            assertEquals(0, errors.size());
        }
    }

    @Test
    void testSuccessOfAllValidations() {
        OpenAPI openAPI = createValidOpenAPI();
        OpenApiSpecStyleValidator validator = new OpenApiSpecStyleValidator(openAPI);

        List<StyleError> errors = validator.validate(new ValidatorParameters());
        assertEquals(0, errors.size(), errors.toString());
    }

    /**
     * See https://github.com/OpenAPITools/openapi-style-validator/issues/190
     */
    @Test
    void shouldNotFailWhenThereAreNoPaths() throws Throwable {
        OpenAPI openAPI = createValidOpenAPI()
                .components(OASFactory.createComponents()
                        .addSchema("sample", OASFactory.createSchema().type(SchemaType.STRING)))
                .paths(null);
        OpenApiSpecStyleValidator validator = new OpenApiSpecStyleValidator(openAPI);

        Assertions.assertDoesNotThrow(() -> {
            validator.validate(new ValidatorParameters());
        });
    }

    @Test
    void shouldNotFailWhenThereAreNoComponents() throws Throwable {
        OpenAPI openAPI = createValidOpenAPI()
                .paths(OASFactory.createPaths().addPathItem("sample", OASFactory.createPathItem()))
                .components(null);
        OpenApiSpecStyleValidator validator = new OpenApiSpecStyleValidator(openAPI);

        Assertions.assertDoesNotThrow(() -> {
            validator.validate(new ValidatorParameters());
        });
    }

    @Test
    void shouldReportMissingPathsOrComponents() {
        OpenAPI openAPI = createValidOpenAPI().paths(null).components(null);

        OpenApiSpecStyleValidator validator = new OpenApiSpecStyleValidator(openAPI);

        List<StyleError> errors = validator.validate(new ValidatorParameters());
        Assertions.assertAll(
                () -> assertEquals(1, errors.size()),
                () -> assertEquals(
                        "*ERROR* Section: OpenAPI: 'paths,components' -> Should have at least one of paths or components",
                        errors.get(0).toString()));
    }

    private static OpenAPI createValidOpenAPI() {
        return OASFactory.createOpenAPI()
                .openapi("3.0.1")
                .info(OASFactory.createInfo()
                        .title("Ping Specification")
                        .version("1.0")
                        .license(OASFactory.createLicense()
                                .name("Eclipse Public License - v2.0")
                                .url("https://www.eclipse.org/legal/epl-2.0/"))
                        .description("This is a test spec")
                        .contact(
                                OASFactory.createContact().name("OpenAPI Tools").email("team@openapitools.org")))
                .addServer(OASFactory.createServer().url("http://localhost:8000/"))
                .paths(OASFactory.createPaths()
                        .addPathItem(
                                "/ping",
                                OASFactory.createPathItem()
                                        .GET(OASFactory.createOperation()
                                                .operationId("pingGet")
                                                .summary("A simple get call")
                                                .description(
                                                        "When this method is called, the server answers with 200 OKs")
                                                .addTag("demo")
                                                .responses(OASFactory.createAPIResponses()
                                                        .addAPIResponse(
                                                                "200",
                                                                OASFactory.createAPIResponse()
                                                                        .description("OK"))))));
    }
}
