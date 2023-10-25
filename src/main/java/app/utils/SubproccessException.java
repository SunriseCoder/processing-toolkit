package app.utils;

import java.util.List;

public class SubproccessException extends RuntimeException {
    private static final long serialVersionUID = 6475040410078966993L;

    private String message;
    private List<String> command;
    private int exitCode;

    private String fullMessage;

    public SubproccessException(String message, List<String> command, int exitCode) {
        super();

        this.message = message;
        this.command = command;
        this.exitCode = exitCode;

        this.fullMessage = generateMessage();
    }

    private String generateMessage() {
        return message + ", command: \"" + String.join(" ", command) + "\", exitCode: " + exitCode;
    }

    @Override
    public String getMessage() {
        return fullMessage;
    }
}
