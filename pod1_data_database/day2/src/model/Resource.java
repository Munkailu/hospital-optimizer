package model;

public class Resource {

    private int id;
    private String type;
    private String resourceName;
    private String availabilityStatus;
    private int currentLocationId;

    public Resource(int id,
                    String type,
                    String resourceName,
                    String availabilityStatus,
                    int currentLocationId) {

        this.id = id;
        this.type = type;
        this.resourceName = resourceName;
        this.availabilityStatus = availabilityStatus;
        this.currentLocationId = currentLocationId;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public int getCurrentLocationId() {
        return currentLocationId;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", resourceName='" + resourceName + '\'' +
                ", availabilityStatus='" + availabilityStatus + '\'' +
                ", currentLocationId=" + currentLocationId +
                '}';
    }
}