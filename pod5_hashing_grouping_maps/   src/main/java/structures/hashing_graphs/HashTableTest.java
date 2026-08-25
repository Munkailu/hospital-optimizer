package structures.hashing_graphs;

/**
 * Owner: Ibrahim Hidayat (Pod 5, Member 13)
 *
 * Simple hand-written tests for HashTable (no test framework needed —
 * just plain checks with clear pass/fail messages).
 *
 * Three cases, as required:
 *  1. Normal case — add something, find it again.
 *  2. Tricky case — two keys clash (land in the same bucket).
 *  3. Bad-input case — look for a key that was never added.
 */
public class HashTableTest {

    public static void main(String[] args) {
        testNormalCase();
        testCollisionCase();
        testMissingKeyCase();
        testRemove();
        System.out.println("All HashTable tests passed.");
    }

    // 1. Normal case: add something, then find it.
    private static void testNormalCase() {
        HashTable<String, String> table = new HashTable<>();
        table.add("Alice", "Ward A");

        String result = table.find("Alice");
        check(result.equals("Ward A"), "Normal case: found value should match what was added");
    }

    // 2. Tricky case: force two keys into the SAME bucket on purpose,
    // and check both are still findable afterward.
    //
    // We do this with keys we know collide, using a tiny table capacity
    // so it's easy to prove. DEFAULT_CAPACITY is 16, so we pick two keys
    // whose hash codes happen to land in the same slot mod 16.
    private static void testCollisionCase() {
        HashTable<Integer, String> table = new HashTable<>();

        // Integer.hashCode() for an Integer is just its value, so
        // 1 and 17 both land in bucket (1 % 16) = 1 and (17 % 16) = 1.
        // This guarantees a real collision, not a coincidence.
        table.add(1, "Location One");
        table.add(17, "Location Seventeen");

        check(table.find(1).equals("Location One"),
                "Collision case: key 1 should still be found correctly");
        check(table.find(17).equals("Location Seventeen"),
                "Collision case: key 17 (colliding with key 1) should still be found correctly");
    }

    // 3. Bad-input case: looking up a key that was never added should
    // return null, not crash or return the wrong thing.
    private static void testMissingKeyCase() {
        HashTable<String, String> table = new HashTable<>();
        table.add("Alice", "Ward A");

        String result = table.find("Bob");
        check(result == null, "Bad-input case: looking up a missing key should return null");
    }

    // Extra check: removing a key actually removes it.
    private static void testRemove() {
        HashTable<String, String> table = new HashTable<>();
        table.add("Alice", "Ward A");

        boolean removed = table.remove("Alice");
        check(removed, "Remove case: removing an existing key should return true");
        check(table.find("Alice") == null, "Remove case: key should be gone after removal");

        boolean removedAgain = table.remove("Alice");
        check(!removedAgain, "Remove case: removing an already-removed key should return false");
    }

    private static void check(boolean condition, String description) {
        if (!condition) {
            throw new AssertionError("FAILED: " + description);
        }
        System.out.println("PASSED: " + description);
    }
}