// Owner: Dogbe Nicole Eyram (Pod 1)
// Field names match the shared shape defined in docs/interfaces.md (owned by Pod 3)

public class Request {
    public final String id;
    public final String type;              // e.g. "patient_transfer"
    public final int urgencyLevel;         // 1 (low) - 5 (critical)
    public final long submittedTime;       // use System.currentTimeMillis() or similar
    public final String originLocation;
    public final String destinationLocation;
    public final String status;

    public Request(String id, String type, int urgencyLevel, long submittedTime,
                    String originLocation, String destinationLocation, String status) {
        this.id = id;
        this.type = type;
        this.urgencyLevel = urgencyLevel;
        this.submittedTime = submittedTime;
        this.originLocation = originLocation;
        this.destinationLocation = destinationLocation;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Request{" + id + ", urgency=" + urgencyLevel + ", type=" + type + "}";
    }
}