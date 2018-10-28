//package com.example.android.popularmovies.data;
//
//import android.arch.persistence.room.ColumnInfo;
//import android.arch.persistence.room.Entity;
//import android.arch.persistence.room.PrimaryKey;
//import android.support.annotation.NonNull;
//
//import java.io.Serializable;
//
//@Entity(tableName = "favorites_table")
//public class FavoriteMovie implements Serializable {
//
//    @PrimaryKey
//    @NonNull
//    @ColumnInfo(name = "movie_id")
//    private int mId;
//
//    @NonNull
//    @ColumnInfo(name = "movie_title")
//    private String mTitle;
//
//    public FavoriteMovie(@NonNull int id, @NonNull String title) {
//        this.mId = id;
//        this.mTitle = title;
//    }
//
//    public int getId() { return this.mId; }
//
//    public String getTitle() { return this.mTitle; }
//}
