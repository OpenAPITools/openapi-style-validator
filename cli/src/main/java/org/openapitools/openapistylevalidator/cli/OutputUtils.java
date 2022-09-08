package org.openapitools.openapistylevalidator.cli;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Properties;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openapitools.openapistylevalidator.error.StyleError;

class OutputUtils {
    private static final Logger logger = LogManager.getLogger(OutputUtils.class);

    void printHelp(final Options options, final String cmdLineSyntax) {
        try (StringWriter stringWriter = new StringWriter();
                PrintWriter printWriter = new PrintWriter(stringWriter)) {
            HelpFormatter helpFormatter = new HelpFormatter();

            helpFormatter.printHelp(
                    printWriter,
                    HelpFormatter.DEFAULT_WIDTH,
                    cmdLineSyntax,
                    "",
                    options,
                    HelpFormatter.DEFAULT_LEFT_PAD,
                    HelpFormatter.DEFAULT_DESC_PAD,
                    "Thanks for using OpenAPI Style Validator! If you this project useful, consider buying us a tree here: https://ecologi.com/darkjaff",
                    true);

            printWriter.flush();

            String info = stringWriter.toString();
            logger.info(info);
        } catch (IOException e) {
            logger.error("Could not print help because there was an IO Exception: ", e);
        }
    }

    void printResults(List<StyleError> errorList) {
        if (errorList.isEmpty()) {
            logger.info("There are no style errors in this spec.");
        } else {
            for (StyleError error : errorList) {
                String errorMessage = error.toString();
                logger.error(errorMessage);
            }

            System.exit(1);
        }
    }

    void printVersion() {
        logger.info("Current version: {}", getVersion());
    }

    void printRequestError() {
        logger.error("There was something wrong in your request. Please check documentation for more info");
        System.exit(1);
    }

    private String getVersion() {
        try (InputStream input = OutputUtils.class.getResourceAsStream("/version.properties")) {
            if (input != null) {
                Properties prop = new Properties();
                prop.load(input);
                return prop.getProperty("version");
            }
        } catch (IOException ignored) {
        }

        return "No version | Running in the IDE?";
    }

    public void printReplacementUsage(String strategyKey, String conventionKey) {
        logger.info(
                "The deprecated option '{}' is ignored, because its replacement '{}' is set",
                strategyKey,
                conventionKey);
    }

    public void printDeprecationWarning(String strategyKey, String conventionKey) {
        logger.info("The option '{}' is deprecated, please use '{}' instead", strategyKey, conventionKey);
    }
}
