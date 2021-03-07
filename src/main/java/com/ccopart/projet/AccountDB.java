package com.ccopart.projet;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SupportFactory;


@Database(entities = {Account.class}, version = 2)
public abstract class AccountDB extends RoomDatabase {
    private static AccountDB instance;
    public abstract AccountDao accountDao();

    public static synchronized AccountDB getInstance(Context context) {
        String psw = new String(Base64.decode(context.getString(R.string.db_psw),Base64.DEFAULT));
        final byte[] passphrase = psw.getBytes();
        final SupportFactory factory = new SupportFactory(passphrase);
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AccountDB.class, "account_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .openHelperFactory(factory)
                    .build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

}
