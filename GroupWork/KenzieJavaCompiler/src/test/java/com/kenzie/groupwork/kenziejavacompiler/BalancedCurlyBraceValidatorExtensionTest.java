package com.kenzie.groupwork.kenziejavacompiler;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BalancedCurlyBraceValidatorExtensionTest {
    private BalancedCurlyBraceValidator balancedCurlyBraceValidator = new BalancedCurlyBraceValidator();

    @Test
    void check_balancedSingleBraces_returnsTrue() {
        // GIVEN
        java.util.List<Character> chars = ImmutableList.of('{','}');

        // WHEN
        boolean result = balancedCurlyBraceValidator.checkExtension(chars);

        // THEN
        assertTrue(result, "Expected a single set of balanced curly braces to be valid.");
    }

    @Test
    void check_balancedNestedBraces_returnsTrue() {
        // GIVEN
        java.util.List<Character> chars = ImmutableList.of('{','{','}','}');

        // WHEN
        boolean result = balancedCurlyBraceValidator.checkExtension(chars);

        // THEN
        assertTrue(result, "Expected a nested set of balanced curly braces to be valid.");
    }

    @Test
    void check_balancedNestedBraces2_returnsTrue() {
        // GIVEN
        java.util.List<Character> chars = ImmutableList.of('{','{','}','{','}','}');

        // WHEN
        boolean result = balancedCurlyBraceValidator.checkExtension(chars);

        // THEN
        assertTrue(result, "Expected a nested set of balanced curly braces to be valid.");
    }

    @Test
    void check_noCurlyBraces_returnsTrue() {
        // GIVEN
        java.util.List<Character> chars = ImmutableList.of();

        // WHEN
        boolean result = balancedCurlyBraceValidator.checkExtension(chars);

        // THEN
        assertTrue(result, "Expected a list with no curly braces to be valid.");
    }

    @Test
    void check_imbalancedLeftBraces_returnsFalse() {
        // GIVEN
        java.util.List<Character> chars = ImmutableList.of('{','{','{', '}','}');

        // WHEN
        boolean result = balancedCurlyBraceValidator.checkExtension(chars);

        // THEN
        assertFalse(result, "Expected an extra left curly brace to be invalid.");
    }

    @Test
    void check_imbalancedRightBraces_returnsFalse() {
        // GIVEN
        java.util.List<Character> chars = ImmutableList.of('{','{','}', '}','}');

        // WHEN
        boolean result = balancedCurlyBraceValidator.checkExtension(chars);

        // THEN
        assertFalse(result, "Expected an extra right curly brace to be invalid.");
    }

    @Test
    void check_singleLeftBrace_returnsFalse() {
        // GIVEN
        java.util.List<Character> chars = ImmutableList.of('{');

        // WHEN
        boolean result = balancedCurlyBraceValidator.checkExtension(chars);

        // THEN
        assertFalse(result, "Expected a single left curly brace to be invalid.");
    }

    @Test
    void check_singleRightBrace_returnsFalse() {
        // GIVEN
        java.util.List<Character> chars = ImmutableList.of('}');

        // WHEN
        boolean result = balancedCurlyBraceValidator.checkExtension(chars);

        // THEN
        assertFalse(result, "Expected a single right curly brace to be invalid.");
    }

    @Test
    void check_rightBraceBeforeLeftBrace_returnsFalse() {
        // GIVEN
        java.util.List<Character> chars = ImmutableList.of('}', '{');

        // WHEN
        boolean result = balancedCurlyBraceValidator.checkExtension(chars);

        // THEN
        assertFalse(result, "Expected a right curly brace coming before a left curly brace to be invalid.");
    }
}
