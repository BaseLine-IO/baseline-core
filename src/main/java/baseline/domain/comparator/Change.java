package baseline.domain.comparator;

import baseline.script.SortOrder;

import java.util.Objects;

/**
 * Created by Home on 21.02.16.
 */
public class Change implements Comparable<Change> {


    private Object Target;
    private Object Source;
    private String FieldName;
    private Type Type;

    public Change() {

    }

    public Change(Object target, Object source, String fieldName, Change.Type type) {
        Target = target;
        Source = source;
        FieldName = fieldName;
        Type = type;
    }

    @Override
    public int compareTo(Change o) {
        if (this.getSource().getClass().isAnnotationPresent(SortOrder.class)) {
            int $this = this.getSource().getClass().getAnnotation(SortOrder.class).value() * this.getType().getSort();
            int $there = o.getSource().getClass().getAnnotation(SortOrder.class).value() * o.getType().getSort();
            if ($this < $there) return -1;
            if ($this > $there) return 1;

        }

        return 0;
    }

    public Object getSource() {
        return Source;
    }

    public Change.Type getType() {
        return Type;
    }

    public void setType(Change.Type type) {
        Type = type;
    }

    public void setSource(Object source) {
        Source = source;
    }

    public Object getTarget() {
        return Target;
    }

    public void setTarget(Object target) {
        Target = target;
    }

    public String getFieldName() {
        return FieldName;
    }

    public void setFieldName(String fieldName) {
        FieldName = fieldName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Target, Source, FieldName, Type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Change other = (Change) obj;
        return Objects.equals(this.Target, other.Target)
                && Objects.equals(this.Source, other.Source)
                && Objects.equals(this.FieldName, other.FieldName)
                && Objects.equals(this.Type, other.Type);
    }

    public enum Type {
        ADD(1), MODIFY(1), REMOVE(-1);

        private int Sort;

        Type(int sort) {
        }

        public int getSort() {
            return Sort;
        }
    }
}
