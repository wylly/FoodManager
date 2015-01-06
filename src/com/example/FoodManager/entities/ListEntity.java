package com.example.FoodManager.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


public class ListEntity implements Parcelable {

    private long id;
    private String name;
    private ArrayList<SimpleItemEntity> items;
    private String date;


    public ListEntity() {
    }

    public ListEntity(long id){
        this.id = id;
    }

    public ListEntity(long id, String name, ArrayList<SimpleItemEntity> items, String date) {
        this.name = name;
        this.id = id;
        this.items = new ArrayList<SimpleItemEntity>(items);
        this.date = date;
    }

    public ListEntity(ListEntity list){
        this.id = list.getId();
        this.name = list.getName();
        this.items = new ArrayList<SimpleItemEntity>(list.getItems());
        this.date = list.getDate();
    }

    public ListEntity(Parcel in){
        this.id = in.readLong();
        this.name = in.readString();
        this.items = new ArrayList<SimpleItemEntity>(in.readArrayList(null));
        this.date = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeList(items);
        dest.writeString(date);
    }

    public static final Parcelable.Creator<ListEntity> CREATOR =
            new Parcelable.Creator<ListEntity>() {
                public ListEntity createFromParcel(Parcel in) {

                    return new ListEntity(in);
                }
                @Override
                public ListEntity[] newArray(int size) {
                    return new ListEntity[size];
                }
            };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<SimpleItemEntity> getItems() {
        return items;
    }

    public void setItems(ArrayList<SimpleItemEntity> items) {
        this.items = items;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
