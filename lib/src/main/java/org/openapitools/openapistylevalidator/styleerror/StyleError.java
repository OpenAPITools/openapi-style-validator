package org.openapitools.openapistylevalidator.styleerror;

public class StyleError {
    public enum StyleCheckSection {
        APIInfo,
        Operations,
        Models,
        Naming,
        OpenAPI,
    }

    final StyleCheckSection styleCheckSection;
    final String fieldNames;
    final String description;

    public StyleError(StyleCheckSection styleCheckSection, String fieldNames, String description) {
        this.styleCheckSection = styleCheckSection;
        this.fieldNames = fieldNames;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("*ERROR* Section: %s: '%s' -> %s", styleCheckSection.toString(), fieldNames, description);
    }
}
