package org.openapitools.openapistylevalidator.naming;

import java.util.function.Predicate;
import org.apache.commons.lang3.StringUtils;

public interface PatternPredicates {

    Predicate<String> notEmpty = StringUtils::isNotEmpty;
    Predicate<String> underscoreCaseMatcher =
            (input) -> notEmpty.test(input) && input.matches("[a-z][a-z\\d]*(?:_[a-z\\d]+)*");
    Predicate<String> underScoreUpperCaseMatcher =
            (input) -> notEmpty.test(input) && input.matches("[A-Z][A-Z\\d]*(?:_[A-Z\\d]+)*");
    Predicate<String> camelCaseMatcher =
            (input) -> notEmpty.test(input) && input.matches("[a-z][a-z\\d]*(?:[A-Z\\d]+[a-z\\d]*)*");
    Predicate<String> hyphenCaseMatcher =
            (input) -> notEmpty.test(input) && input.matches("[a-z][a-z\\d]*(?:-[a-z\\d]+)*");
    Predicate<String> hyphenUpperCaseMatcher =
            (input) -> notEmpty.test(input) && input.matches("[A-Z][A-Z\\d]*(?:-[A-Z\\d]+)*");

    Predicate<String> anyCaseMatcher = (string) -> true;
}
