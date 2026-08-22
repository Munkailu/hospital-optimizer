package structures.hashing_graphs;

import model.Location;

/**
 * Owner: Ibrahim Hidayat (Pod 5, Member 13)
 *
 * A tool that stores Location objects and lets you find one instantly
 * by its name, using the HashTable built in Task 1/2.
 *
 * Uses Pod 1's real Location class (package model), matching the
 * locked schema in docs/interfaces.md: id, name, type.
 */
public class LocationLookup {

    // Reuses the same HashTable — the key is the location's name,
    // the value is the whole Location object.
    private final HashTable<String, Location> table;

    public LocationLookup() {
        table = new HashTable<>();
    }

    /** Store a location so it can be found later by name. */
    public void addLocation(Location location) {
        table.add(location.getName(), location);
    }

    /** Find a location instantly by its name. Returns null if not found. */
    public Location findByName(String name) {
        return table.find(name);
    }

    /** Remove a location by name. Returns true if it was actually removed. */
    public boolean removeByName(String name) {
        return table.remove(name);
    }
}