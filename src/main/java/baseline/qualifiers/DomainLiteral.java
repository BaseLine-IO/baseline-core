package baseline.qualifiers;

import baseline.domain.model.SchemaObject;
import org.glassfish.hk2.api.AnnotationLiteral;

/**
 * Created by Home on 24.03.16.
 */
public class DomainLiteral extends AnnotationLiteral<Domain> implements Domain {

    private Class<? extends SchemaObject> value;

    public DomainLiteral(Class<? extends SchemaObject> value) {
        this.value = value;
    }

    @Override
    public Class<? extends SchemaObject> value() {
        return value;
    }
}
