package in.gov.cowin;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Center {
    private Integer centerId;
    private String name;
    private String address;
    private String stateName;
    private String districtName;
    private String blockName;
    private Integer pinCode;
    private Integer lat;
    private Integer longitude;
    private String from;
    private String to;
    private String feeType;
    private List<Session> sessions;
    private List<Session> matchingSessions;

    @Override
    public String toString() {
        Map<String, List<Session>> sessionsMap = sessions.stream().collect(Collectors.groupingBy(session -> session.getDate()));
        return "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", stateName='" + stateName + '\'' +
                ", pincode=" + pinCode +
                ", lat=" + lat +
                ", longitude=" + longitude +
                "\n" + sessionsMap +"\n";
    }

    public List<Session> getMatchingSessions() {
        return matchingSessions;
    }

    public void setMatchingSessions(List<Session> matchingSessions) {
        this.matchingSessions = matchingSessions;
    }

    public Integer getCenterId() {
        return centerId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getStateName() {
        return stateName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public String getBlockName() {
        return blockName;
    }

    public Integer getPincode() {
        return pinCode;
    }

    public Integer getLat() {
        return lat;
    }

    public Integer getLongitude() {
        return longitude;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getFeeType() {
        return feeType;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public Center(Integer centerId, String name, String address, String stateName, String districtName,
                  String blockName, Integer pinCode, Integer lat, Integer longitude, String from,
                  String to, String feeType, List sessions) {
        this.centerId = centerId;
        this.name = name;
        this.address = address;
        this.stateName = stateName;
        this.districtName = districtName;
        this.blockName = blockName;
        this.pinCode = pinCode;
        this.lat = lat;
        this.longitude = longitude;
        this.from = from;
        this.to = to;
        this.feeType = feeType;
        this.sessions = (List<Session>) sessions.stream().map(s -> {
            LinkedHashMap session = (LinkedHashMap) s;
            return new Session((String) session.get("session_id"),
                    (String) session.get("date"),
                    (Integer) session.get("available_capacity"),
                    (Integer) session.get("min_age_limit"),
                    (String) session.get("vaccine"),
                    (List) session.get("slots"));
        }).collect(Collectors.toList());
    }
}
