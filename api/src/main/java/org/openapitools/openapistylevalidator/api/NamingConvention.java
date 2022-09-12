package org.openapitools.openapistylevalidator.api;

public enum NamingConvention {
    UnderscoreCase("underscore_case"),
    UnderscoreUpperCase("UNDERSCORE_UPPER_CASE"),
    CamelCase("camelCase"),
    HyphenCase("hyphen-case"),
    AnyCase("AnyCase"),
    HyphenUpperCase("Hyphen-Upper-Case");

    private final String designation;

    NamingConvention(String appelation) {
        this.designation = appelation;
    }

    /**
     * Method to get the name of the naming convention to be used in the reports
     * @return the name of the naming convention
     */
    public String getDesignation() {
        return designation;
    }
}
