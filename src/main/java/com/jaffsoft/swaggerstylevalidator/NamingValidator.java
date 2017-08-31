package com.jaffsoft.swaggerstylevalidator;

class NamingValidator {

    private static final String REGEX_LOWER_CASE_ALPHA_NUMERIC_ONLY = "[a-z0-9]+";
    private static final String REGEX_LOWER_CASE_ONLY = "[a-z]+";
    private static final String REGEX_CAMEL_CASE = "([a-z]+[A-Z]+\\w+)+";

    public boolean isUnderscoreCase(String variableName) {
        return isSeparatorCaseValid(variableName, "_");
    }

    public boolean isCamelCase(String variableName) {
        return variableName.matches(REGEX_LOWER_CASE_ONLY) || variableName.matches(REGEX_CAMEL_CASE);

    }

    public boolean isHyphenCase(String variableName) {
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

    public boolean isNamingValid(String name, ValidatorParameters.NamingStrategy namingStrategy) {
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
