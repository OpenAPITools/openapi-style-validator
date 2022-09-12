package org.openapitools.openapistylevalidator.api;

public interface RuleParameterProvider {

    NamingConvention pathNamingConvention();

    NamingConvention parameterNamingConvention();

    NamingConvention headerNamingConvention();

    NamingConvention propertyNamingConvention();
}
