package loci.traning.splitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SplitterImpl implements Splitter {
    private static final int RANDOM_BOUND = 2;

    @Override
    public List<String> splitWord(String word) {

        String temp1;
        String temp2;
        List<String> splitted = new ArrayList<>();

        temp1 = word.substring(0, randEndIndexForm(word));
        splitted.add(temp1.substring(0, randEndIndexForm(temp1)));
        splitted.add(temp1.replace(splitted.get(0), ""));
        temp2 = word.replace(temp1, "");
        splitted.add(temp2.substring(0, randEndIndexForm(temp2)));
        splitted.add(temp2.replace(splitted.get(2), ""));

        return splitted;
    }

    private int randEndIndexForm(String part) {
        if (part.length() > 2) {
            return part.length() / 2 + getPartForForParseWord();
        }
        return part.length() / 2;
    }

    private int getPartForForParseWord() {
        return new Random().nextInt(RANDOM_BOUND);
    }
}
