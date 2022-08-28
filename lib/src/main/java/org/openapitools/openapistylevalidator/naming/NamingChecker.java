package org.openapitools.openapistylevalidator.naming;

import java.util.function.Predicate;
import org.openapitools.openapistylevalidator.ValidatorParameters.NamingConvention;

public class NamingChecker {

    private final NamingConvention pathNamingChecker;

    private final NamingConvention parameterNamingChecker;

    private final NamingConvention headerNamingChecker;

    private final NamingConvention propertyNamingChecker;

    protected NamingChecker(
            NamingConvention pathNamingChecker,
            NamingConvention parameterNamingChecker,
            NamingConvention headerNamingChecker,
            NamingConvention propertyNamingChecker) {
        this.pathNamingChecker = pathNamingChecker;
        this.parameterNamingChecker = parameterNamingChecker;
        this.headerNamingChecker = headerNamingChecker;
        this.propertyNamingChecker = propertyNamingChecker;
    }

    public NamingConvention getPathNamingChecker() {
        return pathNamingChecker;
    }

    public NamingConvention getParameterNamingChecker() {
        return parameterNamingChecker;
    }

    public NamingConvention getHeaderNamingChecker() {
        return headerNamingChecker;
    }

    public NamingConvention getPropertyNamingChecker() {
        return propertyNamingChecker;
    }

    public static class Builder {
        private final Predicate<String> alwaysTruePredicate = (String) -> true;

        private NamingConvention pathNamingChecker = NamingConvention.AnyCase;

        private NamingConvention parameterNamingChecker = NamingConvention.AnyCase;

        private NamingConvention headerNamingChecker = NamingConvention.AnyCase;

        private NamingConvention propertyNamingChecker = NamingConvention.AnyCase;

        public Builder withPathNamingChecker(NamingConvention predicate) {
            pathNamingChecker = predicate;
            return this;
        }

        public Builder withParameterNamingChecker(NamingConvention predicate) {
            parameterNamingChecker = predicate;
            return this;
        }

        public Builder withHeaderNamingChecker(NamingConvention predicate) {
            headerNamingChecker = predicate;
            return this;
        }

        public Builder withPropertyNamingChecker(NamingConvention predicate) {
            propertyNamingChecker = predicate;
            return this;
        }

        public NamingChecker build() {
            return new NamingChecker(
                    pathNamingChecker, parameterNamingChecker, headerNamingChecker, propertyNamingChecker);
        }
    }
}
