package loci.traning.splitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SplitterThreeChars implements Splitter {
    private static final int RANDOM_BOUND = 2;

    @Override
    public List<String> splitWord(String word) {
        int beginIndex = getPartForForParseWord();
        int endIndex = beginIndex +1;

        List<String> splitted = new ArrayList<>();
        splitted.add(word.substring(beginIndex, endIndex));
        splitted.add(word.replaceFirst(splitted.get(0), ""));
        return splitted;
    }

    private int getPartForForParseWord() {
        return new Random().nextInt(RANDOM_BOUND);
    }
}
