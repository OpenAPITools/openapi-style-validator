package org.openapitools.openapistylevalidator.cli;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openapitools.openapistylevalidator.ValidatorParameters;

class OptionManager {
    private static final Logger logger = LogManager.getLogger(OptionManager.class);

    private static final String SOURCE_OPT_SHORT = "s";
    private static final String SOURCE_OPT_LONG = "source";

    private static final String OPTIONS_OPT_SHORT = "o";
    private static final String OPTIONS_OPT_LONG = "options";

    private static final String HELP_OPT_SHORT = "h";
    private static final String HELP_OPT_LONG = "help";

    private static final String VERSION_OPT_SHORT = "v";
    private static final String VERSION_OPT_LONG = "version";

    private final Options options;
    private final OutputUtils outputUtils;

    OptionManager(OutputUtils outputUtils) {
        this.outputUtils = outputUtils;
        options = new Options();

        OptionGroup mutualExclusiveOptions = new OptionGroup();
        Option help = new Option(HELP_OPT_SHORT, HELP_OPT_LONG, false, "Show help");

        Option version = new Option(VERSION_OPT_SHORT, VERSION_OPT_LONG, false, "Show current version");

        Option source = new Option(
                SOURCE_OPT_SHORT, SOURCE_OPT_LONG, true, "Path to your yaml or json swagger/openApi spec file");

        mutualExclusiveOptions.addOption(help);
        mutualExclusiveOptions.addOption(version);
        mutualExclusiveOptions.addOption(source);

        Option optionFile =
                new Option(OPTIONS_OPT_SHORT, OPTIONS_OPT_LONG, true, "Path to the json file containing the options");

        options.addOption(optionFile);
        options.addOptionGroup(mutualExclusiveOptions);
    }

    Options getOptions() {
        return options;
    }

    ValidatorParameters getOptionalValidatorParametersOrDefault(CommandLine commandLine) {
        ValidatorParameters parameters = new ValidatorParameters();
        if (commandLine.hasOption(OPTIONS_OPT_SHORT)) {
            ObjectMapper objectMapper = new ObjectMapperProvider().getOptionsObjectMapper();
            try {
                JsonNode json = objectMapper.readTree(new File(commandLine.getOptionValue(OPTIONS_OPT_SHORT)));
                fixConventionRenaming(json, "path");
                fixConventionRenaming(json, "parameter");
                fixConventionRenaming(json, "property");
                parameters = objectMapper.treeToValue(json, ValidatorParameters.class);
                validateNamingConventions(parameters);
            } catch (JsonMappingException jsonException) {
                logger.error("Invalid JSON, using default.");
            } catch (IOException ioException) {
                logger.error("Invalid path to option files, using default.");
            }
        }
        return parameters;
    }

    private void fixConventionRenaming(JsonNode json, String prefix) {
        String strategyKey = String.format("%sNamingStrategy", prefix);
        if (json.has(strategyKey)) {
            // JsonNode.has does a type check on ObjectNode, we can safely cast
            ObjectNode object = (ObjectNode) json;
            String conventionKey = String.format("%sNamingConvention", prefix);
            if (object.has(conventionKey)) {
                outputUtils.printReplacementUsage(strategyKey, conventionKey);
            } else {
                outputUtils.printDeprecationWarning(strategyKey, conventionKey);
                object.set(conventionKey, object.get(strategyKey));
            }
            /* Jackson will fail on unknown properties, we have to remove the
             * old (unknown) key */
            object.remove(strategyKey);
        }
    }

    private void validateNamingConventions(ValidatorParameters parameters) {
        validateNamingConvention("header", parameters.getHeaderNamingConvention());
        validateNamingConvention("parameter", parameters.getParameterNamingConvention());
        validateNamingConvention("path", parameters.getPathNamingConvention());
        validateNamingConvention("property", parameters.getPropertyNamingConvention());
    }

    private void validateNamingConvention(String kind, ValidatorParameters.NamingConvention convention) {
        if (convention != null) return;
        throw new IllegalArgumentException("Invalid " + kind.toLowerCase() + "NamingConvention");
    }

    String getSource(CommandLine commandLine) {
        return commandLine.getOptionValue(SOURCE_OPT_SHORT);
    }

    boolean isHelpRequested(CommandLine commandLine) {
        return commandLine.hasOption(HELP_OPT_SHORT) || commandLine.hasOption(HELP_OPT_LONG);
    }

    boolean isVersionRequested(CommandLine commandLine) {
        return commandLine.hasOption(VERSION_OPT_SHORT) || commandLine.hasOption(VERSION_OPT_LONG);
    }

    boolean isSourceProvided(CommandLine commandLine) {
        return commandLine.hasOption(SOURCE_OPT_SHORT) || commandLine.hasOption(SOURCE_OPT_LONG);
    }
}
