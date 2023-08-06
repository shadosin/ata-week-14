package com.kenzie.stacks.sentencebuilder;

import java.util.Stack;

public class SentenceBuilder {
    Stack<String> sentence = new Stack<>();

    /**
     * Constructor.
     */
    public SentenceBuilder() {

    }

    /**
     * Adds a word to the sentence.
     * @param word to be added to the sentence.
     */
    public void addWord(String word) {
        sentence.push(word);
    }

    /**
     * Undoes the last word added, and returns it.
     * @return The word most recently added to the sentence, if any; null, otherwise.
     */
    public String undo() {
        if(!sentence.isEmpty()){
        return sentence.pop();}
      return null;
    }

    /**
     * Display all the current words in our sentence.
     * @return string representation of the words currently in the sentence.
     */
    public String getSentenceWords() {
        return sentence.toString();
    }
}
