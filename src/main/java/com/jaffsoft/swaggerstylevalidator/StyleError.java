package com.jaffsoft.swaggerstylevalidator;

class StyleError {

    enum StyleCheckSection {
        APIInfo,
        Operations,
        Models,
        Naming
    }

    final StyleCheckSection styleCheckSection;
    final String fieldNames;
    final String description;

    public StyleError(StyleCheckSection styleCheckSection,
                      String fieldNames,
                      String description) {
        this.styleCheckSection = styleCheckSection;
        this.fieldNames = fieldNames;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("*ERROR* Section: %s: '%s' -> %s",
                styleCheckSection.toString(),
                fieldNames,
                description);
    }
}
