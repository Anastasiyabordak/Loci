package loci.validator;

import java.nio.file.Paths;

public class DataValidator {

    private static final int NUMBER_OF_PARAMS_FOR_CARD = 3;

    /**
     * checks if number of parameters for card.
     */
    public boolean rightNumberOfParams(final String[] data) {
        return (data.length == NUMBER_OF_PARAMS_FOR_CARD);
    }

    /**
     * checks if data stringarray is nor empty.
     */
    public boolean isEmptyParam(final String[] data) {
        for (String param : data) {
            if(param.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * checks if picture path is valid.
     */
    public boolean isValidPicture(final String path) {
        return Paths.get(path).toFile().exists();
    }
}
