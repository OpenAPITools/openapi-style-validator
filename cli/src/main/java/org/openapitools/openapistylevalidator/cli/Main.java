package org.openapitools.openapistylevalidator.cli;

import org.apache.commons.cli.*;

public class Main {

    private static final String APP_NAME = "openapi-style-validator-cli";
    private static final DefaultParser parser = new DefaultParser();
    private static final OutputUtils outputUtils = new OutputUtils();

    public static void main(String[] args) {
        OptionManager optionManager = new OptionManager(outputUtils);
        ValidationInitiator validationInitiator = new ValidationInitiator();

        try {
            Options options = optionManager.getOptions();
            CommandLine commandLine = parser.parse(options, args);

            if (commandLine.getOptions().length == 0) {
                outputUtils.printHelp(options, APP_NAME);
            } else {
                if (optionManager.isHelpRequested(commandLine)) {
                    outputUtils.printHelp(options, APP_NAME);
                } else if (optionManager.isVersionRequested(commandLine)) {
                    outputUtils.printVersion();
                } else if (optionManager.isSourceProvided(commandLine)) {
                    outputUtils.printResults(validationInitiator.validate(optionManager, commandLine));
                } else {
                    outputUtils.printRequestError();
                }
            }
        } catch (ParseException e) {
            outputUtils.printRequestError();
        }
    }
}
