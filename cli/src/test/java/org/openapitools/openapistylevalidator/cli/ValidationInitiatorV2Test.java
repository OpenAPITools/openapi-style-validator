package org.openapitools.openapistylevalidator.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openapitools.openapistylevalidator.error.StyleError;
import org.opentest4j.MultipleFailuresError;

class ValidationInitiatorV2Test {
    private static final DefaultParser parser = new DefaultParser();

    public static final String SRC_TEST_RESOURCES_PING_YAML = "src/test/resources/ping.yaml";

    private final OutputUtils outputUtils = Mockito.mock(OutputUtils.class);
    private OptionManager optionManager;
    private Options options;
    private CommandLine commandLine;

    private ValidationInitiator validationInitiator;

    @BeforeEach
    void init() {
        validationInitiator = new ValidationInitiator();

        optionManager = new OptionManager(outputUtils);
        options = optionManager.getOptions();
    }

    @Nested
    @DisplayName("Method: validate")
    class Validate {
        @Nested
        class GivenFileProvidedWithInvalidAPIInfoYaml {
            @BeforeEach
            void init() throws ParseException {
                commandLine = parser.parse(options, new String[] {"-s", SRC_TEST_RESOURCES_PING_YAML});
            }

            @Test
            void returnSevenErrors() {
                List<StyleError> errorList = validationInitiator.validateV2(optionManager, commandLine);
                fiveErrorsAssertions(errorList);
            }
        }

        @Nested
        class GivenInvalidHeaderNamingConvention {

            public static final String INVALID_HEADER_NAMING_YAML = "src/test/resources/invalid_header_naming.yaml";
            public static final String VALID_HEADER_NAMING_YAML = "src/test/resources/vaild_header_naming.yaml";

            @Test
            void invalidHeaderNamingConventionTest() throws ParseException {
                commandLine = parser.parse(options, new String[] {"-s", INVALID_HEADER_NAMING_YAML});
                List<StyleError> errorList = validationInitiator.validateV2(optionManager, commandLine);
                namingErrorsAssertions(errorList);
            }

            @Test
            void validHeaderNamingConventionTest() throws ParseException {
                commandLine = parser.parse(options, new String[] {"-s", VALID_HEADER_NAMING_YAML});
                List<StyleError> errorList = validationInitiator.validateV2(optionManager, commandLine);
                assertEquals(0, errorList.size());
            }
        }
    }

    private void fiveErrorsAssertions(List<StyleError> errorList) throws MultipleFailuresError {
        Assertions.assertAll(
                () -> assertEquals(3, errorList.size()),
                () -> assertEquals(
                        "*ERROR* Section: APIInfo: 'license' -> Should be present and not empty",
                        errorList.get(0).toString()),
                () -> assertEquals(
                        "*ERROR* Section: APIInfo: 'description' -> Should be present and not empty",
                        errorList.get(1).toString()),
                () -> assertEquals(
                        "*ERROR* Section: APIInfo: 'contact' -> Should be present and not empty",
                        errorList.get(2).toString()));
    }

    private void namingErrorsAssertions(List<StyleError> errorList) throws MultipleFailuresError {
        Assertions.assertAll(
                () -> assertEquals(1, errorList.size()),
                () -> assertEquals(
                        "*ERROR* in path GET /ping 'user-name' -> header should be in " + "UNDERSCORE_UPPER_CASE",
                        errorList.get(0).toString()));
    }
}
