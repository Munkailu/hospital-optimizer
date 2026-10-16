package pod4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadRealData {
    public static void main(String[] args) throws Exception {
        BasicSearchTree tree = new BasicSearchTree();

        // Groups request IDs by urgency level, since the tree itself
        // can't hold duplicate keys or extra info like urgency.
        Map<Integer, List<Integer>> idsByUrgency = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(
                new FileReader("../../../../pod1_data_database/day2/data/requests.csv"))) {
            String line = reader.readLine(); // skip header row
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                int id = Integer.parseInt(fields[0].trim());
                int urgency = Integer.parseInt(fields[6].trim()); // urgency_level column

                tree.add(id);

                idsByUrgency.computeIfAbsent(urgency, k -> new ArrayList<>()).add(id);
            }
        }

        System.out.println("Loaded " + tree.inorder().size() + " real request IDs into the tree");

        // --- Search by ID ---
        int testId = 42;
        System.out.println("Searching for ID " + testId + ": " + (tree.search(testId) ? "FOUND" : "NOT FOUND"));

        int missingId = 99999;
        System.out.println("Searching for ID " + missingId + ": " + (tree.search(missingId) ? "FOUND" : "NOT FOUND"));

        // --- Edge case: first and last row in the CSV ---
        int firstRowId = 1;
        int lastRowId = 300;
        System.out.println("Searching for ID " + firstRowId + " (first row): " + (tree.search(firstRowId) ? "FOUND" : "NOT FOUND"));
        System.out.println("Searching for ID " + lastRowId + " (last row): " + (tree.search(lastRowId) ? "FOUND" : "NOT FOUND"));

        // --- Search by urgency level ---
        int urgencyToFind = 5;
        List<Integer> matches = idsByUrgency.getOrDefault(urgencyToFind, new ArrayList<>());
        System.out.println("Requests with urgency " + urgencyToFind + ": " + matches.size() + " found");
        System.out.println("  IDs: " + matches);
    }
}