package pod3;

import java.util.ArrayList;
import java.util.List;

/**
 * "Grab the best option now" — greedy resource assignment (Day 4 deliverable).
 *
 * Processes pending requests in most-urgent-first order (using the priority
 * tool), and for each one immediately assigns the nearest available
 * resource, then removes that resource from the pool and moves on. It never
 * reconsiders a decision once made.
 *
 * This is a genuine greedy algorithm, and greedy algorithms are not always
 * optimal: see GreedyAssignmentTest.greedyCanProduceAWorseResultThanOptimalMatching()
 * for the project brief's required "when it fails" example — a small,
 * deliberately constructed case where this method produces a clearly worse
 * total distance than a smarter (globally optimal) pairing would.
 */
public class GreedyAssignment {

    public interface DistanceLookup {
        double distanceBetween(int locationIdA, int locationIdB);
    }

    public static final class Assignment {
        private final Request request;
        private final Resource resource;
        private final double distance;

        public Assignment(Request request, Resource resource, double distance) {
            this.request = request;
            this.resource = resource;
            this.distance = distance;
        }

        public Request getRequest() { return request; }
        public Resource getResource() { return resource; }
        public double getDistance() { return distance; }

        @Override
        public String toString() {
            return "Assignment{request=" + request.getId() + ", resource=" + resource.getId()
                    + ", distance=" + distance + "}";
        }
    }

    public static List<Assignment> assign(List<Request> requests, List<Resource> resources,
                                           DistanceLookup distances) {
        PriorityQueue<Request> pending = new PriorityQueue<>(Request.BY_URGENCY_DESC);
        for (Request r : requests) pending.add(r);

        List<Resource> available = new ArrayList<>(resources);
        List<Assignment> result = new ArrayList<>();

        while (!pending.isEmpty()) {
            Request request = pending.removeHighestPriority();

            Resource nearest = null;
            double bestDistance = Double.MAX_VALUE;
            for (Resource resource : available) {
                double d = distances.distanceBetween(request.getOriginLocationId(), resource.getCurrentLocationId());
                if (d < bestDistance) {
                    bestDistance = d;
                    nearest = resource;
                }
            }

            if (nearest != null) {
                available.remove(nearest);
                result.add(new Assignment(request, nearest, bestDistance));
            }
        }
        return result;
    }

    public static double totalDistance(List<Assignment> assignments) {
        double total = 0;
        for (Assignment a : assignments) total += a.getDistance();
        return total;
    }
}
