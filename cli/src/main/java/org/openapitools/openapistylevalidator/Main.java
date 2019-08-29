package org.openapitools.openapistylevalidator;

import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.parser.core.models.ParseOptions;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.apache.commons.cli.*;

import org.openapitools.empoa.swagger.core.internal.SwAdapter;

public class Main {

    private static final String APP_NAME = "openapi-style-validator-cli";
    private static final DefaultParser parser = new DefaultParser();

    public static void main(String[] args) {
        OptionManager optionManager = new OptionManager();

        try {
            Options options = optionManager.getOptions();
            CommandLine commandLine = parser.parse(options, args);

            if (commandLine.getOptions().length == 0) {
                OutputUtils.printHelp(options, APP_NAME);
            } else {
                OpenAPIParser openApiParser = new OpenAPIParser();
                ParseOptions parseOptions = new ParseOptions();
                parseOptions.setResolve(true);

                SwaggerParseResult parserResult = openApiParser.readLocation(optionManager.getSource(commandLine), null, parseOptions);
                io.swagger.v3.oas.models.OpenAPI swaggerOpenAPI = parserResult.getOpenAPI();

                org.eclipse.microprofile.openapi.models.OpenAPI openAPI = SwAdapter.toOpenAPI(swaggerOpenAPI);
                OpenApiSpecStyleValidator openApiSpecStyleValidator = new OpenApiSpecStyleValidator(openAPI);

                ValidatorParameters parameters = optionManager.getOptionalValidatorParametersOrDefault(commandLine);

                OutputUtils.printResults(openApiSpecStyleValidator.validate(parameters));
            }
        } catch (ParseException e) {
            System.out.println("There was something wrong in your request. Please check documentation for more info");
            System.exit(1);
        }
    }
}
