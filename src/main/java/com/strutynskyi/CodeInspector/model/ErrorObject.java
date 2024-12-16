package com.strutynskyi.CodeInspector.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ErrorObject {
    @JsonProperty("line")
    private int line;

    @JsonProperty("column")
    private int column;

    @JsonProperty("message")
    private String message;

    @JsonProperty("type")
    private String type;

    public ErrorObject(int line, int column, String message, String type) {
        this.line = line;
        this.column = column;
        this.message = message;
        this.type = type;
    }
}