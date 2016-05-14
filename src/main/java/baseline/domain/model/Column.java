package baseline.domain.model;

import baseline.script.SortOrder;

import java.util.Objects;

/**
 * Created by Home on 24.02.16.
 */
@SortOrder(2)
public class Column extends SchemaObject {

    private DataType Type;
    private String Ref;
    private Integer OrderNum;
    private Boolean NULLABLE = null;
    private String DefaultValue;
    private String Comment;

    public String getRef() {
        return Ref;
    }

    public void setRef(String ref) {
        Ref = ref;
    }

    public Integer getOrderNum() {
        return OrderNum;
    }

    public void setOrderNum(Integer orderNum) {
        OrderNum = orderNum;
    }

    public Boolean getNULLABLE() {
        return NULLABLE;
    }

    public void setNULLABLE(Boolean NULLABLE) {
        this.NULLABLE = NULLABLE;
    }

    public String getDefaultValue() {
        return DefaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        DefaultValue = defaultValue;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getType() {
        return Type.getName();
    }

    public void setType(String name) {
        Type.setName(name);
    }

    public Integer getSize() {
        return Type.getSize();
    }

    public void setSize(Integer size) {
        Type.setSize(size);
    }

    public Integer getScale() {
        return Type.getScale();
    }

    public void setScale(Integer scale) {
        Type.setScale(scale);
    }

    public String getUnits() {
        return Type.getUnits();
    }

    public void setUnits(String units) {
        Type.setUnits(units);
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + Objects.hash(Type, Ref, OrderNum, NULLABLE, DefaultValue, Comment);
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
        final Column other = (Column) obj;
        return Objects.equals(this.Type, other.Type)
                && Objects.equals(this.Ref, other.Ref)
                && Objects.equals(this.OrderNum, other.OrderNum)
                && Objects.equals(this.NULLABLE, other.NULLABLE)
                && Objects.equals(this.DefaultValue, other.DefaultValue)
                && Objects.equals(this.Comment, other.Comment);
    }

    public class DataType {

        private String Name;

        private Integer Size;   // for ALL

        private Integer Scale; // for NUMBER

        private String Units; // CHAR or BYTE

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public Integer getSize() {
            return Size;
        }

        public void setSize(Integer size) {
            Size = size;
        }

        public Integer getScale() {
            return Scale;
        }

        public void setScale(Integer scale) {
            Scale = scale;
        }

        public String getUnits() {
            return Units;
        }

        public void setUnits(String units) {
            Units = units;
        }

        @Override
        public int hashCode() {
            return Objects.hash(Name, Size, Scale, Units);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            final DataType other = (DataType) obj;
            return Objects.equals(this.Name, other.Name)
                    && Objects.equals(this.Size, other.Size)
                    && Objects.equals(this.Scale, other.Scale)
                    && Objects.equals(this.Units, other.Units);
        }
    }
}
