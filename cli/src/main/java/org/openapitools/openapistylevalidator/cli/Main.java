package org.openapitools.openapistylevalidator.cli;

import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.parser.core.models.ParseOptions;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.apache.commons.cli.*;

import org.openapitools.empoa.swagger.core.internal.SwAdapter;
import org.openapitools.openapistylevalidator.OpenApiSpecStyleValidator;
import org.openapitools.openapistylevalidator.ValidatorParameters;
import org.openapitools.openapistylevalidator.styleerror.StyleError;

import java.util.List;

public class Main {

    private static final String APP_NAME = "openapi-style-validator-cli";
    private static final DefaultParser parser = new DefaultParser();
    private static final OutputUtils outputUtils = new OutputUtils();

    public static void main(String[] args) {
        OptionManager optionManager = new OptionManager();

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
                    validateAndPrint(optionManager, commandLine);
                } else {
                    outputUtils.printRequestError();
                }
            }
        } catch (ParseException e) {
            outputUtils.printRequestError();
        }
    }

    private static void validateAndPrint(OptionManager optionManager, CommandLine commandLine) {
        outputUtils.printResults(validate(optionManager, commandLine));
    }

    static List<StyleError> validate(OptionManager optionManager, CommandLine commandLine) {
        OpenAPIParser openApiParser = new OpenAPIParser();
        ParseOptions parseOptions = new ParseOptions();
        parseOptions.setResolve(true);

        SwaggerParseResult parserResult = openApiParser.readLocation(optionManager.getSource(commandLine), null, parseOptions);
        io.swagger.v3.oas.models.OpenAPI swaggerOpenAPI = parserResult.getOpenAPI();

        org.eclipse.microprofile.openapi.models.OpenAPI openAPI = SwAdapter.toOpenAPI(swaggerOpenAPI);
        OpenApiSpecStyleValidator openApiSpecStyleValidator = new OpenApiSpecStyleValidator(openAPI);

        ValidatorParameters parameters = optionManager.getOptionalValidatorParametersOrDefault(commandLine);
        return openApiSpecStyleValidator.validate(parameters);
    }

}
