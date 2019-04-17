package loci.database;

import loci.entity.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class
Database {

    private static Database database;
    private final Map<String, List<Card>> dataMap;

    private Database() {
        this.dataMap = new HashMap<>();
    }

    public static Database getDatabase() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    public Map<String, List<Card>> getDataMap() {
        return dataMap;
    }

    /**
     * add new words category to database
     *
     * @param category is name of new category
     * @param cards is list of card of new category
     */
    public void addCategory(final String category, final List<Card> cards) {
        if (!cards.isEmpty()) {
            this.dataMap.put(category, cards);
        }
    }

    /**
     * getting list of categories existing in database
     *
     * @return list of categories names
     */
    public List<String> getCategoriesList() {
        return new ArrayList<>(dataMap.keySet());
    }

    /**
     * getting all cards of given category
     *
     * @param category of necessary cards
     * @return list of cards of given category
     */
    public List<Card> getCardsByCategory(final String category) {
        return this.dataMap.get(category);
    }
}
