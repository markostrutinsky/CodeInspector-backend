package com.strutynskyi.CodeInspector.services.interfaces;

import com.strutynskyi.CodeInspector.model.ErrorObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SyntaxCheckerService {
    List<ErrorObject> check(MultipartFile cppFile) throws IOException;
}
