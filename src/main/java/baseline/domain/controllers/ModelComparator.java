package baseline.domain.controllers;

import baseline.domain.model.Schema;
import baseline.domain.model.SchemaObject;
import baseline.domain.support.Change;
import baseline.domain.support.Nodes;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by Home on 23.02.16.
 */
public class ModelComparator {

    private HashSet<Change> Changes = new HashSet<Change>();

    private void compareNodes(Nodes<SchemaObject> target, Nodes<SchemaObject> source) throws IllegalAccessException {
        Set<SchemaObject> set = new HashSet<SchemaObject>();
        set.addAll(source);
        set.removeAll(target);
        for (SchemaObject obj : set) {
            if (target.containsKey(obj.getName())) {
                this.compareObjects(target.find(obj.getName()), source.find(obj.getName()));
            } else {
                Changes.add(
                        new Change(
                                target, source, null, Change.Type.ADD
                        )
                );
            }
        }

        set.clear();
        set.addAll(target);
        set.removeAll(source);
        for (SchemaObject obj : set) {
            if (!source.containsObjKey(obj))
                Changes.add(
                        new Change(
                                target, source, null, Change.Type.REMOVE
                        )
                );
        }

    }


    private void compareObjects(SchemaObject target, SchemaObject source) throws IllegalAccessException {
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getType().equals(Nodes.class) || field.getType().equals(SchemaObject.class)) {
                this.compare(field.get(target), field.get(source));
            } else {
                if (!Objects.equals(field.get(target), field.get(source))) {
                    Changes.add(
                            new Change(
                                    target, source, field.getName(), Change.Type.MODIFY
                            )
                    );
                }
            }
        }


    }

    private void compareSchema(Schema target, Schema source) throws IllegalAccessException {
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            this.compare(field.get(target), field.get(source));
        }

    }

    public void compare(Object target, Object source) throws IllegalAccessException {
        if (Objects.equals(target, source)) return;


        if (source instanceof Nodes) {
            this.compareNodes((Nodes) target, (Nodes) source);
        }

        if (source instanceof SchemaObject) {
            this.compareObjects((SchemaObject) target, (SchemaObject) source);
        }

        if (source instanceof Schema) {
            this.compareSchema((Schema) target, (Schema) source);
        }

    }


    public HashSet<Change> changes() {
        return Changes;
    }


}
