package loci.parser;

import loci.entity.Card;
import loci.validator.DataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class DataFilesParser {

    private static final String SPLIT_CHAR = "\",\"";
    private static final int PICTURE_PATH_POSITION = 2;
    private static final int WORD_POSITION = 0;
    private static final int DEFINITION_POSITION = 1;

    /**
     * filtering valid data strings from file
     *
     * @param fileLines all strings read from file
     * @return list of valid data strings
     */
    List<String[]> findValidInfoStrings(final List<String> fileLines) {
        Logger logger = LogManager.getLogger();

        DataValidator validator = new DataValidator();
        List<String[]> validDataStrings = new ArrayList<>();

        for (String input : fileLines) {
            String[] data = input.trim().split(SPLIT_CHAR);
            for (int i = 0; i < data.length; i++) {
                data[i] = data[i].replace("\"", "");
            }
            if (validator.rightNumberOfParams(data)
                    && validator.isEmptyParam(data)
                    && validator.isValidPicture(data[PICTURE_PATH_POSITION])) {

                validDataStrings.add(data);
            } else {
                logger.log(Level.INFO, "String <<"
                        + input
                        + ">> contains invalid data\n");
            }
        }
        return validDataStrings;
    }

    /**
     * creating Card entities from valid data from file
     *
     * @param validDataStrings string with data for card
     * @return
     */
    List<Card> createCardsFromValidData(
            final List<String[]> validDataStrings) {
        List<Card> cards = new ArrayList<>();
        for (String[] data : validDataStrings) {
            cards.add(new Card(data[WORD_POSITION],
                    data[DEFINITION_POSITION],
                    data[PICTURE_PATH_POSITION]));
        }
        return cards;
    }
}
