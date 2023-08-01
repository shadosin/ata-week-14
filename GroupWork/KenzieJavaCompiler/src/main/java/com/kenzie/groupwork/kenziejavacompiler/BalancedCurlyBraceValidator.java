package com.kenzie.groupwork.kenziejavacompiler;

import java.util.List;
import java.util.Stack;

/**
 * Compiler check that ensures the curly braces in a file are evenly matched. The file must contain an even number of
 * opening and closing curly braces. This check does not perform any validation that the curly braces are in legal
 * locations in the file. [NOTE] Your implementation does not need to handle escaped curly braces within strings
 * literals.
 */
public class BalancedCurlyBraceValidator {

    private static final char OPEN = '{';
    private static final char CLOSE = '}';
    private boolean debug = false;

    /**
     * Validates that the curly braces in the list of provided file characters are balanced.
     * @param fileCharacters all characters in a java file
     * @return true if the braces are balanced, false otherwise
     */
    public boolean check(List<Character> fileCharacters) {
        // TODO: complete this method
        return false;
    }

    /**
     * Does the above and prints out debugging information. This can be combined into one method, but separating these
     * out so we can see both the basic solution and the extension solution.
     * @param fileCharacters all characters in a java file
     * @return true if the braces are balanced, false otherwise
     */
    public boolean checkExtension(List<Character> fileCharacters) {
        // TODO: complete this method
        return false;
    }

    /**
     * Use this to enable or disable additional debug messages.
     * @param debug the value to set the debug variable
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
