package loci.parser;

import loci.database.Database;
import loci.entity.Card;
import loci.exception.CustomException;
import loci.reader.CsvReader;

import java.util.List;

public class DatabaseCreator {

    private static final String PATH_TO_CATEGORYS_DATA_FILE = "src/main/resources/cards/all.txt";

    public void createDatabase() throws CustomException {
        CsvReader reader = new CsvReader();
        DataFilesParser parser = new DataFilesParser();
        Database database = Database.getDatabase();

        List<String> categoryFiles = reader.readStringsFromFile(PATH_TO_CATEGORYS_DATA_FILE);

        for (String pathToFile : categoryFiles) {
            pathToFile = pathToFile.replace("\"", "");
            List<String> data = reader.readStringsFromFile(pathToFile);
            List<Card> cards = parser.createCardsFromValidData(parser.findValidInfoStrings(data));
            String category = pathToFile.replaceAll("src/main/resources/cards/", "")
                                        .replaceAll(".txt", "");
            database.addCategory(category, cards);
        }
    }
}
