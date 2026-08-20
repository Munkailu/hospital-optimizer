package model;

/**
 * Mirrors the locked "Request" schema from docs/interfaces.md:
 *   id: integer, type: string, urgency_level: integer (1 = low, 5 = critical),
 *   submitted_time: string, origin_location_id: integer,
 *   destination_location_id: integer, status: string.
 * The CSV file also carries internal patient_id / resource_id extras that are
 * NOT loaded into the database. Owned by Pod 1.
 */
public class Request {

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

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getUrgencyLevel() {
        return urgencyLevel;
    }

    public String getSubmittedTime() {
        return submittedTime;
    }

    public int getOriginLocationId() {
        return originLocationId;
    }

    public int getDestinationLocationId() {
        return destinationLocationId;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Request{id=" + id + ", type='" + type + "', urgencyLevel=" + urgencyLevel
                + ", status='" + status + "'}";
    }
}