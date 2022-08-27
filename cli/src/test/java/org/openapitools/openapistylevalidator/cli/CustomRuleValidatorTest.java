package org.openapitools.openapistylevalidator.cli;

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
import org.openapitools.openapistylevalidator.styleerror.StyleError;
import org.opentest4j.MultipleFailuresError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomRuleValidatorTest {
    private static final DefaultParser parser = new DefaultParser();
    public static final String SRC_TEST_RESOURCES_MISSING_TAG_YAML = "src/test/resources/missing_tags.yaml";

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
        class GivenFileProvidedWithInvalidTagDefinedYaml {
            @BeforeEach
            void init() throws ParseException {
                commandLine = parser.parse(options, new String[] {"-s", SRC_TEST_RESOURCES_MISSING_TAG_YAML});
            }

            @Test
            void returnSevenErrors() {
                List<StyleError> errorList = validationInitiator.validate(optionManager, commandLine);

                sevenErrorsAssertions(errorList);
            }
        }
    }

    private void sevenErrorsAssertions(List<StyleError> errorList) throws MultipleFailuresError {
        Assertions.assertAll(
                () -> assertEquals(7, errorList.size()),
                () -> assertEquals(
                        "*ERROR* Section: APIInfo: 'license' -> Should be present and not empty",
                        errorList.get(0).toString()),
                () -> assertEquals(
                        "*ERROR* Section: APIInfo: 'description' -> Should be present and not empty",
                        errorList.get(1).toString()),
                () -> assertEquals(
                        "*ERROR* Section: APIInfo: 'contact' -> Should be present and not empty",
                        errorList.get(2).toString()),
                () -> assertEquals(
                        "*ERROR* in Operation POST /ping 'description' -> This field should be present and not empty",
                        errorList.get(3).toString()),
                () -> assertEquals(
                        "*ERROR* in Operation POST /ping 'summary' -> This field should be present and not empty",
                        errorList.get(4).toString()),
                () -> assertEquals(
                        "*ERROR* Section: Operations: 'tags' -> FIX ME is not defined under tag section",
                        errorList.get(5).toString()),

                () -> assertEquals(
                        "*ERROR* Section: Operations: 'tags' -> FIX ME is not defined under tag section",
                        errorList.get(5).toString()),
                () -> assertEquals(
                        "*ERROR* Section: OpenAPI: 'tags' -> Should be present and not empty",
                        errorList.get(6).toString()));
    }
}
