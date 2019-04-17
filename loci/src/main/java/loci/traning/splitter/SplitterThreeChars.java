package loci.traning.splitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SplitterThreeChars implements Splitter {
    private static final int RANDOM_BOUND = 2;

    @Override
    public List<String> splitWord(final String word) {
        int beginIndex = getPartForForParseWord();
        int endIndex = beginIndex + 1;

        List<String> splitted = new ArrayList<>();
        splitted.add(word.substring(beginIndex, endIndex + 1));
        splitted.add(word.replaceFirst(splitted.get(0), ""));
        if (beginIndex > 0) {
            return makeRightSyllabSquence(splitted);
        } else {
            return splitted;
        }
    }

    /**
     * placing syllables in proper order
     *
     * @param splitted list of word parts
     * @return lis tof word parts in proper order
     */
    private List<String> makeRightSyllabSquence(final List<String> splitted) {
        List<String> rightSplit = new ArrayList<>();
        rightSplit.add(splitted.get(1));
        rightSplit.add(splitted.get(0));
        return rightSplit;
    }

    /**
     * randomly generating 0 or 1
     *
     * @return 0 or 1
     */
    private int getPartForForParseWord() {
        return new Random().nextInt(RANDOM_BOUND);
    }
}
