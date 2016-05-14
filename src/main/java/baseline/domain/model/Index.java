package baseline.domain.model;

import baseline.script.SortOrder;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Created by Home on 30.01.16.
 */
@SortOrder(3)
public class Index extends SchemaObject {

    private boolean Unique = false;

    private boolean AscDescend = true;

    private LinkedList<String> Columns;


    @XmlAttribute(name = "Unique")
    public boolean isUnique() {
        return Unique;
    }

    public void setUnique(boolean unique) {
        Unique = unique;
    }

    @XmlAttribute(name = "ASC")
    public boolean isAscDescend() {
        return AscDescend;
    }

    public void setAscDescend(boolean ascDescend) {
        AscDescend = ascDescend;
    }


    @XmlElement(name = "Column")
    public LinkedList<String> getColumns() {
        return Columns;
    }

    public void setColumns(LinkedList<String> columns) {
        Columns = columns;
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + Objects.hash(Unique, AscDescend, Columns);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        final Index other = (Index) obj;
        return Objects.equals(this.Unique, other.Unique)
                && Objects.equals(this.AscDescend, other.AscDescend)
                && Objects.equals(this.Columns, other.Columns);
    }
}
