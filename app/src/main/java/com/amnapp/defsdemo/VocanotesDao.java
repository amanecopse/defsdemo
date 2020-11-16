package com.amnapp.defsdemo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface VocanotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(VocanotesEntity vocanotes);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    List<Long> insertAll(List<VocanotesEntity> vocanotes);

    @Delete
    void delete(VocanotesEntity vocanotes);

    @Update
    void update(VocanotesEntity vocanotes);

    @Query("SELECT * FROM vocanotes ORDER BY _id DESC")
    List<VocanotesEntity> loadAllVocanotes();

    @Query("SELECT * FROM vocanotes where _id = :id")
    VocanotesEntity loadVocanotesById(int id);

    @Query("SELECT * FROM vocanotes where headword LIKE :searchWord" +
                                    " OR relatedWord LIKE :searchWord"+
                                    " OR meaning LIKE :searchWord"+
                                    " OR exampleSentence LIKE :searchWord"+
                                    " OR otherMemo LIKE :searchWord")
    List<VocanotesEntity> loadVocanotesListBySearchWord(String searchWord);

//    @Query("SELECT * FROM NEWS ORDER BY pubDate DESC")
//    LiveData<List<VocanotesDto>> loadAllNews();
//
//    @Query("SELECT COUNT(*) FROM NEWS WHERE LINK = :link")
//    LiveData<Integer> getNews(String link);
//
//    @Query("SELECT * FROM NEWS WHERE isBookmark = 1 ORDER BY pubDate DESC")
//    LiveData<List<VocanotesDto>> loadBookmarks();
}
