package baseline.domain.comparator;

import baseline.domain.model.Schema;
import baseline.domain.model.SchemaObject;
import baseline.domain.support.Nodes;
import baseline.qualifiers.DomainLiteral;
import org.glassfish.hk2.api.IterableProvider;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by Home on 23.02.16.
 */
public class ModelComparator {

    @Inject
    private IterableProvider<Rule> rules;

    private Collection<Change> changes = new HashSet<Change>();


    private void compareNodes(Nodes<SchemaObject> target, Nodes<SchemaObject> source) throws IllegalAccessException {
        Set<SchemaObject> set = new HashSet<SchemaObject>();
        set.addAll(source);
        set.removeAll(target);
        for (SchemaObject obj : set) {
            if (target.containsKey(obj.getName())) {
                this.compareObjects(target.find(obj.getName()), source.find(obj.getName()));
            } else {
                changes.add(
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
                changes.add(
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
                    changes.add(
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

    public ModelComparator compare(Object target, Object source) throws IllegalAccessException {
        if (!Objects.equals(target, source)) {


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
        return this;
    }

    public ModelComparator applyRules(Collection<Change> newChangeSet) {

        changes.stream().forEach(
                change -> {

                    rules.qualifiedWith(new DomainLiteral((Class<? extends SchemaObject>) change.getSource().getClass()))

                            .forEach(r -> {
                                if (r.apply(change, newChangeSet)) {
                                    newChangeSet.add(change);
                                }
                            });

                }
        );

        changes = newChangeSet;
        return this;

    }


    public Collection<Change> changes() {
        return changes;
    }


}
