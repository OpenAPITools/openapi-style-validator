package org.openapitools.openapistylevalidator.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openapitools.openapistylevalidator.TestDataProvider.createValidOpenApi;

import java.util.List;
import org.eclipse.microprofile.openapi.OASFactory;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.junit.jupiter.api.Test;
import org.openapitools.openapistylevalidator.api.IRule;
import org.openapitools.openapistylevalidator.error.StyleError;

public class OperationDescriptionRuleTest {

    private final IRule rule = new OperationDescriptionRule();

    @Test
    public void testWithValidOpenAPI() {
        List<StyleError> violation = rule.execute(createValidOpenApi());
        assertTrue(violation.isEmpty());
    }

    @Test
    public void testWithInValidOpenAPI() {
        List<StyleError> styleErrors = rule.execute(createInValidOpenAPI());
        assertEquals(1, styleErrors.size());
        assertEquals(
                "*ERROR* in Operation GET /ping 'description' -> This field should be present and not empty",
                styleErrors.get(0).toString());
    }

    private OpenAPI createInValidOpenAPI() {
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
                                                .addTag("demo")
                                                .responses(OASFactory.createAPIResponses()
                                                        .addAPIResponse(
                                                                "200",
                                                                OASFactory.createAPIResponse()
                                                                        .description("OK"))))));
    }
}
