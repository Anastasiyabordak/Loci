package loci.traning.splitter;

import java.util.ArrayList;
import java.util.List;

public class SplitterTwoOrFourChars implements Splitter {

    @Override
    public List<String> splitWord(String word) {
        List<String> splitted = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            splitted.add(String.valueOf(word.charAt(i)));
        }
        return splitted;
    }
}
