package loci.traning;

import loci.database.Database;
import loci.entity.Card;

import java.util.List;
import java.util.Random;

public class Training {
    private Database database = Database.getDatabase();

    public Card chooseCard(String category) {
        Random random = new Random(System.currentTimeMillis());
        List<Card> cards = database.getCardsByCategory(category);

        int number = random.nextInt(cards.size());
        return cards.get(number);
    }

    public String chooseCategory() {
        Random random = new Random(System.currentTimeMillis());
        List<String> categories = database.getCategoriesList();

        int number = random.nextInt(categories.size());
        return categories.get(number);
    }
}
