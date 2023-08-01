package com.kenzie.groupwork.kenziejavacompiler;

import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KenzieJavaFileCompilerIntegrationTest {

    private KenzieJavaFileCompiler compiler;

    @Test
    void compile_classWithHeader_returnsTrue() {
        // GIVEN
        compiler = new KenzieJavaFileCompiler(safeGetResource("ClassWithHeader.java"));

        // WHEN
        boolean result = compiler.compile();

        // THEN
        assertTrue(result, "Expected a class containing only a header to compile.");
    }

    @Test
    void compile_classWithConstructor_returnsTrue() {
        // GIVEN
        compiler = new KenzieJavaFileCompiler(safeGetResource("ClassWithConstructor.java"));

        // WHEN
        boolean result = compiler.compile();

        // THEN
        assertTrue(result, "Expected a class containing only a constructor to compile.");
    }

    @Test
    void compile_classWithMethod_returnsTrue() {
        // GIVEN
        compiler = new KenzieJavaFileCompiler(safeGetResource("ClassWithMethod.java"));

        // WHEN
        boolean result = compiler.compile();

        // THEN
        assertTrue(result, "Expected a class containing a method to compile.");
    }

    @Test
    void compile_classWithForLoop_returnsTrue() {
        // GIVEN
        compiler = new KenzieJavaFileCompiler(safeGetResource("ClassWithForLoopInMethod.java"));

        // WHEN
        boolean result = compiler.compile();

        // THEN
        assertTrue(result, "Expected a class containing a for loop to compile.");
    }

    @Test
    void compile_classWithExtraLeftBrace_returnsFalse() {
        // GIVEN
        compiler = new KenzieJavaFileCompiler(safeGetResource("InvalidClassWithExtraLeftBrace.java"));

        // WHEN
        boolean result = compiler.compile();

        // THEN
        assertFalse(result, "Expected a class containing an extra left brace to not compile.");
    }

    @Test
    void compile_classWithExtraRightBrace_returnsFalse() {
        // GIVEN
        compiler = new KenzieJavaFileCompiler(safeGetResource("InvalidClassWithExtraRightBrace.java"));

        // WHEN
        boolean result = compiler.compile();

        // THEN
        assertFalse(result, "Expected a class containing an extra right brace to not compile.");
    }

    @Test
    void compile_classWithMissingRightBrace_returnsFalse() {
        // GIVEN
        compiler = new KenzieJavaFileCompiler(safeGetResource("InvalidClassWithMissingRightBrace.java"));

        // WHEN
        boolean result = compiler.compile();

        // THEN
        assertFalse(result, "Expected a class missing a right brace to not compile.");
    }

    @Test
    void compile_classWithMissingLeftBrace_returnsFalse() {
        // GIVEN
        compiler = new KenzieJavaFileCompiler(safeGetResource("InvalidClassWithMissingLeftBrace.java"));

        // WHEN
        boolean result = compiler.compile();

        // THEN
        assertFalse(result, "Expected a class missing a left brace to not compile.");
    }

    @Test
    void compile_classWithExtraMethodBrace_returnsFalse() {
        // GIVEN
        compiler = new KenzieJavaFileCompiler(safeGetResource("InvalidClassWithExtraMethodBrace.java"));

        // WHEN
        boolean result = compiler.compile();

        // THEN
        assertFalse(result, "Expected a class with an extra method brace to not compile.");
    }

    private String safeGetResource(String filename) {
        URL url = getClass().getClassLoader().getResource(filename);

        assertNotNull(url, String.format("Could not load file %s, please ask an instructor for assistance!",
                filename));

        return url.getPath();
    }
}
