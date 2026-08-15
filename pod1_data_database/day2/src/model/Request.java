package model;

public class Request {

    private int id;
    private int patientId;
    private int resourceId;
    private int originLocationId;
    private int destinationLocationId;
    private String type;
    private int urgencyLevel;
    private String status;
    private String submittedTime;

    public Request(int id,
                   int patientId,
                   int resourceId,
                   int originLocationId,
                   int destinationLocationId,
                   String type,
                   int urgencyLevel,
                   String status,
                   String submittedTime) {

        this.id = id;
        this.patientId = patientId;
        this.resourceId = resourceId;
        this.originLocationId = originLocationId;
        this.destinationLocationId = destinationLocationId;
        this.type = type;
        this.urgencyLevel = urgencyLevel;
        this.status = status;
        this.submittedTime = submittedTime;
    }

    public int getId() {
        return id;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getOriginLocationId() {
        return originLocationId;
    }

    public int getDestinationLocationId() {
        return destinationLocationId;
    }

    public String getType() {
        return type;
    }

    public int getUrgencyLevel() {
        return urgencyLevel;
    }

    public String getStatus() {
        return status;
    }

    public String getSubmittedTime() {
        return submittedTime;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", resourceId=" + resourceId +
                ", originLocationId=" + originLocationId +
                ", destinationLocationId=" + destinationLocationId +
                ", type='" + type + '\'' +
                ", urgencyLevel=" + urgencyLevel +
                ", status='" + status + '\'' +
                ", submittedTime='" + submittedTime + '\'' +
                '}';
    }
}