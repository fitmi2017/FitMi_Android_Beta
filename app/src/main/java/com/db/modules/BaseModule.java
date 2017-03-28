package com.db.modules;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.db.DatabaseHelper;

import java.io.IOException;

public class BaseModule {

    DatabaseHelper databaseObject;
    Context ctx;
    SQLiteDatabase db;
    SQLiteDatabase readDb;

    public BaseModule(Context ctx) {
        this.ctx = ctx;
        initDb();
    }

    public void initDb() {
        databaseObject = new DatabaseHelper(ctx);
        try {

            databaseObject.createDatabase();
            databaseObject.openDatabase();

        } catch (IOException e) {
            e.printStackTrace();
        }
        db = databaseObject.getWritableDatabase();
        readDb = databaseObject.getReadableDatabase();
    }
}
