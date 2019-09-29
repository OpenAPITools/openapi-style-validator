package org.openapitools.openapistylevalidator;

import org.eclipse.microprofile.openapi.OASFactory;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.junit.jupiter.api.Test;
import org.openapitools.openapistylevalidator.styleerror.StyleError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class OpenApiSpecStyleValidatorTest {
    @Test
    void validatePingOpenAPI() {
        OpenAPI openAPI = createPingOpenAPI();
        OpenApiSpecStyleValidator validator = new OpenApiSpecStyleValidator(openAPI);

        ValidatorParameters parameters = new ValidatorParameters();
        List<StyleError> errors = validator.validate(parameters);

        assertTrue(errors.isEmpty());
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
                );
    }
}