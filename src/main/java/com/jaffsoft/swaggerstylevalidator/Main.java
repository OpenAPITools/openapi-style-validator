package com.jaffsoft.swaggerstylevalidator;

import com.jaffsoft.swaggerstylevalidator.styleerror.StyleError;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;

import java.util.List;

class Main {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("You must at least provide the path to the spec (yaml/json)");
            return;
        }

        Swagger swagger = new SwaggerParser().read(args[0]);
        OpenApiSpecStyleValidator openApiSpecStyleValidator = new OpenApiSpecStyleValidator(swagger);

        ValidatorParameters parameters = new ValidatorParameters();
        List<StyleError> errorList = openApiSpecStyleValidator.validate(parameters);

        if (errorList.isEmpty()) {
            System.out.println("There is no style error in this spec. Good job!");
        } else {
            for (StyleError error : errorList) {
                System.out.println(error.toString());
            }
        }
    }
}
