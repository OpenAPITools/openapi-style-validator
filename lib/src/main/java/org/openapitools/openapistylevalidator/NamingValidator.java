package org.openapitools.openapistylevalidator;

class NamingValidator {

    private static final String REGEX_LOWER_CASE_ALPHA_NUMERIC_ONLY = "[a-z0-9]+";
    private static final String REGEX_CAMEL_CASE = "([a-z0-9]+[A-Z]+\\w+)+";

    private boolean isUnderscoreCase(String variableName) {
        return isSeparatorCaseValid(variableName, "_");
    }

    private boolean isCamelCase(String variableName) {
        return variableName.matches(REGEX_LOWER_CASE_ALPHA_NUMERIC_ONLY) || variableName.matches(REGEX_CAMEL_CASE);
    }

    private boolean isHyphenCase(String variableName) {
        return isSeparatorCaseValid(variableName, "-");
    }

    private boolean isSeparatorCaseValid(String variableName, String separator) {
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
            if (!token.toLowerCase().equals(token)) {
                return false;
            }

            if (!token.matches(REGEX_LOWER_CASE_ALPHA_NUMERIC_ONLY)) {
                return false;
            }
        }

        return variableName.length() == (totalLength + tokens.length - 1);
    }

    boolean isNamingValid(String name, ValidatorParameters.NamingConvention namingStrategy) {
        switch (namingStrategy) {
            case UnderscoreCase:
                return isUnderscoreCase(name);
            case CamelCase:
                return isCamelCase(name);
            case HyphenCase:
                return isHyphenCase(name);
        }
        return false;
    }
}
