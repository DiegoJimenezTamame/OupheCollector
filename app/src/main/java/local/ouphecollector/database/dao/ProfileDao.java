package local.ouphecollector.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import local.ouphecollector.models.Profile;

import java.util.List;

@Dao
public interface ProfileDao {
    @Insert
    void insert(Profile profile);

    @Update
    void update(Profile profile);

    @Delete
    void delete(Profile profile);

    @Query("SELECT * FROM profile") // Changed to "profile"
    LiveData<List<Profile>> getAllProfiles();

    @Query("SELECT * FROM profile WHERE id = :profileId") // Changed to "profile"
    Profile getProfileById(int profileId);
}