// Owner: Dogbe Nicole Eyram (Pod 1)

import java.util.ArrayList;
import java.util.List;

public class MostUrgentFirstScheduler {

    public static List<Request> dispatchOrder(Queue<Request> incoming) {
        List<Request> pending = new ArrayList<>();
        while (!incoming.isEmpty()) {
            pending.add(incoming.dequeue());
        }
        return dispatchOrder(pending);
    }

    public static List<Request> dispatchOrder(List<Request> pendingInput) {
        List<Request> pending = new ArrayList<>(pendingInput);
        List<Request> order = new ArrayList<>();

        while (!pending.isEmpty()) {
            int bestIndex = 0;
            for (int i = 1; i < pending.size(); i++) {
                Request candidate = pending.get(i);
                Request current = pending.get(bestIndex);
                boolean moreUrgent = candidate.urgencyLevel > current.urgencyLevel;
                boolean sameUrgencyButEarlier =
                        candidate.urgencyLevel == current.urgencyLevel
                        && candidate.submittedTime < current.submittedTime;
                if (moreUrgent || sameUrgencyButEarlier) {
                    bestIndex = i;
                }
            }
            order.add(pending.remove(bestIndex));
        }
        return order;
    }
}