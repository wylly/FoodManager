package com.example.FoodManager.entities;


import android.os.Parcel;
import android.os.Parcelable;

public class SimpleItemEntity implements Parcelable{
    private String name;
    private String brand;
    private String size;
    private String additionalInfo;
    private double amount;
    private String barcode;
    private long id;

    public SimpleItemEntity(String name, String brand, String size, String additionalInfo, double amount, long id) {
        this.name = name;
        this.brand = brand;
        this.size = size;
        this.additionalInfo = additionalInfo;
        this.amount = amount;
        this.id = id;
        this.barcode = " ";
    }
    public SimpleItemEntity(String name, String brand, String size, String additionalInfo, double amount) {
        this.name = name;
        this.brand = brand;
        this.size = size;
        this.additionalInfo = additionalInfo;
        this.amount = amount;
        this.id = id;
        this.barcode = " ";
    }

    public SimpleItemEntity(Parcel in) {
        name = in.readString();
        brand = in.readString();
        size = in.readString();
        additionalInfo = in.readString();
        amount = in.readDouble();
        id = in.readLong();
        barcode =  in.readString();
    }

    public SimpleItemEntity() {

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
        dest.writeString(additionalInfo);
        dest.writeDouble(amount);
        dest.writeLong(id);
        dest.writeString(barcode);
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

    public void setSimple(String name, String brand, String size, String additionalInfo, double amount) {
        this.name = name;
        this.brand = brand;
        this.size = size;
        this.additionalInfo = additionalInfo;
        this.amount = amount;
    }

    public void setSimple(SimpleItemEntity newItem) {
        this.name = newItem.name;
        this.brand = newItem.brand;
        this.size = newItem.size;
        this.additionalInfo = newItem.additionalInfo;
        this.amount = newItem.amount;
        this.barcode = " ";
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
