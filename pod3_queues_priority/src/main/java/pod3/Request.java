package pod3;

import java.util.Objects;
import java.util.Comparator;

/**
 * Mirrors the locked "Request" schema from Pod 1 (docs/interfaces.md):
 *   id: integer
 *   type: string
 *   urgency_level: integer (1 = low, 5 = critical)
 *   submitted_time: string
 *   origin_location_id: integer
 *   destination_location_id: integer
 *   status: string
 *
 * NOTE: Pod 4 has an identical copy of this class in their own package.
 * Both copies should collapse into a single shared/Request.java during the
 * Day 5 merge so every pod imports the same definition instead of keeping
 * separate copies that could drift apart.
 */
public class Request implements Comparable<Request> {

    private final int id;
    private final String type;
    private final int urgencyLevel;
    private final String submittedTime;
    private final int originLocationId;
    private final int destinationLocationId;
    private final String status;

    public Request(int id, String type, int urgencyLevel, String submittedTime,
                    int originLocationId, int destinationLocationId, String status) {
        this.id = id;
        this.type = type;
        this.urgencyLevel = urgencyLevel;
        this.submittedTime = submittedTime;
        this.originLocationId = originLocationId;
        this.destinationLocationId = destinationLocationId;
        this.status = status;
    }

    public static Request forId(int id) {
        return new Request(id, null, 0, null, 0, 0, null);
    }

    public static Request forUrgency(int urgencyLevel) {
        return new Request(0, null, urgencyLevel, null, 0, 0, null);
    }

    public int getId() { return id; }
    public String getType() { return type; }
    public int getUrgencyLevel() { return urgencyLevel; }
    public String getSubmittedTime() { return submittedTime; }
    public int getOriginLocationId() { return originLocationId; }
    public int getDestinationLocationId() { return destinationLocationId; }
    public String getStatus() { return status; }

    /** Natural ordering: by id. */
    @Override
    public int compareTo(Request other) {
        return Integer.compare(this.id, other.id);
    }

    /** Alternate ordering: by urgency, most urgent first. */
    public static final Comparator<Request> BY_URGENCY_DESC =
            (a, b) -> Integer.compare(b.urgencyLevel, a.urgencyLevel);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Request)) return false;
        return id == ((Request) o).id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Request{id=" + id + ", type='" + type + "', urgencyLevel=" + urgencyLevel
                + ", status='" + status + "'}";
    }
}
