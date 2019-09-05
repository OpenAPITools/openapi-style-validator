package org.openapitools.openapistylevalidator.cli;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.openapitools.openapistylevalidator.styleerror.StyleError;

import java.io.PrintWriter;
import java.util.List;

class OutputUtils {

    @SuppressWarnings("SameParameterValue")
    void printHelp(final Options options,
                          final String cmdLineSyntax) {
        PrintWriter writer = new PrintWriter(System.out);
        final HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp(writer, HelpFormatter.DEFAULT_WIDTH, cmdLineSyntax, "", options, HelpFormatter.DEFAULT_LEFT_PAD, HelpFormatter.DEFAULT_DESC_PAD, "", true);
        writer.flush();
    }

    void printResults(List<StyleError> errorList) {
        if (errorList.isEmpty()) {
            System.out.println("There are no style errors in this spec.");
        } else {
            for (StyleError error : errorList) {
                System.out.println(error.toString());
            }
            System.exit(1);
        }
    }

    void printVersion() {
        System.out.println(String.format("Current version: %s", getVersion()));
    }

    void printRequestError() {
        System.out.println("There was something wrong in your request. Please check documentation for more info");
        System.exit(1);
    }

    private String getVersion() {
        String tentativeVersion = getClass().getPackage().getImplementationVersion();
        return tentativeVersion == null ? "DEVELOPMENT" : tentativeVersion;
    }
}
