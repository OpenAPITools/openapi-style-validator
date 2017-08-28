package com.jaffsoft.swaggerstylevalidator;

class StyleError {

    enum StyleCheckSection {
        APIInfo,
        Operations,
        Models,
        Naming
    }

    protected final StyleCheckSection styleCheckSection;
    protected final String parentObjectName;
    protected final String fieldNames;
    protected final String description;

    public StyleError(StyleCheckSection styleCheckSection,
                      String parentObjectName,
                      String fieldNames,
                      String description) {
        this.styleCheckSection = styleCheckSection;
        this.parentObjectName = parentObjectName;
        this.fieldNames = fieldNames;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public StyleCheckSection getStyleCheckSection() {
        return styleCheckSection;
    }

    public String getParentObjectName() {
        return parentObjectName;
    }

    public String getFieldNames() {
        return fieldNames;
    }

    @Override
    public String toString() {
        return String.format("*ERROR* Section: %s: '%s' %s -> %s",
                styleCheckSection.toString(),
                fieldNames,
                parentObjectName.isEmpty() ? "" : String.format("in %s", parentObjectName),
                description);
    }
}
