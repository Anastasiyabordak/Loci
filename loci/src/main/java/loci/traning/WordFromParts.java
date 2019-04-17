package loci.traning;

import loci.entity.Card;
import loci.traning.splitter.Splitter;
import loci.traning.splitter.SplitterImpl;
import loci.traning.splitter.SplitterThreeChars;
import loci.traning.splitter.SplitterTwoOrFourChars;

import java.util.Collections;
import java.util.List;

public class WordFromParts extends Training {

    private static final int WORD_FROM_TWO_LETTERS = 2;
    private static final int WORD_FROM_THREE_LETTERS = 3;
    private static final int WORD_FROM_FOUR_LETTERS = 4;

    /**
     * checking if is possible to split word
     *
     * @param card with word to be splitted
     * @return true if possible to split word
     */
    public boolean validToSplit(final Card card) {
        return card.getWord().length() >= 1;
    }

    /**
     * calling all stuff to split word
     *
     * @param card with word to be splitted
     * @return list of word parts
     */
    public List<String> wordSplit(final Card card) {
        return splitterSwitch(card.getWord());
    }

    /**
     * choosing type of splitter class depending on word length
     *
     * @param word to be splitted
     * @return list of word parts
     */
    private List<String> splitterSwitch(final String word) {
        Splitter splitter;

        switch (word.length()) {
            case WORD_FROM_TWO_LETTERS:
            case WORD_FROM_FOUR_LETTERS:
                splitter = new SplitterTwoOrFourChars();
                break;
            case WORD_FROM_THREE_LETTERS:
                splitter = new SplitterThreeChars();
                break;
            default:
                splitter = new SplitterImpl();
        }

        return splitter.splitWord(word);
    }

    /**
     * mixing order of word parts in list
     *
     * @param splitted list of word parts
     */
    public void mixWordParts(final List splitted) {
        Collections.shuffle(splitted);
    }
}
