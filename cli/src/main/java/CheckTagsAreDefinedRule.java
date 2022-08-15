import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.openapitools.openapistylevalidator.ErrorAggregator;
import org.openapitools.openapistylevalidator.custom.OpenAPIRule;
import org.openapitools.openapistylevalidator.styleerror.StyleError;

/**
 * This rule will check whether tags are defined in the rule section
 */
public class CheckTagsAreDefinedRule extends OpenAPIRule {

    public CheckTagsAreDefinedRule(ErrorAggregator errorAggregator) {
        super(errorAggregator);
    }

    @Override
    public void execute(OpenAPI api) {
        if (api.getTags() == null || api.getTags().isEmpty()) {
            errorAggregator.logMissingOrEmptyAttribute(StyleError.StyleCheckSection.OpenAPI, "tags");
        }
    }
}
