package loci.traning;

import loci.entity.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChooseOneOfFour extends Training {

    private static final int NUMBER_OF_WORDS_REQUIRED = 4;

    /**
     * getting list of 4 word to choose only 1 right from
     *
     * @param card containing the word to be trained
     * @return list of 4 words
     */
    public List<String> getListOfWords(final Card card) {
        List<String> words = new ArrayList<>();
        words.add(card.getWord());
        while (words.size() < NUMBER_OF_WORDS_REQUIRED) {
            String word = this.chooseCardFromCategory(this.chooseCategory()).getWord();
            if (!words.contains(word)) {
                words.add(word);
            }
            Collections.shuffle(words);
        }
        return words;
    }
}
