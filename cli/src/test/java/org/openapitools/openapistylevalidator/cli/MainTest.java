package org.openapitools.openapistylevalidator.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openapitools.openapistylevalidator.ValidatorParameters;
import org.openapitools.openapistylevalidator.ValidatorParameters.NamingConvention;
import org.openapitools.openapistylevalidator.styleerror.StyleError;
import org.opentest4j.MultipleFailuresError;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MainTest {
	private static final String PREFIX = "    \"";
	private static final String SEPARATOR = "\": ";
	private static final String SEPARATOR_QUOTE = "\": \"";
	private static final String NEXT_LINE = "," + System.lineSeparator();
	private static final String NEXT_LINE_QUOTE = "\"," + System.lineSeparator();
	private static final DefaultParser parser = new DefaultParser();

    @Test
    void validateShouldReturnSixErrorsWithoutOptionFile() throws Exception {
        OptionManager optionManager = new OptionManager();
        Options options = optionManager.getOptions();
        CommandLine commandLine = parser.parse(options, new String[]{"-s", "src/test/resources/ping.yaml"});
        List<StyleError> errorList = Main.validate(optionManager, commandLine);

        sixErrorsAssertions(errorList);
    }

    @Test
    void validateShouldReturnSixErrorsWithEmptyOptionFile() throws Exception {
        OptionManager optionManager = new OptionManager();
        Options options = optionManager.getOptions();
        CommandLine commandLine = parser.parse(options, new String[]{"-s", "src/test/resources/ping.yaml", "-o", "src/test/resources/empty.json"});
        List<StyleError> errorList = Main.validate(optionManager, commandLine);

        sixErrorsAssertions(errorList);
    }

    @Test
    void validateShouldReturnSixErrorsWithDefaultOptionFile() throws Exception {
        OptionManager optionManager = new OptionManager();
        Options options = optionManager.getOptions();
        CommandLine commandLine = parser.parse(options, new String[]{"-s", "src/test/resources/ping.yaml", "-o", "src/test/resources/default.json"});
        List<StyleError> errorList = Main.validate(optionManager, commandLine);

        sixErrorsAssertions(errorList);
    }

    private void sixErrorsAssertions(List<StyleError> errorList) throws MultipleFailuresError {
        Assertions.assertAll(
                () -> assertEquals(6, errorList.size()),
                () -> assertEquals("*ERROR* Section: APIInfo: 'license' -> Should be present and not empty", errorList.get(0).toString()),
                () -> assertEquals("*ERROR* Section: APIInfo: 'description' -> Should be present and not empty", errorList.get(1).toString()),
                () -> assertEquals("*ERROR* Section: APIInfo: 'contact' -> Should be present and not empty", errorList.get(2).toString()),
                () -> assertEquals("*ERROR* in Operation POST /ping 'description' -> This field should be present and not empty", errorList.get(3).toString()),
                () -> assertEquals("*ERROR* in Operation POST /ping 'summary' -> This field should be present and not empty", errorList.get(4).toString()),
                () -> assertEquals("*ERROR* in Operation POST /ping 'tags' -> The collection should be present and there should be at least one item in it", errorList.get(5).toString())
                );
    }

    @Test
    void validateShouldReturnNoErrorsWithCustomOptionFile() throws Exception {
        OptionManager optionManager = new OptionManager();
        Options options = optionManager.getOptions();
        CommandLine commandLine = parser.parse(options, new String[]{"-s", "src/test/resources/ping.yaml", "-o", "src/test/resources/custom.json"});
        List<StyleError> errorList = Main.validate(optionManager, commandLine);
        assertEquals(0, errorList.size());
    }

    @Test
    void validateWithoutOptionFileShouldReturnNamingErrors() throws Exception {
        OptionManager optionManager = new OptionManager();
        Options options = optionManager.getOptions();
        CommandLine commandLine = parser.parse(options, new String[]{"-s", "src/test/resources/some.yaml"});
        List<StyleError> errorList = Main.validate(optionManager, commandLine);
        namingErrorsAssertions(errorList, "camelCase", "camelCase", "hyphen-case");
    }

    @Test
    void validateWithAlternativeNamingOptionTestShouldReturnNamingErrors() throws Exception {
        OptionManager optionManager = new OptionManager();
        Options options = optionManager.getOptions();
        CommandLine commandLine = parser.parse(options, new String[]{"-s", "src/test/resources/some.yaml", "-o", "src/test/resources/alternative.json"});
        List<StyleError> errorList = Main.validate(optionManager, commandLine);
        namingErrorsAssertions(errorList, "hyphen-case", "hyphen-case", "camelCase");
    }

    @Test
    void validateWithUnderscoreNamingOptionTestShouldReturnNoError() throws Exception {
        OptionManager optionManager = new OptionManager();
        Options options = optionManager.getOptions();
        CommandLine commandLine = parser.parse(options, new String[]{"-s", "src/test/resources/some.yaml", "-o", "src/test/resources/underscore.json"});
        List<StyleError> errorList = Main.validate(optionManager, commandLine);
        assertEquals(0, errorList.size());
    }

    @Test
    void validateWithAlternativeLegacyNamingOptionTestShouldReturnNamingErrors() throws Exception {
        OptionManager optionManager = new OptionManager();
        Options options = optionManager.getOptions();
        CommandLine commandLine = parser.parse(options, new String[]{"-s", "src/test/resources/some.yaml", "-o", "src/test/resources/alternativeLegacy.json"});
        List<StyleError> errorList = Main.validate(optionManager, commandLine);
        namingErrorsAssertions(errorList, "hyphen-case", "hyphen-case", "camelCase");
    }

    @Test
    void validateWithAlternativeAndLegacyNamingOptionTestShouldReturnNamingErrors() throws Exception {
        OptionManager optionManager = new OptionManager();
        Options options = optionManager.getOptions();
        CommandLine commandLine = parser.parse(options, new String[]{"-s", "src/test/resources/some.yaml", "-o", "src/test/resources/alternativeAndLegacy.json"});
        List<StyleError> errorList = Main.validate(optionManager, commandLine);
        namingErrorsAssertions(errorList, "hyphen-case", "hyphen-case", "camelCase");
    }

    @Test
    void validateWithUnderscoreLegacyNamingOptionTestShouldReturnNoError() throws Exception {
        OptionManager optionManager = new OptionManager();
        Options options = optionManager.getOptions();
        CommandLine commandLine = parser.parse(options, new String[]{"-s", "src/test/resources/some.yaml", "-o", "src/test/resources/underscoreLegacy.json"});
        List<StyleError> errorList = Main.validate(optionManager, commandLine);
        assertEquals(0, errorList.size());
    }

    private void namingErrorsAssertions(List<StyleError> errorList, String expectedPathParameterConvention, String expectedQueryParameterConvention, String expectedPathConvention) throws MultipleFailuresError {
        Assertions.assertAll(
                () -> assertEquals(3, errorList.size()),
                () -> assertEquals("*ERROR* in path POST /some_path/{some_id} 'some_id' -> parameter should be in " + expectedPathParameterConvention, errorList.get(0).toString()),
                () -> assertEquals("*ERROR* in path POST /some_path/{some_id} 'some_name' -> parameter should be in " + expectedQueryParameterConvention, errorList.get(1).toString()),
                () -> assertEquals("*ERROR* in path /some_path/{some_id} 'some_path' -> path should be in " + expectedPathConvention, errorList.get(2).toString())
                );
    }
    
    @Test
    void defaultJsonFileContainsConstants() throws Exception {
        InputStream stream = getClass().getResourceAsStream("/default.json");
        String content = IOUtils.toString(stream, StandardCharsets.UTF_8);

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append(System.lineSeparator());
        sb.append(PREFIX);
        sb.append(ValidatorParameters.VALIDATE_INFO_LICENSE);
        sb.append(SEPARATOR);
        sb.append("true");
        sb.append(NEXT_LINE);
        sb.append(PREFIX);
        sb.append(ValidatorParameters.VALIDATE_INFO_DESCRIPTION);
        sb.append(SEPARATOR);
        sb.append("true");
        sb.append(NEXT_LINE);
        sb.append(PREFIX);
        sb.append(ValidatorParameters.VALIDATE_INFO_CONTACT);
        sb.append(SEPARATOR);
        sb.append("true");
        sb.append(NEXT_LINE);
        sb.append(PREFIX);
        sb.append(ValidatorParameters.VALIDATE_OPERATION_OPERATION_ID);
        sb.append(SEPARATOR);
        sb.append("true");
        sb.append(NEXT_LINE);
        sb.append(PREFIX);
        sb.append(ValidatorParameters.VALIDATE_OPERATION_DESCRIPTION);
        sb.append(SEPARATOR);
        sb.append("true");
        sb.append(NEXT_LINE);
        sb.append(PREFIX);
        sb.append(ValidatorParameters.VALIDATE_OPERATION_TAG);
        sb.append(SEPARATOR);
        sb.append("true");
        sb.append(NEXT_LINE);
        sb.append(PREFIX);
        sb.append(ValidatorParameters.VALIDATE_OPERATION_SUMMARY);
        sb.append(SEPARATOR);
        sb.append("true");
        sb.append(NEXT_LINE);
        sb.append(PREFIX);
        sb.append(ValidatorParameters.VALIDATE_MODEL_PROPERTIES_EXAMPLE);
        sb.append(SEPARATOR);
        sb.append("true");
        sb.append(NEXT_LINE);
        sb.append(PREFIX);
        sb.append(ValidatorParameters.VALIDATE_MODEL_PROPERTIES_DESCRIPTION);
        sb.append(SEPARATOR);
        sb.append("true");
        sb.append(NEXT_LINE);
        sb.append(PREFIX);
        sb.append(ValidatorParameters.VALIDATE_MODEL_REQUIRED_PROPERTIES);
        sb.append(SEPARATOR);
        sb.append("true");
        sb.append(NEXT_LINE);
        sb.append(PREFIX);
        sb.append(ValidatorParameters.VALIDATE_MODEL_NO_LOCAL_DEF);
        sb.append(SEPARATOR);
        sb.append("true");
        sb.append(NEXT_LINE);
        sb.append(PREFIX);
        sb.append(ValidatorParameters.VALIDATE_NAMING);
        sb.append(SEPARATOR);
        sb.append("true");
        sb.append(NEXT_LINE);
        sb.append(PREFIX);
        sb.append(ValidatorParameters.IGNORE_HEADER_X_NAMING);
        sb.append(SEPARATOR);
        sb.append("true");
        sb.append(NEXT_LINE);
        sb.append(PREFIX);
        sb.append(ValidatorParameters.PATH_NAMING_CONVENTION);
        sb.append(SEPARATOR_QUOTE);
        sb.append(NamingConvention.HyphenCase);
        sb.append(NEXT_LINE_QUOTE);
        sb.append(PREFIX);
        sb.append(ValidatorParameters.HEADER_NAMING_CONVENTION);
        sb.append(SEPARATOR_QUOTE);
        sb.append(NamingConvention.UnderscoreUpperCase);
        sb.append(NEXT_LINE_QUOTE);
        sb.append(PREFIX);
        sb.append(ValidatorParameters.PARAMETER_NAMING_CONVENTION);
        sb.append(SEPARATOR_QUOTE);
        sb.append(NamingConvention.CamelCase);
        sb.append(NEXT_LINE_QUOTE);
        sb.append(PREFIX);
        sb.append(ValidatorParameters.PROPERTY_NAMING_CONVENTION);
        sb.append(SEPARATOR_QUOTE);
        sb.append(NamingConvention.CamelCase);
        sb.append("\"");
        sb.append(System.lineSeparator());
        sb.append("}");
        assertEquals(sb.toString(), content);
    }

    @Test
    void validateShouldThrowIllegalArgumentExceptionForInvalidPathNamingConvention() throws ParseException {
        OptionManager optionManager = new OptionManager();
        Options options = optionManager.getOptions();
        CommandLine commandLine = parser.parse(options, new String[]{
                "-s", "src/test/resources/some.yaml",
                "-o", "src/test/resources/invalidPathNamingConvention.json"
        });

        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Main.validate(optionManager, commandLine);
        });
        Assertions.assertEquals("Invalid pathNamingConvention", thrown.getMessage());
    }

    @Test
    void validateShouldThrowIllegalArgumentExceptionForInvalidParameterNamingConvention() throws ParseException {
        OptionManager optionManager = new OptionManager();
        Options options = optionManager.getOptions();
        CommandLine commandLine = parser.parse(options, new String[]{
                "-s", "src/test/resources/some.yaml",
                "-o", "src/test/resources/invalidParameterNamingConvention.json"
        });

        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Main.validate(optionManager, commandLine);
        });
        Assertions.assertEquals("Invalid parameterNamingConvention", thrown.getMessage());
    }

    @Test
    void validateShouldThrowIllegalArgumentExceptionForInvalidHeaderNamingConvention() throws ParseException {
        OptionManager optionManager = new OptionManager();
        Options options = optionManager.getOptions();
        CommandLine commandLine = parser.parse(options, new String[]{
                "-s", "src/test/resources/some.yaml",
                "-o", "src/test/resources/invalidHeaderNamingConvention.json"
        });

        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Main.validate(optionManager, commandLine);
        });
        Assertions.assertEquals("Invalid headerNamingConvention", thrown.getMessage());
    }

    @Test
    void validateShouldThrowIllegalArgumentExceptionForInvalidPropertyNamingConvention() throws ParseException {
        OptionManager optionManager = new OptionManager();
        Options options = optionManager.getOptions();
        CommandLine commandLine = parser.parse(options, new String[]{
                "-s", "src/test/resources/some.yaml",
                "-o", "src/test/resources/invalidPropertyNamingConvention.json"
        });

        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Main.validate(optionManager, commandLine);
        });
        Assertions.assertEquals("Invalid propertyNamingConvention", thrown.getMessage());
    }

    /**
     * See https://github.com/OpenAPITools/openapi-style-validator/issues/190
     */
    @Test
    void shouldNotFailWhenThereAreNoPaths() throws Throwable {
        OptionManager optionManager = new OptionManager();
        Options options = optionManager.getOptions();
        CommandLine commandLine = parser.parse(options, new String[]{
                "-s", "src/test/resources/openApiWithoutPaths.yaml",
                "-o", "src/test/resources/default.json"
        });

        // Test will fail if any exception is thrown
        Main.validate(optionManager, commandLine);
    }

}
