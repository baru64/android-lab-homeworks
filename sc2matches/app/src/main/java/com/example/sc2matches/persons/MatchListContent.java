package com.example.sc2matches.persons;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.sc2matches.MainActivity;

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
public class MatchListContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Match> ITEMS = new ArrayList<Match>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Match> ITEM_MAP = new HashMap<String, Match>();

    private static final int COUNT = 3;

    static {
//        for (int i = 1; i <= COUNT; i++) {
//            addItem(createDummyItem(i));
//        }
    }

    public static void addItem(Match item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static Match createDummyItem(int position) {
        return new Match(String.valueOf(position), "WCS Summer 2019 ro32", "2019-07-14",
                "Elazer", "Zerg", 101,
                "Mana", "Protoss", 201,
                "3-2");
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about this person: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("Aaa Bbb");
        }
        return builder.toString();
    }

    public static void removeItem(int position) {
        String itemId = ITEMS.get(position).id;
        ITEMS.remove(position);
        ITEM_MAP.remove(itemId);

    }

    public static void clearList(){
        ITEMS.clear();
        ITEM_MAP.clear();
    }

    /**
     * TODO person -> match
     */
    public static class Match implements Parcelable {
        public final String id;
        public final String event;
        public final String date;
        public final String p1_name;
        public final String p1_race;
        public final int p1_id;
        public final String p2_name;
        public final String p2_race;
        public final int p2_id;
        public final String score;

        public Match(String id, String event, String date,
                     String p1_name, String p1_race, int p1_id,
                     String p2_name, String p2_race, int p2_id,
                     String score) {
            this.id = id;
            this.event = event;
            this.date = date;
            this.p1_id = p1_id;
            this.p1_name = p1_name;
            this.p1_race = p1_race;
            this.p2_id = p2_id;
            this.p2_name = p2_name;
            this.p2_race = p2_race;
            this.score = score;
        }


        protected Match(Parcel in) {
            id = in.readString();
            event = in.readString();
            date = in.readString();
            p1_id = in.readInt();
            p1_name = in.readString();
            p1_race = in.readString();
            p2_id = in.readInt();
            p2_name = in.readString();
            p2_race = in.readString();
            score = in.readString();
        }

        public static final Creator<Match> CREATOR = new Creator<Match>() {
            @Override
            public Match createFromParcel(Parcel in) {
                return new Match(in);
            }

            @Override
            public Match[] newArray(int size) {
                return new Match[size];
            }
        };

        @Override
        public String toString() {
            return event;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(event);
            dest.writeString(date);
            dest.writeInt(p1_id);
            dest.writeString(p1_name);
            dest.writeString(p1_race);
            dest.writeInt(p2_id);
            dest.writeString(p2_name);
            dest.writeString(p2_race);
            dest.writeString(score);
        }
    }

//    public static class Player {
//        public final String nickname;
//        public final char race;
//        public final String country;
//        public final int id;
//
//        public Player(String nickname, char race, String country, int id) {
//            this.nickname = nickname;
//            this.race = race;
//            this.country = country;
//            this.id = id;
//        }
//    }
}
