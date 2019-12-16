package org.openapitools.openapistylevalidator.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openapitools.openapistylevalidator.styleerror.StyleError;
import org.opentest4j.MultipleFailuresError;

import java.util.List;

public class MainTest {
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

    private void namingErrorsAssertions(List<StyleError> errorList, String expectedPathParameterConvention, String expectedQueryParameterConvention, String expectedPathConvention) throws MultipleFailuresError {
        Assertions.assertAll(
                () -> assertEquals(3, errorList.size()),
                () -> assertEquals("*ERROR* in path POST /some_path/{some_id} 'some_id' -> parameter should be in " + expectedPathParameterConvention, errorList.get(0).toString()),
                () -> assertEquals("*ERROR* in path POST /some_path/{some_id} 'some_name' -> parameter should be in " + expectedQueryParameterConvention, errorList.get(1).toString()),
                () -> assertEquals("*ERROR* in path /some_path/{some_id} 'some_path' -> path should be in " + expectedPathConvention, errorList.get(2).toString())
                );
    }
}
