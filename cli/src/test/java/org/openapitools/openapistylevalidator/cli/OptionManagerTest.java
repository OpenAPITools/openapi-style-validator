package org.openapitools.openapistylevalidator.cli;

import static org.mockito.Mockito.verify;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openapitools.openapistylevalidator.ValidatorParameters;

class OptionManagerTest {

    private OutputUtils outputUtils = Mockito.mock(OutputUtils.class);
    private OptionManager subject = new OptionManager(outputUtils);

    @Test
    void shouldPrintReplacementUsageForPathNamingStrategy() throws Exception {
        subject.getOptionalValidatorParametersOrDefault(withOptions("alternativeAndLegacy"));

        verify(outputUtils).printReplacementUsage("pathNamingStrategy", "pathNamingConvention");
    }

    @Test
    void shouldPrintReplacementUsageForParameterNamingStrategy() throws Exception {
        subject.getOptionalValidatorParametersOrDefault(withOptions("alternativeAndLegacy"));

        verify(outputUtils).printReplacementUsage("parameterNamingStrategy", "parameterNamingConvention");
    }

    @Test
    void shouldPrintReplacementUsageForPropertyNamingStrategy() throws Exception {
        subject.getOptionalValidatorParametersOrDefault(withOptions("alternativeAndLegacy"));

        verify(outputUtils).printReplacementUsage("propertyNamingStrategy", "propertyNamingConvention");
    }

    @Test
    void shouldPrintDeprecationWarningForPathNamingStrategy() throws Exception {
        subject.getOptionalValidatorParametersOrDefault(withOptions("alternativeLegacy"));

        verify(outputUtils).printDeprecationWarning("pathNamingStrategy", "pathNamingConvention");
    }

    @Test
    void shouldPrintDeprecationWarningForParameterNamingStrategy() throws Exception {
        subject.getOptionalValidatorParametersOrDefault(withOptions("alternativeLegacy"));

        verify(outputUtils).printDeprecationWarning("parameterNamingStrategy", "parameterNamingConvention");
    }

    @Test
    void shouldPrintDeprecationWarningForPropertyNamingStrategy() throws Exception {
        subject.getOptionalValidatorParametersOrDefault(withOptions("alternativeLegacy"));

        verify(outputUtils).printDeprecationWarning("propertyNamingStrategy", "propertyNamingConvention");
    }

    @Test
    void shouldFixPathNamingStrategy() throws Exception {
        ValidatorParameters parameters =
                subject.getOptionalValidatorParametersOrDefault(withOptions("alternativeLegacy"));

        Assertions.assertEquals(ValidatorParameters.NamingConvention.CamelCase, parameters.getPathNamingConvention());
    }

    @Test
    void shouldFixParameterNamingStrategy() throws Exception {
        ValidatorParameters parameters =
                subject.getOptionalValidatorParametersOrDefault(withOptions("alternativeLegacy"));

        Assertions.assertEquals(
                ValidatorParameters.NamingConvention.HyphenCase, parameters.getParameterNamingConvention());
    }

    @Test
    void shouldFixPropertyNamingStrategy() throws Exception {
        ValidatorParameters parameters =
                subject.getOptionalValidatorParametersOrDefault(withOptions("alternativeLegacy"));

        Assertions.assertEquals(
                ValidatorParameters.NamingConvention.HyphenCase, parameters.getPropertyNamingConvention());
    }

    @Test
    void shouldIgnorePathNamingStrategyWhenPathNamingConventionIsSet() throws Exception {
        ValidatorParameters parameters =
                subject.getOptionalValidatorParametersOrDefault(withOptions("alternativeAndLegacy"));

        Assertions.assertEquals(ValidatorParameters.NamingConvention.CamelCase, parameters.getPathNamingConvention());
    }

    @Test
    void shouldIgnoreParameterNamingStrategyWhenParameterNamingConventionIsSet() throws Exception {
        ValidatorParameters parameters =
                subject.getOptionalValidatorParametersOrDefault(withOptions("alternativeAndLegacy"));

        Assertions.assertEquals(
                ValidatorParameters.NamingConvention.HyphenCase, parameters.getParameterNamingConvention());
    }

    @Test
    void shouldIgnorePropertyNamingStrategyWhenPropertyNamingConventionIsSet() throws Exception {
        ValidatorParameters parameters =
                subject.getOptionalValidatorParametersOrDefault(withOptions("alternativeAndLegacy"));

        Assertions.assertEquals(
                ValidatorParameters.NamingConvention.HyphenCase, parameters.getPropertyNamingConvention());
    }

