package org.openapitools.openapistylevalidator.cli;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.openapitools.openapistylevalidator.error.StyleError;
import org.opentest4j.MultipleFailuresError;

class ValidationInitiatorTest {
    private static final DefaultParser parser = new DefaultParser();
    public static final String SRC_TEST_RESOURCES_PING_YAML = "src/test/resources/ping.yaml";
    public static final String SRC_TEST_RESOURCES_SOME_YAML = "src/test/resources/some.yaml";

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
        class GivenNoOptionFileProvidedWithPingYaml {
            @BeforeEach
            void init() throws ParseException {
                commandLine = parser.parse(options, new String[] {"-s", SRC_TEST_RESOURCES_PING_YAML});
            }

            @Test
            void returnsSixErrors() {
                List<StyleError> errorList = validationInitiator.validate(optionManager, commandLine);

                sixErrorsAssertions(errorList);
            }
        }

        @Nested
        class GivenNoOptionFileProvidedWithSomeYaml {
            @BeforeEach
            void init() throws ParseException {
                commandLine = parser.parse(options, new String[] {"-s", SRC_TEST_RESOURCES_SOME_YAML});
            }

            @Test
            void returnsNamingErrors() {
                List<StyleError> errorList = validationInitiator.validate(optionManager, commandLine);

                namingErrorsAssertions(errorList, "camelCase", "camelCase", "hyphen-case");
            }
        }

        @Nested
        class GivenProvidedOptionFileIsEmpty {
            @BeforeEach
            void init() throws ParseException {
                commandLine = parser.parse(
                        options,
                        new String[] {"-s", SRC_TEST_RESOURCES_PING_YAML, "-o", "src/test/resources/empty.json"});
            }

            @Test
            void returnsSixErrors() {
                List<StyleError> errorList = validationInitiator.validate(optionManager, commandLine);

                sixErrorsAssertions(errorList);
            }
        }

        @Nested
        class GivenProvidedDefaultOptionFile {
            @BeforeEach
            void init() throws ParseException {
                commandLine = parser.parse(
                        options,
                        new String[] {"-s", SRC_TEST_RESOURCES_PING_YAML, "-o", "src/test/resources/default.json"});
            }

            @Test
            void returnsSixErrors() {
                List<StyleError> errorList = validationInitiator.validate(optionManager, commandLine);

                sixErrorsAssertions(errorList);
            }
        }

        @Nested
        class GivenProvidedCustomOptionFile {
            @BeforeEach
            void init() throws ParseException {
                commandLine = parser.parse(
                        options,
                        new String[] {"-s", SRC_TEST_RESOURCES_PING_YAML, "-o", "src/test/resources/custom.json"});
            }

            @Test
            void returnsNoErrors() {
                List<StyleError> errorList = validationInitiator.validate(optionManager, commandLine);

                assertEquals(0, errorList.size());
            }
        }

        @Nested
        class GivenAlternativeNamingOption {
            @BeforeEach
            void init() throws ParseException {
                commandLine = parser.parse(
                        options,
                        new String[] {"-s", SRC_TEST_RESOURCES_SOME_YAML, "-o", "src/test/resources/alternative.json"});
            }

            @Test
            void returnsNamingErrors() throws Exception {
                List<StyleError> errorList = validationInitiator.validate(optionManager, commandLine);

                namingErrorsAssertions(errorList, "hyphen-case", "hyphen-case", "camelCase");
            }
        }

        @Nested
        class GivenUnderscoreNamingOption {
            @BeforeEach
            void init() throws ParseException {
                commandLine = parser.parse(
                        options,
                        new String[] {"-s", SRC_TEST_RESOURCES_SOME_YAML, "-o", "src/test/resources/underscore.json"});
            }

            @Test
            void returnsNoError() {
                List<StyleError> errorList = validationInitiator.validate(optionManager, commandLine);

                assertEquals(0, errorList.size());
            }
        }

        @Nested
        class GivenAlternativeLegacyNamingOption {
            @BeforeEach
            void init() throws ParseException {
                commandLine = parser.parse(options, new String[] {
                    "-s", SRC_TEST_RESOURCES_SOME_YAML, "-o", "src/test/resources/alternativeLegacy.json"
                });
            }

            @Test
            void returnsNamingErrors() {
                List<StyleError> errorList = validationInitiator.validate(optionManager, commandLine);

                namingErrorsAssertions(errorList, "hyphen-case", "hyphen-case", "camelCase");
            }
        }

        @Nested
        class GivenAlternativeAndLegacyNamingOption {
            @BeforeEach
            void init() throws ParseException {
                commandLine = parser.parse(options, new String[] {
                    "-s", SRC_TEST_RESOURCES_SOME_YAML, "-o", "src/test/resources/alternativeAndLegacy.json"
                });
            }

