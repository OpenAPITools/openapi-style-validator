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
    void validateShouldReturnSixErrorsWihtoutOptionFile() throws Exception {
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
    void validateShouldReturnSixErrorsWihtDefaultOptionFile() throws Exception {
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
    void validateShouldReturnNoErrorsWihtCustomOptionFile() throws Exception {
        OptionManager optionManager = new OptionManager();
        Options options = optionManager.getOptions();
        CommandLine commandLine = parser.parse(options, new String[]{"-s", "src/test/resources/ping.yaml", "-o", "src/test/resources/custom.json"});
        List<StyleError> errorList = Main.validate(optionManager, commandLine);
        assertEquals(0, errorList.size());
    }
}
