package loci.traning;

import loci.entity.Card;

public class EnterWord extends Training {

    /**
     * check if the entered word equals the cerds one
     *
     * @param card containing the right answer word
     * @param word entered one
     * @return true is words equals
     */
    public boolean checkEnteredWord(final Card card, final  String word) {
        return (card.getWord().equalsIgnoreCase(word));
    }
}
