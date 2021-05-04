package in.gov.cowin;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CoWinResponse implements Serializable {
    private List<Center> centers;

    public List<Center> getCenters() {
        return centers;
    }

    public CoWinResponse(@JsonProperty("centers") List centers) {
        this.centers = (List<Center>) centers.stream().map(c -> {
            LinkedHashMap<String, Object> center = (LinkedHashMap) c;
            return new Center((Integer) center.get("center_id"),
                    (String) center.get("name"),
                    (String) center.get("address"),
                    (String) center.get("state_name"),
                    (String) center.get("district_name"),
                    (String) center.get("block_name"),
                    (Integer) center.get("pincode"),
                    (Integer) center.get("lat"),
                    (Integer) center.get("long"),
                    (String) center.get("from"),
                    (String) center.get("to"),
                    (String) center.get("fee_type"),
                    (List) center.get("sessions"));
        }).collect(Collectors.toList());
    }
}
