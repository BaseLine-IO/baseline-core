package baseline.script;

import baseline.domain.support.Change;

import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Created by Home on 21.03.16.
 */
public class ScriptManager {

    private PriorityQueue<Change> Script;
    private Change Current;
    private boolean remove = false;

    public ScriptManager(HashSet<Change> script) {
        Script = new PriorityQueue<Change>(script);
    }

    public Change current() {
        return Current;
    }

    public void add(Change change) {
        Script.add(change);
    }


    public void removeCurrent() {
        remove = true;
    }

    public void commit() {
        if (remove) Script.remove(Current);
        remove = false;
    }


    public void applyRules() {

    }


}
