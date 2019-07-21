package com.example.sc2matches.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class topPlayerListContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<playerItem> ITEMS = new ArrayList<playerItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, playerItem> ITEM_MAP = new HashMap<String, playerItem>();

    private static final int COUNT = 15;

    static {
//         Add some sample items.
//        for (int i = 1; i <= COUNT; i++) {
//            addItem(createDummyItem(i));
//        }
    }

    public static void addItem(playerItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static playerItem createDummyItem(int position) {
        return new playerItem(
                String.valueOf(position),
                "Item " + position,
                "Zerg",
                9000,
                "PL");
    }

//    private static String makeDetails(int position) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("Details about Item: ").append(position);
//        for (int i = 0; i < position; i++) {
//            builder.append("\nMore details information here.");
//        }
//        return builder.toString();
//    }

    public static class playerItem {
        public final String id;
        public final String name;
        public final String race;
        public final int rating;
        public final String country;

        public playerItem(String id, String name, String race, int rating, String country) {
            this.id = id;
            this.name = name;
            this.race = race;
            this.rating = rating;
            this.country = country;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
