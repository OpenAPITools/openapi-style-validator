package com.jaffsoft.swaggerstylevalidator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jaffsoft.swaggerstylevalidator.styleerror.StyleError;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class Main {

    public static void main(String[] args) {
        Options options = new Options()
                .addRequiredOption("s", "source", true, "Path to your yaml or json swagger/openApi spec file")
                .addOption("o", "options", true, "Path to the json file containing the options");

        DefaultParser parser = new DefaultParser();
        try {
            CommandLine commandLine = parser.parse(options, args);
            Swagger swagger = new SwaggerParser().read(commandLine.getOptionValue("s"));
            OpenApiSpecStyleValidator openApiSpecStyleValidator = new OpenApiSpecStyleValidator(swagger);

            ValidatorParameters parameters = getOptionalValidatorParametersOrDefault(commandLine);

            List<StyleError> errorList = openApiSpecStyleValidator.validate(parameters);
            if (errorList.isEmpty()) {
                System.out.println("There is no style error in this spec. Good job!");
            } else {
                for (StyleError error : errorList) {
                    System.out.println(error.toString());
                }
            }
        } catch (ParseException e) {
            System.out.println("There was something wrong in your request. Please check documentation for more info");
        }
    }

    private static ValidatorParameters getOptionalValidatorParametersOrDefault(CommandLine commandLine) {
        ValidatorParameters parameters = new ValidatorParameters();
        if (commandLine.hasOption("o")) {
            try {
                Gson gson = new GsonBuilder().create();
                String content = readFile(commandLine.getOptionValue("o"), Charset.defaultCharset());
                parameters = gson.fromJson(content, ValidatorParameters.class);
            } catch (Exception ignored) {
                System.out.println("Invalid path to option files, using default.");
            }
        }
        return parameters;
    }

    static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
