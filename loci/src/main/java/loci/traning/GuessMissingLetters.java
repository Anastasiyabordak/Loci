package loci.traning;

import java.util.Arrays;
import java.util.Random;

public class GuessMissingLetters extends Training{

    /**
     * converts half of words letters to '*'
     *
     * @return word with changed letters
     */
    public String hideLetters(String word) {
        char[] output = word.toCharArray();
        int bound = word.length();
        int numberOfHides = bound / 2;

        for (int i = 0; i < numberOfHides; i++) {
            int pos, count = 0;
            do {
                pos = getLetterPosition(bound);
                count++;
            } while ( output[pos] == '*' && count < 10);

            output[pos] = '*';
        }

        return String.valueOf(output);
    }

    /**
     * randomly generating number from 0 to word length
     *
     * @return random number in bound
     */
    private int getLetterPosition(int bound) {
        return new Random().nextInt(bound);
    }
}
