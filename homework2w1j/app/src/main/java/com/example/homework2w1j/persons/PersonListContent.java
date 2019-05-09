package com.example.homework2w1j.persons;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.homework2w1j.R;

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
public class PersonListContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Person> ITEMS = new ArrayList<Person>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Person> ITEM_MAP = new HashMap<String, Person>();

    private static final int COUNT = 3;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    public static void addItem(Person item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static Person createDummyItem(int position) {
        return new Person(String.valueOf(position), "Johnny Testowy " + position, makeDetails(position),
                "2000-01-01", "drawable " + (position % 4));
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
     * A dummy item representing a piece of content.
     */
    public static class Person implements Parcelable {
        public final String id;
        public final String name;
        public final String description;
        public final String birthday;
        public String picPath;

        public Person(String id, String name, String description, String birthday) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.birthday = birthday;
            this.picPath = "";
        }

        public Person(String id, String name, String description, String birthday, String picPath) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.birthday = birthday;
            this.picPath = picPath;
        }

        protected Person(Parcel in) {
            id = in.readString();
            name = in.readString();
            description = in.readString();
            birthday = in.readString();
            picPath = in.readString();
        }

        public static final Creator<Person> CREATOR = new Creator<Person>() {
            @Override
            public Person createFromParcel(Parcel in) {
                return new Person(in);
            }

            @Override
            public Person[] newArray(int size) {
                return new Person[size];
            }
        };

        public void setPicPath(String path){
            this.picPath = path;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(name);
            dest.writeString(description);
            dest.writeString(birthday);
            dest.writeString(picPath);
        }
    }
}
