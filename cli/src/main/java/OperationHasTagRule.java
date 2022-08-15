import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.Operation;
import org.eclipse.microprofile.openapi.models.PathItem;
import org.openapitools.openapistylevalidator.ErrorAggregator;
import org.openapitools.openapistylevalidator.custom.OpenAPIRule;

/**
 * This rule will check each operation has at least one tag defined
 */
public class OperationHasTagRule extends OpenAPIRule {

    public OperationHasTagRule(ErrorAggregator errorAggregator) {
        super(errorAggregator);
    }

    @Override
    public void execute(OpenAPI openAPI) {

        for (String key : openAPI.getPaths().getPathItems().keySet()) {
            PathItem path = openAPI.getPaths().getPathItems().get(key);
            for (PathItem.HttpMethod method : path.getOperations().keySet()) {
                Operation op = path.getOperations().get(method);
                if (op.getTags() == null || op.getTags().isEmpty()) {
                    errorAggregator.logMissingOrEmptyOperationCollection(key, method, "tags");
                }
            }
        }
    }

}