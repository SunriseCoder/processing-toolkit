package app.core.dto;

import java.util.HashMap;
import java.util.Map;

public class TrackMetadata {
    private Map<String, String> parameters;

    public TrackMetadata() {
        parameters = new HashMap<>();
    }

    public void setParameter(String key, String value) {
        parameters.put(key, value);
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }
}
