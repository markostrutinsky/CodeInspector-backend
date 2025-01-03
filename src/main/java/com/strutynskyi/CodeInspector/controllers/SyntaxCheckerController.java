package com.strutynskyi.CodeInspector.controllers;

import com.strutynskyi.CodeInspector.model.ErrorObject;
import com.strutynskyi.CodeInspector.services.interfaces.SyntaxCheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173/")
public class SyntaxCheckerController {
    private final SyntaxCheckerService syntaxCheckerService;

    @Autowired
    public SyntaxCheckerController(SyntaxCheckerService syntaxCheckerService) {
        this.syntaxCheckerService = syntaxCheckerService;
    }

    @PostMapping("/check")
    public ResponseEntity<List<ErrorObject>> checkSyntax(@RequestParam(value = "cppFile") MultipartFile cppFile) throws IOException {
        List<ErrorObject> errors = syntaxCheckerService.check(cppFile);
        return ResponseEntity.ok(errors);
    }
}