package org.openapitools.openapistylevalidator.rules;

import org.eclipse.microprofile.openapi.OASFactory;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.junit.jupiter.api.Test;
import org.openapitools.openapistylevalidator.TestDataProvider;
import org.openapitools.openapistylevalidator.error.StyleError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openapitools.openapistylevalidator.TestDataProvider.*;

public class OpenAPIRuleTest {

    private OpenAPIRule rule = new OpenAPIRule();

    @Test
    public void testWithValidOpenAPI() {
        List<StyleError> violation = rule.execute(createValidOpenApi());
        assertTrue(violation.isEmpty());
    }

    @Test
    public void testWithInValidOpenAPI() {
        OpenAPI openapi = OASFactory.createOpenAPI().openapi("3.0.1");
        List<StyleError> styleErrors = rule.execute(openapi);
        assertEquals(1, styleErrors.size());
        assertEquals("*ERROR* Section: OpenAPI: 'paths,components' -> Should have at least one of paths or components", styleErrors.get(0).toString());
    }
}
