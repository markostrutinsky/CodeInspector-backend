package com.strutynskyi.CodeInspector.services.impl;

import com.strutynskyi.CodeInspector.exceptions.InvalidFileFormatException;
import com.strutynskyi.CodeInspector.listeners.CustomErrorListener;
import com.strutynskyi.CodeInspector.model.ErrorObject;
import com.strutynskyi.CodeInspector.services.interfaces.SyntaxCheckerService;
import com.strutynskyi.CodeInspector.utils.Utils;
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
import java.util.Objects;

@Service
public class SyntaxCheckerServiceImpl implements SyntaxCheckerService {
    @Override
    public List<ErrorObject> check(MultipartFile cppFile) throws IOException {
        if (!Objects.requireNonNull(cppFile.getOriginalFilename()).endsWith(".cpp"))
            throw new InvalidFileFormatException(Utils.getFileFormat(cppFile.getOriginalFilename()));

        CharStream input = CharStreams.fromStream(cppFile.getInputStream());
        CPP14Lexer lexer = new CPP14Lexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CPP14Parser parser = new CPP14Parser(tokens);
        CustomErrorListener errorListener = new CustomErrorListener();
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);
        parser.translationUnit();
        return new ArrayList<>(errorListener.getErrors());
    }
}