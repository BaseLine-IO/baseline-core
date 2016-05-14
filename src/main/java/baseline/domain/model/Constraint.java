package baseline.domain.model;

import baseline.script.SortOrder;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Home on 30.01.16.
 */
public class Constraint extends SchemaObject {

    private transient boolean Username = true;
    private transient String DBSysName;
    private String Expression;
    private String OnDelete;
    private ArrayList<Column> Columns;
    private String RefTable;
    private String onColumn;

    public boolean isUsername() {
        return Username;
    }

    public void setUsername(boolean username) {
        Username = username;
    }

    public String getDBSysName() {
        return DBSysName;
    }

    public void setDBSysName(String DBSysName) {
        this.DBSysName = DBSysName;
    }

    public String getExpression() {
        return Expression;
    }

    public void setExpression(String expression) {
        Expression = expression;
    }

    @XmlAttribute
    public String getOnDelete() {
        return OnDelete;
    }

    public void setOnDelete(String onDelete) {
        OnDelete = onDelete;
    }

    @XmlElement(name = "Column")
    public ArrayList<Column> getColumns() {
        return Columns;
    }

    public void setColumns(ArrayList<Column> columns) {
        Columns = columns;
    }

    @XmlAttribute
    public String getRefTable() {
        return RefTable;
    }

    public void setRefTable(String refTable) {
        RefTable = refTable;
    }

    @XmlAttribute
    public String getOnColumn() {
        return onColumn;
    }

    public void setOnColumn(String onColumn) {
        this.onColumn = onColumn;
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + Objects.hash(Username, DBSysName, Expression, OnDelete, Columns, RefTable, onColumn);
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
        final Constraint other = (Constraint) obj;
        return Objects.equals(this.Username, other.Username)
                && Objects.equals(this.DBSysName, other.DBSysName)
                && Objects.equals(this.Expression, other.Expression)
                && Objects.equals(this.OnDelete, other.OnDelete)
                && Objects.equals(this.Columns, other.Columns)
                && Objects.equals(this.RefTable, other.RefTable)
                && Objects.equals(this.onColumn, other.onColumn);
    }

    @SortOrder(4)
    public static class PrimaryKey extends Constraint {

    }

    @SortOrder(5)
    public static class ForeignKey extends Constraint {

    }

    @SortOrder(4)
    public static class Check extends Constraint {

    }

    @SortOrder(4)
    public static class Unique extends Constraint {

    }
}
