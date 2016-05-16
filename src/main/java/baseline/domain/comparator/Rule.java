package baseline.domain.comparator;

import java.util.Collection;

public interface Rule {
    // return true if the input change must be added to the result set after apply rule by the RuleEngine
    boolean apply(Change change, Collection<Change> result);
}
