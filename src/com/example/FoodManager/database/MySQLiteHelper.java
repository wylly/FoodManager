package com.example.FoodManager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.FoodManager.entities.ListEntity;
import com.example.FoodManager.entities.OwnedEntity;
import com.example.FoodManager.entities.SimpleItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SG0944783 on 2015-01-04.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_ITEMS = "items";
    public static final String COL_ITEMS_ID = "_id";
    public static final String COL_ITEM_NAME = "item_name";
    public static final String COL_ITEM_BRAND = "item_brand";
    public static final String COL_ITEM_AMOUNT = "item_amount";
    public static final String COL_ITEM_SIZE = "item_size";
    public static final String COL_ITEM_BARCODE = "item_barcode";
    public static final String COL_ITEM_OTHERS = "item_others";

    public static final String TABLE_LISTS = "lists";
    public static final String COL_LIST_ID = "_id";
    public static final String COL_LIST_NAME = "list_name";
    public static final String COL_LIST_DATE = "list_date";

    public static final String TABLE_HELPER =  "helper";
    public static final String COL_LIST_ITEM_ITEM = "itemID";
    public static final String COL_LIST_ITEM_LIST = "listID";

    public static final String TABLE_OWNED = "owned";
    public static final String COL_OWNED_ID = "_id";
    public static final String COL_OWNED_ITEMID = "itemID";
    public static final String COL_OWNED_DATE = "owned_date";

    private static final String DATABASE_NAME = "manager.db";
    private static final int DATABASE_VERSION = 1;
    private static final String LOG = "DATABASE QUERY ";

    private static final String CREATE_TABLE_ITEMS =
            "create table "
            + TABLE_ITEMS + "("
            + COL_ITEMS_ID + " integer primary key autoincrement,"
            + COL_ITEM_NAME + " text not null,"
            + COL_ITEM_BRAND + " text,"
            + COL_ITEM_AMOUNT + " real,"
            + COL_ITEM_SIZE + " text,"
            + COL_ITEM_OTHERS + " text,"
            + COL_ITEM_BARCODE + " text,"
            + ") ";

    private static final String CREATE_TABLE_LISTS =
            "create table "
            + TABLE_LISTS + "("
            + COL_LIST_ID + " integer primary key autoincrement,"
            + COL_LIST_NAME + " text,"
            + COL_LIST_DATE + " text"
            + ")";

    private static final String CREATE_TABLE_LIST_ITEM =
            "create table "
            + TABLE_HELPER + "("
            + COL_LIST_ITEM_LIST + " integer,"
            + COL_LIST_ITEM_ITEM + " integer"
            + ")";

    private static final String CREATE_TABLE_OWNED =
            "create table "
            + TABLE_OWNED + "("
            + COL_OWNED_ITEMID + " integer not null,"
            + COL_OWNED_DATE + " text"
            +")";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ITEMS);
        db.execSQL(CREATE_TABLE_LISTS);
        db.execSQL(CREATE_TABLE_LIST_ITEM);
        db.execSQL(CREATE_TABLE_OWNED);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_HELPER);
        db.execSQL("drop table if exists " + TABLE_LISTS);
        db.execSQL("drop table if exists " + TABLE_ITEMS);
        db.execSQL("drop table if exists " + TABLE_OWNED);

        onCreate(db);
    }

    //List helpers
    public long createList(ListEntity list){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_LIST_DATE,list.getDate());
        values.put(COL_LIST_NAME,list.getName());

        long list_id = db.insert(TABLE_LISTS,null,values);
        list.setId(list_id);

        createListItems(list_id, list.getItems());

        return list_id;
    }


    public ListEntity getList(long list_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery =
                "select * from " + TABLE_LISTS
                + " where " + COL_LIST_ID + " = " + list_id;

        Log.e(LOG,selectQuery);

        Cursor c = db.rawQuery(selectQuery,null);

        if(c!=null)
            c.moveToFirst();

        ListEntity list = new ListEntity();
        list.setId(c.getInt(c.getColumnIndex(COL_LIST_ID)));
        list.setDate(c.getString(c.getColumnIndex(COL_LIST_DATE)));
        list.setName(c.getString(c.getColumnIndex(COL_LIST_NAME)));
        list.setItems(getListItems(list_id));

        return list;
    }

    public List<ListEntity> getAllLists(){
        List<ListEntity> list = new ArrayList<ListEntity>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery =
                "Select * from " + TABLE_LISTS;
        Cursor c = db.rawQuery(selectQuery,null);

        Log.e(LOG,selectQuery);

        if(c.moveToFirst()){
            do{
                ListEntity listEntity = new ListEntity();
                listEntity.setId(c.getInt(c.getColumnIndex(COL_LIST_ID)));
                listEntity.setDate(c.getString(c.getColumnIndex(COL_LIST_DATE)));
                listEntity.setName(c.getString(c.getColumnIndex(COL_LIST_NAME)));
                listEntity.setItems(getListItems(listEntity.getId()));
                list.add(listEntity);
            }while(c.moveToNext());
        }

        return list;
    }

    public int updateList(ListEntity listEntity){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_LIST_NAME,listEntity.getName());
        values.put(COL_LIST_DATE,listEntity.getDate());

        if(isItemListChanged(listEntity.getId(),listEntity.getItems()))
            createListItems(listEntity.getId(),listEntity.getItems());

        return db.update(TABLE_LISTS, values, COL_LIST_ID + " = ?",
                new String[] { String.valueOf(listEntity.getId()) });
    }

    public void deleteList(long list_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        deleteListItem(list_id);
        db.delete(TABLE_LISTS, COL_LIST_ID + " = ?",
                new String[] { String.valueOf(list_id) });
    }



    //ListItem helpers
    public void createListItems(long list_id, ArrayList<SimpleItemEntity> items) {
        for(SimpleItemEntity item : items){
            createListItem(list_id,item.getId());
        }
    }

    public void createListItem(long list_id, long item_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_LIST_ITEM_LIST,list_id);
        values.put(COL_LIST_ITEM_ITEM, item_id);

        db.insert(TABLE_LISTS,null,values);
    }

    public ArrayList<SimpleItemEntity> getListItems(long list_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery =
                "select * from " + CREATE_TABLE_LIST_ITEM
                        + " where " + COL_LIST_ITEM_LIST + " = " + list_id;

        Log.e(LOG,selectQuery);

        Cursor c = db.rawQuery(selectQuery,null);
        ArrayList<SimpleItemEntity> itemsList = new ArrayList<SimpleItemEntity>();

        do{
            long itemId;
            SimpleItemEntity item = new SimpleItemEntity();

            itemId = c.getLong(c.getColumnIndex(COL_LIST_ITEM_ITEM));
            item = getItem(itemId);

            itemsList.add(item);
        }while(c.moveToNext());

        return itemsList;
    }

    public void updateListItem(ListEntity listEntity){
        deleteListItem(listEntity.getId());
        createListItems(listEntity.getId(),listEntity.getItems());
    }

    public void deleteListItem(long list_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HELPER, COL_LIST_ITEM_LIST + " = ?",
                new String[] { String.valueOf(list_id) });
    }
    public boolean isItemListChanged(long listId, ArrayList<SimpleItemEntity> item_id) {
        ArrayList<SimpleItemEntity> oldList = getListItems(listId);
        return !(oldList.containsAll(item_id) && item_id.containsAll(oldList));
    }


    //Owned helpers
    public long createOwned(long id, String date){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_OWNED_ITEMID,id);
        values.put(COL_OWNED_DATE,date);

        long owned_id = db.insert(TABLE_OWNED,null,values);

        return owned_id;
    }

    public OwnedEntity getOwned(long ownedId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery =
                "select * from " + TABLE_OWNED
                        + " where " + COL_ITEMS_ID + " = " + ownedId;

        Log.e(LOG,selectQuery);

        Cursor c = db.rawQuery(selectQuery,null);

        if(c!=null)
            c.moveToFirst();

        OwnedEntity ownedEntity = new OwnedEntity();
        ownedEntity.setId(c.getInt(c.getColumnIndex(COL_OWNED_ID)));
        ownedEntity.setItem(getItem(c.getInt(c.getColumnIndex(COL_OWNED_ITEMID))));
        ownedEntity.setDate(c.getString(c.getColumnIndex(COL_OWNED_DATE)));

        return ownedEntity;
    }

    public ArrayList<OwnedEntity> getAllOwned(){
        ArrayList<OwnedEntity> list = new ArrayList<OwnedEntity>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery =
                "Select * from " + TABLE_OWNED;
        Cursor c = db.rawQuery(selectQuery,null);

        Log.e(LOG,selectQuery);

        if(c.moveToFirst()){
            do{
                OwnedEntity ownedEntity = new OwnedEntity();
                ownedEntity.setId(c.getInt(c.getColumnIndex(COL_OWNED_ID)));
                ownedEntity.setItem(getItem(c.getInt(c.getColumnIndex(COL_OWNED_ITEMID))));
                ownedEntity.setDate(c.getString(c.getColumnIndex(COL_OWNED_DATE)));
                list.add(ownedEntity);
            }while(c.moveToNext());
        }

        return list;
    }

    public int updateOwned(OwnedEntity item){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_OWNED_ITEMID,item.getId());
        values.put(COL_OWNED_DATE,item.getDate());

        return db.update(TABLE_OWNED, values, COL_OWNED_ID + " = ?",
                new String[] { String.valueOf(item.getId()) });
    }

    public void deleteOwned(long ownedId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_OWNED, COL_OWNED_ID + " = ?",
                new String[] { String.valueOf(ownedId) });
    }

    //Item Helpers
    public long createItem(SimpleItemEntity item){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_ITEM_NAME,item.getName());
        values.put(COL_ITEM_BRAND,item.getBrand());
        values.put(COL_ITEM_AMOUNT,item.getAmount());
        values.put(COL_ITEM_SIZE,item.getSize());
        values.put(COL_ITEM_OTHERS,item.getAdditionalInfo());
        values.put(COL_ITEM_BARCODE,item.getBarcode());

        long item_id = db.insert(TABLE_ITEMS,null,values);
        item.setId(item_id);

        return item_id;
    }

    public SimpleItemEntity getItem(long itemId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery =
                "select * from " + TABLE_ITEMS
                        + " where " + COL_ITEMS_ID + " = " + itemId;

        Log.e(LOG,selectQuery);

        Cursor c = db.rawQuery(selectQuery,null);

        if(c!=null)
            c.moveToFirst();

        SimpleItemEntity simpleItemEntity = new SimpleItemEntity();
        simpleItemEntity.setId(c.getInt(c.getColumnIndex(COL_ITEMS_ID)));
        simpleItemEntity.setName(c.getString(c.getColumnIndex(COL_ITEM_NAME)));
        simpleItemEntity.setBrand(c.getString(c.getColumnIndex(COL_ITEM_BRAND)));
        simpleItemEntity.setAmount(c.getDouble(c.getColumnIndex(COL_ITEM_AMOUNT)));
        simpleItemEntity.setSize(c.getString(c.getColumnIndex(COL_ITEM_SIZE)));
        simpleItemEntity.setAdditionalInfo(c.getString(c.getColumnIndex(COL_ITEM_OTHERS)));
        simpleItemEntity.setBarcode(c.getString(c.getColumnIndex(COL_ITEM_BARCODE)));

        return simpleItemEntity;
    }

    public ArrayList<SimpleItemEntity> getAllItems(){
        ArrayList<SimpleItemEntity> list = new ArrayList<SimpleItemEntity>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery =
                "Select * from " + TABLE_ITEMS;
        Cursor c = db.rawQuery(selectQuery,null);

        Log.e(LOG,selectQuery);

        if(c.moveToFirst()){
            do{
                SimpleItemEntity simpleItemEntity = new SimpleItemEntity();
                simpleItemEntity.setId(c.getInt(c.getColumnIndex(COL_ITEMS_ID)));
                simpleItemEntity.setName(c.getString(c.getColumnIndex(COL_ITEM_NAME)));
                simpleItemEntity.setBrand(c.getString(c.getColumnIndex(COL_ITEM_BRAND)));
                simpleItemEntity.setAmount(c.getDouble(c.getColumnIndex(COL_ITEM_AMOUNT)));
                simpleItemEntity.setSize(c.getString(c.getColumnIndex(COL_ITEM_SIZE)));
                simpleItemEntity.setAdditionalInfo(c.getString(c.getColumnIndex(COL_ITEM_OTHERS)));
                simpleItemEntity.setBarcode(c.getString(c.getColumnIndex(COL_ITEM_BARCODE)));
                list.add(simpleItemEntity);
            }while(c.moveToNext());
        }

        return list;
    }

    public int updateItem(SimpleItemEntity item){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_ITEM_NAME,item.getName());
        values.put(COL_ITEM_BRAND,item.getBrand());
        values.put(COL_ITEM_AMOUNT,item.getAmount());
        values.put(COL_ITEM_SIZE,item.getSize());
        values.put(COL_ITEM_OTHERS,item.getAdditionalInfo());
        values.put(COL_ITEM_BARCODE,item.getBarcode());

        return db.update(TABLE_ITEMS, values, COL_LIST_ID + " = ?",
                new String[] { String.valueOf(item.getId()) });
    }

    public void deleteItem(long itemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEMS, COL_LIST_ID + " = ?",
                new String[] { String.valueOf(itemId) });
    }
}
