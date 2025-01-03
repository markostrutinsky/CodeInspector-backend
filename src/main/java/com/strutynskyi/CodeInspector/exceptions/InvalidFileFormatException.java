package com.strutynskyi.CodeInspector.exceptions;

public class InvalidFileFormatException extends RuntimeException {
    private final static String messageStart = "Invalid file format: .";
    private final static String messageEnd = ". Expected format: .cpp";
    private String fileFormat;
    public InvalidFileFormatException(String fileFormat) {
        super(messageStart.concat(fileFormat).concat(messageEnd));
        this.fileFormat = fileFormat;
    }

    public String getInvalidFormat() {
        return fileFormat;
    }
}
