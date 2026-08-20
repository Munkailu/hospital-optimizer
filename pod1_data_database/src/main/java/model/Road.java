package model;

/**
 * Mirrors the locked "Road" schema from docs/interfaces.md:
 *   id: integer, from_location_id: integer, to_location_id: integer,
 *   distance: number.
 * The CSV file also carries an internal "estimated_time" extra that is NOT
 * loaded into the database. Owned by Pod 1.
 */
public class Road {

    private final int id;
    private final int fromLocationId;
    private final int toLocationId;
    private final double distance;

    public Road(int id, int fromLocationId, int toLocationId, double distance) {
        this.id = id;
        this.fromLocationId = fromLocationId;
        this.toLocationId = toLocationId;
        this.distance = distance;
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

    @Override
    public String toString() {
        return "Road{id=" + id + ", from=" + fromLocationId + ", to=" + toLocationId
                + ", distance=" + distance + "}";
    }
}