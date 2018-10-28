package com.example.android.popularmovies.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.android.popularmovies.models.Movie;

@Database(entities = {Movie.class}, version = 1)
public abstract class FavoritesRoomDatabase extends RoomDatabase {

    public abstract FavoritesDao favoriteMovieDao();

    private static volatile FavoritesRoomDatabase INSTANCE;

    static FavoritesRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FavoritesRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create a database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FavoritesRoomDatabase.class, "favorites_table")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
