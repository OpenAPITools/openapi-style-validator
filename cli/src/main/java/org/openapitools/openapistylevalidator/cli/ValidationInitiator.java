package org.openapitools.openapistylevalidator.cli;

import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.parser.core.models.ParseOptions;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import java.util.List;
import org.apache.commons.cli.CommandLine;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.openapitools.empoa.swagger.core.internal.SwAdapter;
import org.openapitools.openapistylevalidator.OpenApiSpecStyleValidator;
import org.openapitools.openapistylevalidator.ValidatorParameters;
import org.openapitools.openapistylevalidator.styleerror.StyleError;

public class ValidationInitiator {
    public List<StyleError> validate(OptionManager optionManager, CommandLine commandLine) {
        OpenAPI openAPI = parseToOpenAPIModels(optionManager, commandLine);

        OpenApiSpecStyleValidator openApiSpecStyleValidator = new OpenApiSpecStyleValidator(openAPI);

        ValidatorParameters parameters = optionManager.getOptionalValidatorParametersOrDefault(commandLine);
        return openApiSpecStyleValidator.validate(parameters);
    }

    private OpenAPI parseToOpenAPIModels(OptionManager optionManager, CommandLine commandLine) {
        OpenAPIParser openApiParser = new OpenAPIParser();
        ParseOptions parseOptions = new ParseOptions();
        parseOptions.setResolve(true);

        SwaggerParseResult parserResult =
                openApiParser.readLocation(optionManager.getSource(commandLine), null, parseOptions);
        io.swagger.v3.oas.models.OpenAPI swaggerOpenAPI = parserResult.getOpenAPI();
        return SwAdapter.toOpenAPI(swaggerOpenAPI);
    }
}
