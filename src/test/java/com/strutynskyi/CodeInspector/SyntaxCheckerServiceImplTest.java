package com.strutynskyi.CodeInspector;

import com.strutynskyi.CodeInspector.exceptions.InvalidFileFormatException;
import com.strutynskyi.CodeInspector.model.ErrorObject;
import com.strutynskyi.CodeInspector.services.impl.SyntaxCheckerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SyntaxCheckerServiceImplTest {
        private SyntaxCheckerServiceImpl syntaxCheckerService;

        @Mock
        private MultipartFile mockFile;

        @BeforeEach
        void setUp() {
            syntaxCheckerService = new SyntaxCheckerServiceImpl();
        }

        @Test
        void shouldReturnEmptyErrorListForValidCppFile() throws IOException {
            String validCppCode = "int main() { return 0; }";
            InputStream inputStream = new ByteArrayInputStream(validCppCode.getBytes());
            Mockito.when(mockFile.getOriginalFilename()).thenReturn("test.cpp");
            Mockito.when(mockFile.getInputStream()).thenReturn(inputStream);
            Mockito.when(mockFile.isEmpty()).thenReturn(false);

            List<ErrorObject> errors = syntaxCheckerService.check(mockFile);

            Assertions.assertTrue(errors.isEmpty(), "The error list should be empty for valid C++ code.");
        }

        @Test
        void shouldThrowInvalidFileFormatExceptionForNonCppFile() {
            Mockito.when(mockFile.getOriginalFilename()).thenReturn("test.txt");

            InvalidFileFormatException exception = Assertions.assertThrows(
                    InvalidFileFormatException.class,
                    () -> syntaxCheckerService.check(mockFile),
                    "Expected InvalidFileFormatException to be thrown for non-CPP files."
            );

            Assertions.assertEquals("txt", exception.getInvalidFormat(), "Invalid format should be 'txt'.");
        }

        @Test
        void shouldThrowIOExceptionForEmptyFile() throws IOException {
            Mockito.when(mockFile.getOriginalFilename()).thenReturn("empty.cpp");
            Mockito.when(mockFile.isEmpty()).thenReturn(true);

            IOException exception = Assertions.assertThrows(
                    IOException.class,
                    () -> syntaxCheckerService.check(mockFile),
                    "Expected IOException to be thrown for empty files."
            );

            Assertions.assertEquals("File is empty", exception.getMessage(), "Exception message should match.");
        }

        @Test
        void shouldReturnErrorListForInvalidCppSyntax() throws IOException {
            String invalidCppCode = "int main() { return 0;";
            InputStream inputStream = new ByteArrayInputStream(invalidCppCode.getBytes());
            Mockito.when(mockFile.getOriginalFilename()).thenReturn("invalid.cpp");
            Mockito.when(mockFile.getInputStream()).thenReturn(inputStream);
            Mockito.when(mockFile.isEmpty()).thenReturn(false);

            List<ErrorObject> errors = syntaxCheckerService.check(mockFile);

            Assertions.assertFalse(errors.isEmpty(), "The error list should not be empty for invalid C++ code.");
        }
}
