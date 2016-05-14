package baseline.domain.model;

import baseline.domain.support.ID;
import baseline.domain.support.Parent;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.Objects;

/**
 * Created by Home on 21.02.16.
 */
public class SchemaObject {


    protected String Name;


    protected Object Parent;


    @XmlAttribute
    @ID
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


    public Object getParent() {
        return Parent;
    }

    @Parent
    public void setParent(Object parent) {
        Parent = parent;
    }


    @Override
    public int hashCode() {
        return Objects.hash(Name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final SchemaObject other = (SchemaObject) obj;
        return Objects.equals(this.Name, other.Name);
    }

    public String className() {
        return this.getClass().getName();
    }
}
