package loci.traning.splitter;

import java.util.List;

public interface Splitter {

    /**
     * splitting word necessary quantity of parts
     *
     * @param word to be splitted
     * @return list of word parts
     */
    List<String> splitWord(String word);
}
