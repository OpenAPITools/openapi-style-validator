package org.openapitools.openapistylevalidator;

import org.openapitools.openapistylevalidator.naming.NamingChecker;

import java.util.ArrayList;
import java.util.List;

import static org.openapitools.openapistylevalidator.rules.ContactInfoRule.CONTACT_INFO;
import static org.openapitools.openapistylevalidator.rules.HeaderNamingRule.HEADER_NAMING;
import static org.openapitools.openapistylevalidator.rules.InfoDescriptionRule.INFO_DESCRIPTION;
import static org.openapitools.openapistylevalidator.rules.LicenseRule.LICENCE_INFO;
import static org.openapitools.openapistylevalidator.rules.ParameterNamingRule.PARAMETER_NAMING;
import static org.openapitools.openapistylevalidator.rules.PathNamingRule.PATH_NAMING;
import static org.openapitools.openapistylevalidator.rules.PropertyNamingRule.PROPERTY_NAMING;

public class ValidatorParametersUtils {

    public static List<String> toIgnoredRules(ValidatorParameters parameters) {
        ArrayList<String> ignoredRules = new ArrayList<>();
        if (!parameters.isValidateInfoContact()) {
            ignoredRules.add(CONTACT_INFO);
        }
        if (!parameters.isValidateInfoLicense()) {
            ignoredRules.add(LICENCE_INFO);
        }
        if (!parameters.isValidateInfoDescription()) {
            ignoredRules.add(INFO_DESCRIPTION);
        }
        if (!parameters.isValidateNaming()) {
            ignoredRules.add(HEADER_NAMING);
            ignoredRules.add(PARAMETER_NAMING);
            ignoredRules.add(PATH_NAMING);
            ignoredRules.add(PROPERTY_NAMING);
        }
        return ignoredRules;
    }

    public static NamingChecker toNamingChecker(ValidatorParameters parameters) {
        NamingChecker.Builder builder = new NamingChecker.Builder();
        builder.withParameterNamingChecker(parameters.getParameterNamingConvention());
        builder.withHeaderNamingChecker(parameters.getHeaderNamingConvention());
        builder.withPathNamingChecker(parameters.getPathNamingConvention());
        builder.withPropertyNamingChecker(parameters.getPropertyNamingConvention());
        return builder.build();
    }
}
