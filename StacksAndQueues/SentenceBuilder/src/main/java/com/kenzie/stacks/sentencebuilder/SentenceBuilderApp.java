package com.kenzie.stacks.sentencebuilder;

public class SentenceBuilderApp {

    /**
     * Main method to interact with the SentenceBuilder Class.
     * @param args passed in from the command line. Not used.
     */
    public static void main(String[] args) {
        SentenceBuilder builder = new SentenceBuilder();

        // Add some words
        builder.addWord("Hello");
        builder.addWord("there");
        builder.addWord("my");
        builder.addWord("fiend");

        // Undo the wrong word
        System.out.println("removing: " + builder.undo());

        // add the right word
        builder.addWord("friend");

        // display the sentence words
        System.out.println(builder.getSentenceWords());
    }
}
