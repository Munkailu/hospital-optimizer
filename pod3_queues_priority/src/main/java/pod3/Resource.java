package pod3;

/**
 * Mirrors the locked "Resource" schema from Pod 1 (docs/interfaces.md):
 *   id: integer
 *   type: string
 *   availability_status: string
 *   current_location_id: integer
 *
 * NOTE: Should also collapse into shared/Resource.java on Day 5.
 */
public class Resource {

    private final int id;
    private final String type;
    private final String availabilityStatus;
    private final int currentLocationId;

    public Resource(int id, String type, String availabilityStatus, int currentLocationId) {
        this.id = id;
        this.type = type;
        this.availabilityStatus = availabilityStatus;
        this.currentLocationId = currentLocationId;
    }

    public int getId() { return id; }
    public String getType() { return type; }
    public String getAvailabilityStatus() { return availabilityStatus; }
    public int getCurrentLocationId() { return currentLocationId; }

    @Override
    public String toString() {
        return "Resource{id=" + id + ", type='" + type + "', currentLocationId=" + currentLocationId + "}";
    }
}
