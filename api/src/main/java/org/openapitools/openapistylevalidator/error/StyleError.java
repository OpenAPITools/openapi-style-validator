package org.openapitools.openapistylevalidator.error;

public class StyleError {

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
