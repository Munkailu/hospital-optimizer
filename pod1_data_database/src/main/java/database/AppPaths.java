package database;

import java.io.File;

/**
 * Resolves file/folder locations that differ depending on where the program
 * is launched from (repo root vs. inside the pod folder). Owned by Pod 1.
 *
 * The CSV data files live at pod1_data_database/day2/data/ relative to the
 * repo root. Maven runs with the working directory set to the pom folder, so
 * we also accept day2/data/ (and data/) as fallbacks.
 */
public final class AppPaths {

    private static final String[] DATA_CANDIDATES = {
            "pod1_data_database/day2/data",
            "day2/data",
            "data"
    };

    private AppPaths() {}

    /** Full path to the day2 data folder (first candidate that holds locations.csv). */
    public static String dataFolder() {
        return firstExistingWithFile(DATA_CANDIDATES, "locations.csv") + "/";
    }

    /**
     * Full path to the SQLite database file, placed next to the data folder
     * that was actually resolved. The parent folder is created if it does not
     * exist so the loader can run on a fresh checkout.
     */
    public static String databaseFilePath() {
        String dataFolder = firstExistingWithFile(DATA_CANDIDATES, "locations.csv");
        String dbFolder;
        if (dataFolder.equals("data")) {
            dbFolder = "data/database";
        } else {
            dbFolder = dataFolder.replace("/data", "/database");
        }

        File dir = new File(dbFolder);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dbFolder + "/hospital_optimizer.db";
    }

    private static String firstExistingWithFile(String[] candidates, String fileName) {
        for (String candidate : candidates) {
            if (new File(candidate, fileName).isFile()) {
                return candidate;
            }
        }
        return candidates[0];
    }
}