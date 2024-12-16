package com.strutynskyi.CodeInspector.listeners;

import com.strutynskyi.CodeInspector.model.ErrorObject;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class CustomErrorListener extends BaseErrorListener {
    private final List<ErrorObject> errors = new ArrayList<>();

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                            int line, int charPositionInLine, String msg,
                            RecognitionException e) {
        errors.add(new ErrorObject(line, charPositionInLine, msg, "Syntax Error"));
    }

    public List<ErrorObject> getErrors() {
        return errors;
    }
}
