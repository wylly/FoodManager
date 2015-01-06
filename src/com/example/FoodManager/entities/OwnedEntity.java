package com.example.FoodManager.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SG0944783 on 2015-01-05.
 */
public class OwnedEntity implements Parcelable {

    private long id;
    private SimpleItemEntity item;
    private String date;

    public OwnedEntity() {
    }

    public OwnedEntity(SimpleItemEntity item, String date) {
        this.item = item;
        this.date = date;
    }

    public OwnedEntity(long id, SimpleItemEntity item, String date) {
        this.id = id;
        this.item = item;
        this.date = date;
    }

    public OwnedEntity(Parcel in) {
        this.id = in.readLong();
        this.item = SimpleItemEntity.CREATOR.createFromParcel(in);
        this.date = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeValue(item);
        dest.writeString(date);
    }

    public static final Parcelable.Creator<OwnedEntity> CREATOR =
            new Parcelable.Creator<OwnedEntity>() {
                public OwnedEntity createFromParcel(Parcel in) {

                    return new OwnedEntity(in);
                }
                
                @Override
                public OwnedEntity[] newArray(int size) {
                    return new OwnedEntity[size];
                }
            };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SimpleItemEntity getItem() {
        return item;
    }

    public void setItem(SimpleItemEntity item) {
        this.item = item;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
