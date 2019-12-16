package org.openapitools.openapistylevalidator.cli;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.openapitools.openapistylevalidator.ValidatorParameters;
import org.openapitools.openapistylevalidator.commons.Utils;

import java.nio.charset.Charset;

class OptionManager {

    private static final String SOURCE_OPT_SHORT = "s";
    private static final String SOURCE_OPT_LONG = "source";

    private static final String OPTIONS_OPT_SHORT = "o";
    private static final String OPTIONS_OPT_LONG = "options";

    private static final String HELP_OPT_SHORT = "h";
    private static final String HELP_OPT_LONG = "help";

    private static final String VERSION_OPT_SHORT = "v";
    private static final String VERSION_OPT_LONG = "version";

    private final Options options;

    OptionManager() {
        options = new Options();

        OptionGroup mutualExclusiveOptions = new OptionGroup();
        Option help = new Option(HELP_OPT_SHORT,
                HELP_OPT_LONG,
                false,
                "Show help");

        Option version = new Option(VERSION_OPT_SHORT,
                VERSION_OPT_LONG,
                false,
                "Show current version");

        Option source = new Option(SOURCE_OPT_SHORT,
                SOURCE_OPT_LONG,
                true,
                "Path to your yaml or json swagger/openApi spec file");

        mutualExclusiveOptions.addOption(help);
        mutualExclusiveOptions.addOption(version);
        mutualExclusiveOptions.addOption(source);

        Option optionFile = new Option(OPTIONS_OPT_SHORT,
                OPTIONS_OPT_LONG,
                true,
                "Path to the json file containing the options");

        options.addOption(optionFile);
        options.addOptionGroup(mutualExclusiveOptions);
    }

    Options getOptions() {
        return options;
    }

    ValidatorParameters getOptionalValidatorParametersOrDefault(CommandLine commandLine) {
        ValidatorParameters parameters = new ValidatorParameters();
        if (commandLine.hasOption(OPTIONS_OPT_SHORT)) {
            try {
                String content = Utils.readFile(commandLine.getOptionValue(OPTIONS_OPT_SHORT), Charset.defaultCharset());
                JsonParser parser = new JsonParser();
                JsonElement jsonElement = parser.parse(content);
                fixConventionRenaming(jsonElement, "path");
                fixConventionRenaming(jsonElement, "parameter");
                fixConventionRenaming(jsonElement, "property");
                Gson gson = new GsonBuilder().create();
                parameters = gson.fromJson(jsonElement, ValidatorParameters.class);
            } catch (Exception ignored) {
                System.out.println("Invalid path to option files, using default.");
            }
        }
        return parameters;
    }

    private void fixConventionRenaming(JsonElement jsonElement, String prefix) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String strategyKey = prefix + "NamingStrategy";
        if(jsonObject.has(strategyKey)) {
            String conventionKey = prefix + "NamingConvention";
            if(jsonObject.has(conventionKey)) {
                System.err.println("The deprecated option '" + strategyKey + "' is ignored, because its replacement '" + conventionKey + "' is set");
            } else {
                System.err.println("The option '" + strategyKey + "' is depreacted, please use '" + conventionKey + "' instead");
                jsonObject.add(conventionKey, jsonObject.get(strategyKey));
            }
        }
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
