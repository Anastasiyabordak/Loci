package loci.traning;

import loci.entity.Card;

public class EnterWord extends Training {

    public boolean checkEnteredWord(Card card, String word){
        return (card.getWord().equals(word));
    }
}