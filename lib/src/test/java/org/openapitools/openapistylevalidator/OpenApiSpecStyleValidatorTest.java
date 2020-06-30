package org.openapitools.openapistylevalidator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.microprofile.openapi.OASFactory;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.media.Schema.SchemaType;
import org.junit.jupiter.api.Test;
import org.openapitools.openapistylevalidator.styleerror.StyleError;

class OpenApiSpecStyleValidatorTest {
    @Test
    void validatePingOpenAPI() {
        OpenAPI openAPI = createPingOpenAPI();
        OpenApiSpecStyleValidator validator = new OpenApiSpecStyleValidator(openAPI);

        ValidatorParameters parameters = new ValidatorParameters();
        List<StyleError> errors = validator.validate(parameters);
        assertTrue(errors.size() == 1);
        assertEquals("*ERROR* in Model 'Myobject', property 'name', field 'example' -> This field should be present and not empty", errors.get(0).toString());
    }

    @Test
    void validatePingOpenAPI_without_ValidateModelPropertiesExample() {
        OpenAPI openAPI = createPingOpenAPI();
        OpenApiSpecStyleValidator validator = new OpenApiSpecStyleValidator(openAPI);

        ValidatorParameters parameters = new ValidatorParameters();
        parameters.setValidateModelPropertiesExample(false);
        List<StyleError> errors = validator.validate(parameters);
        assertTrue(errors.size() == 0);
    }

    @Test
    void validatePingOpenAPI_WithoutSchema_and_components() {
        OpenAPI openAPI = createSimplePingOpenAPI();
        OpenApiSpecStyleValidator validator = new OpenApiSpecStyleValidator(openAPI);

        ValidatorParameters parameters = new ValidatorParameters();
        List<StyleError> errors = validator.validate(parameters);
        assertTrue(errors.size() == 0);
    }

    private static OpenAPI createSimplePingOpenAPI() {
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

    private static OpenAPI createPingOpenAPI() {
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
                                .addPathItem(
                                        "/ping2", OASFactory.createPathItem()
                                                .GET(
                                                        OASFactory.createOperation()
                                                                .operationId("ping2Get")
                                                                .summary("A get that return an object response")
                                                                .description("When this method is called, the server answers with 200 and a json object as response")
                                                                .addTag("demo")
                                                                .responses(
                                                                        OASFactory.createAPIResponses()
                                                                                .addAPIResponse(
                                                                                        "200", OASFactory.createAPIResponse()
                                                                                        .content(OASFactory.createContent()
                                                                                                .addMediaType("application/json", OASFactory.createMediaType()
                                                                                                        .schema(OASFactory.createSchema()
                                                                                                                .ref("'#/components/schemas/Myobject'")))
                                                                                        )
                                                                                        .description("A second path to test validation of ref examples")
                                                                                )
                                                                )
                                                )
                                )
                ).components(OASFactory
                        .createComponents()
                                .addSchema("Myobject", OASFactory.createSchema()
                                        .type(SchemaType.OBJECT)
                                        .addProperty("name", OASFactory.createSchema()
                                                .type(SchemaType.STRING))
                                        .addProperty("prop1", OASFactory.createSchema()
                                                .type(SchemaType.STRING)
                                                .example("prop 1 description"))
                                        .addProperty("status", OASFactory.createSchema()
                                                .ref("#/components/schemas/Status"))
                                ).addSchema("Status", OASFactory.createSchema()
                                        .type(SchemaType.STRING)
                                        .description("status description")
                                        .example("status example")
                                        .addEnumeration("item1")
                                        .addEnumeration("item2")
                                )

                );
    }
}