            @Test
            void returnsNamingErrors() {
                List<StyleError> errorList = validationInitiator.validate(optionManager, commandLine);

                namingErrorsAssertions(errorList, "hyphen-case", "hyphen-case", "camelCase");
            }
        }

        @Nested
        class GivenUnderscoreLegacyNamingOption {
            @BeforeEach
            void init() throws ParseException {
                commandLine = parser.parse(options, new String[] {
                    "-s", SRC_TEST_RESOURCES_SOME_YAML, "-o", "src/test/resources/underscoreLegacy.json"
                });
            }

            @Test
            void returnsNoError() {
                List<StyleError> errorList = validationInitiator.validate(optionManager, commandLine);

                assertEquals(0, errorList.size());
            }
        }

        @Nested
        class GivenInvalidPathNamingConvention {
            @BeforeEach
            void init() throws ParseException {
                commandLine = parser.parse(options, new String[] {
                    "-s", SRC_TEST_RESOURCES_SOME_YAML, "-o", "src/test/resources/invalidPathNamingConvention.json"
                });
            }

            @Test
            void throwsIllegalArgumentException() {
                assertThrowsIllegalArgumentExceptionWithExceptedMessage("Invalid pathNamingConvention", commandLine);
            }
        }

        @Nested
        class GivenInvalidParameterNamingConvention {
            @BeforeEach
            void init() throws ParseException {
                commandLine = parser.parse(options, new String[] {
                    "-s", SRC_TEST_RESOURCES_SOME_YAML, "-o", "src/test/resources/invalidParameterNamingConvention.json"
                });
            }

            @Test
            void throwsIllegalArgumentException() {
                assertThrowsIllegalArgumentExceptionWithExceptedMessage(
                        "Invalid parameterNamingConvention", commandLine);
            }
        }

        @Nested
        class GivenInvalidHeaderNamingConvention {
            @BeforeEach
            void init() throws ParseException {
                commandLine = parser.parse(options, new String[] {
                    "-s", SRC_TEST_RESOURCES_SOME_YAML, "-o", "src/test/resources/invalidHeaderNamingConvention.json"
                });
            }

            @Test
            void throwsIllegalArgumentException() {
                assertThrowsIllegalArgumentExceptionWithExceptedMessage("Invalid headerNamingConvention", commandLine);
            }
        }

        @Nested
        class GivenInvalidPropertyNamingConvention {
            @BeforeEach
            void init() throws ParseException {
                commandLine = parser.parse(options, new String[] {
                    "-s", SRC_TEST_RESOURCES_SOME_YAML, "-o", "src/test/resources/invalidPropertyNamingConvention.json"
                });
            }

            @Test
            void throwsIllegalArgumentException() {
                assertThrowsIllegalArgumentExceptionWithExceptedMessage(
                        "Invalid propertyNamingConvention", commandLine);
            }
        }
    }

    @Nested
    class Integration {
        /**
         * See <a href="https://github.com/OpenAPITools/openapi-style-validator/issues/190">https://github.com/OpenAPITools/openapi-style-validator/issues/190</a>
         */
        @Test
        void shouldNotFailWhenThereAreNoPaths() throws Throwable {
            CommandLine commandLine = parser.parse(options, new String[] {
                "-s", "src/test/resources/openApiWithoutPaths.yaml",
                "-o", "src/test/resources/default.json"
            });

            assertDoesNotThrow(() -> validationInitiator.validate(optionManager, commandLine));
        }
    }

    private void assertThrowsIllegalArgumentExceptionWithExceptedMessage(
            String expectedMessage, CommandLine commandLine) {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class, () -> validationInitiator.validate(optionManager, commandLine));
        Assertions.assertEquals(expectedMessage, thrown.getMessage());
    }

    private void sixErrorsAssertions(List<StyleError> errorList) throws MultipleFailuresError {
        Assertions.assertAll(
                () -> assertEquals(6, errorList.size()),
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
                        "*ERROR* in Operation POST /ping 'tags' -> The collection should be present and there should be at least one item in it",
                        errorList.get(5).toString()));
    }

    private void namingErrorsAssertions(
            List<StyleError> errorList,
            String expectedPathParameterConvention,
            String expectedQueryParameterConvention,
            String expectedPathConvention)
            throws MultipleFailuresError {
        Assertions.assertAll(
                () -> assertEquals(3, errorList.size()),
                () -> assertEquals(
                        "*ERROR* in path POST /some_path/{some_id} 'some_id' -> parameter should be in "
                                + expectedPathParameterConvention,
                        errorList.get(0).toString()),
                () -> assertEquals(
                        "*ERROR* in path POST /some_path/{some_id} 'some_name' -> parameter should be in "
                                + expectedQueryParameterConvention,
                        errorList.get(1).toString()),
                () -> assertEquals(
                        "*ERROR* in path /some_path/{some_id} 'some_path' -> path should be in "
                                + expectedPathConvention,
                        errorList.get(2).toString()));
    }
}
