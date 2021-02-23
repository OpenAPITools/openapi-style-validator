package org.openapitools.openapistylevalidator;

import java.util.List;
import org.eclipse.microprofile.openapi.OASFactory;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.media.Schema.SchemaType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openapitools.openapistylevalidator.styleerror.StyleError;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OpenApiSpecStyleValidatorTest {

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void testModelPropertiesExampleValidation(boolean isValidateModelPropertiesExample) {
        OpenAPI openAPI = createValidOpenAPI()
                .components(
                        OASFactory.createComponents()
                                .addSchema("MyObject", OASFactory.createSchema()
                                        .type(SchemaType.OBJECT)
                                        .addProperty("propertyWithExample", OASFactory.createSchema()
                                                .type(SchemaType.STRING)
                                                .example("example"))
                                        .addProperty("propertyWithoutExample", OASFactory.createSchema()
                                                .type(SchemaType.STRING))
                                )
                );

        ValidatorParameters parameters = TestDataProvider.createParametersDisablingAllValidations();
        parameters.setValidateModelPropertiesExample(isValidateModelPropertiesExample);

        List<StyleError> errors = new OpenApiSpecStyleValidator(openAPI).validate(parameters);
        if (isValidateModelPropertiesExample) {
            assertEquals(1, errors.size());
            assertEquals("*ERROR* in Model 'MyObject', property 'propertyWithoutExample', field 'example' -> This field should be present and not empty", errors.get(0).toString());
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
                .components(
                        OASFactory.createComponents()
                                .addSchema("MyObject", OASFactory.createSchema()
                                        .addProperty("refProperty", OASFactory.createSchema()
                                                .ref("#/components/schemas/RefProperty")))
                );

        ValidatorParameters parameters = TestDataProvider.createParametersDisablingAllValidations()
                .setValidateModelPropertiesExample(true);

        List<StyleError> errors = new OpenApiSpecStyleValidator(openAPI).validate(parameters);
        assertEquals(0, errors.size());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void validateModelRequiredProperties(boolean isValidateModelRequiredProperties) {
        OpenAPI openAPI = createValidOpenAPI()
                .components(
                        OASFactory.createComponents()
                                .addSchema("MyObject", OASFactory.createSchema()
                                        .type(SchemaType.OBJECT)
                                        .addRequired("id")
                                        .addProperty("name", OASFactory.createSchema()
                                                .type(SchemaType.STRING)
                                        )
                                )
                );

        ValidatorParameters parameters = TestDataProvider.createParametersDisablingAllValidations()
                .setValidateModelRequiredProperties(isValidateModelRequiredProperties);

        List<StyleError> errors = new OpenApiSpecStyleValidator(openAPI).validate(parameters);
        if (isValidateModelRequiredProperties) {
            assertEquals(1, errors.size());
            assertEquals("*ERROR* in Model 'MyObject', property 'id' -> This property should be present or removed from the list of required", errors.get(0).toString());
        } else {
            assertEquals(0, errors.size());
        }
    }

    @Test
    void testSuccessOfAllValidations() {
        OpenAPI openAPI = createValidOpenAPI();
        OpenApiSpecStyleValidator validator = new OpenApiSpecStyleValidator(openAPI);

        List<StyleError> errors = validator.validate(new ValidatorParameters());
        assertEquals(0, errors.size());
    }

    private static OpenAPI createValidOpenAPI() {
        return OASFactory.createOpenAPI()
                .openapi("3.0.1")
                .info(
                        OASFactory.createInfo()
                                .title("Ping Specification")
                                .version("1.0")
                                .license(OASFactory.createLicense()
                                        .name("Eclipse Public License - v2.0")
                                        .url("https://www.eclipse.org/legal/epl-2.0/"))
                                .description("This is a test spec")
                                .contact(
                                        OASFactory.createContact()
                                                .name("OpenAPI Tools")
                                                .email("team@openapitools.org"))
                )
                .addServer(
                        OASFactory.createServer()
                                .url("http://localhost:8000/")
                )
                .paths(
                        OASFactory.createPaths()
                                .addPathItem(
                                        "/ping", OASFactory.createPathItem()
                                                .GET(
                                                        OASFactory.createOperation()
                                                                .operationId("pingGet")
                                                                .summary("A simple get call")
                                                                .description("When this method is called, the server answers with 200 OKs")
                                                                .addTag("demo")
                                                                .responses(
                                                                        OASFactory.createAPIResponses()
                                                                                .addAPIResponse(
                                                                                        "200", OASFactory.createAPIResponse()
                                                                                                .description("OK")
                                                                                )
                                                                )
                                                )
                                )
                );
    }
}