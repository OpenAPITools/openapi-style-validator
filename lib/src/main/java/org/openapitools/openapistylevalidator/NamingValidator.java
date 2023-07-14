package org.openapitools.openapistylevalidator;

import java.util.Arrays;
import java.util.Optional;

class NamingValidator {

    private static final String REGEX_LOWER_CASE_ALPHA_NUMERIC_ONLY = "[a-z0-9]+";
    private static final String REGEX_UPPER_CASE_ALPHA_NUMERIC_ONLY = "[A-Z0-9]+";
    private static final String REGEX_CAMEL_CASE = "([a-z0-9]+[A-Z]+\\w+)+";
    private static final String REGEX_PASCAL_CASE = "^[A-Z0-9][a-z]*(?:[A-Z0-9][a-z]*)*$";

    private boolean isUnderscoreCase(String variableName) {
        return isSeparatorCaseValid(variableName, "_", false);
    }

    private boolean isUnderscoreUpperCase(String variableName) {
        return isSeparatorCaseValid(variableName, "_", true);
    }

    private boolean isCamelCase(String variableName) {
        return variableName.matches(REGEX_LOWER_CASE_ALPHA_NUMERIC_ONLY) || variableName.matches(REGEX_CAMEL_CASE);
    }

    private boolean isPascalCase(String variableName) {
        return variableName.matches(REGEX_PASCAL_CASE);
    }

    private boolean isAnyCase() {
        return true;
    }

    private boolean isHyphenCase(String variableName) {
        return isSeparatorCaseValid(variableName, "-", false);
    }

    private boolean isHyphenUpperCase(String variableName) {
        if (variableName.startsWith("-") || variableName.endsWith("-")) {
            return false;
        }

        String expectedTokenFormat = "[A-Z][a-z]+";

        Optional<String> firstInvalidToken = Arrays.stream(variableName.split("-"))
                .filter(token -> !token.matches(expectedTokenFormat))
                .findFirst();
        return !firstInvalidToken.isPresent();
    }

    private boolean isSeparatorCaseValid(String variableName, String separator, boolean isUpperCase) {
        if (variableName.startsWith(separator) || variableName.endsWith(separator)) {
            return false;
        }

        String[] tokens = variableName.split(separator);
        int totalLength = 0;
        for (String token : tokens) {
            if (token.isEmpty()) {
                return false;
            }
            totalLength += token.length();
            if (isUpperCase) {
                if (!token.equalsIgnoreCase(token)) {
                    return false;
                }

                if (!token.matches(REGEX_UPPER_CASE_ALPHA_NUMERIC_ONLY)) {
                    return false;
                }
            } else {
                if (!token.equalsIgnoreCase(token)) {
                    return false;
                }

                if (!token.matches(REGEX_LOWER_CASE_ALPHA_NUMERIC_ONLY)) {
                    return false;
                }
            }
        }

        return variableName.length() == (totalLength + tokens.length - 1);
    }

    boolean isNamingValid(String name, ValidatorParameters.NamingConvention namingStrategy) {
        switch (namingStrategy) {
            case UnderscoreCase:
                return isUnderscoreCase(name);
            case UnderscoreUpperCase:
                return isUnderscoreUpperCase(name);
            case CamelCase:
                return isCamelCase(name);
            case PascalCase:
                return isPascalCase(name);
            case HyphenCase:
                return isHyphenCase(name);
            case AnyCase:
                return isAnyCase();
            case HyphenUpperCase:
                return isHyphenUpperCase(name);
        }
        return false;
    }
}
