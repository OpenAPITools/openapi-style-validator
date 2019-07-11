package org.openapitools.openapistylevalidator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.parser.core.models.ParseOptions;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.apache.commons.cli.*;
import java.nio.charset.Charset;

import org.openapitools.empoa.swagger.core.internal.SwAdapter;
import org.openapitools.openapistylevalidator.commons.Utils;
import org.openapitools.openapistylevalidator.styleerror.StyleError;

import java.util.List;

class Main {

    private static final String SOURCE_OPT_SHORT = "s";
    private static final String SOURCE_OPT_LONG = "source";

    private static final String OPTIONS_OPT_SHORT = "o";
    private static final String OPTIONS_OPT_LONG = "options";

    private static final DefaultParser parser = new DefaultParser();

    public static void main(String[] args) {
        Options options = new Options()
                .addRequiredOption(SOURCE_OPT_SHORT, SOURCE_OPT_LONG,
                        true, "Path to your yaml or json swagger/openApi spec file")
                .addOption(OPTIONS_OPT_SHORT, OPTIONS_OPT_LONG,
                        true, "Path to the json file containing the options");

        try {
            CommandLine commandLine = parser.parse(options, args);
            OpenAPIParser openApiParser = new OpenAPIParser();
            ParseOptions parseOptions = new ParseOptions();

            SwaggerParseResult parserResult = openApiParser.readLocation(commandLine.getOptionValue(SOURCE_OPT_SHORT), null, parseOptions);
            io.swagger.v3.oas.models.OpenAPI swaggerOpenAPI = parserResult.getOpenAPI();

            org.eclipse.microprofile.openapi.models.OpenAPI openAPI = SwAdapter.toOpenAPI(swaggerOpenAPI);
            OpenApiSpecStyleValidator openApiSpecStyleValidator = new OpenApiSpecStyleValidator(openAPI);

            ValidatorParameters parameters = getOptionalValidatorParametersOrDefault(commandLine);

            printResults(openApiSpecStyleValidator.validate(parameters));
        } catch (ParseException e) {
            System.out.println("There was something wrong in your request. Please check documentation for more info");
            System.exit(1);
        }
    }

    private static void printResults(List<StyleError> errorList) {
        if (errorList.isEmpty()) {
            System.out.println("There are no style errors in this spec.");
        } else {
            for (StyleError error : errorList) {
                System.out.println(error.toString());
            }
            System.exit(1);
        }
    }

    private static ValidatorParameters getOptionalValidatorParametersOrDefault(CommandLine commandLine) {
        ValidatorParameters parameters = new ValidatorParameters();
        if (commandLine.hasOption(OPTIONS_OPT_SHORT)) {
            try {
                Gson gson = new GsonBuilder().create();
                String content = Utils.readFile(commandLine.getOptionValue(OPTIONS_OPT_SHORT), Charset.defaultCharset());
                parameters = gson.fromJson(content, ValidatorParameters.class);
            } catch (Exception ignored) {
                System.out.println("Invalid path to option files, using default.");
            }
        }
        return parameters;
    }
}
