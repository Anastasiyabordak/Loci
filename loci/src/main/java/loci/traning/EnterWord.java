package loci.traning;

import loci.entity.Card;

public class EnterWord extends Training {

    public boolean checkEnteredWord(final Card card, final  String word) {
        return (card.getWord().equalsIgnoreCase(word));
    }
}
