package org.openapitools.openapistylevalidator.naming;

import org.openapitools.openapistylevalidator.ValidatorParameters.NamingConvention;

public class RuleParameterProvider {

    private final NamingConvention pathNamingChecker;

    private final NamingConvention parameterNamingChecker;

    private final NamingConvention headerNamingChecker;

    private final NamingConvention propertyNamingChecker;

    public RuleParameterProvider(
            NamingConvention pathNamingChecker,
            NamingConvention parameterNamingChecker,
            NamingConvention headerNamingChecker,
            NamingConvention propertyNamingChecker) {
        this.pathNamingChecker = pathNamingChecker;
        this.parameterNamingChecker = parameterNamingChecker;
        this.headerNamingChecker = headerNamingChecker;
        this.propertyNamingChecker = propertyNamingChecker;
    }

    public NamingConvention getPathNamingConvention() {
        return pathNamingChecker;
    }

    public NamingConvention getParameterNamingConvention() {
        return parameterNamingChecker;
    }

    public NamingConvention getHeaderNamingConvention() {
        return headerNamingChecker;
    }

    public NamingConvention getPropertyNamingConvention() {
        return propertyNamingChecker;
    }

    public static class Builder {

        private NamingConvention namingConvention = NamingConvention.AnyCase;

        private NamingConvention parameterNamingConvention = NamingConvention.AnyCase;

        private NamingConvention headerNamingConvention = NamingConvention.AnyCase;

        private NamingConvention propertyNamingConvention = NamingConvention.AnyCase;

        public Builder withPathNamingChecker(NamingConvention predicate) {
            namingConvention = predicate;
            return this;
        }

        public Builder withParameterNamingChecker(NamingConvention namingConvention) {
            parameterNamingConvention = namingConvention;
            return this;
        }

        public Builder withHeaderNamingChecker(NamingConvention namingConvention) {
            headerNamingConvention = namingConvention;
            return this;
        }

        public Builder withPropertyNamingChecker(NamingConvention namingConvention) {
            propertyNamingConvention = namingConvention;
            return this;
        }

        public RuleParameterProvider build() {
            return new RuleParameterProvider(
                    namingConvention, parameterNamingConvention, headerNamingConvention, propertyNamingConvention);
        }
    }
}
