package loci.traning;

import loci.entity.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChooseOneOfFour extends Training{

    private static final int NUMBER_OF_WORDS_REQUIRED = 4;

    public List<String> getListOfWords(Card card) {
        List<String> words = new ArrayList<>();
        words.add(card.getWord());
        while (words.size() < NUMBER_OF_WORDS_REQUIRED) {
            String word = this.chooseCard(this.chooseCategory()).getWord();
            if (!words.contains(word)) {
                words.add(word);
            }
            Collections.shuffle(words);
        }
        return words;
    }
}
