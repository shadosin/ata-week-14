package com.kenzie.groupwork.kenziejavacompiler;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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
       Stack<Character> stack = new Stack<>();
       for (char c: fileCharacters){
           if(c == OPEN){
            stack.push(c);
           } else if (c == CLOSE) {
            if(stack.isEmpty()){
                return false;
            }
            stack.pop();
           }
       }
       return stack.isEmpty();
    }

    /**
     * Does the above and prints out debugging information. This can be combined into one method, but separating these
     * out so we can see both the basic solution and the extension solution.
     * @param fileCharacters all characters in a java file
     * @return true if the braces are balanced, false otherwise
     */
    public boolean checkExtension(List<Character> fileCharacters) {
        // TODO: complete this method
        Stack<Character> stack = new Stack<>();
        for(int i = 0; i < fileCharacters.size(); i++){
            char c = fileCharacters.get(i);
            if (c == OPEN){
                stack.push(c);
            } else if (c == CLOSE) {
                if(stack.isEmpty()){
                    System.out.println("Unbalanced closing brace at index " + i);
                    return false;
                }
                stack.pop();
            }
        }
        if(!stack.isEmpty()){
            System.out.println("Unbalanced opening brace at index " + fileCharacters.indexOf(stack.peek()));
        }
        return stack.isEmpty();
    }

    /**
     * Use this to enable or disable additional debug messages.
     * @param debug the value to set the debug variable
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
