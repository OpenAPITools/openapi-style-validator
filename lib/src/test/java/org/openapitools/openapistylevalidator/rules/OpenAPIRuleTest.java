package org.openapitools.openapistylevalidator.rules;

import org.eclipse.microprofile.openapi.OASFactory;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.junit.jupiter.api.Test;
import org.openapitools.openapistylevalidator.error.StyleError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OpenAPIRuleTest {

    private OpenAPIRule rule = new OpenAPIRule();

    @Test
    public void validOpenAPI() {
        List<StyleError> violation = rule.execute(createOpenApi());
        assertTrue(violation.isEmpty());
    }

    @Test
    public void invalidOpenAPI() {
        OpenAPI openapi = OASFactory.createOpenAPI().openapi("3.0.1");
        List<StyleError> styleErrors = rule.execute(openapi);
        assertEquals(1, styleErrors.size());
        assertEquals("*ERROR* Section: OpenAPI: 'paths,components' -> Should have at least one of paths or components", styleErrors.get(0).toString());
    }

    private OpenAPI createOpenApi() {
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
