package org.openapitools.openapistylevalidator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;
import java.util.stream.Collectors;
import org.eclipse.microprofile.openapi.OASFactory;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.media.Schema;
import org.eclipse.microprofile.openapi.models.media.Schema.SchemaType;
import org.eclipse.microprofile.openapi.models.parameters.Parameter.In;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openapitools.openapistylevalidator.ValidatorParameters.NamingConvention;
import org.openapitools.openapistylevalidator.styleerror.StyleError;

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

    /*
     * See https://github.com/OpenAPITools/openapi-style-validator/issues/741
     *
     * Plain @Test methods are used on purpose: junit-jupiter-params is
     * declared testCompileOnly in the root build.gradle, so @ParameterizedTest
     * methods compile but are silently skipped at runtime.
     */
    @Test
    void testModelAdditionalPropertiesValidation() {
        OpenAPI openAPI = createValidOpenAPI()
                .components(OASFactory.createComponents()
                        .addSchema(
                                "MyObject",
                                OASFactory.createSchema()
                                        .type(SchemaType.OBJECT)
                                        .addProperty(
                                                "name",
                                                OASFactory.createSchema().type(SchemaType.STRING))));

        ValidatorParameters parameters =
                TestDataProvider.createParametersDisablingAllValidations().setValidateModelAdditionalProperties(true);

        List<StyleError> errors = new OpenApiSpecStyleValidator(openAPI).validate(parameters);
        Assertions.assertAll(
                () -> assertEquals(1, errors.size()),
                () -> assertEquals(
                        "*ERROR* in Model 'MyObject', field 'additionalProperties' -> An object schema should declare additionalProperties",
                        errors.get(0).toString()));
    }

    /*
     * The rule is opt-in: by default an object without `additionalProperties`
     * is not reported.
     */
    @Test
    void testModelAdditionalPropertiesValidationIsOptIn() {
        OpenAPI openAPI = createValidOpenAPI()
                .components(OASFactory.createComponents()
                        .addSchema(
                                "MyObject",
                                OASFactory.createSchema()
                                        .type(SchemaType.OBJECT)
                                        .addProperty(
                                                "name",
                                                OASFactory.createSchema()
                                                        .type(SchemaType.STRING)
                                                        .example("example")
                                                        .description("description"))));

        List<StyleError> errors = new OpenApiSpecStyleValidator(openAPI).validate(new ValidatorParameters());
        assertEquals(0, errors.size());

        List<StyleError> errorsWithRuleEnabled = new OpenApiSpecStyleValidator(openAPI)
                .validate(new ValidatorParameters().setValidateModelAdditionalProperties(true));
        Assertions.assertAll(
                () -> assertEquals(1, errorsWithRuleEnabled.size()),
                () -> assertEquals(
                        "*ERROR* in Model 'MyObject', field 'additionalProperties' -> An object schema should declare additionalProperties",
                        errorsWithRuleEnabled.get(0).toString()));
    }

    /*
     * The `additionalProperties` keyword is either a boolean or a schema:
     * declaring either of them satisfies the rule.
     */
    @Test
    void testModelAdditionalPropertiesValidationSuccessWhenDeclaredAsFalse() {
        OpenAPI openAPI = createValidOpenAPI()
                .components(OASFactory.createComponents()
                        .addSchema(
                                "MyObject",
                                OASFactory.createSchema()
                                        .type(SchemaType.OBJECT)
                                        .addProperty(
                                                "name",
                                                OASFactory.createSchema().type(SchemaType.STRING))
                                        .additionalPropertiesBoolean(false)));

        ValidatorParameters parameters =
                TestDataProvider.createParametersDisablingAllValidations().setValidateModelAdditionalProperties(true);

        List<StyleError> errors = new OpenApiSpecStyleValidator(openAPI).validate(parameters);
        assertEquals(0, errors.size());
    }

    @Test
    void testModelAdditionalPropertiesValidationSuccessWhenDeclaredAsTrue() {
        OpenAPI openAPI = createValidOpenAPI()
                .components(OASFactory.createComponents()
                        .addSchema(
                                "MyObject",
                                OASFactory.createSchema()
                                        .type(SchemaType.OBJECT)
                                        .addProperty(
                                                "name",
                                                OASFactory.createSchema().type(SchemaType.STRING))
                                        .additionalPropertiesBoolean(true)));

        ValidatorParameters parameters =
                TestDataProvider.createParametersDisablingAllValidations().setValidateModelAdditionalProperties(true);

        List<StyleError> errors = new OpenApiSpecStyleValidator(openAPI).validate(parameters);
        assertEquals(0, errors.size());
    }

    @Test
    void testModelAdditionalPropertiesValidationSuccessWhenDeclaredAsSchema() {
        OpenAPI openAPI = createValidOpenAPI()
                .components(OASFactory.createComponents()
                        .addSchema(
                                "MyObject",
                                OASFactory.createSchema()
                                        .type(SchemaType.OBJECT)
                                        .addProperty(
                                                "name",
                                                OASFactory.createSchema().type(SchemaType.STRING))
                                        .additionalPropertiesSchema(
                                                OASFactory.createSchema().type(SchemaType.STRING))));

        ValidatorParameters parameters =
                TestDataProvider.createParametersDisablingAllValidations().setValidateModelAdditionalProperties(true);

        List<StyleError> errors = new OpenApiSpecStyleValidator(openAPI).validate(parameters);
        assertEquals(0, errors.size());
    }

    /*
     * The `type` keyword is optional: a schema declaring properties is
     * understood as an object and must declare `additionalProperties` too.
     */
    @Test
    void testModelAdditionalPropertiesValidationReportsInferredObject() {
        OpenAPI openAPI = createValidOpenAPI()
                .components(OASFactory.createComponents()
                        .addSchema(
                                "MyInferredObject",
                                OASFactory.createSchema()
                                        .addProperty(
                                                "name",
                                                OASFactory.createSchema().type(SchemaType.STRING))));

        ValidatorParameters parameters =
                TestDataProvider.createParametersDisablingAllValidations().setValidateModelAdditionalProperties(true);

        List<StyleError> errors = new OpenApiSpecStyleValidator(openAPI).validate(parameters);
        Assertions.assertAll(
                () -> assertEquals(1, errors.size()),
                () -> assertEquals(
                        "*ERROR* in Model 'MyInferredObject', field 'additionalProperties' -> An object schema should declare additionalProperties",
                        errors.get(0).toString()));
    }

    @Test
    void testModelAdditionalPropertiesValidationIgnoresNonObjectSchema() {
        OpenAPI openAPI = createValidOpenAPI()
                .components(OASFactory.createComponents()
                        .addSchema(
                                "MyString",
                                OASFactory.createSchema()
                                        .type(SchemaType.STRING)
                                        .addEnumeration("one")
                                        .addEnumeration("two")));

        ValidatorParameters parameters =
                TestDataProvider.createParametersDisablingAllValidations().setValidateModelAdditionalProperties(true);

        List<StyleError> errors = new OpenApiSpecStyleValidator(openAPI).validate(parameters);
        assertEquals(0, errors.size());
    }

    /*
     * An empty properties map declares no property, so the schema is not
     * understood as an object from its properties alone.
     */
    @Test
    void testModelAdditionalPropertiesValidationIgnoresEmptyProperties() {
        OpenAPI openAPI = createValidOpenAPI()
                .components(OASFactory.createComponents()
                        .addSchema("MyEmptyObject", OASFactory.createSchema().properties(new HashMap<>())));

        ValidatorParameters parameters =
                TestDataProvider.createParametersDisablingAllValidations().setValidateModelAdditionalProperties(true);

        List<StyleError> errors = new OpenApiSpecStyleValidator(openAPI).validate(parameters);
        assertEquals(0, errors.size());
    }

    /*
     * A composition of other schemas (e.g. an allOf used for inheritance)
     * declares no property of its own: it is not flagged as an object.
     */
    @Test
    void testModelAdditionalPropertiesValidationIgnoresAllOfComposition() {
        OpenAPI openAPI = createValidOpenAPI()
                .components(OASFactory.createComponents()
                        .addSchema(
                                "MyComposition",
                                OASFactory.createSchema()
                                        .addAllOf(OASFactory.createSchema().ref("#/components/schemas/One"))
                                        .addAllOf(OASFactory.createSchema().ref("#/components/schemas/Two"))));

        ValidatorParameters parameters =
                TestDataProvider.createParametersDisablingAllValidations().setValidateModelAdditionalProperties(true);

        List<StyleError> errors = new OpenApiSpecStyleValidator(openAPI).validate(parameters);
        assertEquals(0, errors.size());
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

    /* tests for naming convention parameters */
    @Test
    void shouldReportPathNamingConventionError() {
        OpenAPI openAPI = createValidOpenAPI();
        openAPI.paths(openAPI.getPaths()
                .addPathItem(
                        "/foo_path",
                        OASFactory.createPathItem()
                                .GET(OASFactory.createOperation()
                                        .operationId("pingGet")
                                        .summary("A simple get call")
                                        .description("When this method is called, the server answers with 200 OKs")
                                        .addTag("demo")
                                        .responses(OASFactory.createAPIResponses()
                                                .addAPIResponse(
                                                        "200",
                                                        OASFactory.createAPIResponse()
                                                                .description("OK"))))));

        OpenApiSpecStyleValidator validator = new OpenApiSpecStyleValidator(openAPI);

        List<StyleError> errors = validator.validate(new ValidatorParameters());
        Assertions.assertAll(
                () -> assertEquals(1, errors.size()),
                () -> assertEquals(
                        "*ERROR* in path /foo_path 'foo_path' -> path should be in hyphen-case",
                        errors.get(0).toString()));
    }

    @Test
    void shouldReportParameterNamingConventionError() {
        OpenAPI openAPI = createValidOpenAPI();

        OpenApiSpecStyleValidator validator = new OpenApiSpecStyleValidator(openAPI);

        ValidatorParameters validatorParams = new ValidatorParameters();
        validatorParams.setParameterNamingConvention(NamingConvention.HyphenCase);
        List<StyleError> errors = validator.validate(validatorParams);
        Assertions.assertAll(
                () -> assertEquals(3, errors.size()),
                () -> assertEquals(
                        "*ERROR* in path POST /foo/{pathParamOne} 'pathParamOne' -> path parameter should be in hyphen-case",
                        errors.get(0).toString()),
                () -> assertEquals(
                        "*ERROR* in path POST /foo/{pathParamOne} 'queryParamOne' -> query parameter should be in hyphen-case",
                        errors.get(1).toString()),
                () -> assertEquals(
                        "*ERROR* in path POST /foo/{pathParamOne} 'cookieParamOne' -> cookie parameter should be in hyphen-case",
                        errors.get(2).toString()));
    }

    @Test
    void shouldReportHeaderNamingConventionError() {
        OpenAPI openAPI = createValidOpenAPI();

        OpenApiSpecStyleValidator validator = new OpenApiSpecStyleValidator(openAPI);

        ValidatorParameters validatorParams = new ValidatorParameters();
        validatorParams.setHeaderNamingConvention(NamingConvention.HyphenCase);
        List<StyleError> errors = validator.validate(validatorParams);
        Assertions.assertAll(
                () -> assertEquals(1, errors.size()),
                () -> assertEquals(
                        "*ERROR* in path POST /foo/{pathParamOne} 'HEADER_PARAM_ONE' -> header should be in hyphen-case",
                        errors.get(0).toString()));
    }

    @Test
    void shouldReportSchemaNamingConventionError() {
        OpenAPI openAPI = createValidOpenAPI();

        OpenApiSpecStyleValidator validator = new OpenApiSpecStyleValidator(openAPI);

        ValidatorParameters validatorParams = new ValidatorParameters();
        validatorParams.setSchemaNamingConvention(NamingConvention.HyphenUpperCase);
        List<StyleError> errors = validator.validate(validatorParams);
        Assertions.assertAll(
                () -> assertEquals(2, errors.size()),
                () -> assertEquals(
                        "*ERROR* in model FooSchema 'FooSchema' -> schema should be in Hyphen-Upper-Case",
                        errors.get(0).toString()),
                () -> assertEquals(
                        "*ERROR* in model BazSchema 'BazSchema' -> schema should be in Hyphen-Upper-Case",
                        errors.get(1).toString()));
    }

    @Test
    void shouldReportPropertyNamingConventionError() {
        OpenAPI openAPI = createValidOpenAPI();

        OpenApiSpecStyleValidator validator = new OpenApiSpecStyleValidator(openAPI);

        ValidatorParameters validatorParams = new ValidatorParameters();
        validatorParams.setPropertyNamingConvention(NamingConvention.HyphenCase);
        List<StyleError> errors = validator.validate(validatorParams);
        Assertions.assertAll(
                () -> assertEquals(5, errors.size()),
                () -> assertEquals(
                        "*ERROR* in model FooSchema 'fooPropertyOne' -> property should be in hyphen-case",
                        errors.get(0).toString()),
                () -> assertEquals(
                        "*ERROR* in model FooSchema 'fooPropertyTwo' -> property should be in hyphen-case",
                        errors.get(1).toString()),
                () -> assertEquals(
                        "*ERROR* in model BazSchema 'bazPropertyTwo' -> property should be in hyphen-case",
                        errors.get(2).toString()),
                () -> assertEquals(
                        "*ERROR* in model BazSchema 'bazRecursiveProperty' -> property should be in hyphen-case",
                        errors.get(3).toString()),
                () -> assertEquals(
                        "*ERROR* in model BazSchema 'bazPropertyOne' -> property should be in hyphen-case",
                        errors.get(4).toString()));
    }

    @Test
    void shouldReportEnumNamingConventionError() {
        OpenAPI openAPI = createValidOpenAPI();
        openAPI.components(OASFactory.createComponents()
                .addSchema(
                        "EnumSchema",
                        OASFactory.createSchema()
                                .title("Enum schema title")
                                .type(SchemaType.STRING)
                                .nullable(true)
                                .enumeration(new ArrayList<Object>() {
                                    {
                                        add("red");
                                        add("Green");
                                        add("Light_Brown");
                                        add("BLUE");
                                        add("DARK_GREY");
                                        add(null);
                                    }
                                }))
                .addSchema(
                        "EnumInPropertySchema",
                        OASFactory.createSchema()
                                .title("Enum in property schema title")
                                .type(SchemaType.STRING)
                                .addProperty(
                                        "enumInProperty",
                                        OASFactory.createSchema()
                                                .example("example")
                                                .description("Enum in property description")
                                                .type(SchemaType.STRING)
                                                .nullable(true)
                                                .enumeration(new ArrayList<Object>() {
                                                    {
                                                        add("dark-green");
                                                        add("YELLOW");
                                                        add("TEAL_BLUE");
                                                        add(null);
                                                    }
                                                }))));

        OpenApiSpecStyleValidator validator = new OpenApiSpecStyleValidator(openAPI);

        ValidatorParameters validatorParams = new ValidatorParameters();
        validatorParams.setEnumNamingConvention(NamingConvention.UnderscoreUpperCase);
        List<StyleError> errors = validator.validate(validatorParams);
        Assertions.assertAll(
                () -> assertEquals(4, errors.size()),
                () -> assertEquals(
                        "*ERROR* in model EnumSchema 'red' -> enum value should be in UNDERSCORE_UPPER_CASE",
                        errors.get(0).toString()),
                () -> assertEquals(
                        "*ERROR* in model EnumSchema 'Green' -> enum value should be in UNDERSCORE_UPPER_CASE",
                        errors.get(1).toString()),
                () -> assertEquals(
                        "*ERROR* in model EnumSchema 'Light_Brown' -> enum value should be in UNDERSCORE_UPPER_CASE",
                        errors.get(2).toString()),
                () -> assertEquals(
                        "*ERROR* in model EnumInPropertySchema 'dark-green' -> enum value should be in UNDERSCORE_UPPER_CASE",
                        errors.get(3).toString()));
    }

    /* begin - tests for issue #367 */
    @Test
    void shouldReportQueryParamNamingConventionError() {
        OpenAPI openAPI = createValidOpenAPI();

        OpenApiSpecStyleValidator validator = new OpenApiSpecStyleValidator(openAPI);

        ValidatorParameters validatorParams = new ValidatorParameters();
        validatorParams.setQueryParamNamingConvention(NamingConvention.HyphenCase);
        List<StyleError> errors = validator.validate(validatorParams);
        Assertions.assertAll(
                () -> assertEquals(1, errors.size()),
                () -> assertEquals(
                        "*ERROR* in path POST /foo/{pathParamOne} 'queryParamOne' -> query parameter should be in hyphen-case",
                        errors.get(0).toString()));
    }

    @Test
    void shouldReportPathParamNamingConventionError() {
        OpenAPI openAPI = createValidOpenAPI();

        OpenApiSpecStyleValidator validator = new OpenApiSpecStyleValidator(openAPI);

        ValidatorParameters validatorParams = new ValidatorParameters();
        validatorParams.setPathParamNamingConvention(NamingConvention.HyphenCase);
        List<StyleError> errors = validator.validate(validatorParams);
        Assertions.assertAll(
                () -> assertEquals(1, errors.size()),
                () -> assertEquals(
                        "*ERROR* in path POST /foo/{pathParamOne} 'pathParamOne' -> path parameter should be in hyphen-case",
                        errors.get(0).toString()));
    }

    @Test
    void shouldReportCookieParamNamingConventionError() {
        OpenAPI openAPI = createValidOpenAPI();

        OpenApiSpecStyleValidator validator = new OpenApiSpecStyleValidator(openAPI);

        ValidatorParameters validatorParams = new ValidatorParameters();
        validatorParams.setCookieParamNamingConvention(NamingConvention.HyphenCase);
        List<StyleError> errors = validator.validate(validatorParams);
        Assertions.assertAll(
                () -> assertEquals(1, errors.size()),
                () -> assertEquals(
                        "*ERROR* in path POST /foo/{pathParamOne} 'cookieParamOne' -> cookie parameter should be in hyphen-case",
                        errors.get(0).toString()));
    }
    /* end - tests for issue #367 */

    // https://github.com/OpenAPITools/openapi-style-validator/issues/152
    @Test
    void shouldAcceptSpecificModelProperties() {
        OpenAPI openAPI = createValidOpenAPI();
        openAPI.components(openAPI.getComponents()
                .addSchema(
                        "Items",
                        OASFactory.createSchema().type(SchemaType.OBJECT).properties(new HashMap<String, Schema>() {
                            {
                                put(
                                        "_links",
                                        OASFactory.createSchema()
                                                // _links should be an object. However, this is enough
                                                // to represent the case
                                                .type(SchemaType.STRING));
                            }
                        })));

        OpenApiSpecStyleValidator validator = new OpenApiSpecStyleValidator(openAPI);
        ValidatorParameters parameters = new ValidatorParameters()
                // Keep the details away
                .setValidateModelPropertiesDescription(false)
                .setValidateModelPropertiesExample(false)
                // Reproduce the original error
                .setPropertyNamingConvention(NamingConvention.CamelCase)
                .setValidateNaming(true)
                // Add the fix
                .setAllowedModelProperties(Collections.singletonList("_links"));
        List<StyleError> errors = validator.validate(parameters);
        Assertions.assertTrue(
                errors.isEmpty(),
                () -> errors.stream().map(StyleError::toString).collect(Collectors.joining()));
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
                                                                        .description("OK")))))
                        .addPathItem(
                                "/foo/{pathParamOne}",
                                OASFactory.createPathItem()
                                        .POST(OASFactory.createOperation()
                                                .operationId("fooPost")
                                                .summary("A simple post call")
                                                .description(
                                                        "When this method is called, the server answers with 200 OKs")
                                                .addTag("demo")
                                                .addParameter(OASFactory.createParameter()
                                                        .name("pathParamOne")
                                                        .in(In.PATH)
                                                        .required(true)
                                                        .description("Foo path param number one"))
                                                .addParameter(OASFactory.createParameter()
                                                        .name("queryParamOne")
                                                        .in(In.QUERY)
                                                        .required(true)
                                                        .description("Foo query param number one"))
                                                .addParameter(OASFactory.createParameter()
                                                        .name("HEADER_PARAM_ONE")
                                                        .in(In.HEADER)
                                                        .required(true)
                                                        .description("Foo header param number one"))
                                                .addParameter(OASFactory.createParameter()
                                                        .name("cookieParamOne")
                                                        .in(In.COOKIE)
                                                        .required(true)
                                                        .description("Foo cookie param number one"))
                                                .requestBody(
                                                        OASFactory.createRequestBody()
                                                                .description("Foo request body")
                                                                .required(true)
                                                                .content(
                                                                        OASFactory.createContent()
                                                                                .addMediaType(
                                                                                        "application/json",
                                                                                        OASFactory.createMediaType()
                                                                                                .schema(
                                                                                                        OASFactory
                                                                                                                .createSchema()
                                                                                                                .ref(
                                                                                                                        "#/components/schemas/FooSchema")))))
                                                .responses(OASFactory.createAPIResponses()
                                                        .addAPIResponse(
                                                                "200",
                                                                OASFactory.createAPIResponse()
                                                                        .description("OK")))))
                        .addPathItem(
                                "/baz",
                                OASFactory.createPathItem()
                                        .POST(OASFactory.createOperation()
                                                .operationId("bazPost")
                                                .summary("A simple post call")
                                                .description(
                                                        "When this method is called, the server answers with 200 OKs")
                                                .addTag("demo")
                                                .requestBody(
                                                        OASFactory.createRequestBody()
                                                                .description("Baz request body")
                                                                .required(true)
                                                                .content(
                                                                        OASFactory.createContent()
                                                                                .addMediaType(
                                                                                        "application/json",
                                                                                        OASFactory.createMediaType()
                                                                                                .schema(
                                                                                                        OASFactory
                                                                                                                .createSchema()
                                                                                                                .ref(
                                                                                                                        "#/components/schemas/BazSchema")))))
                                                .responses(OASFactory.createAPIResponses()
                                                        .addAPIResponse(
                                                                "200",
                                                                OASFactory.createAPIResponse()
                                                                        .description("OK"))))))
                .components(OASFactory.createComponents().schemas(new HashMap<String, Schema>() {
                    {
                        put(
                                "FooSchema",
                                OASFactory.createSchema()
                                        .title("Foo schema title")
                                        .type(SchemaType.OBJECT)
                                        .properties(new HashMap<String, Schema>() {
                                            {
                                                put(
                                                        "fooPropertyOne",
                                                        OASFactory.createSchema()
                                                                .type(SchemaType.STRING)
                                                                .example("example1")
                                                                .description("Simple property description 1"));
                                                put(
                                                        "fooPropertyTwo",
                                                        OASFactory.createSchema()
                                                                .type(SchemaType.INTEGER)
                                                                .example("example2")
                                                                .description("Simple property description 2"));
                                            }
                                        })
                                        .required(new ArrayList<String>() {
                                            {
                                                add("fooPropertyOne");
                                                add("fooPropertyTwo");
                                            }
                                        }));
                        put(
                                "BazSchema",
                                OASFactory.createSchema()
                                        .title("Baz schema title")
                                        .type(SchemaType.OBJECT)
                                        .properties(new HashMap<String, Schema>() {
                                            {
                                                put(
                                                        "bazPropertyOne",
                                                        OASFactory.createSchema()
                                                                .type(SchemaType.STRING)
                                                                .example("example1")
                                                                .description("Simple property description 1"));
                                                put(
                                                        "bazPropertyTwo",
                                                        OASFactory.createSchema()
                                                                .type(SchemaType.INTEGER)
                                                                .example("example2")
                                                                .description("Simple property description 2")
                                                                .addProperty(
                                                                        "bazRecursiveProperty",
                                                                        OASFactory.createSchema()
                                                                                .type(SchemaType.STRING)
                                                                                .example("recursive example")
                                                                                .description(
                                                                                        "Recursive property description")));
                                            }
                                        })
                                        .required(new ArrayList<String>() {
                                            {
                                                add("bazPropertyOne");
                                                add("bazPropertyTwo");
                                            }
                                        }));
                    }
                }));
    }
}
