package loci.entity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import loci.database.Database;

import java.util.List;

public class Desk {

    private Database database = Database.getDatabase();

    public ObservableList<Card> setCardsList() {
        ObservableList<Card> cardData = FXCollections.observableArrayList();
        List<String> categories = database.getCategoriesList();

        for (String category : categories) {
            List<Card> cards = database.getCardsByCategory(category);
            cardData.addAll(cards);
        }

        return cardData;
    }

    public ObservableList<Card> setCardsList(final String category) {
        ObservableList<Card> cardData = FXCollections.observableArrayList();
        List<Card> cards = database.getCardsByCategory(category);

        cardData.addAll(cards);

        return cardData;
    }

    public ObservableList<String> setCategoryList() {
        ObservableList<String> categoryData = FXCollections.observableArrayList();
        List<String> categories = database.getCategoriesList();

        categoryData.add("all");
        categoryData.addAll(categories);
        return categoryData;
    }
}
