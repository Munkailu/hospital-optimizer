package helper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Test helper: writes small CSV fixtures to a temporary folder so validator
 * and loader tests can exercise normal, edge and bad-input cases without
 * touching the real dataset. Owned by Pod 1.
 */
public final class TestFiles {

    private TestFiles() {}

    /** Creates a fresh temp folder and returns its path. */
    public static Path newTempFolder() throws IOException {
        return Files.createTempDirectory("pod1-test");
    }

    /** Writes one file inside folder and returns its full path. */
    public static String write(Path folder, String fileName, String content) throws IOException {
        Path file = folder.resolve(fileName);
        Files.write(file, content.getBytes(StandardCharsets.UTF_8));
        return file.toString();
    }

    // ------------------------ well-formed fixtures ------------------------

    public static String validLocations() {
        return "id,name,type,floor\n"
                + "1,Main Entrance,Entrance,0\n"
                + "2,Emergency Ward,Ward,1\n"
                + "3,Pharmacy,Pharmacy,2\n";
    }

    public static String validRoads() {
        return "id,from_location_id,to_location_id,distance,estimated_time\n"
                + "1,1,2,68,1\n"
                + "2,2,3,110,2\n";
    }

    public static String validResources() {
        return "id,type,resource_name,availability_status,current_location_id\n"
                + "1,Ambulance,Ambulance 1,Available,1\n"
                + "2,Nurse,Nurse 2,Busy,2\n";
    }

    public static String validRequests() {
        return "id,patient_id,resource_id,origin_location_id,destination_location_id,"
                + "type,urgency_level,status,submitted_time\n"
                + "1,11,1,1,2,Patient Transfer,5,Completed,2026-08-08 17:38\n"
                + "2,12,2,2,3,Sample Pickup,1,Pending,2026-08-11 12:13\n";
    }

    // ------------------------ bad-input fixtures ------------------------

    public static String duplicateLocationIds() {
        return "id,name,type,floor\n"
                + "1,Main Entrance,Entrance,0\n"
                + "1,Emergency Ward,Ward,1\n";
    }

    public static String badUrgencyRequests() {
        return "id,patient_id,resource_id,origin_location_id,destination_location_id,"
                + "type,urgency_level,status,submitted_time\n"
                + "1,11,1,1,2,Patient Transfer,9,Completed,2026-08-08 17:38\n";
    }

    public static String missingColumnsLocations() {
        return "id,name,type,floor\n"
                + "1,OnlyTwoColumns\n";
    }

    public static String badReferenceRoads() {
        return "id,from_location_id,to_location_id,distance,estimated_time\n"
                + "1,1,99,68,1\n"; // location 99 does not exist
    }

    // ------------------------ edge-case fixtures ------------------------

    public static String blankLinesLocations() {
        return "id,name,type,floor\n"
                + "\n"
                + "1,Main Entrance,Entrance,0\n"
                + "\n"
                + "2,Emergency Ward,Ward,1\n";
    }
}