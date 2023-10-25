package app.core.dto;

import java.util.List;

public class VideoFileMetadata {
    private List<TrackMetadata> tracks;

    public List<TrackMetadata> getTracks() {
        return tracks;
    }

    public void setTracks(List<TrackMetadata> tracks) {
        this.tracks = tracks;
    }
}
