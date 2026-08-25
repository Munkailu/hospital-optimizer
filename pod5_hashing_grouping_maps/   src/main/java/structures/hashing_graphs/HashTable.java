package structures.hashing_graphs;

/**
 * Owner: Ibrahim Hidayat (Pod 5, Member 13)
 *
 * A simple hash table: stores a value under a short label (the "key"),
 * and can find or remove that value again using the same key.
 *
 * Think of it like a phone book that's organized so well you can find
 * any name almost instantly, no matter how many names are in it.
 */
public class HashTable<K, V> {

    // Each "bucket" is one slot in our table. We start with a fixed
    // number of buckets. Each key gets sent to one bucket using its
    // hash code, so we don't have to search the whole table every time.
    private static final int DEFAULT_CAPACITY = 16;

    // A simple linked "Entry" holds one key-value pair.
    // We'll explain why we need this chain of entries in Task 2 (collisions).
    private Entry<K, V>[] buckets;
    private int size;

    @SuppressWarnings("unchecked")
    public HashTable() {
        buckets = new Entry[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * Store a value under a key. If the key already exists, its value
     * is replaced with the new one.
     */
    public void add(K key, V value) {
        int index = indexFor(key);
        Entry<K, V> entry = buckets[index];

        // Walk the chain at this bucket to see if the key is already here.
        while (entry != null) {
            if (entry.key.equals(key)) {
                entry.value = value; // key already exists — just update it
                return;
            }
            entry = entry.next;
        }

        // Key not found in this bucket — add a brand new entry at the front.
        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.next = buckets[index];
        buckets[index] = newEntry;
        size++;
    }

    /**
     * Find the value stored under a key. Returns null if the key was
     * never added.
     */
    public V find(K key) {
        int index = indexFor(key);
        Entry<K, V> entry = buckets[index];

        while (entry != null) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
            entry = entry.next;
        }

        return null; // not found
    }

    /**
     * Remove a key (and its value) from the table.
     * Returns true if something was actually removed, false if the key
     * wasn't there in the first place.
     */
    public boolean remove(K key) {
        int index = indexFor(key);
        Entry<K, V> entry = buckets[index];
        Entry<K, V> previous = null;

        while (entry != null) {
            if (entry.key.equals(key)) {
                if (previous == null) {
                    buckets[index] = entry.next; // removing the first entry in the chain
                } else {
                    previous.next = entry.next; // skip over the removed entry
                }
                size--;
                return true;
            }
            previous = entry;
            entry = entry.next;
        }

        return false; // key wasn't found, nothing to remove
    }

    public int size() {
        return size;
    }

    // Turns a key into a bucket index between 0 and (capacity - 1).
    // key.hashCode() can be negative, so we strip the sign with Math.abs
    // before taking the remainder.
    private int indexFor(K key) {
        return Math.abs(key.hashCode()) % buckets.length;
    }

    // One key-value pair, plus a link to the next entry in the same
    // bucket (used when two keys land in the same spot — see Task 2).
    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}