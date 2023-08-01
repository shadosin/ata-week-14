package com.kenzie.stacks.sentencebuilder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SentenceBuilderTest {

    @Test
    public void getSentenceWords_whileEmpty_returnEmptyListString() {
        //GIVEN
        SentenceBuilder builder = new SentenceBuilder();

        //WHEN
        String words = builder.getSentenceWords();

        //THEN
        assertEquals("[]", words, "getSentenceWords is not returning the correct String when empty.");
    }

    @Test
    public void getSentenceWords_passedInSentence_returnCorrectSentenceString() {
        //GIVEN
        SentenceBuilder builder = new SentenceBuilder();
        builder.addWord("fish");
        builder.addWord("cakes");

        //WHEN
        String words = builder.getSentenceWords();

        //THEN
        assertEquals("[fish, cakes]", words, "getSentenceWords is not returning the correct String when empty.");
    }

    @Test
    public void addWord_addingOneWord_returnCorrectSentenceString() {
        //GIVEN
        SentenceBuilder builder = new SentenceBuilder();

        //WHEN
        builder.addWord("This");

        //THEN
        String words = builder.getSentenceWords();
        assertEquals("[This]", words, "getSentenceWords is not returning the correct String after a " +
                "call to addWord.");
    }

    @Test
    public void undo_whileEmpty_returnNull() {
        //GIVEN
        SentenceBuilder builder = new SentenceBuilder();

        //WHEN
        String word = builder.undo();

        //THEN
        assertNull(word, "Expected undo() to return null when builder is empty, but returned: " + word);
    }

    @Test
    public void undo_passedOneWord_returnCorrectWord() {
        //GIVEN
        SentenceBuilder builder = new SentenceBuilder();
        builder.addWord("fish");

        //WHEN
        String word = builder.undo();

        //THEN
        assertEquals("fish", word, "Should return top value of the stack that was passed into the " +
                "constructor.");
    }

    @Test
    public void getSentenceWord_passedOneWordThenUndo_returnEmptyListString() {
        //GIVEN
        SentenceBuilder builder = new SentenceBuilder();
        builder.addWord("fish");

        //WHEN
        builder.undo();
        String words = builder.getSentenceWords();

        //THEN
        assertEquals("[]", words, "getSentenceWords is not returning the correct String after a " +
                "call to undo.");
    }

    @Test
    public void addWord_addingFourWords_correctSentenceWords() {
        //GIVEN
        SentenceBuilder builder = new SentenceBuilder();

        //WHEN
        builder.addWord("This");
        builder.addWord("is");
        builder.addWord("a");
        builder.addWord("test");

        //THEN
        String words = builder.getSentenceWords();
        assertEquals("[This, is, a, test]", words, "The sentence did not get built " +
                "correctly.");
    }

    @Test
    public void undo_undoInTheMiddleOfSentence_returnsCorrectSentenceWords() {
        //GIVEN
        SentenceBuilder builder = new SentenceBuilder();

        //WHEN
        builder.addWord("This");
        builder.addWord("is");
        builder.addWord("not");
        builder.undo();
        builder.addWord("a");
        builder.addWord("test");

        //THEN
        String words = builder.getSentenceWords();
        assertEquals("[This, is, a, test]", words, "The builder did not perform an undo " +
                "correctly.");
    }

    @Test
    public void undo_severalWords_returnCorrectThirdWord() {
        //GIVEN
        SentenceBuilder builder = new SentenceBuilder();

        //WHEN
        builder.addWord("These");
        builder.addWord("fish");
        builder.addWord("cakes");
        builder.addWord("rule");
        builder.undo();

        String thirdWord = builder.undo();

        //THEN
        assertEquals("cakes", thirdWord, "The builder did not perform multiple undos " +
                "correctly.");
    }


}
