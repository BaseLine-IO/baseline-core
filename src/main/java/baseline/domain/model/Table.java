package baseline.domain.model;


import baseline.domain.support.Nodes;
import baseline.script.SortOrder;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import java.util.Objects;

/**
 * Created by Home on 30.01.16.
 */
@SortOrder(1)
public class Table extends SchemaObject {

    private String Comment;

    private Nodes<Column> Columns = new Nodes<Column>(this);

    private Nodes<Index> Indexes = new Nodes<Index>(this);

    private Nodes<Constraint> Constraints = new Nodes<Constraint>(this);

    private boolean session = false;

    private boolean transaction = false;


    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public boolean isSession() {
        return session;
    }

    public void setSession(boolean session) {
        this.session = session;
    }

    public boolean isTransaction() {
        return transaction;
    }

    public void setTransaction(boolean transaction) {
        this.transaction = transaction;
    }

    @XmlElement(name = "Column")
    public Nodes<Column> getColumns() {
        return Columns;
    }

    public void setColumns(Nodes<Column> columns) {
        Columns = columns;
    }

    @XmlElement(name = "Index")
    public Nodes<Index> getIndexes() {
        return Indexes;
    }

    public void setIndexes(Nodes<Index> indexes) {
        Indexes = indexes;
    }

    @XmlElements({
            @XmlElement(name = "Unique", type = baseline.domain.model.Constraint.Unique.class),
            @XmlElement(name = "Check", type = baseline.domain.model.Constraint.Check.class),
            @XmlElement(name = "PrimaryKey", type = baseline.domain.model.Constraint.PrimaryKey.class),
            @XmlElement(name = "ForeignKey", type = baseline.domain.model.Constraint.ForeignKey.class)
    })
    public Nodes<Constraint> getConstraints() {
        return Constraints;
    }

    public void setConstraints(Nodes<Constraint> constraints) {
        Constraints = constraints;
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + Objects.hash(Comment, Columns, Indexes, Constraints, session, transaction);
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
        final Table other = (Table) obj;
        return Objects.equals(this.Comment, other.Comment)
                && Objects.equals(this.Columns, other.Columns)
                && Objects.equals(this.Indexes, other.Indexes)
                && Objects.equals(this.Constraints, other.Constraints)
                && Objects.equals(this.session, other.session)
                && Objects.equals(this.transaction, other.transaction);
    }


}
