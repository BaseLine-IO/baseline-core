package baseline.domain.controllers;

import baseline.domain.support.Change;
import org.glassfish.hk2.api.IterableProvider;

import javax.inject.Inject;
import java.util.Collection;

/**
 * Created by Home on 23.03.16.
 */
public class RuleEngine {


    @Inject
    private IterableProvider<RuleEngine.Rule> Rules;
    private Collection<?> Result;

    public void applyRules(Collection<Change> changes) {

        changes.stream().forEach(
                change -> {

                    change.getSource().getClass();
                }
        );


    }

    public void setResultCollection(Collection<?> r) {
        Result = r;
    }

    public interface Rule {
        void apply(Change change, Collection<?> result);
    }


}
