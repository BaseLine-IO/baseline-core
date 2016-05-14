package baseline.qualifiers;

import org.glassfish.hk2.api.AnnotationLiteral;

/**
 * Created by Home on 24.03.16.
 */
public class DriverLiteral extends AnnotationLiteral<Driver> implements Driver {

    private String value;

    public DriverLiteral(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }
}
