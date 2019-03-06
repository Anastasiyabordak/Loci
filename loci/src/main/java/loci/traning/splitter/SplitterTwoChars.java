package loci.traning.splitter;

import java.util.ArrayList;
import java.util.List;

public class SplitterTwoChars implements Splitter {

    @Override
    public List<String> splitWord(String word) {
        List<String> splitted = new ArrayList<>();
        splitted.add(String.valueOf(word.charAt(0)));
        splitted.add(String.valueOf(word.charAt(1)));
        return splitted;
    }
}
