package com.example.FoodManager.entities;


import android.os.Parcel;
import android.os.Parcelable;

public class SimpleItemEntity implements Parcelable{
    private String name;
    private String brand;
    private String size;
    private String dateDue;
    private String additionalInfo;
    private int amount;

    public SimpleItemEntity(String name, String brand, String size, String additionalInfo, int amount) {
        this.name = name;
        this.brand = brand;
        this.size = size;
        this.additionalInfo = additionalInfo;
        this.amount = amount;
    }

    public SimpleItemEntity(Parcel in) {
        name = in.readString();
        brand = in.readString();
        size = in.readString();
        dateDue = in.readString();
        additionalInfo = in.readString();
        amount = in.readInt();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(brand);
        dest.writeString(size);
        dest.writeString(dateDue);
        dest.writeString(additionalInfo);
        dest.writeInt(amount);

    }

    public static final Parcelable.Creator<SimpleItemEntity> CREATOR =
            new Parcelable.Creator<SimpleItemEntity>() {
                public SimpleItemEntity createFromParcel(Parcel in) {

                    return new SimpleItemEntity(in);
                }
                @Override
                public SimpleItemEntity[] newArray(int size) {
                    return new SimpleItemEntity[size];
                }
            };

    public void setAll(String name, String brand, String size, String additionalInfo, int amount) {
        this.name = name;
        this.brand = brand;
        this.size = size;
        this.additionalInfo = additionalInfo;
        this.amount = amount;
    }

    public void setAll(SimpleItemEntity newItem) {
        this.name = newItem.name;
        this.brand = newItem.brand;
        this.size = newItem.size;
        this.additionalInfo = newItem.additionalInfo;
        this.amount = newItem.amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDateDue() {
        return dateDue;
    }

    public void setDateDue(String dateDue) {
        this.dateDue = dateDue;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
