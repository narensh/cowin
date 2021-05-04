package in.gov.cowin;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Session {
    private String sessionId;
    private String date;
    private Integer availableCapacity;
    private Integer minAgeLimit;

    @Override
    public String toString() {
        return "\navailableCapacity=" + availableCapacity +
                ", minAgeLimit=" + minAgeLimit +
                ", vaccine='" + vaccine + '\'' +
                ", slots=" + slots;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getDate() {
        return date;
    }

    public Integer getAvailableCapacity() {
        return availableCapacity;
    }

    public Integer getMinAgeLimit() {
        return minAgeLimit;
    }

    public String getVaccine() {
        return vaccine;
    }

    public List<String> getSlots() {
        return slots;
    }

    private String vaccine;
    private List<String> slots;

    public Session(@JsonProperty("session_id") String sessionId,
                   @JsonProperty("date") String date,
                   @JsonProperty("available_capacity") Integer availableCapacity,
                   @JsonProperty("min_age_limit") Integer minAgeLimit,
                   @JsonProperty("vaccine") String vaccine,
                   @JsonProperty("slots") List slots) {
        this.sessionId = sessionId;
        this.date = date;
        this.availableCapacity = availableCapacity;
        this.minAgeLimit = minAgeLimit;
        this.vaccine = vaccine;
        this.slots = slots;
    }
}
