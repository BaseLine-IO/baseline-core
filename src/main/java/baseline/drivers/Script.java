package baseline.drivers;

import baseline.domain.support.Change;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by Home on 21.03.16.
 */
public class Script {
    PriorityQueue<Change> queue;

    public Script() {
        this.queue = new PriorityQueue<>(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return 0;
            }
        });
    }
}
