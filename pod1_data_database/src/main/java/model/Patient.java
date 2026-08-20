package model;

/**
 * Represents a patient linked to service requests. Not part of the
 * cross-pod locked schema (other pods don't need patient identity), but
 * kept as a real table so Requests.patient_id resolves to an actual row
 * instead of a dangling reference — matches the original ERD from Day 1.
 * Owned by Pod 1.
 */
public class Patient {

    private final int id;
    private final String firstName;
    private final String lastName;
    private final int age;
    private final String gender;
    private final int locationId;
    private final String condition;

    public Patient(int id, String firstName, String lastName, int age,
                    String gender, int locationId, String condition) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.locationId = locationId;
        this.condition = condition;
    }

    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public int getLocationId() { return locationId; }
    public String getCondition() { return condition; }

    @Override
    public String toString() {
        return "Patient{id=" + id + ", name='" + firstName + " " + lastName
                + "', age=" + age + ", condition='" + condition + "'}";
    }
}
