package loci.traning;

import loci.entity.Card;
import loci.traning.splitter.Splitter;
import loci.traning.splitter.SplitterImpl;
import loci.traning.splitter.SplitterThreeChars;
import loci.traning.splitter.SplitterTwoChars;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class WordFromParts extends Traning {

    private static final int WORD_FROM_TWO_LETTERS = 2;
    private static final int WORD_FROM_THREE_LETTERS = 3;

    public boolean validToSplit(Card card) {
        return card.getWord().length() >= 1;
    }

    public List<String> wordSplit(Card card) {
        return splitterSwitch(card.getWord());
    }

    private List<String> splitterSwitch(String word) {
        Splitter splitter;

        switch (word.length()) {
            case WORD_FROM_TWO_LETTERS:
                splitter = new SplitterTwoChars();
                break;
            case WORD_FROM_THREE_LETTERS:
                splitter = new SplitterThreeChars();
                break;
            default:
                splitter = new SplitterImpl();
        }

        return splitter.splitWord(word);
    }

    public void mixWordParts(List splitted) {
         Collections.shuffle(splitted);
    }
}