package loci.database;

import loci.entity.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Database {

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

    public void addCategory(String category, List<Card> cards) {
        if(!cards.isEmpty()) {
            this.dataMap.put(category, cards);
        }
    }

    public List<String> getCategoriesList() {
        return new ArrayList<>(dataMap.keySet());
    }

    public List<Card> getCardsByCategory(String category) {
        return this.dataMap.get(category);
    }

    /**
     * This method return size of list of objects BusStop.
     * @return size of list of stops.
     */
    public int size() {
        return this.dataMap.size();
    }

}
