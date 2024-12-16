package com.strutynskyi.CodeInspector.services.impl;

import com.strutynskyi.CodeInspector.listeners.CustomErrorListener;
import com.strutynskyi.CodeInspector.model.ErrorObject;
import com.strutynskyi.CodeInspector.services.interfaces.SyntaxCheckerService;
import cpp.CPP14Lexer;
import cpp.CPP14Parser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SyntaxCheckerServiceImpl implements SyntaxCheckerService {
    @Override
    public List<ErrorObject> check(MultipartFile cppFile) {
        List<ErrorObject> errors = new ArrayList<>();

        try {
            //Path filePath = Path.of("C:/Users/marko/Desktop/test2.cpp");
            //CharStream input = CharStreams.fromPath(Path.of(filePath.toString()));
            CharStream input = CharStreams.fromStream(cppFile.getInputStream());
            CPP14Lexer lexer = new CPP14Lexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            CPP14Parser parser = new CPP14Parser(tokens);
            CustomErrorListener errorListener = new CustomErrorListener();
            parser.removeErrorListeners();
            parser.addErrorListener(errorListener);
            parser.translationUnit();
            errors.addAll(errorListener.getErrors());
        } catch (IOException e) {
            errors.add(new ErrorObject(-1, -1, "File reading error: " + e.getMessage(), "IO Error"));
        }
        return errors;
    }
}