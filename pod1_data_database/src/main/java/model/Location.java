package model;

/**
 * Mirrors the locked "Location" schema from docs/interfaces.md:
 *   id: integer, name: string, type: string.
 * The CSV file also carries an internal "floor" extra that is NOT loaded.
 * Owned by Pod 1.
 */
public class Location {

    private final int id;
    private final String name;
    private final String type;

    public Location(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Location{id=" + id + ", name='" + name + "', type='" + type + "'}";
    }
}