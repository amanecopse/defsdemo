package com.amnapp.defsdemo;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Entity(tableName = "vocanotes")
public class VocanotesEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int _id;
    @NonNull
    @ColumnInfo(name = "headword")
    private String headWord;
    @ColumnInfo(name = "relatedWord")
    private String relatedWord;
    @ColumnInfo(name = "meaning")
    private String meaning;
    @ColumnInfo(name = "exampleSentence")
    private String exampleSentence;
    @ColumnInfo(name = "otherMemo")
    private String otherMemo;

    public VocanotesEntity(String headWord, String relatedWord, String meaning, String exampleSentence, String otherMemo) {
        this.headWord = headWord;
        this.relatedWord = relatedWord;
        this.meaning = meaning;
        this.exampleSentence = exampleSentence;
        this.otherMemo = otherMemo;
    }

    public int get_id() {
        return _id;
    }

    public String getHeadWord() {
        return headWord;
    }

    public String getRelatedWord() {
        return relatedWord;
    }

    public String getMeaning() {
        return meaning;
    }

    public String getExampleSentence() {
        return exampleSentence;
    }

    public String getOtherMemo() {
        return otherMemo;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setHeadWord(String headWord) {
        this.headWord = headWord;
    }

    public void setRelatedWord(String relatedWord) {
        this.relatedWord = relatedWord;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public void setExampleSentence(String exampleSentence) {
        this.exampleSentence = exampleSentence;
    }

    public void setOtherMemo(String otherMemo) {
        this.otherMemo = otherMemo;
    }
}

