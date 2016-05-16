package baseline.domain.explorer;

import baseline.domain.model.Constraint;
import baseline.domain.model.Schema;
import baseline.domain.model.SchemaObject;
import baseline.domain.model.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Home on 21.02.16.
 */
public class ModelExplorer {

    private Schema Schema;
    private SchemaObject forWhat;

    public SchemaObject find(Class<? extends SchemaObject> type, String name) {
        return null;
    }

    public ModelExplorer inSchema(Schema schema) {
        this.Schema = schema;
        return this;
    }

    public ModelExplorer forPrimaryKey(Constraint pk) {
        forWhat = pk;
        return this;
    }


    public List<Constraint> findForegnKeys() {
        ArrayList<Constraint> List = new ArrayList<>();
        if (forWhat instanceof Constraint.PrimaryKey) {

            Schema.getTables().stream().forEach(table -> table.getConstraints().stream()

                    .filter(constraint -> constraint instanceof Constraint.ForeignKey)

                    .filter(constraint -> ((Table) (forWhat.getParent())).getName().equals(constraint.getRefTable()))

                    .filter(constraint ->
                            constraint.getColumns().stream().map(column -> column.getRef()).collect(Collectors.toCollection(ArrayList::new)).equals(
                                    ((Constraint.PrimaryKey) forWhat).getColumns().stream().map(column -> column.getName()).collect(Collectors.toCollection(ArrayList::new)))

                    )
                    .forEach(constraint -> List.add(constraint))
            );

        }

        return List;
    }


}
