package loci.desk;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import loci.database.Database;
import loci.entity.Card;

import java.util.List;

public class Desk {

    private Database database = Database.getDatabase();

    public ObservableList<Card> setCardsList() {
        ObservableList<Card> cardData = FXCollections.observableArrayList();
        List<String> categories = database.getCategoriesList();

        for (int i = 0; i < categories.size(); i++)
        {
            String category = categories.get(i);
            List<Card> cards = database.getCardsByCategory(category);
            for (int j = 0; j < cards.size(); j++)
            {
                Card card = cards.get(j);
                cardData.add(card);
            }
        }

        return cardData;
    }

    public ObservableList<Card>  setCardsList(String category)
    {
        ObservableList<Card> cardData = FXCollections.observableArrayList();
        List<Card> cards = database.getCardsByCategory(category);

        for (int i = 0; i < cards.size(); i++)
        {
            Card card = cards.get(i);
            cardData.add(card);
        }

        return cardData;
    }

    public ObservableList<String>  setCategoryList()
    {
        ObservableList<String> categoryData = FXCollections.observableArrayList();
        List<String> categories = database.getCategoriesList();

        categoryData.add("all");

        for (int i = 0; i < categories.size(); i++)
        {
            String category = categories.get(i);
            categoryData.add(category);
        }

        return categoryData;
    }
}
