package com.example.sked;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;


@Dao
public interface MessageDao {

    @Insert
    void insert(Message tt);

    @Query("DELETE FROM message_list")
    void deleteAll();

    @Query("SELECT * from message_list")
    LiveData<List<Message>> getAllRows();

    @Query("SELECT * from message_list")
    List<Message> getAllRowsL();


}
