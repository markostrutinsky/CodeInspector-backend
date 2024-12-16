package com.strutynskyi.CodeInspector.controllers;

import com.strutynskyi.CodeInspector.model.ErrorObject;
import com.strutynskyi.CodeInspector.services.interfaces.SyntaxCheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SyntaxCheckerController {
    private final SyntaxCheckerService syntaxCheckerService;

    @Autowired
    public SyntaxCheckerController(SyntaxCheckerService syntaxCheckerService) {
        this.syntaxCheckerService = syntaxCheckerService;
    }

    @PostMapping("/check")
    public ResponseEntity<List<ErrorObject>> checkSyntax(@RequestParam("cppFile") MultipartFile cppFile) {
        List<ErrorObject> errors = syntaxCheckerService.check(cppFile);
        return ResponseEntity.ok(errors);
    }
}