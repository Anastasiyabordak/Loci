package loci.entity;

import java.util.Objects;

public class Card {
    private String word;
    private String definition;
    private String picturePath;

    public Card(final String word, final String definition, final String picturePath) {
        this.word = word;
        this.definition = definition;
        this.picturePath = picturePath;
    }

    public String getWord() {
        return word;
    }

    public void setWord(final String word) {
        this.word = word;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(final String definition) {
        this.definition = definition;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(final String picturePath) {
        this.picturePath = picturePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Card)) {
            return false;
        }
        Card card = (Card) o;
        return getWord().equals(card.getWord())
                && getDefinition().equals(card.getDefinition())
                && getPicturePath().equals(card.getPicturePath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWord(), getDefinition(), getPicturePath());
    }

    @Override
    public String toString() {
        return "Card{"
                + "word='" + word + '\''
                + ", definition='" + definition + '\''
                + ", picturePath='" + picturePath + '\''
                + '}';
    }
}
