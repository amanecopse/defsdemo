package com.amnapp.defsdemo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.Entity;
import androidx.room.InvalidationTracker;
import androidx.room.PrimaryKey;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {VocanotesEntity.class}, version=1)
//@TypeConverters({RoomTypeConverter.class}) // type converter를 사용하려면 포함
public abstract class AppDatabase extends RoomDatabase {
    public abstract VocanotesDao vocanotesDao();
    private static AppDatabase mAppDatabase;

    public static AppDatabase getInstance(Context context) {//싱글톤 방식으로 한번만 객체를 만들어 필드에 저장
        if(mAppDatabase==null){
            mAppDatabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                    "AppDB").build();
        }
        return mAppDatabase;
    }
}

