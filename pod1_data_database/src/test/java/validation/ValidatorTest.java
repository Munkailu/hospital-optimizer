package validation;

import helper.TestFiles;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Pod 1: CSV validator tests covering normal, edge and bad-input cases. */
class ValidatorTest {

    // ------------------------ normal cases ------------------------

    @Test
    void validFilesPass() throws Exception {
        Path folder = TestFiles.newTempFolder();
        TestFiles.write(folder, "locations.csv", TestFiles.validLocations());
        TestFiles.write(folder, "roads.csv", TestFiles.validRoads());
        TestFiles.write(folder, "resources.csv", TestFiles.validResources());
        TestFiles.write(folder, "requests.csv", TestFiles.validRequests());

        List<String> problems = new ArrayList<>();

        assertTrue(Validator.validateLocations(folder.resolve("locations.csv").toString(), problems));
        assertTrue(Validator.validateRoads(folder.resolve("roads.csv").toString(), problems));
        assertTrue(Validator.validateResources(folder.resolve("resources.csv").toString(), problems));
        assertTrue(Validator.validateRequests(folder.resolve("requests.csv").toString(), problems));
        assertTrue(Validator.validateReferences(folder.toString() + java.io.File.separator, problems));
        assertTrue(problems.isEmpty());
    }

    @Test
    void realDatasetPasses() throws Exception {
        String dataFolder = database.AppPaths.dataFolder();
        List<String> problems = new ArrayList<>();
        assertTrue(Validator.validateAll(dataFolder, problems), String.join("; ", problems));
    }

    // ------------------------ edge cases ------------------------

    @Test
    void blankLinesAreSkipped() throws Exception {
        Path folder = TestFiles.newTempFolder();
        String path = TestFiles.write(folder, "locations.csv", TestFiles.blankLinesLocations());

        List<String> problems = new ArrayList<>();
        assertTrue(Validator.validateLocations(path, problems));
    }

    // ------------------------ bad-input cases ------------------------

    @Test
    void duplicateLocationIdsFail() throws Exception {
        Path folder = TestFiles.newTempFolder();
        String path = TestFiles.write(folder, "locations.csv", TestFiles.duplicateLocationIds());

        List<String> problems = new ArrayList<>();
        assertFalse(Validator.validateLocations(path, problems));
        assertFalse(problems.isEmpty());
    }

    @Test
    void missingColumnsFail() throws Exception {
        Path folder = TestFiles.newTempFolder();
        String path = TestFiles.write(folder, "locations.csv", TestFiles.missingColumnsLocations());

        assertFalse(Validator.validateLocations(path, null));
    }

    @Test
    void urgencyOutsideRangeFails() throws Exception {
        Path folder = TestFiles.newTempFolder();
        String path = TestFiles.write(folder, "requests.csv", TestFiles.badUrgencyRequests());

        assertFalse(Validator.validateRequests(path, null));
    }

    @Test
    void badResourceStatusFails() throws Exception {
        String content = "id,type,resource_name,availability_status,current_location_id\n"
                + "1,Ambulance,Ambulance 1,Suspended,1\n";
        Path folder = TestFiles.newTempFolder();
        String path = TestFiles.write(folder, "resources.csv", content);

        assertFalse(Validator.validateResources(path, null));
    }

    @Test
    void roadSelfLoopFails() throws Exception {
        String content = "id,from_location_id,to_location_id,distance,estimated_time\n"
                + "1,1,1,68,1\n";
        Path folder = TestFiles.newTempFolder();
        String path = TestFiles.write(folder, "roads.csv", content);

        assertFalse(Validator.validateRoads(path, null));
    }

    @Test
    void danglingForeignKeyReferenceFails() throws Exception {
        Path folder = TestFiles.newTempFolder();
        TestFiles.write(folder, "locations.csv", TestFiles.validLocations());
        TestFiles.write(folder, "roads.csv", TestFiles.badReferenceRoads());
        TestFiles.write(folder, "resources.csv", TestFiles.validResources());
        TestFiles.write(folder, "requests.csv", TestFiles.validRequests());

        List<String> problems = new ArrayList<>();
        assertFalse(Validator.validateReferences(folder.toString() + java.io.File.separator, problems));
        assertFalse(problems.isEmpty());
    }

    @Test
    void nonExistentFileFails() throws Exception {
        Path folder = TestFiles.newTempFolder();
        assertFalse(Validator.validateLocations(folder.resolve("nope.csv").toString()));
    }
}