package app.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.core.dto.TrackMetadata;
import app.core.dto.VideoFileMetadata;
import app.utils.process.WaitingProcessRunner;

public class FFMpegUtils {

    public static VideoFileMetadata getVideoFileMetadata(String absolutePath) {
        WaitingProcessRunner processRunner = new WaitingProcessRunner();
        List<String> command = Arrays.asList("ffprobe", "-v", "error", "-show_streams", "\"" + absolutePath + "\"");

        int exitCode = processRunner.execute(command);
        if (exitCode != 0) {
            throw new SubproccessException("Non-Zero exit code", command, exitCode);
        }

        String processOutput = processRunner.getOutput().toString();
        VideoFileMetadata metadata = parseVideoFileMetadata(processOutput);
        return metadata;
    }

    private static VideoFileMetadata parseVideoFileMetadata(String processOutput) {
        List<TrackMetadata> tracks = new ArrayList<>();

        String[] outputLines = processOutput.split("\n");
        TrackMetadata currentTrack = null;
        for (String outputLine : outputLines) {
            if (outputLine.equals("[STREAM]")) {
                currentTrack = new TrackMetadata();
                continue;
            } else if (outputLine.equals("[/STREAM]")) {
                tracks.add(currentTrack);
                continue;
            } else {
                if (outputLine.contains("=")) {
                    int splitPosition = outputLine.indexOf("=");
                    String key = outputLine.substring(0, splitPosition);
                    String value = outputLine.substring(splitPosition + 1);
                    currentTrack.setParameter(key, value);
                } else {
                    throw new IllegalArgumentException("Error due to parse video metadata, "
                            + "parameter pair does not contain symbol \"=\"");
                }
            }
        }
        System.out.println(processOutput);

        VideoFileMetadata videoMetadata = new VideoFileMetadata();
        videoMetadata.setTracks(tracks);

        return videoMetadata;
    }
}
