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

    /**
     * choose one card from list randomly
     *
     * @return chosen card
     */
    private Card chooseCard() {
        Random random = new Random(System.currentTimeMillis());

        int number = random.nextInt(cardsQuery.size());
        return cardsQuery.get(number);
    }

    /**
     * choose one category from list randomly
     *
     * @return chosen category name
     */
    public String chooseCategory() {
        Random random = new Random(System.currentTimeMillis());
        List<String> categories = database.getCategoriesList();

        int number = random.nextInt(categories.size());
        return categories.get(number);
    }

    /**
     * copping list of cards of current category to cardsQuery fields
     */
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

    /**
     * choosing card from cardsQuery field, deleting this card from cardsQuery
     * and establishing new category if necessary
     *
     * @param newCategory chosen by user
     * @return randomly generated card
     */
    public Card chooseCardFromCategory(final String newCategory) {
        if (!category.equals(newCategory) || cardsQuery.isEmpty()){
            category = newCategory;
            setCardsQueryByCategory();
        }

        Card card = chooseCard();
        cardsQuery.remove(card);
        return card;
    }
}