    @Test
    void shouldParseAllOptions() throws Exception {
        ValidatorParameters parameters = subject.getOptionalValidatorParametersOrDefault(withOptions("default"));

        Assertions.assertEquals(true, parameters.isValidateInfoLicense());
        Assertions.assertEquals(true, parameters.isValidateInfoDescription());
        Assertions.assertEquals(true, parameters.isValidateInfoContact());
        Assertions.assertEquals(true, parameters.isValidateOperationOperationId());
        Assertions.assertEquals(true, parameters.isValidateOperationDescription());
        Assertions.assertEquals(true, parameters.isValidateOperationTag());
        Assertions.assertEquals(true, parameters.isValidateOperationSummary());
        Assertions.assertEquals(true, parameters.isValidateModelPropertiesExample());
        Assertions.assertEquals(true, parameters.isValidateModelPropertiesDescription());
        Assertions.assertEquals(true, parameters.isValidateModelRequiredProperties());
        Assertions.assertEquals(true, parameters.isValidateNaming());
        Assertions.assertEquals(ValidatorParameters.NamingConvention.HyphenCase, parameters.getPathNamingConvention());
        Assertions.assertEquals(
                ValidatorParameters.NamingConvention.UnderscoreUpperCase, parameters.getHeaderNamingConvention());
        Assertions.assertEquals(
                ValidatorParameters.NamingConvention.CamelCase, parameters.getParameterNamingConvention());
        Assertions.assertEquals(
                ValidatorParameters.NamingConvention.CamelCase, parameters.getPropertyNamingConvention());
    }

    /* begin - tests for issue #367 */
    @Test
    void shouldParseDetailedParameterNamingConventionOptions() throws Exception {
        ValidatorParameters parameters = subject.getOptionalValidatorParametersOrDefault(withOptions("detailedParameterNamingConvention"));

        Assertions.assertEquals(true, parameters.isValidateNaming());
        Assertions.assertEquals(ValidatorParameters.NamingConvention.HyphenCase, parameters.getPathNamingConvention());
        Assertions.assertEquals(
                ValidatorParameters.NamingConvention.CamelCase, parameters.getParameterNamingConvention());
        Assertions.assertEquals(
                ValidatorParameters.NamingConvention.CamelCase, parameters.getPropertyNamingConvention());
        Assertions.assertEquals(
                ValidatorParameters.NamingConvention.HyphenCase, parameters.getPathParamNamingConvention());
        Assertions.assertEquals(
                ValidatorParameters.NamingConvention.HyphenCase, parameters.getQueryParamNamingConvention());
        Assertions.assertEquals(
                ValidatorParameters.NamingConvention.HyphenCase, parameters.getCookieParamNamingConvention());
    }

    @Test
    /**
     * When only the 'parameterNamingConvention' naming convention option is set, it should rule over all parameters types.
     * Note that 'getPathParamNamingConvention', 'getQueryParamNamingConvention' and 'getCookieParamNamingConvention'
     * must return the same naming convention returned for 'getParameterNamingConvention'.
     */
    void shouldSetAllParametersNamingConventionUnderSameNameConvention() throws Exception {
        ValidatorParameters parameters = subject.getOptionalValidatorParametersOrDefault(withOptions("alternative"));

        Assertions.assertEquals(true, parameters.isValidateNaming());
        Assertions.assertEquals(ValidatorParameters.NamingConvention.CamelCase, parameters.getPathNamingConvention());
        Assertions.assertEquals(
                ValidatorParameters.NamingConvention.HyphenCase, parameters.getParameterNamingConvention());
        Assertions.assertEquals(
                ValidatorParameters.NamingConvention.HyphenCase, parameters.getPropertyNamingConvention());
        Assertions.assertEquals(parameters.getParameterNamingConvention(), parameters.getPathParamNamingConvention());
        Assertions.assertEquals(parameters.getParameterNamingConvention(), parameters.getQueryParamNamingConvention());
        Assertions.assertEquals(parameters.getParameterNamingConvention(), parameters.getCookieParamNamingConvention());
    }

    @Test
    /**
     * If 'parameterNamingConvention' naming convention option is set and another naming convention for specific parameters
     * (query, path or cookie) is also set, the getters must return the specified values.
     * The parameters which hadn't had their naming conventions explicitly specified, will be ruled by the
     * 'parameterNamingConvention' option.
     */
    void shouldParseCoexistingParameterNamingConventionOptions() throws Exception {
        ValidatorParameters parameters = subject.getOptionalValidatorParametersOrDefault(withOptions("parameterNamingConventionCoexistence"));

        Assertions.assertEquals(true, parameters.isValidateNaming());
        Assertions.assertEquals(ValidatorParameters.NamingConvention.HyphenCase, parameters.getPathNamingConvention());
        Assertions.assertEquals(
                ValidatorParameters.NamingConvention.UnderscoreUpperCase, parameters.getParameterNamingConvention());
        Assertions.assertEquals(parameters.getParameterNamingConvention(), parameters.getPathParamNamingConvention());
        Assertions.assertEquals(
                ValidatorParameters.NamingConvention.HyphenCase, parameters.getQueryParamNamingConvention());
        Assertions.assertEquals(
                ValidatorParameters.NamingConvention.HyphenCase, parameters.getCookieParamNamingConvention());
    }
    /* end - tests for issue #367 */

    private CommandLine withOptions(String fileName) throws Exception {
        DefaultParser parser = new DefaultParser();
        Options options = subject.getOptions();
        return parser.parse(
                options,
                new String[] {"-s", "src/test/resources/some.yaml", "-o", "src/test/resources/" + fileName + ".json"});
    }
}
