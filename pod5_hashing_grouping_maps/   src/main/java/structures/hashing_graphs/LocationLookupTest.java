package structures.hashing_graphs;

import model.Location;

/**
 * Owner: Ibrahim Hidayat (Pod 5, Member 13)
 *
 * Tests LocationLookup using a few realistic Ghanaian hospital
 * location names, matching the style of Pod 1's real data.
 */
public class LocationLookupTest {

    public static void main(String[] args) {
        LocationLookup lookup = new LocationLookup();

        lookup.addLocation(new Location(1, "Accident and Emergency Ward", "ward"));
        lookup.addLocation(new Location(2, "Central Pharmacy", "pharmacy"));
        lookup.addLocation(new Location(3, "Main Entrance", "entrance"));

        Location found = lookup.findByName("Central Pharmacy");
        check(found != null && found.getId() == 2, "Should find Central Pharmacy by name");

        Location missing = lookup.findByName("Radiology Unit");
        check(missing == null, "Looking up a location that was never added should return null");

        boolean removed = lookup.removeByName("Main Entrance");
        check(removed, "Should successfully remove Main Entrance");
        check(lookup.findByName("Main Entrance") == null, "Main Entrance should no longer be found");

        System.out.println("All LocationLookup tests passed.");
    }

    private static void check(boolean condition, String description) {
        if (!condition) {
            throw new AssertionError("FAILED: " + description);
        }
        System.out.println("PASSED: " + description);
    }
}