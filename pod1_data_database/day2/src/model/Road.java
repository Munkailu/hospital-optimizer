package model;

public class Road {

    private int id;
    private int fromLocationId;
    private int toLocationId;
    private double distance;
    private int estimatedTime;

    public Road(int id, int fromLocationId, int toLocationId,
                double distance, int estimatedTime) {

        this.id = id;
        this.fromLocationId = fromLocationId;
        this.toLocationId = toLocationId;
        this.distance = distance;
        this.estimatedTime = estimatedTime;
    }

    public int getId() {
        return id;
    }

    public int getFromLocationId() {
        return fromLocationId;
    }

    public int getToLocationId() {
        return toLocationId;
    }

    public double getDistance() {
        return distance;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    @Override
    public String toString() {
        return "Road{" +
                "id=" + id +
                ", from=" + fromLocationId +
                ", to=" + toLocationId +
                ", distance=" + distance +
                ", estimatedTime=" + estimatedTime +
                '}';
    }
}