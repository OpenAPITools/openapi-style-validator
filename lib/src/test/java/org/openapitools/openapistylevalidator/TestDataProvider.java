package org.openapitools.openapistylevalidator;

import org.eclipse.microprofile.openapi.OASFactory;
import org.eclipse.microprofile.openapi.models.OpenAPI;

public class TestDataProvider {
    /**
     * With these parameters the validator should skip all checks
     */
    static ValidatorParameters createParametersDisablingAllValidations() {
        return new ValidatorParameters()
                .setValidateInfoLicense(false)
                .setValidateInfoDescription(false)
                .setValidateInfoContact(false)
                .setValidateOperationOperationId(false)
                .setValidateOperationDescription(false)
                .setValidateOperationTag(false)
                .setValidateOperationSummary(false)
                .setValidateModelPropertiesExample(false)
                .setValidateModelPropertiesDescription(false)
                .setValidateModelRequiredProperties(false)
                .setValidateModelNoLocalDef(false)
                .setValidateNaming(false);
    }

    public static OpenAPI createValidOpenApi() {
        return OASFactory.createOpenAPI()
                .openapi("3.0.1")
                .info(OASFactory.createInfo()
                        .title("Ping Specification")
                        .version("1.0")
                        .description("Ping Specification")
                        .license(OASFactory.createLicense()
                                .name("Eclipse Public License - v2.0")
                                .url("https://www.eclipse.org/legal/epl-2.0/"))
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
