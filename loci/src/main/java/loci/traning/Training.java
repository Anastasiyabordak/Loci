package loci.traning;

import loci.database.Database;
import loci.entity.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Training {
    private static Database database = Database.getDatabase();
    private static List<Card> cardsQuery;
    private static String category = "";

    private Card chooseCard(final String category) {
        Random random = new Random(System.currentTimeMillis());

        int number = random.nextInt(cardsQuery.size());
        return cardsQuery.get(number);
    }

    public String chooseCategory() {
        Random random = new Random(System.currentTimeMillis());
        List<String> categories = database.getCategoriesList();

        int number = random.nextInt(categories.size());
        return categories.get(number);
    }

    private static void setCardsQueryByCategory() {
        cardsQuery = new ArrayList<>();
        if(category.equals("all")) {
            for (String categ : database.getCategoriesList()) {
                cardsQuery.addAll(database.getDataMap().get(categ));
            }

        } else {
            cardsQuery.addAll(database.getDataMap().get(category));
        }
    }

    public Card chooseCardFromCategory(final String currentCategory) {
        if (!category.equals(currentCategory) || cardsQuery.isEmpty()){
            category = currentCategory;
            setCardsQueryByCategory();
        }

        Card card = chooseCard(category);
        cardsQuery.remove(card);
        return card;
    }
}
